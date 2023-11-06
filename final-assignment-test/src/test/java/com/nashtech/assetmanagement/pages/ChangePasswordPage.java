package com.nashtech.assetmanagement.pages;

import org.openqa.selenium.By;

public class ChangePasswordPage extends BasePage {
    private final By TXT_OLDPASSWORD = By.id("oldPassword");
    private final By TXT_NEWPASSWORD = By.id("newPassword");
    private final By BTN_SAVE = By.xpath("//button[text()='Save']");

    public void inputOldPassword(String oldPassword) {
        inputText(TXT_OLDPASSWORD, oldPassword);
    }

    public void inputNewPassword(String newPassword) {
        inputText(TXT_NEWPASSWORD, newPassword);
    }

    public void clickSave() {
        clickElement(BTN_SAVE);
    }
}
