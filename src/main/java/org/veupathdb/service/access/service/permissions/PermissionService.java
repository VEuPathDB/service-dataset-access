package org.veupathdb.service.access.service.permissions;

import java.util.List;
import java.util.Map;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.WebApplicationException;
import org.glassfish.jersey.server.ContainerRequest;
import org.gusdb.fgputil.Wrapper;
import org.veupathdb.lib.container.jaxrs.model.User;
import org.veupathdb.service.access.controller.Util;
import org.veupathdb.service.access.generated.model.ActionList;
import org.veupathdb.service.access.generated.model.ActionListImpl;
import org.veupathdb.service.access.generated.model.DatasetPermissionEntry;
import org.veupathdb.service.access.generated.model.DatasetPermissionEntryImpl;
import org.veupathdb.service.access.generated.model.DatasetPermissionLevel;
import org.veupathdb.service.access.generated.model.PermissionsGetResponse;
import org.veupathdb.service.access.generated.model.PermissionsGetResponseImpl;
import org.veupathdb.service.access.model.DatasetProps;
import org.veupathdb.service.access.model.UserDatasetIsaStudies;
import org.veupathdb.service.access.service.dataset.DatasetRepo;
import org.veupathdb.service.access.service.provider.ProviderRepo;
import org.veupathdb.service.access.service.staff.StaffRepo;
import org.veupathdb.service.access.service.user.EndUserRepo;

public class PermissionService
{
  private static PermissionService instance;

  public PermissionsGetResponse getUserPermissions(ContainerRequest request) {
    return getUserPermissions(Util.requireUser(request));
  }

  public PermissionsGetResponse getUserPermissions(User user) {
    var out = new PermissionsGetResponseImpl();

    try {
      Wrapper<Boolean> grantAll = new Wrapper<>(false);

      // check if current user is staff and set outgoing perms accordingly
      StaffRepo.Select.byUserId(user.getUserID())
        .ifPresent(s -> {
          // all staff get access to all studies
          grantAll.set(true);
          // assign staff role
          out.setIsStaff(true);
          if (s.isOwner()) {
            // staff/owner is essentially a superuser
            out.setIsOwner(true);
          }
        });

      // level of access assigned to each dataset
      var datasetProps = DatasetRepo.Select.getDatasetProps();

      // if datasetId is present, then user is provider; boolean indicates isManager
      Map<String,Boolean> providerInfoMap = ProviderRepo.Select.datasets(user.getUserID());

      // list of datasetIds user has approved access for
      List<String> approvedStudiesList = EndUserRepo.Select.datasets(user.getUserID());

      // assign specific permissions on each dataset for this user
      PermissionMap datasetPerms = getPermissionMap(grantAll.get(), datasetProps, providerInfoMap, approvedStudiesList);

      // supplement official studies with studies from user datasets
      datasetPerms.putAll(UserDatasetIsaStudies.getUserDatasetPermissions(user.getUserID()));

      // set permission map on permissions object
      out.setPerDataset(datasetPerms);

      return out;
    }
    catch (WebApplicationException e) {
      throw e;
    }
    catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  private static PermissionMap getPermissionMap(boolean grantToAllDatasets,
      List<DatasetProps> datasetProps,
      Map<String, Boolean> providerInfoMap,
      List<String> approvedDatasetsList) {
    var permissionMap = new PermissionMap();
    for (DatasetProps dataset : datasetProps) {

      DatasetPermissionEntry permEntry = new DatasetPermissionEntryImpl();

      permEntry.setStudyId(dataset.studyId);
      permEntry.setSha1Hash(dataset.sha1hash);
      permEntry.setIsUserStudy(false);

      boolean isProvider = providerInfoMap.containsKey(dataset.id);

      // set permission type for this dataset
      permEntry.setType(isProvider ?
          DatasetPermissionLevel.PROVIDER :
          DatasetPermissionLevel.ENDUSER);

      // is manager if isProvider and provider info map has value true
      permEntry.setIsManager(isProvider && providerInfoMap.get(dataset.id));

      boolean accessGranted = approvedDatasetsList.contains(dataset.id);
      boolean grantAllPermsForThisDataset = grantToAllDatasets || isProvider || accessGranted;

      ActionList actions = new ActionListImpl();

      // all users have access to the study page of all studies
      actions.setStudyMetadata(true);

      // controls search, visualizations, small results
      boolean allowBasicAccess = grantAllPermsForThisDataset || dataset.accessLevel.allowsBasicAccess();
      actions.setSubsetting(allowBasicAccess);
      actions.setVisualizations(allowBasicAccess);
      actions.setResultsFirstPage(allowBasicAccess);

      // controls access to full dataset, downloads
      boolean allowFullAccess = grantAllPermsForThisDataset || dataset.accessLevel.allowsFullAccess();
      actions.setResultsAll(allowFullAccess);

      permEntry.setActionAuthorization(actions);

      // add to map
      permissionMap.put(dataset.id, permEntry);
    }

    return permissionMap;
  }

  public static PermissionService getInstance() {
    if (instance == null)
      instance = new PermissionService();

    return instance;
  }
}
