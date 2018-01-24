package util;

public enum EnvironmentType {

  DEVELOP("fiesta.q4web.newtest/login.aspx"),
  BETA("ensco.s1.q4web.newtest/admin"), //facebookrelease url
  PRODUCTION("chicagotest.s3.q4preview.com/admin/");

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

