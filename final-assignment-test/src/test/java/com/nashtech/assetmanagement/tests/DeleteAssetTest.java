package com.nashtech.assetmanagement.tests;

import com.google.gson.JsonObject;
import com.nashtech.assetmanagement.constants.APIConstants;
import com.nashtech.assetmanagement.constants.UrlConstants;
import com.nashtech.assetmanagement.helpers.AccountHelper;
import com.nashtech.assetmanagement.helpers.AssetHelper;
import com.nashtech.assetmanagement.pages.*;
import io.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.nashtech.assetmanagement.utils.JsonUtil.readJsonFile;
import static java.lang.System.getProperty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DeleteAssetTest extends BaseTest {
    LoginPage loginPage;
    AccountHelper accountHelper;
    AssetHelper assetHelper;
    HomePage homePage;
    ManageAssetPage manageAssetPage;
    ModelDialogPage deleteAsset;

    @BeforeMethod
    public void beforeDeleteAsset(ITestContext context) {
        loginPage = new LoginPage();
        manageAssetPage = new ManageAssetPage();
        accountHelper = new AccountHelper();
        assetHelper = new AssetHelper();
        homePage = new HomePage();
        deleteAsset = new ModelDialogPage();

        Response response = accountHelper.generateToken(APIConstants.PUBLIC_ACCOUNT_USERNAME, APIConstants.PUBLIC_ACCOUNT_PASSWORD);
        String token = response.jsonPath().getString("token");
        JsonObject jsonObject = readJsonFile("src/test/resources/testdata/asset_create/create_asset_form_api.json");
        response = assetHelper.createAsset(token, jsonObject.get("assetName").getAsString(), jsonObject.get("specification").getAsString(), jsonObject.get("installedDay").getAsString(), jsonObject.get("categoryID").getAsString(), jsonObject.get("assetState").getAsInt(), response.jsonPath().getString("location"));
        context.setAttribute("assetCode", response.jsonPath().getString("result.assetCode"));

        BasePage.navigate(UrlConstants.LOGIN_PAGE);
        loginPage.loginWithValidAccount(getProperty("USERNAME"), getProperty("PASSWORD"));
    }

    @Test
    public void deleteAsset(ITestContext context) throws InterruptedException {
        String assetCode = (String) context.getAttribute("assetCode");
        homePage.gotoManageAssetPage();
        Thread.sleep(2000);
        manageAssetPage.searchAssetWithCriteria(assetCode);
        Thread.sleep(3000);
        manageAssetPage.clickDeleteAsset();
        deleteAsset.clickDelete();
        Thread.sleep(2000);
        assertThat("Verify type", manageAssetPage.getMessageNoRecord(), equalTo("No data found"));
    }
}
