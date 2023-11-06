package com.nashtech.assetmanagement.pages;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.openqa.selenium.By;

import java.util.ArrayList;

public class ManageAssignmentPage extends BasePage {
    private final By BTN_CREATE_ASSIGNMENT = By.xpath("//a[text()='Create New Assignment']");
    private final By BTN_SEARCH = By.cssSelector("[class='input-group'] span");
    private final By BTN_NEXT = By.xpath("//a[text()='Next']");
    private final By TBL_ASSET_CODE = By.cssSelector("[class='table-container'] tbody tr td:nth-child(2)");
    private final By TXT_SEARCH_BAR = By.xpath("//input[@class='form-control']");
    private final By BTN_NEXT_DISABLE = By.xpath("//a[text()='Next' and contains(@class,'disable')]");
    private final By TOTAL_PAGE = By.cssSelector(".pagination li");
    private final By TBL_FIRST_RECORD = By.cssSelector("[class='table-container'] tbody tr:first-child");
    private final By TBL_ASSET_CODE_FIRST_RECORD = By.cssSelector("[class='table-container'] tbody>tr:first-child>td:nth-child(2)");
    private final By TBL_ASSET_NAME_FIRST_RECORD = By.cssSelector("[class='table-container'] tbody>tr:first-child>td:nth-child(3)");
    private final By TBL_ASSIGN_TO_FIRST_RECORD = By.cssSelector("[class='table-container'] tbody>tr:first-child>td:nth-child(4)");
    private final By TBL_ASSIGN_BY_FIRST_RECORD = By.cssSelector("[class='table-container'] tbody>tr:first-child>td:nth-child(5)");
    private final By TBL_ASSIGN_DATE_FIRST_RECORD = By.cssSelector("[class='table-container'] tbody>tr:first-child>td:nth-child(6)");
    private final By TBL_STATE_FIRST_RECORD = By.cssSelector("[class='table-container'] tbody>tr:first-child>td:nth-child(7)");
    private final By BTN_ASSET_NAME = By.xpath("//a[text()='Asset Code']");


    public void clickCreateBtn() {
        clickElement(BTN_CREATE_ASSIGNMENT);
    }

    public void getAssetCodeAfterSearch(JsonArray jsonArray) throws InterruptedException {
        ArrayList<String> getAssetCode;
        Thread.sleep(500);
        getAssetCode = getTextOfDataInTBL(TBL_ASSET_CODE);

        for (int i = 0; i < getAssetCode.size(); i++) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("assetCode", getAssetCode.get(i));
            jsonArray.add(jsonObject);
        }
        if (!isElementPresent(BTN_NEXT_DISABLE))
            clickElement(BTN_NEXT);
    }

    public int getTotalPage() {
        waitForElementToBeClickable(BTN_NEXT);
        int totalPage = findElements(TOTAL_PAGE).size();
        return totalPage - 2;
    }

    public void waitBtnActive(int i) {
        By BTN_INDEX_ACTIVE = By.xpath("//a[contains(@class,'link-active') and text()='%s']".formatted(i));
        waitForElementToBeClickable(BTN_INDEX_ACTIVE);
    }

    public void searchAssignmentWithCriteria(String criteria) {
        inputText(TXT_SEARCH_BAR, criteria);
        clickElement(BTN_SEARCH);
    }

    public void clickOnFirstRecord() {
        clickElement(TBL_FIRST_RECORD);
    }

    public String getAssetCodeFirstRecord() {
        return getText(TBL_ASSET_CODE_FIRST_RECORD);
    }

    public String getAssetNameFirstRecord() {
        return getText(TBL_ASSET_NAME_FIRST_RECORD);
    }

    public String getAssignToFirstRecord() {
        return getText(TBL_ASSIGN_TO_FIRST_RECORD);
    }

    public String getAssignByFirstRecord() {
        return getText(TBL_ASSIGN_BY_FIRST_RECORD);
    }

    public String getAssignDateFirstRecord() {
        return getText(TBL_ASSIGN_DATE_FIRST_RECORD);
    }

    public String getStateFirstRecord() {
        return getText(TBL_STATE_FIRST_RECORD);
    }

    public void clickSortFollowAssetCode() throws InterruptedException {
        clickElement(BTN_ASSET_NAME);
        Thread.sleep(1000);
        clickElement(BTN_ASSET_NAME);
    }
}
