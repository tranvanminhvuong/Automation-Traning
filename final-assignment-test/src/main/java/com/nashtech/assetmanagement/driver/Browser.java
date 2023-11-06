package com.nashtech.assetmanagement.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

public class Browser {
    private static final Logger LOGGER = LogManager.getLogger(Browser.class);
    private static final ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();

    private Browser() {
    }

    public static void initBrowser(LocalBrowserDriver browserDriver) throws MalformedURLException {
        if (driver.get() == null) {
            LOGGER.info("Init a new local browser driver instance...");
            setDriver(browserDriver.getWebDriver());
        }
    }

    public static void initBrowser(RemoteBrowserDriver remoteBrowserDriver) throws MalformedURLException {
        if (driver.get() == null) {
            LOGGER.info("Init a new remote browser driver instance...");
            setDriver(remoteBrowserDriver.getWebDriver());
        }
    }

    public static void initBrowser(String driverMode, String browserType) throws MalformedURLException {
        if (driverMode.equalsIgnoreCase("LOCAL")) {
            initBrowser(LocalBrowserDriver.valueOf(browserType));
        } else {
            Browser.initBrowser(RemoteBrowserDriver.valueOf(browserType));
        }
        getDriver().manage().window().maximize();
    }

    public static RemoteWebDriver getDriver() {
        LOGGER.info("getWebDriver {}", driver.get());
        return driver.get();
    }

    public static void setDriver(RemoteWebDriver remoteDriverThreadLocal) {
        LOGGER.info("Set driver");
        driver.set(remoteDriverThreadLocal);
    }

    public static void remove() {
        if (driver.get() != null) {
            LOGGER.info("Remove driver {}", driver.get());
            driver.remove();
        }
    }

}
