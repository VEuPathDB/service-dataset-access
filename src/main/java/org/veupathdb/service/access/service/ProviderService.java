package org.veupathdb.service.access.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.*;
import javax.ws.rs.core.Request;

import org.gusdb.fgputil.accountdb.UserProfile;
import org.veupathdb.lib.container.jaxrs.providers.UserProvider;
import org.veupathdb.service.access.generated.model.*;
import org.veupathdb.service.access.model.PartialProviderRow;
import org.veupathdb.service.access.model.ProviderRow;
import org.veupathdb.service.access.repo.Providers;
import org.veupathdb.service.access.util.Keys;

public class ProviderService
{
  public static int createProvider(DatasetProviderCreateRequest body) {
    final var row = new PartialProviderRow();
    row.setDatasetId(body.getDatasetId());
    row.setManager(body.getIsManager());
    row.setUserId(body.getUserId());

    try {
      return Providers.Insert.newProvider(row);
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
  public static DatasetProviderList getProviderList(
    final String datasetId,
    final int limit,
    final int offset
  ) {
    try {
      final var total = Providers.Select.countByDataset(datasetId);
      final var rows  = Providers.Select.byDataset(datasetId, limit, offset);
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
  public static ProviderRow requireProviderById(int providerId) {
    try {
      return Providers.Select.byId(providerId)
        .orElseThrow(NotFoundException::new);
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
  public static ProviderRow requireProviderByUserId(long userId) {
    try {
      return Providers.Select.byUserId(userId)
        .orElseThrow(NotFoundException::new);
    } catch (WebApplicationException e) {
      throw e;
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public static boolean userIsManager(Request req) {
    return userIsManager(UserProvider.lookupUser(req)
      .map(UserProfile::getUserId)
      .orElseThrow(InternalServerErrorException::new));
  }

  /**
   * Looks up the user provider record and returns whether or not the provider
   * with the given <code>userId</code> is marked as a manager.
   * <p>
   * If provider exists with the given <code>userId</code>, a 404 exception
   * will be thrown via {@link #requireProviderByUserId(long)}.
   */
  public static boolean userIsManager(long userId) {
    return requireProviderByUserId(userId).isManager();
  }

  public static void deleteProvider(int providerId) {
    try {
      Providers.Delete.byId(providerId);
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public static void updateProvider(ProviderRow row) {
    try {
      Providers.Update.isManagerById(row);
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public static void validatePatch(List<DatasetProviderPatch> items) {
    // If there is nothing in the patch, it's a bad request.
    if (items.isEmpty())
      throw new BadRequestException();

    // not allowed to do more than one thing
    if (items.size() > 1)
      throw new ForbiddenException();

    var item = items.get(0);

    // only allow replace ops
    if (!"replace".equals(item.getOp()))
      throw new ForbiddenException();

    // only allow modifying the isManager property
    if (!("/" + Keys.Json.KEY_IS_MANAGER).equals(item.getPath()))
      throw new ForbiddenException();
  }

  private static DatasetProviderList list2Providers(
    final List < ProviderRow > rows,
    final int offset,
    final int total
  ) {
    var out = new DatasetProviderListImpl();

    out.setRows(rows.size());
    out.setOffset(offset);
    out.setTotal(total);
    out.setData(rows.stream()
      .map(ProviderService::row2Provider)
      .collect(Collectors.toList()));

    return out;
  }

  private static DatasetProvider row2Provider(ProviderRow row) {
    var user = new UserDetailsImpl();
    user.setUserId(row.getUserId());
    user.setFirstName(row.getFirstName());
    user.setLastName(row.getLastName());
    user.setOrganization(row.getOrganization());

    var out = new DatasetProviderImpl();
    out.setDatasetId(row.getDatasetId());
    out.setIsManager(row.isManager());
    out.setProviderId(row.getProviderId());
    out.setUser(user);

    return out;
  }
}
