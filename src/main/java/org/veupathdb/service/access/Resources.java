package org.veupathdb.service.access;

import org.veupathdb.lib.container.jaxrs.config.Options;
import org.veupathdb.lib.container.jaxrs.server.ContainerResources;
import org.veupathdb.service.access.controller.ProviderController;
import org.veupathdb.service.access.controller.StaffController;

/**
 * Service Resource Registration.
 *
 * This is where all the individual service specific resources and middleware
 * should be registered.
 */
public class Resources extends ContainerResources {
  public Resources(Options opts) {
    super(opts);
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
      StaffController.class
    };
  }
}
