package util;

public enum EnvironmentType {

  DEVELOP("chicagotest.s1.q4web.dev/admin/"),
  BETA("chicagotest.s1.q4web.newtest/admin/"), //chicagotest url
  //BETA("chicagotest.s1.q4web.release/admin/"), //chicagotest url
  PRODUCTION("chicagotest.s3.q4web.com/admin/");

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

