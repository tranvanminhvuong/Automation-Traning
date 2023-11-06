package com.nashtech.assetmanagement.pages;

import org.openqa.selenium.By;

public class SelectAssetPage extends BasePage {
    private final By TXT_SEARCH_BOX = By.cssSelector(".input-group>input");
    private final By RDB_FIRST_RECORD = By.cssSelector("tbody>tr:first-child>td:nth-child(1)");
    private final By LBL_ASSET_CODE = By.cssSelector("tbody>tr:first-child>td:nth-child(2)");
    private final By LBL_ASSET_NAME = By.cssSelector("tbody>tr:first-child>td:nth-child(3)");
    private final By LBL_CATEGORY = By.cssSelector("tbody>tr:first-child>td:nth-child(4)");
    private final By BTN_SEARCH = By.cssSelector(".input-group span");
    private final By BTN_SAVE = By.xpath("//button[text()='Save']");

    public void searchKey(String key) {
        inputText(TXT_SEARCH_BOX, key);
        clickElement(BTN_SEARCH);
    }

    public String getAssetCodeAfterSearch() {
        return getText(LBL_ASSET_CODE);
    }

    public String getAssetNameAfterSearch() {
        return getText(LBL_ASSET_NAME);
    }

    public String getCategoryAfterSearch() {
        return getText(LBL_CATEGORY);
    }

    public void selectFirstRecord() {
        clickElement(RDB_FIRST_RECORD);
    }

    public void clickSave() {
        clickElement(BTN_SAVE);
    }
}
