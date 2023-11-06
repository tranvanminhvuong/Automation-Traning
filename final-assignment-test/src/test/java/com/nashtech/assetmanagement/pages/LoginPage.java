package com.nashtech.assetmanagement.pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {
    /**
     * ------------------ Web Elements ----------------------
     */
    private final By TXT_USERNAME = By.id("userName");
    private final By TXT_PASSWORD = By.id("password");
    private final By BTN_LOGIN = By.cssSelector("button[type='submit']");
    private final By LBL_ERROR_MESSAGE = By.xpath("//div[@class='invalid']");

    /**
     * -------------------- Page Methods ---------------------
     */
    public void loginWithValidAccount(String userName, String password) {
        inputUserName(userName);
        inputPassword(password);
        clickLoginBtn();
    }

    public void inputUserName(String username) {
        inputText(TXT_USERNAME, username);
    }

    public void inputPassword(String password) {
        inputText(TXT_PASSWORD, password);
    }

    public void clickLoginBtn() {
        clickElement(BTN_LOGIN);
    }

    public void waitUsername(String userName) {
        waitForVisibilityOfElementLocated(By.xpath("//a[text()='" + userName + "']"));
    }

    public String getErrorMessage() {
        return getText(LBL_ERROR_MESSAGE);
    }
}
