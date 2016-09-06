package util;


public enum EnvironmentType {

  DEVELOP("beta.q4desktop.com"),
  BETA("beta.q4desktop.com"),
  PRODUCTION("");

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

