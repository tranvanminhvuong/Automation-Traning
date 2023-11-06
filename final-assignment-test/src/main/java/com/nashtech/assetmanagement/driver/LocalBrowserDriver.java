package com.nashtech.assetmanagement.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public enum LocalBrowserDriver implements IDriverSetup {
    Chrome {
        @Override
        public RemoteWebDriver getWebDriver() {
            LOGGER.info("Init a new local Chrome driver instance...");
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver(getCapabilities());

        }

        @Override
        public ChromeOptions getCapabilities() {
            ChromeOptions chromeOptions = new ChromeOptions();
            // Your connection is private --> NET::ERR_CERT_DATE_INVALID
            chromeOptions.addArguments("--ignore-certificate-errors");
            return chromeOptions;
        }
    },

    IE {
        @Override
        public RemoteWebDriver getWebDriver() {
            LOGGER.info("Init a new local IE driver instance... ");
            WebDriverManager.iedriver().setup();
            return new InternetExplorerDriver(getCapabilities());
        }

        @Override
        public InternetExplorerOptions getCapabilities() {
            return new InternetExplorerOptions();
        }
    },
    Edge {
        @Override
        public RemoteWebDriver getWebDriver() {
            LOGGER.info("Init a new local Edge driver instance... ");
            WebDriverManager.iedriver().setup();
            return new EdgeDriver(getCapabilities());
        }

        @Override
        public EdgeOptions getCapabilities() {
            return new EdgeOptions();
        }
    },
    Firefox {
        @Override
        public RemoteWebDriver getWebDriver() {
            LOGGER.info("Init a new local firefox driver instance... ");
            WebDriverManager.firefoxdriver().setup();
            return new FirefoxDriver(getCapabilities());
        }

        @Override
        public FirefoxOptions getCapabilities() {
            return new FirefoxOptions();
        }
    };

    private static final Logger LOGGER = LogManager.getLogger(LocalBrowserDriver.class);
}
