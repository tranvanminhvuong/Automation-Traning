package com.nashtech.assetmanagement.pages;

import org.openqa.selenium.By;

public class NavigationPage extends BasePage {

    /**
     * ------------------ Web Elements ----------------------
     */
    By LBL_USERNAME = By.xpath("//div[@class='dropdown']/a/a");
    By logOutBtn = By.id("submit");

    /**
     * -------------------- Page Methods ---------------------
     */

    public String getUserName() {
        return getText(LBL_USERNAME);
    }
}
