package com.nashtech.assetmanagement.tests;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nashtech.assetmanagement.helpers.AccountHelper;
import com.nashtech.assetmanagement.helpers.AssetHelper;
import com.nashtech.assetmanagement.utils.JdbcSQLServerConnection;
import com.nashtech.assetmanagement.constants.APIConstants;
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

import static com.nashtech.assetmanagement.repos.ManageAssetFunction.searchAssetListWithKeyWord;
import static com.nashtech.assetmanagement.utils.JsonUtil.readJsonFile;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SearchAssetTest extends BaseTest {
    LoginPage loginPage;
    NavigationPage navigationPage;
    HomePage homePage;
    ManageAssetPage manageAssetPage;
    AssetHelper assetHelper = new AssetHelper();
    AccountHelper accountHelper = new AccountHelper();

    private static final Logger LOGGER = LogManager.getLogger(LoginTest.class);

    @BeforeMethod
    public void beforeMethod(ITestContext context) {
        Response response = accountHelper.generateToken(APIConstants.PUBLIC_ACCOUNT_USERNAME,
                APIConstants.PUBLIC_ACCOUNT_PASSWORD);
        String userToken = response.jsonPath().getString("token");
        String location = response.jsonPath().getString("location");
        context.setAttribute("userToken", userToken);
        context.setAttribute("location", location);
        loginPage = new LoginPage();
        navigationPage = new NavigationPage();
        homePage = new HomePage();
        manageAssetPage = new ManageAssetPage();
        BasePage.navigate(UrlConstants.LOGIN_PAGE);
        loginPage.loginWithValidAccount(System.getProperty("USERNAME"), System.getProperty("PASSWORD"));
    }

    @Test(dataProvider = "getData")
    public void searchAsset(ITestContext context, JsonObject jsonObject) throws InterruptedException, SQLException {
        String userToken = (String) context.getAttribute("userToken");
        String criteria = jsonObject.get("criteria").getAsString();
        homePage.waitTabDisplayed();
        homePage.gotoManageAssetPage();

        String location = (String) context.getAttribute("location");
        int limit = Integer.parseInt(System.getProperty("ITEM_PAGE"));
        JsonArray getUserListFollowLocationInDB = searchAssetListWithKeyWord(location,
                criteria);
        Response response = assetHelper.searchAsset(userToken, 1, limit, location, criteria);
        int pageCount = response.jsonPath().getInt("pageCount");
        manageAssetPage.searchWithCriteria(criteria);
        // data in ui
        JsonArray getUserListInUI = new JsonArray();

        for (int i = 1; i <= pageCount; i++) {
            response = assetHelper.searchAsset(userToken, i, limit, location, criteria);
            if (response.statusCode() == 200) {
                manageAssetPage.getDataAfterSearch(getUserListInUI);
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

    @DataProvider(name = "getData")
    public Object[][] getData() throws FileNotFoundException {
        JsonObject jsonObject = readJsonFile("src/test/resources/testdata/search_asset/data_search.json");
        JsonArray jsonArray = jsonObject.getAsJsonArray("dataSearch");
        Object[][] objects = new Object[jsonArray.size()][1];
        for (int i = 0; i < jsonArray.size(); i++) {
            objects[i][0] = jsonArray.get(i).getAsJsonObject();
        }
        return objects;
    }
}
