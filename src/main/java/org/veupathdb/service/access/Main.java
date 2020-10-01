package org.veupathdb.service.access;

import org.veupathdb.lib.container.jaxrs.config.Options;
import org.veupathdb.lib.container.jaxrs.server.ContainerResources;
import org.veupathdb.lib.container.jaxrs.server.Server;
import org.veupathdb.service.access.model.ApprovalStatusCache;
import org.veupathdb.service.access.model.Config;
import org.veupathdb.service.access.repo.ApprovalStatusRepo;
import org.veupathdb.service.access.repo.RestrictionLevelRepo;

public class Main extends Server {
  public static final Config config = new Config();

  public static void main(final String[] args) {
    var server = new Main();
    server.enableAccountDB();
    server.enableApplicationDB();
    server.start(args);
  }

  @Override
  protected ContainerResources newResourceConfig(final Options options) {
    final var out =  new Resources(options);
    out.enableAuth();
    out.enableJerseyTrace();
    out.enableCors();

    return out;
  }

  @Override
  protected Options newOptions() {
    return config;
  }

  @Override
  protected void postAcctDb() {
    try {
      ApprovalStatusRepo.Select.populateApprovalStatusCache();
      RestrictionLevelRepo.Select.populateRestrictionLevelCache();
    } catch (Throwable t) {
      throw new RuntimeException(t);
    }
  }
}
