package org.veupathdb.service.access.service.permissions;

import java.util.HashMap;

import org.veupathdb.service.access.generated.model.DatasetPermissionEntry;
import org.veupathdb.service.access.generated.model.PermissionsGetResponse;

public class PermissionMap
  extends HashMap<String, DatasetPermissionEntry>
  implements PermissionsGetResponse.PerDatasetType
{
}
