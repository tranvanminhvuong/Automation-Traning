package com.nashtech.assetmanagement.pages;

import org.openqa.selenium.By;

public class EditUserPage extends BasePage {
    private final By DPK_BIRTHDAY = By
            .xpath("//label[text()='Date of Birth']//following-sibling::div//descendant::input");
    private final By RDB_GENDER_F = By.cssSelector("input[value='F']");
    private final By RDB_GENDER_M = By.cssSelector("input[value='M']");
    private final By DPK_JOINDATE = By
            .xpath("//label[text()='Joined Date']//following-sibling::div//descendant::input");
    private final By DDL_TYPE = By.xpath("//label[text()='Type']//following-sibling::div//descendant::select");
    private final By BTN_SUBMIT = By.xpath("//button[text()='Save ']");

    public void inputBirthday(String birthday) {
        clickElement(DPK_BIRTHDAY);
        blackedOut();
        inputText(DPK_BIRTHDAY, birthday);
        pressEnter();
    }

    public void selectGender(String gender) {
        if (gender.equals("Male")) {
            clickElement(RDB_GENDER_M);
        } else
            clickElement(RDB_GENDER_F);
    }

    public void inputJoindate(String joinday) {
        clickElement(DPK_JOINDATE);
        blackedOut();
        inputText(DPK_JOINDATE, joinday);
        pressEnter();
    }

    public void selectType(String type) {
        selectDropdown(DDL_TYPE, type);
    }

    public void clickSubmit() {
        clickElement(BTN_SUBMIT);
    }
}
