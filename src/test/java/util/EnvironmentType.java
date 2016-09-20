package util;

public enum EnvironmentType {

  DEVELOP("goldcorp.s1.q4web.dev/admin/"),
  BETA("aestest.s1.q4web.newtest/admin/"), //AES url
  //BETA("kinross.s1.q4web.newtest/admin/"), //kinross url
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

