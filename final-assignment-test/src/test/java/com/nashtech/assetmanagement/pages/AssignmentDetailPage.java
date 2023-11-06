package com.nashtech.assetmanagement.pages;

import org.openqa.selenium.By;

public class AssignmentDetailPage extends BasePage {
    private final By LBL_ASSET_CODE = By.xpath("//div[text()='Asset Code:']//following-sibling::div");
    private final By LBL_ASSET_NAME = By.xpath("//div[text()='Asset Name:']//following-sibling::div");
    private final By LBL_SPECIFICATION = By.xpath("//div[text()='Specification:']//following-sibling::div");
    private final By LBL_ASSIGNED_TO = By.xpath("//div[text()='Assigned to:']//following-sibling::div");
    private final By LBL_ASSIGNED_BY = By.xpath("//div[text()='Assigned by:']//following-sibling::div");
    private final By LBL_ASSIGNED_DATE = By.xpath("//div[text()='Assigned Date:']//following-sibling::div");
    private final By LBL_STATE = By.xpath("//div[text()='State:']//following-sibling::div");
    private final By LBL_NOTE = By.xpath("//div[text()='Note:']//following-sibling::div");

    public String getAssetCode() {
        return getText(LBL_ASSET_CODE);
    }

    public String getAssetName() {
        return getText(LBL_ASSET_NAME);
    }

    public String getSpecification() {
        return getText(LBL_SPECIFICATION);
    }

    public String getAssignedTo() {
        return getText(LBL_ASSIGNED_TO);
    }

    public String getAssignedBy() {
        return getText(LBL_ASSIGNED_BY);
    }

    public String getAssignedDate() {
        return getText(LBL_ASSIGNED_DATE);
    }

    public String getState() {
        return getText(LBL_STATE);
    }

    public String getNote() {
        return getText(LBL_NOTE);
    }
}
