package util;

public enum EnvironmentType {

  DEVELOP("aestest.s1.q4web.newtest/admin/"),
  BETA("aestest.s1.q4web.newtest/admin/"), //AES url
  //BETA("goldcorptest.s1.q4web.newtest/admin/"), //goldcorp url
  //BETA("kinross.s1.q4web.newtest/admin/"), //kinross url
  //BETA("intactfinancial.s1.q4web.newtest/admin/"), //intact financial url
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

