package org.veupathdb.service.access;

import org.gusdb.fgputil.db.slowquery.QueryLogConfig;
import org.gusdb.fgputil.db.slowquery.QueryLogger;
import org.veupathdb.lib.container.jaxrs.config.Options;
import org.veupathdb.lib.container.jaxrs.server.ContainerResources;
import org.veupathdb.lib.container.jaxrs.server.Server;
import org.veupathdb.lib.prom.PrometheusJVM;
import org.veupathdb.service.access.model.Config;

public class Main extends Server {

  public static void main(String[] args) {
    new Main().start(args);
  }

  public static final Config config = new Config();

  public Main() {
    QueryLogger.initialize(new QLF(){});
    PrometheusJVM.enable();
  }

  @Override
  protected Options newOptions() {
    return config;
  }

  @Override
  protected ContainerResources newResourceConfig(final Options options) {
    return new Resources(options);
  }

  public static class QLF implements QueryLogConfig {
    public double getBaseline() {
      return 0.05D;
    }

    public double getSlow() {
      return 1.0D;
    }

    public boolean isIgnoredSlow(String sql) {
      return false;
    }

    public boolean isIgnoredBaseline(String sql) {
      return false;
    }
  }
}
