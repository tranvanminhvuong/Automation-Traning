package com.nashtech.assetmanagement.tests;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nashtech.assetmanagement.helpers.AccountHelper;
import com.nashtech.assetmanagement.helpers.AssetHelper;
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

import static com.nashtech.assetmanagement.repos.ManageAssetFunction.getAssetListInDFollowLocation;
import static com.nashtech.assetmanagement.utils.JsonUtil.readJsonFile;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ManageAssetTest extends BaseTest {
    LoginPage loginPage;
    NavigationPage navigationPage;
    HomePage homePage;
    ManageAssetPage manageAssetPage;
    AccountHelper accountHelper;
    AssetHelper assetHelper;

    private static final Logger LOGGER = LogManager.getLogger(LoginTest.class);

    @BeforeMethod
    public void beforeMethod(ITestContext context) {
        accountHelper = new AccountHelper();
        assetHelper = new AssetHelper();
        loginPage = new LoginPage();
        navigationPage = new NavigationPage();
        homePage = new HomePage();
        manageAssetPage = new ManageAssetPage();
        BasePage.navigate(UrlConstants.LOGIN_PAGE);
    }

    @Test(dataProvider = "getAdminOfHCM")
    public void viewDefaultAssetListByAdminHCM(JsonObject jsonObject) throws InterruptedException, SQLException {
        Response response = accountHelper.generateToken(jsonObject.get("userName").getAsString(),
                jsonObject.get("password").getAsString());
        loginPage.loginWithValidAccount(jsonObject.get("userName").getAsString(),
                jsonObject.get("password").getAsString());
        String userToken = response.jsonPath().getString("token");
        String location = response.jsonPath().getString("location");
        homePage.waitTabDisplayed();
        homePage.gotoManageAssetPage();

        int limit = Integer.parseInt(System.getProperty("ITEM_PAGE"));
        JsonArray getAssetListFollowLocationInDB = getAssetListInDFollowLocation(location);
        response = assetHelper.getAssetInPage(userToken, 1, limit, location);
        int pageCount = 0;
        if (response.statusCode() == 200) {
            pageCount = (getAssetListFollowLocationInDB.size()%5==0?getAssetListFollowLocationInDB.size()/5:getAssetListFollowLocationInDB.size()/5+1);
        }
        // data in ui
        JsonArray getAssetListInUI = new JsonArray();

        for (int i = 1; i <= pageCount; i++) {
            response = assetHelper.getAssetInPage(userToken, i, limit, location);
            if (response.statusCode() == 200) {
                manageAssetPage.waitBtnActive(i);
                manageAssetPage.getDataAfterSearch(getAssetListInUI);
            }
        }
        assertThat("Verify number of asset list follow location ", getAssetListInUI.size(),
                equalTo(getAssetListFollowLocationInDB.size()));
        for (int i = 0; i < getAssetListFollowLocationInDB.size(); i++) {
            JsonObject objectDB = getAssetListFollowLocationInDB.get(i).getAsJsonObject();
            JsonObject objectUI = getAssetListInUI.get(i).getAsJsonObject();
            assertThat("Verify asset information ", objectUI, equalTo(objectDB));
        }
    }

    @Test(dataProvider = "getAdminOfHN")
    public void viewDefaultAssetListByAdminHN(JsonObject jsonObject) throws InterruptedException, SQLException {
        Response response = accountHelper.generateToken(jsonObject.get("userName").getAsString(),
                jsonObject.get("password").getAsString());
        loginPage.loginWithValidAccount(jsonObject.get("userName").getAsString(),
                jsonObject.get("password").getAsString());
        String userToken = response.jsonPath().getString("token");
        String location = response.jsonPath().getString("location");
        homePage.waitTabDisplayed();
        homePage.gotoManageAssetPage();

        int limit = Integer.parseInt(System.getProperty("ITEM_PAGE"));
        JsonArray getAssetListFollowLocationInDB = getAssetListInDFollowLocation(location);
        response = assetHelper.getAssetInPage(userToken, 1, limit, location);
        int pageCount = 0;
        if (response.statusCode() == 200) {
            pageCount = (getAssetListFollowLocationInDB.size()%5==0?getAssetListFollowLocationInDB.size()/5:getAssetListFollowLocationInDB.size()/5+1);
        }
        // data in ui
        JsonArray getAssetListInUI = new JsonArray();

        for (int i = 1; i <= pageCount; i++) {
            response = assetHelper.getAssetInPage(userToken, i, limit, location);
            if (response.statusCode() == 200) {
                manageAssetPage.waitBtnActive(i);
                manageAssetPage.getDataAfterSearch(getAssetListInUI);
            }
        }
        assertThat("Verify number of asset list follow location ", getAssetListFollowLocationInDB.size(),
                equalTo(getAssetListInUI.size()));
        for (int i = 0; i < getAssetListFollowLocationInDB.size(); i++) {
            JsonObject objectDB = getAssetListFollowLocationInDB.get(i).getAsJsonObject();
            JsonObject objectUI = getAssetListInUI.get(i).getAsJsonObject();
            assertThat("Verify asset information ", objectUI, equalTo(objectDB));
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
