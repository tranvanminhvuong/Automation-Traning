package com.nashtech.assetmanagement.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import static org.openqa.selenium.remote.CapabilityType.ACCEPT_SSL_CERTS;

public enum RemoteBrowserDriver implements IDriverSetup {
    Chrome {
        @Override
        public RemoteWebDriver getWebDriver() throws MalformedURLException {
            Capabilities options = getCapabilities();
            return new RemoteWebDriver(getGridUrl(), options);
        }

        @Override
        public ChromeOptions getCapabilities() {
            ChromeOptions chromeOptions = new ChromeOptions();

            chromeOptions.setCapability(ACCEPT_SSL_CERTS, true);
            chromeOptions.addArguments("--no-sandbox"); // linux only
            chromeOptions.addArguments("--disable-dev-shm-usage");

            HashMap<String, Object> chromeLocalStatePrefs = new HashMap<>();
            List<String> experimentalFlags = new ArrayList<>();
            experimentalFlags.add("calculate-native-win-occlusion@2");
            chromeLocalStatePrefs.put("browser.enabled_labs_experiments", experimentalFlags);
            chromeOptions.setExperimentalOption("localState", chromeLocalStatePrefs);

            // Set download directory
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("download.default_directory", System.getProperty("DOWNLOAD_DIR"));
            chromeOptions.setExperimentalOption("prefs", prefs);

            // This log type pertains to log from the browser
            LoggingPreferences logPrefs = new LoggingPreferences();
            // To get network log
            logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
            // To get console log
            logPrefs.enable(LogType.BROWSER, Level.ALL);
            chromeOptions.setCapability("goog:loggingPrefs", logPrefs);
            // Output will be printed to standard error (eg. printed in the console) and to the debugger
            chromeOptions.addArguments("--enable-logging --v=1");
            return chromeOptions;
        }
    },
    IE {
        @Override
        public RemoteWebDriver getWebDriver() throws MalformedURLException {
            InternetExplorerOptions options = getCapabilities();
            return new RemoteWebDriver(getGridUrl(), options);
        }

        @Override
        public InternetExplorerOptions getCapabilities() {
            InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
            internetExplorerOptions.setCapability("EnableNativeEvents", false);
            internetExplorerOptions
                    .setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            internetExplorerOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
            return internetExplorerOptions;
        }
    };

    private static final Logger LOGGER = LogManager.getLogger(RemoteBrowserDriver.class);

    URL getGridUrl() throws MalformedURLException {
        String seleniumGridEndpoint = System.getProperty("SELENIUM_GRID_HUB_URL");
        LOGGER.info("RemoteWebDriver and selenium grid endpoint {} ", seleniumGridEndpoint);
        return new URL(seleniumGridEndpoint);
    }

}
