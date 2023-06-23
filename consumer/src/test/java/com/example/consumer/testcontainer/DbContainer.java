package com.example.consumer.testcontainer;

import org.testcontainers.containers.PostgreSQLContainer;
public final class DbContainer extends PostgreSQLContainer<DbContainer> {

  private static final int DB_PORT = 5432;

  private DbContainer() {
    super("postgres:14.5-alpine");
    withUsername("test");
    withPassword("test");
    withExposedPorts(DB_PORT);
  }

  public static DbContainer getInstance() {
    return LazyHolder.INSTANCE;
  }

  private static class LazyHolder {
    private static final DbContainer INSTANCE = new DbContainer();

  }

  @Override
  public void stop() {

  }

}
