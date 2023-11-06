package com.nashtech.assetmanagement.pages;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.openqa.selenium.By;

import java.util.ArrayList;

public class ManageAssetPage extends BasePage {
    private final By BTN_CREATE_ASSET = By.xpath("//a[text()='Create new Asset']");
    private final By BTN_SEARCH = By.cssSelector("[class='input-group'] span");
    private final By BTN_NEXT = By.xpath("//a[text()='Next']");
    private final By TBL_ASSET_CODE = By.cssSelector("[class='table-container'] tbody tr td:nth-child(1)");
    private final By TBL_ASSET_NAME = By.cssSelector("[class='table-container'] tbody tr td:nth-child(2)");
    private final By TBL_CATEGORY = By.cssSelector("[class='table-container'] tbody tr td:nth-child(3)");
    private final By TBL_STATE = By.cssSelector("[class='table-container'] tbody tr td:nth-child(4)");
    private final By TXT_SEARCH_BAR = By.xpath("//input[@class='form-control']");
    private final By BTN_NEXT_DISABLE = By.xpath("//a[text()='Next' and contains(@class,'disable')]");
    private final By TOTAL_PAGE = By.cssSelector(".pagination li");
    private final By LBL_NO_RECORD = By.xpath("//div[text()='No data found']");
    private final By BTN_DELETE = By.cssSelector("tbody div:last-child");
    private final By TBL_ASSET_CODE_FIRST_RECORD = By.cssSelector("[class='table-container'] tbody tr:first-child td:nth-child(1)");
    private final By TBL_ASSET_NAME_FIRST_RECORD = By.cssSelector("[class='table-container'] tbody tr:first-child td:nth-child(2)");
    private final By TBL_CATEGORY_FIRST_RECORD = By.cssSelector("[class='table-container'] tbody tr:first-child td:nth-child(3)");
    private final By TBL_STATE_FIRST_RECORD = By.cssSelector("[class='table-container'] tbody tr:first-child td:nth-child(4)");
    private final By FIRST_ASSETOFLIST = By.cssSelector(".table>tbody>tr:first-child");


    public void searchWithCriteria(String criteria) {
        inputText(TXT_SEARCH_BAR, criteria);
        clickElement(BTN_SEARCH);
    }

    public void clickCreateBtn() {
        clickElement(BTN_CREATE_ASSET);
    }

    public void getDataAfterSearch(JsonArray jsonArray) {
        ArrayList<String> getAssetCode;
        ArrayList<String> getAssetName;
        ArrayList<String> getCategory;
        ArrayList<String> getState;

        getAssetCode = getTextOfDataInTBL(TBL_ASSET_CODE);
        getAssetName = getTextOfDataInTBL(TBL_ASSET_NAME);
        getCategory = getTextOfDataInTBL(TBL_CATEGORY);
        getState = getTextOfDataInTBL(TBL_STATE);

        for (int i = 0; i < getAssetCode.size(); i++) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("assetCode", getAssetCode.get(i));
            jsonObject.addProperty("assetName", getAssetName.get(i));
            jsonObject.addProperty("category", getCategory.get(i));
            jsonObject.addProperty("state", getState.get(i));
            jsonArray.add(jsonObject);
        }
        if (!isElementPresent(BTN_NEXT_DISABLE) && isElementPresent(BTN_NEXT))
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

    public String getMessageNoRecord() {
        return getText(LBL_NO_RECORD);
    }

    public void searchAssetWithCriteria(String criteria) {
        inputText(TXT_SEARCH_BAR, criteria);
        clickElement(BTN_SEARCH);
    }

    public void clickDeleteAsset() {
        waitForVisibilityOfAllElementsLocatedBy(FIRST_ASSETOFLIST);
        clickElement(BTN_DELETE);
    }

    public void waitFirstRecordEnable() {
        waitForVisibilityOfElementLocated(FIRST_ASSETOFLIST);
    }

    public void seeFirstRecordInfo() {
        clickElement(FIRST_ASSETOFLIST);
    }

    public String getAssetCodeFirstRecord() {
        return getText(TBL_ASSET_CODE_FIRST_RECORD);
    }

    public String getAssetNameFirstRecord() {
        return getText(TBL_ASSET_NAME_FIRST_RECORD);
    }

    public String getCategoryFirstRecord() {
        return getText(TBL_CATEGORY_FIRST_RECORD);
    }

    public String getStateFirstRecord() {
        return getText(TBL_STATE_FIRST_RECORD);
    }
}
