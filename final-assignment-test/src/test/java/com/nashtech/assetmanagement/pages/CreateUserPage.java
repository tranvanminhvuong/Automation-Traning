package com.nashtech.assetmanagement.pages;

import org.openqa.selenium.By;

public class CreateUserPage extends BasePage {

    private final By TXT_FIRSTNAME = By.id("firstName");
    private final By TXT_LASTNAME = By.id("lastName");
    private final By DPK_BIRTHDAY = By.xpath("//label[text()='Date of Birth']//following-sibling::div//descendant::input");
    private final By RDB_GENDER = By.cssSelector("input[value='%s']");

    private final By DPK_JOINDATE = By.xpath("//label[text()='Joined Date']//following-sibling::div//descendant::input");
    private final By DDL_TYPE = By.xpath("//label[text()='Type']//following-sibling::div//descendant::select");
    private final By BTN_SUBMIT = By.xpath("//button[text()='Save ']");


    public void inputFirstName(String firstName) {
        inputText(TXT_FIRSTNAME, firstName);
    }

    public void inputLastName(String lastName) {
        inputText(TXT_LASTNAME, lastName);
    }

    public void inputBirthday(String birthday) {
        inputText(DPK_BIRTHDAY, birthday);
        pressEnter();
    }

    public void selectGender(String gender) {
        if (gender.equals("Male")) {
            clickElement(By.xpath("//label[text()='%s']".formatted(gender)));
        } else clickElement(By.xpath("//label[text()='%s']".formatted(gender)));
    }

    public void inputJoindate(String joinday) {
        inputText(DPK_JOINDATE, joinday);
        pressEnter();
    }

    public void selectType(String type) {
        selectDropdown(DDL_TYPE, type);
    }

    public String getSuccessfullyMessage() {
        return getAlertMessage();
    }

    public void clickSubmit() {
        clickElement(BTN_SUBMIT);
    }

}
