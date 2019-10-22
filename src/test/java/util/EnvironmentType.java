package util;

public enum EnvironmentType {

  DEV("store.demoqa.dev"),
  QA("shop.demoqa.com"),
  PROD("store.demoqa.prod");

  private final String host;
  private final String protocol = "https://";

  EnvironmentType(String host) {
    this.host = host;
  }

  public String getHost() {
    return host;
  }

  public String getProtocol() {
    return protocol;
  }
}

