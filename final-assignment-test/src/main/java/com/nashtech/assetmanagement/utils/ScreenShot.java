package com.nashtech.assetmanagement.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ScreenShot {
    public static Path takeScreenShot(String methodName, WebDriver driver, String filePath) throws IOException {
        File sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destinationPath = new File(filePath + methodName + ".png");
        FileUtils.copyFile(sourcePath, destinationPath);
        return destinationPath.toPath();
    }
}
