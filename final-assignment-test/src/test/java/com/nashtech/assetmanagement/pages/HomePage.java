package com.nashtech.assetmanagement.pages;

import org.apache.commons.compress.utils.ByteUtils;
import org.openqa.selenium.By;

import java.util.ArrayList;

public class HomePage extends BasePage {
    private final By TBL_LIST_TAB_IN_HOMEPAGE = By.cssSelector("div[class='nav-left mb-5'] a button");

    private final By LBL_ACCOUNT_MENU = By.xpath("//a[text()='%s']");
    private final By BTN_NEXT = By.xpath("//a[text()='Next']");
    private final By BTN_CHANGEPASSWORD = By.xpath("//a[text()='Change Password']");
    private final By BTN_LOGOUT = By.xpath("//a[text()='Logout']");
    private final By LBL_USERNAME = By.cssSelector("#root .dropdownButton>a");

    private final By BTN_MANAGE_USER = By.xpath("//button[text()='Manage User']");
    private final By BTN_MANAGE_ASSET = By.xpath("//button[text()='Manage Asset']");
    private final By BTN_MANAGE_ASSIGNMENT = By.xpath("//button[text()='Manage Assignment']");

    public ArrayList<String> getTextOfTabInHomePage() {
        waitTabDisplayed();
        return getTextOfElements(TBL_LIST_TAB_IN_HOMEPAGE, true);
    }

    public void waitTabDisplayed() {
        waitForVisibilityOfAllElementsLocatedBy(TBL_LIST_TAB_IN_HOMEPAGE);
    }

    public void gotoManageUserPage() {
        clickElement(BTN_MANAGE_USER);
    }

    public void gotoManageAssetPage() {
        clickElement(BTN_MANAGE_ASSET);
    }

    public void gotoManageAssignmentPage() {
        clickElement(BTN_MANAGE_ASSIGNMENT);
    }


    public void clickAccountMenu(String userName) {
        clickElement(By.xpath("//a[text()='%s']".formatted(userName)));
    }

    public void selectChangePassword() {
        clickElement(BTN_CHANGEPASSWORD);
    }

    public String getUsername() {
        return getText(LBL_USERNAME);
    }

    public void selectLogout() {
        clickElement(BTN_LOGOUT);
    }

}
