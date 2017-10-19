package util;

public enum EnvironmentType {

  //BETA("facebook.s1.q4web.newtest/"), //chicagotest url
  //BETA("qachicagotest.s1.q4web.newtest/admin/"), //facebookrelease url
  //DEVELOP("fbreverseproxy.s1.q4web.dev/admin/"),
  //DEVELOP("ensco.s1.q4web.newtest/admin/"),
  //DEVELOP("qaensco.s1.q4web.newtest/admin/"),
  DEVELOP("facebook.q4web.newtest/login.aspx"),
  //DEVELOP("chicagotest.s3.q4web.newtest/admin/"),
  //DEVELOP("enscoreverseproxy.s1.q4web.dev/admin/"),
  //BETA("chicagotest.s1.q4web.newtest/admin/"), //chicagotest url
  BETA("ensco.s1.q4web.newtest/admin"), //facebookrelease url
  PRODUCTION("chicagotest.s3.q4web.com/admin/");
  //DEVELOP("admin-dev.q4inc.com/");

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

