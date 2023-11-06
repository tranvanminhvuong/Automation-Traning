package com.nashtech.assetmanagement.tests;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nashtech.assetmanagement.helpers.AccountHelper;
import com.nashtech.assetmanagement.utils.JdbcSQLServerConnection;
import com.nashtech.assetmanagement.constants.UrlConstants;
import com.nashtech.assetmanagement.pages.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static com.nashtech.assetmanagement.repos.ManageUserFunction.getUserListInDFollowLocation;
import static com.nashtech.assetmanagement.utils.JsonUtil.readJsonFile;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ManageUserTest extends BaseTest {
    LoginPage loginPage;
    NavigationPage navigationPage;
    HomePage homePage;
    ManageUserPage manageUserPage;
    JdbcSQLServerConnection jdbcSQLServerConnection;
    AccountHelper accountHelper = new AccountHelper();

    private static final Logger LOGGER = LogManager.getLogger(LoginTest.class);

    @BeforeMethod
    public void beforeMethod(ITestContext context) {
        loginPage = new LoginPage();
        navigationPage = new NavigationPage();
        homePage = new HomePage();
        manageUserPage = new ManageUserPage();
        jdbcSQLServerConnection = new JdbcSQLServerConnection();
        BasePage.navigate(UrlConstants.LOGIN_PAGE);
    }

    @Test(dataProvider = "getAdminOfHCM")
    public void viewDefaultUserListWithAdminHCM(JsonObject jsonObject) throws SQLException {
        Response response = accountHelper.generateToken(jsonObject.get("userName").getAsString(),
                jsonObject.get("password").getAsString());
        loginPage.loginWithValidAccount(jsonObject.get("userName").getAsString(),
                jsonObject.get("password").getAsString());
        String userToken = response.jsonPath().getString("token");
        String location = response.jsonPath().getString("location");
        homePage.waitTabDisplayed();
        homePage.gotoManageUserPage();

        int limit = Integer.parseInt(System.getProperty("ITEM_PAGE"));
        JsonArray getUserListFollowLocationInDB = getUserListInDFollowLocation(location);
        response = accountHelper.getUserInPage(userToken, 1, limit, location);
        int pageCount = 0;
        if (response.statusCode() == 200) {
            pageCount = response.jsonPath().getInt("pageCount");
        }
        // data in ui
        JsonArray getUserListInUI = new JsonArray();

        for (int i = 1; i <= pageCount; i++) {
            response = accountHelper.getUserInPage(userToken, i, limit, location);
            if (response.statusCode() == 200) {
                manageUserPage.waitBtnActive(i);
                manageUserPage.getDataAfterSearch(getUserListInUI);
            }
        }
        assertThat("Verify number of user list follow location ", getUserListInUI.size(),
                equalTo(getUserListFollowLocationInDB.size()));
        for (int i = 0; i < getUserListFollowLocationInDB.size(); i++) {
            JsonObject objectDB = getUserListFollowLocationInDB.get(i).getAsJsonObject();
            JsonObject objectUI = getUserListInUI.get(i).getAsJsonObject();
            assertThat("Verify user information ", objectUI, equalTo(objectDB));
        }
    }

    @Test(dataProvider = "getAdminOfHN")
    public void viewDefaultUserListWithAdminHN(JsonObject jsonObject) throws InterruptedException, SQLException {
        Response response = accountHelper.generateToken(jsonObject.get("userName").getAsString(),
                jsonObject.get("password").getAsString());
        loginPage.loginWithValidAccount(jsonObject.get("userName").getAsString(),
                jsonObject.get("password").getAsString());
        String userToken = response.jsonPath().getString("token");
        String location = response.jsonPath().getString("location");
        homePage.waitTabDisplayed();
        homePage.gotoManageUserPage();

        int limit = Integer.parseInt(System.getProperty("ITEM_PAGE"));
        JsonArray getUserListFollowLocationInDB = getUserListInDFollowLocation(location);
        response = accountHelper.getUserInPage(userToken, 1, limit, location);
        int pageCount = 0;
        if (response.statusCode() == 200) {
            pageCount = response.jsonPath().getInt("pageCount");
        }
        // data in ui
        JsonArray getUserListInUI = new JsonArray();

        for (int i = 1; i <= pageCount; i++) {
            response = accountHelper.getUserInPage(userToken, i, limit, location);
            if (response.statusCode() == 200) {
                manageUserPage.waitBtnActive(i);
                manageUserPage.getDataAfterSearch(getUserListInUI);
            }
        }
        assertThat("Verify number of user list follow location ", getUserListInUI.size(),
                equalTo(getUserListFollowLocationInDB.size()));
        for (int i = 0; i < getUserListFollowLocationInDB.size(); i++) {
            JsonObject objectDB = getUserListFollowLocationInDB.get(i).getAsJsonObject();
            JsonObject objectUI = getUserListInUI.get(i).getAsJsonObject();
            assertThat("Verify user information ", objectUI, equalTo(objectDB));
        }
    }

    @DataProvider(name = "getAdminOfHCM")
    public Object[][] getAdminOfHCM() throws FileNotFoundException {
        JsonObject jsonObject = readJsonFile("src/test/resources/testdata/view_user/admin_hcm.json");
        JsonArray jsonArray = jsonObject.getAsJsonArray("adminHCM");
        Object[][] objects = new Object[jsonArray.size()][1];
        for (int i = 0; i < jsonArray.size(); i++) {
            objects[i][0] = jsonArray.get(i).getAsJsonObject();
        }
        return objects;
    }

    @DataProvider(name = "getAdminOfHN")
    public Object[][] getAdminOfHN() throws FileNotFoundException {
        JsonObject jsonObject = readJsonFile("src/test/resources/testdata/view_user/admin_hn.json");
        JsonArray jsonArray = jsonObject.getAsJsonArray("adminHN");
        Object[][] objects = new Object[jsonArray.size()][1];
        for (int i = 0; i < jsonArray.size(); i++) {
            objects[i][0] = jsonArray.get(i).getAsJsonObject();
        }
        return objects;
    }


}
