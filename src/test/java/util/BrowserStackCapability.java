package util;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BrowserStackCapability {

    private Platform platformType;
    private BrowserType browserType;
    private String device;

    public BrowserStackCapability(Platform platformType, BrowserType browserType, String device) {
        this.platformType = platformType;
        this.browserType = browserType;
        this.device = device;
    }

    public Platform getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Platform platformType) {
        this.platformType = platformType;
    }

    public BrowserType getBrowserType() {
        return browserType;
    }

    public void setBrowserType(BrowserType browserType) {
        this.browserType = browserType;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public DesiredCapabilities toDesiredCapability() {
        DesiredCapabilities desiredCapabilities =  new DesiredCapabilities();
        if (browserType != null) {
            desiredCapabilities.setBrowserName(browserType.name());
            desiredCapabilities.setVersion(browserType.getLatestVersion());
        }

        if (platformType != null) {
            desiredCapabilities.setPlatform(platformType);
        }

        if (device != null) {
            desiredCapabilities.setCapability("device", device);
            desiredCapabilities.setCapability("deviceOrientation", "portrait");
        }

        return desiredCapabilities;
    }
}
