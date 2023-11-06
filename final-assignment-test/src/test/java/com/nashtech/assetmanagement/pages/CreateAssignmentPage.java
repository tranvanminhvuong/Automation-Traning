package com.nashtech.assetmanagement.pages;

import org.openqa.selenium.By;

public class CreateAssignmentPage extends BasePage{
    private final By SLT_USER = By.id("fullName");
    private final By SLT_ASSET = By.id("assetName");
    private final By DPK_ASSIGNED_DATE = By.xpath("//div[@class='react-datepicker__input-container']//child::input");
    private final By DPK_NOTE = By.id("note");
    private final By BTN_SAVE = By.xpath("//button[text()='Save ']");
    private final By BTN_CANCEL = By.xpath("//a[text()='Cancel']");
    public void clickToSelectUser(){
        clickElement(SLT_USER);
    }
    public void clickToSelectAsset(){
        clickElement(SLT_ASSET);
    }
    public void inputAssignedDate(String date){
        clickElement(DPK_ASSIGNED_DATE);
        blackedOut();
        inputText(DPK_ASSIGNED_DATE, date);
        pressEnter();
    }
    public void inputNote(String note){
        inputText(DPK_NOTE, note);
    }
    public void clickSave(){
        waitForElementToBeClickable(BTN_SAVE);
        clickElement(BTN_SAVE);
    }
}
