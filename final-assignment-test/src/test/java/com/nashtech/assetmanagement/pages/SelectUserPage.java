package com.nashtech.assetmanagement.pages;

import org.openqa.selenium.By;

public class SelectUserPage extends BasePage{
    private final By TXT_SEARCH_BOX = By.cssSelector(".input-group>input");
    private final By RDB_FIRST_RECORD = By.cssSelector("tbody>tr:first-child>td:nth-child(1)");
    private final By LBL_STAFF_CODE = By.cssSelector("tbody>tr:first-child>td:nth-child(2)");
    private final By LBL_FULLNAME = By.cssSelector("tbody>tr:first-child>td:nth-child(3)");
    private final By LBL_TYPE = By.cssSelector("tbody>tr:first-child>td:nth-child(4)");
    private final By BTN_SEARCH = By.cssSelector(".input-group span");
    private final By BTN_SAVE = By.xpath("//button[text()='Save']");
    public void searchKey(String key){
        inputText(TXT_SEARCH_BOX, key);
        clickElement(BTN_SEARCH);
    }
    public String getStaffCodeAfterSearch(){
        return getText(LBL_STAFF_CODE);
    }
    public String getFullNameAfterSearch(){
        return getText(LBL_FULLNAME);
    }
    public String getTypeAfterSearch(){
        return getText(LBL_TYPE);
    }

    public void selectFirstRecord(String text) {
        waitForElementToBeClickable(By.id(""+text+""));
        clickElement(RDB_FIRST_RECORD);
    }
    public void clickSave(){
        clickElement(BTN_SAVE);
    }

}
