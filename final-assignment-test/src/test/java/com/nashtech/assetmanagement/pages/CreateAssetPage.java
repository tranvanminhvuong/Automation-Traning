package com.nashtech.assetmanagement.pages;

import org.openqa.selenium.By;

public class CreateAssetPage extends BasePage {
    private final By TXT_NAME = By.id("assetName");
    private final By DDL_CATEGORY = By.cssSelector("form[action='#'] select");
    private final By TXA_SPECIFICATION = By.id("specification");
    private final By DPK_INSTALLED_DATE = By.cssSelector("form[action='#']>div.mb-3:nth-child(4) input");
    private final By BTN_SAVE = By.xpath("//button[text()='Save ']");

    public void inputAssetName(String name) {
        inputText(TXT_NAME, name);
    }

    public void selectCategory(String category) {
        selectDropdown(DDL_CATEGORY, category);
    }

    public void inputSpecification(String specification) {
        inputText(TXA_SPECIFICATION, specification);
    }

    public void inputInstalledDate(String installedDate) {
        inputText(DPK_INSTALLED_DATE, installedDate);
        pressEnter();
    }

    public void selectState(String state) {
        clickElement(By.xpath("//label[text()='" + state + "']"));
    }

    public void clickSave() {
        clickElement(BTN_SAVE);
    }

}
