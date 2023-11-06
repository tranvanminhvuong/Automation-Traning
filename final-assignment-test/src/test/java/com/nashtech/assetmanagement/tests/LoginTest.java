package com.nashtech.assetmanagement.tests;

import com.google.gson.JsonObject;
import com.nashtech.assetmanagement.constants.MsgConstants;
import com.nashtech.assetmanagement.pages.BasePage;
import com.nashtech.assetmanagement.pages.HomePage;
import com.nashtech.assetmanagement.pages.LoginPage;
import com.nashtech.assetmanagement.pages.NavigationPage;
import com.nashtech.assetmanagement.constants.UrlConstants;
import com.nashtech.assetmanagement.repos.LoginFunction;
import com.nashtech.assetmanagement.utils.ExcelUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LoginTest extends BaseTest {
    LoginPage loginPage;
    NavigationPage navigationPage;
    HomePage homePage;

    private static final Logger LOGGER = LogManager.getLogger(LoginTest.class);

    @BeforeMethod
    public void beforeMethod() {
        loginPage = new LoginPage();
        navigationPage = new NavigationPage();
        homePage = new HomePage();
        BasePage.navigate(UrlConstants.LOGIN_PAGE);
    }

    @Test(dataProvider = "loginUnsuccessfullyWithValidAccount")
    public void loginSuccessfullyWithValidAccount(JsonObject jsonObject) {
        loginPage.inputUserName(jsonObject.get("User name").getAsString());
        loginPage.inputPassword(jsonObject.get("password").getAsString());
        loginPage.clickLoginBtn();
        String actualUsername = navigationPage.getUserName();
        assertThat("Verify username", actualUsername, equalTo(jsonObject.get("User name").getAsString()));
        String[] listTabInHomePageForAdmin = {"Home", "Manage User", "Manage Asset", "Manage Assignment",
                "Request for Returning", "Report"};
        String[] listTabInHomePageForStaff = {"Home"};
        String roleAccount = jsonObject.get("role").getAsString();

        ArrayList<String> listTabInHomePage = homePage.getTextOfTabInHomePage();

        if (roleAccount.equals("Admin")) {
            assertThat("Verify the number of tabs corresponds to the account permissions", listTabInHomePage.size(),
                    equalTo(listTabInHomePageForAdmin.length));
            assertThat("Verify text of Tab in HomePage", LoginFunction.verifyTextOfTabFollowPermission(listTabInHomePage, listTabInHomePageForAdmin));
        } else if (roleAccount.equals("staff")) {
            assertThat("Verify the number of tabs corresponds to the account permissions",
                    listTabInHomePageForStaff.length, equalTo(listTabInHomePage.size()));
            assertThat("Verify text of Tab in HomePage", LoginFunction.verifyTextOfTabFollowPermission(listTabInHomePage, listTabInHomePageForStaff));
        }

    }

    @Test
    public void loginUnsuccessfullyWithInvalidAccount() {
        LOGGER.info("loginUnsuccessfullyWithInvalidUsername");
        loginPage.inputUserName("username");
        loginPage.inputPassword("password");
        loginPage.clickLoginBtn();
        String errorMsg = loginPage.getErrorMessage();
        assertThat("Verify error message", errorMsg, equalTo(MsgConstants.LOGIN_INVALID_ACCOUNT));
    }

    @Test(dataProvider = "loginUnsuccessfullyWithDisableAccount")
    public void loginUnsuccessfullyWithDisableAccount(JsonObject jsonObject) {
        LOGGER.info("loginUnsuccessfullyWithInvalidPassword");
        loginPage.inputUserName(jsonObject.get("User name").getAsString());
        loginPage.inputPassword(jsonObject.get("password").getAsString());
        loginPage.clickLoginBtn();
        String errorMsg = loginPage.getErrorMessage();
        assertThat("Verify error message", errorMsg, equalTo(MsgConstants.LOGIN_DISABLE_ACCOUNT));
    }

    @DataProvider(name = "loginUnsuccessfullyWithValidAccount")
    public Object[][] excelDataProviderWithValidAccount() throws IOException {
        Object[][] arrObj = ExcelUtil.getExcelDataContainValue(
                "src/test/resources/testdata/login_page/account_valid.xlsx", "Data test", false);
        return arrObj;
    }

    @DataProvider(name = "loginUnsuccessfullyWithDisableAccount")
    public Object[][] excelDataProviderWithDisableAccount() throws IOException {
        Object[][] arrObj = ExcelUtil.getExcelDataContainValue(
                "src/test/resources/testdata/login_page/account_disable.xlsx", "Data test", true);
        return arrObj;
    }

}
