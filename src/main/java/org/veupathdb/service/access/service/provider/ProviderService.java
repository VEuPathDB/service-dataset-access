package org.veupathdb.service.access.service.provider;

import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.*;
import javax.ws.rs.core.Request;

import org.apache.logging.log4j.Logger;
import org.gusdb.fgputil.accountdb.UserProfile;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.lib.container.jaxrs.providers.UserProvider;
import org.veupathdb.service.access.generated.model.*;
import org.veupathdb.service.access.model.PartialProviderRow;
import org.veupathdb.service.access.model.ProviderRow;
import org.veupathdb.service.access.service.dataset.DatasetRepo;
import org.veupathdb.service.access.service.email.EmailService;
import org.veupathdb.service.access.service.staff.StaffService;
import org.veupathdb.service.access.util.Keys;

import static org.veupathdb.service.access.service.staff.StaffService.userIsOwner;

public class ProviderService
{
  static ProviderService instance = new ProviderService();

  ProviderService() {
  }

  private static final Logger log = LogProvider.logger(ProviderService.class);

  /**
   * Creates a new Provider record from the given request body.
   *
   * @return the ID of the newly created record.
   */
  public DatasetProviderCreateResponse createNewProvider(
    final DatasetProviderCreateRequest body,
    final UserProfile user
  ) {
    log.trace("ProviderService#createNewProvider(DatasetProviderCreateRequest)");

    // To add a new provider, a user must be a site owner or a manager for the
    // dataset.
    if (!userIsOwner(user.getUserId()) && !userIsManager(user.getUserId(), body.getDatasetId()))
      throw new ForbiddenException();

    try {
      final var out = new DatasetProviderCreateResponseImpl();

      return switch (ProviderValidation.getInstance().validateCreateRequest(body)) {
        case SEND_EMAIL -> {
          EmailService.getInstance().sendProviderRegistrationEmail(
            body.getEmail(),
            DatasetRepo.Select.getInstance().selectDataset(body.getDatasetId()).orElseThrow()
          );

          out.setCreated(false);

          yield out;
        }

        case CREATE_PROVIDER -> {
          final var row = new PartialProviderRow();
          row.setDatasetId(body.getDatasetId());
          row.setManager(body.getIsManager());
          row.setUserId(body.getUserId());

          out.setCreated(true);
          out.setProviderId(ProviderRepo.Insert.newProvider(row));

          yield out;
        }
      };
    } catch (WebApplicationException e) {
      throw e;
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  /**
   * Return a client friendly listing of all providers attached to a given
   * <code>datasetId</code>.
   * <p>
   * <b>NOTE</b>: This method does not verify that the data returned should be
   * available for any particular user.  Verify the user has permission to view
   * this data separately.
   */
  public DatasetProviderList getDatasetProviderList(
    final String datasetId,
    final int limit,
    final int offset,
    final UserProfile currentUser
  ) {
    log.trace("ProviderService#getDatasetProviderList(String, int, int, UserProfile)");

    try {
      var allowed = false;
      var rows    = ProviderRepo.Select.byDataset(datasetId, limit, offset);

      // Determine if the user is a manager for the dataset.
      for (var pro : rows) {
        if (pro.getUserId() == currentUser.getUserId()) {
          allowed = true;
          break;
        }
      }

      if (StaffService.userIsStaff(currentUser.getUserId()))
        allowed = true;

      if (!allowed)
        throw new ForbiddenException();

      final var total = ProviderRepo.Select.countByDataset(datasetId);

      return list2Providers(rows, offset, total);
    } catch (WebApplicationException e) {
      throw e;
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  /**
   * Locates a provider with the given <code>providerId</code> or throws a 404
   * exception.
   */
  public ProviderRow mustGetProviderById(final int providerId) {
    log.trace("ProviderService#requireProviderById(int)");

    try {
      return ProviderRepo.Select.byId(providerId).orElseThrow(NotFoundException::new);
    } catch (WebApplicationException e) {
      throw e;
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public List<ProviderRow> getProviderByUserId(final long userId) {
    log.trace("ProviderService#getProviderByUserId(long)");

    try {
      return ProviderRepo.Select.byUserId(userId);
    } catch (WebApplicationException e) {
      throw e;
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  /**
   * Locates a provider with the given <code>userId</code> or throws a 404
   * exception.
   */
  public List<ProviderRow> mustGetProviderByUserId(final long userId) {
    log.trace("ProviderService#mustGetProviderByUserId(long)");

    final var out = lookupProviderByUserId(userId);

    if (out.isEmpty())
      throw new ForbiddenException();

    return out;
  }

  public boolean isUserManager(final Request req, final String datasetId) {
    log.trace("ProviderService#userIsManager(Request, String)");

    return isUserManager(UserProvider.lookupUser(req)
      .map(UserProfile::getUserId)
      .orElseThrow(InternalServerErrorException::new), datasetId);
  }

  public boolean isUserManager(final long userId, final String datasetId) {
    log.trace("ProviderService#isUserManager(long, String)");

    final var ds = datasetId.toUpperCase();
    return lookupProviderByUserId(userId)
      .stream()
      .filter(row -> ds.equals(row.getDatasetId().toUpperCase()))
      .anyMatch(PartialProviderRow::isManager);
  }

  public boolean isUserProvider(final long userId, final String datasetId) {
    log.trace("ProviderService#isUserProvider");

    final var ds = datasetId.toUpperCase();
    return lookupProviderByUserId(userId)
      .stream()
      .anyMatch(row -> ds.equals(row.getDatasetId().toUpperCase()));
  }

  public void deleteProviderRecord(final int providerId) {
    log.trace("ProviderService#deleteProvider(int)");

    try {
      ProviderRepo.Delete.byId(providerId);
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public void updateProviderRecord(final ProviderRow row) {
    log.trace("ProviderService#updateProvider(row)");

    try {
      ProviderRepo.Update.isManagerById(row);
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public void validatePatchRequest(final List<DatasetProviderPatch> items) {
    log.trace("ProviderService#validatePatch(List)");

    // If there is nothing in the patch, it's a bad request.
    if (items == null || items.isEmpty())
      throw new BadRequestException();

    // not allowed to do more than one thing
    if (items.size() > 1)
      throw new ForbiddenException();

    // WARNING: This cast mess is due to a bug in the JaxRS generator, the type
    // it actually passes up is not the declared type, but a list of linked hash
    // maps instead.
    final var item = items.get(0);

    // only allow replace ops
    if (!"replace".equals(item.getOp()))
      throw new ForbiddenException();

    // only allow modifying the isManager property
    if (!("/" + Keys.Json.KEY_IS_MANAGER).equals(item.getPath()))
      throw new ForbiddenException();
  }

  private DatasetProviderList listToProviders(
    final List<ProviderRow> rows,
    final int offset,
    final int total
  ) {
    log.trace("ProviderService#list2Providers(List, int, int)");

    var out = new DatasetProviderListImpl();

    out.setRows(rows.size());
    out.setOffset(offset);
    out.setTotal(total);
    out.setData(rows.stream()
      .map(ProviderUtil.getInstance()::internalToExternal)
      .collect(Collectors.toList()));

    return out;
  }

  public static ProviderService getInstance() {
    return instance;
  }

  public static DatasetProviderList getProviderList(
    final String datasetId,
    final int limit,
    final int offset,
    final UserProfile user
  ) {
    return getInstance().getDatasetProviderList(datasetId, limit, offset, user);
  }

  public static ProviderRow requireProviderById(final int providerId) {
    return getInstance().mustGetProviderById(providerId);
  }

  public static List<ProviderRow> lookupProviderByUserId(final long userId) {
    return getInstance().getProviderByUserId(userId);
  }

  public static boolean userIsManager(final Request req, final String datasetId) {
    return getInstance().isUserManager(req, datasetId);
  }

  public static boolean userIsManager(final long userId, final String datasetId) {
    return getInstance().isUserManager(userId, datasetId);
  }

  public static void deleteProvider(final int providerId) {
    getInstance().deleteProviderRecord(providerId);
  }

  public static void updateProvider(final ProviderRow row) {
    getInstance().updateProviderRecord(row);
  }

  private static DatasetProviderList list2Providers(
    final List<ProviderRow> rows,
    final int offset,
    final int total
  ) {
    return getInstance().listToProviders(rows, offset, total);
  }
}
