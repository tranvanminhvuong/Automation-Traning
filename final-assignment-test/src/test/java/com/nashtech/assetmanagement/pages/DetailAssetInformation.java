package com.nashtech.assetmanagement.pages;

import org.openqa.selenium.By;

public class DetailAssetInformation extends BasePage{
    private final By LBL_ASSET_CODE = By.xpath("//div[text()='Asset Code:']//following-sibling::div");
    private final By LBL_ASSET_NAME = By.xpath("//div[text()='Asset Name:']//following-sibling::div");
    private final By LBL_ASSET_CATEGORY = By.xpath("//div[text()='Category:']//following-sibling::div");
    private final By LBL_ASSET_INSTALLED_DATE = By.xpath("//div[text()='Installed Date:']//following-sibling::div");
    private final By LBL_ASSET_STATE = By.xpath("//div[text()='State:']//following-sibling::div");
    private final By LBL_ASSET_LOCATION = By.xpath("//div[text()='Location:']//following-sibling::div");
    private final By LBL_ASSET_SPECIFICATION = By.xpath("//div[text()='Specification:']//following-sibling::div");
    public String getAssetCode() {
        return getText(LBL_ASSET_CODE);
    }
    public String getAssetName() {
        return getText(LBL_ASSET_NAME);
    }
    public String getAssetCategory() {
        return getText(LBL_ASSET_CATEGORY);
    }
    public String getAssetInstalledDate() {
        return getText(LBL_ASSET_INSTALLED_DATE);
    }
    public String getAssetState() {
        return getText(LBL_ASSET_STATE);
    }
    public String getAssetLocation() {
        return getText(LBL_ASSET_LOCATION);
    }
    public String getAssetSpecification() {
        return getText(LBL_ASSET_SPECIFICATION);
    }
}
