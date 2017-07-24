package util;

public enum EnvironmentType {

  //DEVELOP("chicagotest.s1.q4web.dev/admin/"),
  //DEVELOP("fbreverseproxy.s1.q4web.dev/admin/"),
  DEVELOP("ensco.s1.q4web.newtest/admin/"),
  //DEVELOP("chicagotest.s1.q4web.dev/admin/"),
  //DEVELOP("enscoreverseproxy.s1.q4web.dev/admin/"),
  //BETA("chicagotest.s1.q4web.newtest/admin/"), //chicagotest url
  BETA("ensco.s1.q4web.newtest/admin"), //facebookrelease url
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

