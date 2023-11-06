package com.nashtech.assetmanagement.pages;

import org.openqa.selenium.By;

public class ModelDialogPage extends BasePage {
    private final By BTN_CONFIRM = By.xpath("//button[text()='Disable']");
    private final By BTN_DELETE = By.xpath("//button[text()='Delete']");
    private final By BTN_CANCEL = By.xpath("//button[text()='Cancel']");

    public void clickConfirm() {
        clickElement(BTN_CONFIRM);
    }
    public void clickDelete() {
        clickElement(BTN_DELETE);
    }
}
