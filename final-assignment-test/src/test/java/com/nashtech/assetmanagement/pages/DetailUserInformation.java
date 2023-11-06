package com.nashtech.assetmanagement.pages;

import org.openqa.selenium.By;

public class DetailUserInformation extends BasePage {
    private final By LBL_STAFFCODE = By.xpath("//div[text()='Staff Code:']//following-sibling::div");
    private final By LBL_FULLNAME = By.xpath("//div[text()='Full Name:']//following-sibling::div");
    private final By LBL_DATEOFBIRTH = By.xpath("//div[text()='Date of Birth:']//following-sibling::div");
    private final By LBL_GENDER = By.xpath("//div[text()='Gender:']//following-sibling::div");
    private final By LBL_JOINDATE = By.xpath("//div[text()='Joined Date:']//following-sibling::div");
    private final By LBL_TYPE = By.xpath("//div[text()='Type:']//following-sibling::div");
    private final By LBL_LOCATION = By.xpath("//div[text()='Location:']//following-sibling::div");

    public String getStaffCode() {
        return getText(LBL_STAFFCODE);
    }
    public String getFullname() {
        return getText(LBL_FULLNAME);
    }

    public String getDateOfBirth() {
        return getText(LBL_DATEOFBIRTH);
    }

    public String getGender() {
        return getText(LBL_GENDER);
    }

    public String getJoinDate() {
        return getText(LBL_JOINDATE);
    }

    public String getType() {
        return getText(LBL_TYPE);
    }
    public String getLocation() {
        return getText(LBL_LOCATION);
    }
}
