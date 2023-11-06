package com.nashtech.assetmanagement.tests;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nashtech.assetmanagement.constants.UrlConstants;
import com.nashtech.assetmanagement.helpers.AccountHelper;
import com.nashtech.assetmanagement.pages.*;
import com.nashtech.assetmanagement.utils.JsonUtil;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import static com.nashtech.assetmanagement.repos.ManageAssetFunction.getAssetListInDFollowLocation;
import static com.nashtech.assetmanagement.repos.ManageUserFunction.getUserListInDFollowLocation;
import static java.lang.System.getProperty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateAssetTest extends BaseTest {
    LoginPage loginPage;
    HomePage homePage;
    ManageAssetPage manageAssetPage;
    CreateAssetPage createAssetPage;
    DetailAssetInformation detailAssetInformation;
    AccountHelper accountHelper;

    @BeforeMethod
    public void beforeCreate() {
        loginPage = new LoginPage();
        homePage = new HomePage();
        accountHelper = new AccountHelper();
        manageAssetPage = new ManageAssetPage();
        createAssetPage = new CreateAssetPage();
        detailAssetInformation = new DetailAssetInformation();
        BasePage.navigate(UrlConstants.LOGIN_PAGE);
        loginPage.loginWithValidAccount(getProperty("USERNAME"), getProperty("PASSWORD"));
    }

    @Test(dataProvider = "getAssetInformation")
    public void createUser(HashMap<String, String> assetData) throws SQLException {
        Response response = accountHelper.generateToken(getProperty("USERNAME"), getProperty("PASSWORD"));
        String name = assetData.get("NAME");
        String category = assetData.get("CATEGORY");
        String specification = assetData.get("SPECIFICATION");
        String installed_date = assetData.get("INSTALLED_DATE");
        String state = assetData.get("STATE");
        String location = response.jsonPath().getString("location");
        homePage.gotoManageAssetPage();
        manageAssetPage.clickCreateBtn();
        createAssetPage.inputAssetName(name);
        createAssetPage.selectCategory(category);
        createAssetPage.inputSpecification(specification);
        createAssetPage.inputInstalledDate(installed_date);
        createAssetPage.selectState(state);
        createAssetPage.clickSave();
        manageAssetPage.waitFirstRecordEnable();
        Integer total = getAssetListInDFollowLocation(location).size();
        JsonArray getUserListFollowLocationInDB = getAssetListInDFollowLocation(location);
        String assetCode = getUserListFollowLocationInDB.get(total-1).getAsJsonObject().get("assetCode").getAsString();
//        assertThat("Verify asset code", manageAssetPage.getAssetCodeFirstRecord(), equalTo(assetCode));
        assertThat("Verify asset name", manageAssetPage.getAssetNameFirstRecord(), equalTo(name));
        assertThat("Verify asset category", manageAssetPage.getCategoryFirstRecord(), equalTo(category));
//        assertThat("Verify asset state", manageAssetPage.getStateFirstRecord(), equalTo(state));
        manageAssetPage.seeFirstRecordInfo();
//        assertThat("Verify asset code", detailAssetInformation.getAssetCode(), equalTo(assetCode));
        assertThat("Verify asset name", detailAssetInformation.getAssetName(), equalTo(name));
        assertThat("Verify asset category", detailAssetInformation.getAssetCategory(), equalTo(category));
        assertThat("Verify asset installed date", detailAssetInformation.getAssetInstalledDate(), equalTo(installed_date));
//        assertThat("Verify asset state", detailAssetInformation.getAssetState(), equalTo(state));
        assertThat("Verify asset location", detailAssetInformation.getAssetLocation(), equalTo(location));
        assertThat("Verify asset specification", detailAssetInformation.getAssetSpecification(), equalTo(specification));

    }

    @DataProvider(name = "getAssetInformation")
    public Object[][] getAssetData() {
        String path = "src/test/resources/testdata/asset_create/asset_create_form.json";
        return JsonUtil.getData(path);
    }

}
