package com.nashtech.assetmanagement.tests;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.service.ExtentTestManager;
import com.aventstack.extentreports.testng.listener.ExtentITestListenerClassAdapter;
import com.nashtech.assetmanagement.driver.Browser;
import com.nashtech.assetmanagement.pages.BasePage;
import com.nashtech.assetmanagement.utils.PropertiesFileUtil;
import com.nashtech.assetmanagement.utils.ScreenShot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

@Listeners(ExtentITestListenerClassAdapter.class)
public class BaseTest {
    private static final Logger LOGGER = LogManager.getLogger(BaseTest.class);
    private static final LocalDateTime START_TIME = LocalDateTime.now();

    @BeforeSuite
    public void beforeSuite() throws IOException {
        Properties properties = PropertiesFileUtil.loadPropertiesFromFile(System.getProperty("env.properties"));
        PropertiesFileUtil.appendSystemProperties(properties);
    }

    @BeforeMethod
    @Parameters({"browser"})
    public void beforeMethod(@Optional("") String browser) throws MalformedURLException {
        String browserType = (browser != null && !browser.isEmpty()) ? browser : System.getProperty("BROWSER_TYPE");
        Browser.initBrowser(System.getProperty("DRIVER_MODE"), browserType);
    }

    @AfterMethod
    public void afterMethod(ITestResult iTestResult, Method method) throws IOException {
        String testcaseName = method.getName();
        switch (iTestResult.getStatus()) {
            case ITestResult.FAILURE:
                String reportDir = System.getProperty("user.dir") + System.getProperty("REPORT_DIR");
                String screenshotsDir = reportDir + "/" + START_TIME.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm"))
                        + "/";
                LOGGER.info("iTestResult.getName()" + iTestResult.getName());
                String screenshotName = iTestResult.getName().replaceAll(" ", "_");
                Path desDirPath = ScreenShot.takeScreenShot(screenshotName, Browser.getDriver(), screenshotsDir);

                Path absoluteReportDir = Paths.get(reportDir);
                String relativePath = absoluteReportDir.relativize(desDirPath).toString();
                ExtentTestManager.getTest(iTestResult).fail("ITestResult.FAILURE, event afterMethod",
                        MediaEntityBuilder.createScreenCaptureFromPath("../../Reports/" + relativePath).build());
                // MediaEntityBuilder.createScreenCaptureFromPath("file://D|/loginSuccessfullyWithValidAccount.png").build());
                break;
            case ITestResult.SKIP:
                ExtentTestManager.getTest(iTestResult).skip("ITestResult.SKIP, event afterMethod");
                break;
            case ITestResult.CREATED:
                ExtentTestManager.getTest(iTestResult).fail("Precondition is FAILED at beforeClass");
                LOGGER.info(testcaseName + " - Precondition is FAILED at beforeClass");
                break;
            default:
                ExtentTestManager.getTest(iTestResult).pass("default, event afterMethod");
        }
        BasePage.quit();
    }
}
