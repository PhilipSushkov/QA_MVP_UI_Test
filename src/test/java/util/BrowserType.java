package util;

public enum BrowserType {

  CHROME("chrome", "68"),
  FIREFOX("firefox", "52"),
  INTERNET_EXPLORER("internet explorer", "11");

  private final String name;
  private final String latestVersion;

  BrowserType(String name, String latestVersion) {
    this.name = name;
    this.latestVersion = latestVersion;
  }

  public String getName() {
    return name;
  }

  public String getLatestVersion() {
    return latestVersion;
  }
}

