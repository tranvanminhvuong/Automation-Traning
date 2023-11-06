package com.nashtech.assetmanagement.pages;

import org.openqa.selenium.By;

public class ChangePasswordAlertPage extends BasePage {
    private final By BTN_CONFIRM_CHANGE_PASSWORD = By.id("confirm-change-password-button");
    private final By LBL_ALERT_MESSAGE = By.cssSelector(".modal-body :first-child.text-center");

    public String getAlertChangePasswordSuccessfully() {
        return getText(LBL_ALERT_MESSAGE);
    }

    public void clickConfirmChangePassword() {
        clickElement(BTN_CONFIRM_CHANGE_PASSWORD);
    }
}
