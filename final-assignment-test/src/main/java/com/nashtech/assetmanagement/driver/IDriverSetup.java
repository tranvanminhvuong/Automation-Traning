package com.nashtech.assetmanagement.driver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;

interface IDriverSetup {
    RemoteWebDriver getWebDriver() throws MalformedURLException;

    Capabilities getCapabilities();
}