package com.nashtech.assetmanagement.pages;

import org.openqa.selenium.By;

public class LogoutPage extends BasePage {
    private final By BTN_LOGOUT = By.xpath("//button[text()='Log out']");
    private final By BTN_CANCEL = By.xpath("//button[text()='Cancel']");

    public void confirmLogout() {
        clickElement(BTN_LOGOUT);
    }
}
