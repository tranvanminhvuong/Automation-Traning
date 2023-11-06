package com.nashtech.assetmanagement.pages;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.openqa.selenium.By;

import java.util.ArrayList;

public class ManageUserPage extends BasePage {
    private final By BTN_CREATE_USER = By.xpath("//a[text()='Create new User']");
    private final By TXT_SEARCH_BAR = By.xpath("//input[@class='form-control']");
    private final By BTN_SEARCH = By.cssSelector("[class='input-group'] span");
    private final By BTN_NEXT = By.xpath("//a[text()='Next']");
    private final By TBL_STAFF_CODE = By.cssSelector("[class='table-container'] tbody tr td:nth-child(1)");
    private final By TBL_FULL_NAME = By.cssSelector("[class='table-container'] tbody tr td:nth-child(2)");
    private final By TBL_USERNAME = By.cssSelector("[class='table-container'] tbody tr td:nth-child(3)");
    private final By TBL_JOINDATE = By.cssSelector("[class='table-container'] tbody tr td:nth-child(4)");
    private final By TBL_TYPE = By.cssSelector("[class='table-container'] tbody tr td:nth-child(5)");
    private final By TBL_STAFF_CODE_FIRST_RECORD = By.cssSelector("[class='table-container'] tbody tr:first-child td:nth-child(1)");
    private final By TBL_FULL_NAME_FIRST_RECORD = By.cssSelector("[class='table-container'] tbody tr:first-child td:nth-child(2)");
    private final By TBL_USER_NAME_FIRST_RECORD = By.cssSelector("[class='table-container'] tbody tr:first-child td:nth-child(3)");
    private final By TBL_JOINED_DATE_FIRST_RECORD = By.cssSelector("[class='table-container'] tbody tr:first-child td:nth-child(4)");
    private final By TBL_TYPE_FIRST_RECORD = By.cssSelector("[class='table-container'] tbody tr:first-child td:nth-child(5)");
    private final By BTN_NEXT_DISABLE = By.xpath("//a[text()='Next' and contains(@class,'disable')]");
    private final By BTN_EDIT = By.cssSelector("tbody div:first-child path");
    private final By BTN_DISABLE = By.cssSelector("tbody div:last-child svg");
    private final By FIRST_USEROFLIST = By.cssSelector(".table>tbody>tr:first-child");
    private final By LBL_NO_RECORD = By.xpath("//div[text()='No data found']");
    private final By TOTAL_PAGE = By.cssSelector(".pagination li");

    public void seeFirstRecordInfo() {
        clickElement(FIRST_USEROFLIST);
    }

    public void searchUserWithCriteria(String criteria) throws InterruptedException {
        inputText(TXT_SEARCH_BAR, criteria);
        clickElement(BTN_SEARCH);
    }

    public void clickCreateBtn() {
        clickElement(BTN_CREATE_USER);
    }

    public void getDataAfterSearch(JsonArray jsonArray) {
        ArrayList<String> getStaffCode;
        ArrayList<String> getFullName;
        ArrayList<String> getUserName;
        ArrayList<String> getJoinDate;
        ArrayList<String> getType;

        getStaffCode = getTextOfDataInTBL(TBL_STAFF_CODE);
        getFullName = getTextOfDataInTBL(TBL_FULL_NAME);
        getUserName = getTextOfDataInTBL(TBL_USERNAME);
        getJoinDate = getTextOfDataInTBL(TBL_JOINDATE);
        getType = getTextOfDataInTBL(TBL_TYPE);

        for (int i = 0; i < getFullName.size(); i++) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("staffCode", getStaffCode.get(i));
            jsonObject.addProperty("fullName", getFullName.get(i));
            jsonObject.addProperty("userName", getUserName.get(i));
            jsonObject.addProperty("joinedDate", getJoinDate.get(i));
            jsonObject.addProperty("type", getType.get(i));
            jsonArray.add(jsonObject);

        }
        if (!isElementPresent(BTN_NEXT_DISABLE) && isElementPresent(BTN_NEXT))
            clickElement(BTN_NEXT);
    }

    public boolean verifySearchResult(String staffCode, String fullName, String criteria) {
        System.out.println(staffCode + "-" + fullName + "-" + criteria);
        criteria = criteria.toLowerCase();
        staffCode = staffCode.toLowerCase();
        fullName = fullName.toLowerCase();
        if (staffCode.contains(criteria) | fullName.contains(criteria))
            return true;
        return false;
    }

    public void clickEditUser() {
        waitForAllElementsToBeVisible(FIRST_USEROFLIST);
        clickElement(BTN_EDIT);
    }

    public void clickDisableUser() {
        waitForAllElementsToBeVisible(FIRST_USEROFLIST);
        clickElement(BTN_DISABLE);
    }

    public String getMessageNoRecord() {
        return getText(LBL_NO_RECORD);
    }

    public int getTotalPage() {
        waitForElementToBeClickable(BTN_NEXT);
        int totalPage = findElements(TOTAL_PAGE).size();
        return totalPage - 2;
    }

    public void waitBtnActive(int i) {
        By BTN_INDEX_ACTIVE = By.xpath("//a[contains(@class,'link-active') and text()='%s']".formatted(i));
    }
    public String getStaffCodeFirstRecord(){
        return getText(TBL_STAFF_CODE_FIRST_RECORD);
    }
    public String getFullNameFirstRecord(){
        return getText(TBL_FULL_NAME_FIRST_RECORD);
    }
    public String getUserNameFirstRecord(){
        return getText(TBL_USER_NAME_FIRST_RECORD);
    }
    public String getJoinedDateFirstRecord(){
        return getText(TBL_JOINED_DATE_FIRST_RECORD);
    }
    public String getTypeFirstRecord(){
        return getText(TBL_TYPE_FIRST_RECORD);
    }
    public void waitFirstRecordEnable(){
        waitForVisibilityOfElementLocated(FIRST_USEROFLIST);
    }
}
