package com.nashtech.assetmanagement.tests;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nashtech.assetmanagement.constants.UrlConstants;
import com.nashtech.assetmanagement.pages.*;
import com.nashtech.assetmanagement.utils.JsonUtil;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import static com.nashtech.assetmanagement.utils.JsonUtil.readJsonFile;
import static java.lang.System.getProperty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ChangePasswordTest extends BaseTest {
    LoginPage loginPage;
    ChangePasswordPage changePasswordPage;
    HomePage homePage;
    LogoutPage logoutPage;
    ChangePasswordAlertPage changePasswordAlertPage;

    @BeforeMethod
    public void beforeTest() {
        loginPage = new LoginPage();
        homePage = new HomePage();
        changePasswordPage = new ChangePasswordPage();
        changePasswordAlertPage = new ChangePasswordAlertPage();
        logoutPage = new LogoutPage();
        BasePage.navigate(UrlConstants.LOGIN_PAGE);

    }

    @Test
    public void changePasswordTest(){
        JsonObject jsonObject = readJsonFile("src/test/resources/testdata/changepassword/changepassword_data.json");
        String username = jsonObject.get("USERNAME").getAsString();
        String old_password = jsonObject.get("OLDPASSWORD").getAsString();
        String new_password = jsonObject.get("NEWPASSWORD").getAsString();
        loginPage.loginWithValidAccount(username, old_password);
        homePage.clickAccountMenu(username);
        homePage.selectChangePassword();
        changePasswordPage.inputOldPassword(old_password);
        changePasswordPage.inputNewPassword(new_password);
        changePasswordPage.clickSave();
        assertThat("Verify type", changePasswordAlertPage.getAlertChangePasswordSuccessfully(),
                equalTo("Your password has been changed successfully!"));
        changePasswordAlertPage.clickConfirmChangePassword();
        homePage.clickAccountMenu(username);
        homePage.selectLogout();
        logoutPage.confirmLogout();
        loginPage.loginWithValidAccount(username, new_password);
        assertThat("Verify username", homePage.getUsername(), equalTo(username));
    }

    @AfterMethod
    public void writeData() {
        JsonObject beforeData = readJsonFile("src/test/resources/testdata/changepassword/changepassword_data.json");
        JsonObject afterData = new JsonObject();
        afterData.addProperty("USERNAME",beforeData.get("USERNAME").getAsString());
        afterData.addProperty("OLDPASSWORD",beforeData.get("NEWPASSWORD").getAsString());
        afterData.addProperty("NEWPASSWORD",beforeData.get("OLDPASSWORD").getAsString());
        try {
            FileWriter file = new FileWriter("src/test/resources/testdata/changepassword/changepassword_data.json",false);
            file.write(afterData.toString());
            file.flush();
            file.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }
}
