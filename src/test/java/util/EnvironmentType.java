package util;

public enum EnvironmentType {

  //BETA("facebook.s1.q4web.newtest/"), //chicagotest url
  //BETA("qachicagotest.s1.q4web.newtest/admin/"), //facebookrelease url
<<<<<<< HEAD
  DEVELOP("qaensco.s1.q4web.newtest/admin/"),
=======
>>>>>>> cb763cc3b170da40f27603681f40b214cf162211
  //DEVELOP("ensco.s1.q4web.newtest/admin/"),
  DEVELOP("chicagotest.s1.q4web.newtest/admin/"),
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

