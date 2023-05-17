package org.veupathdb.service.access;

import org.veupathdb.lib.container.jaxrs.config.Options;
import org.veupathdb.lib.container.jaxrs.server.ContainerResources;
import org.veupathdb.lib.container.jaxrs.utils.db.DbManager;
import org.veupathdb.service.access.controller.EndUserController;
import org.veupathdb.service.access.controller.HistoryController;
import org.veupathdb.service.access.controller.PermissionController;
import org.veupathdb.service.access.controller.ProviderController;
import org.veupathdb.service.access.controller.StaffController;
import org.veupathdb.service.access.repo.ApprovalStatusRepo;
import org.veupathdb.service.access.repo.RestrictionLevelRepo;

/**
 * Service Resource Registration.
 *
 * This is where all the individual service specific resources and middleware
 * should be registered.
 */
public class Resources extends ContainerResources {

  public Resources(Options opts) {
    super(opts);

    // initialize required DBs
    DbManager.initUserDatabase(opts);
    DbManager.initAccountDatabase(opts);
    DbManager.initApplicationDatabase(opts);

    // enable required features
    enableAuth();
    enableJerseyTrace();
    enableCors();

    // load cached data
    try {
      ApprovalStatusRepo.Select.populateApprovalStatusCache();
      RestrictionLevelRepo.Select.populateRestrictionLevelCache();
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Returns an array of JaxRS endpoints, providers, and contexts.
   *
   * Entries in the array can be either classes or instances.
   */
  @Override
  protected Object[] resources() {
    return new Object[] {
      ProviderController.class,
      StaffController.class,
      EndUserController.class,
      PermissionController.class,
      HistoryController.class,
    };
  }
}
