package org.veupathdb.service.access;

import org.veupathdb.lib.container.jaxrs.config.Options;
import org.veupathdb.lib.container.jaxrs.server.ContainerResources;
import org.veupathdb.lib.container.jaxrs.server.Server;

public class Main extends Server {
  public static void main(String[] args) {
    var server = new Main();
    server.enableAccountDB();
    server.enableApplicationDB();
    server.enableApplicationDB();
    server.start(args);
  }

  @Override
  protected ContainerResources newResourceConfig(Options options) {
    final var out =  new Resources(options);
    out.enableAuth();
    out.enableJerseyTrace();
    out.enableCors();

    return out;
  }
}
