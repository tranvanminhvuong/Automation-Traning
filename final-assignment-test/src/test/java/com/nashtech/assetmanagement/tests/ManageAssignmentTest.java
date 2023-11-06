package com.nashtech.assetmanagement.tests;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nashtech.assetmanagement.constants.UrlConstants;
import com.nashtech.assetmanagement.helpers.AccountHelper;
import com.nashtech.assetmanagement.helpers.AssignmentHelper;
import com.nashtech.assetmanagement.pages.*;
import io.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.nashtech.assetmanagement.repos.ManageAssignmentFunction.getAssignmentListInDFollowLocation;
import static com.nashtech.assetmanagement.repos.ManageAssignmentFunction.getSize;
import static com.nashtech.assetmanagement.utils.JsonUtil.readJsonFile;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ManageAssignmentTest extends BaseTest {
    LoginPage loginPage;
    NavigationPage navigationPage;
    HomePage homePage;
    ManageAssignmentPage manageAssignment;
    AccountHelper accountHelper;
    AssignmentHelper assignmentHelper;

    @BeforeMethod
    public void beforeMethod(ITestContext context) {
        accountHelper = new AccountHelper();
        assignmentHelper = new AssignmentHelper();
        loginPage = new LoginPage();
        navigationPage = new NavigationPage();
        homePage = new HomePage();
        manageAssignment = new ManageAssignmentPage();
        BasePage.navigate(UrlConstants.LOGIN_PAGE);
    }

    @Test(dataProvider = "getAdminOfHCM")
    public void viewDefaultAssignmentListByAdminHCM(JsonObject jsonObject) throws InterruptedException, SQLException {
        Response response = accountHelper.generateToken(jsonObject.get("userName").getAsString(),
                jsonObject.get("password").getAsString());
        loginPage.loginWithValidAccount(jsonObject.get("userName").getAsString(),
                jsonObject.get("password").getAsString());
        String userToken = response.jsonPath().getString("token");
        String location = response.jsonPath().getString("location");
        homePage.waitTabDisplayed();
        homePage.gotoManageAssignmentPage();
        Thread.sleep(2000);
        manageAssignment.clickSortFollowAssetCode();
        Thread.sleep(2000);


        int limit = Integer.parseInt(System.getProperty("ITEM_PAGE"));
        ResultSet getAssignmentListFollowLocationInDB = getAssignmentListInDFollowLocation(location);
        //backup data
        ResultSet resultSet = getAssignmentListInDFollowLocation(location);
        response = assignmentHelper.getAssignmentInPage(userToken, 1, limit, location);
        int totalRecordsInUI = response.jsonPath().getInt("totalRecords");
        int totalRecordsInDB = getSize(getAssignmentListFollowLocationInDB);
        assertThat("Verify number of assignment list follow location ", totalRecordsInUI,
                equalTo(totalRecordsInDB));
        // data in ui
        JsonArray getAssignmentInUI = new JsonArray();
        int totalPage = manageAssignment.getTotalPage();
        int totalPageTest = 3;
        if (totalPage < 3)
            totalPageTest = totalPage;
        boolean check = true;
        if (totalPage == 1)
            check = false;

        for (int i = 1; i <= totalPageTest; i++) {
            response = assignmentHelper.getAssignmentInPage(userToken, i, limit, location);
            if (response.statusCode() == 200) {
                if (check)
                    manageAssignment.waitBtnActive(i);
                manageAssignment.getAssetCodeAfterSearch(getAssignmentInUI);
            }
        }

        int totalRecordsTest = 15;
        if (totalRecordsInDB < 15)
            totalRecordsTest = totalRecordsInDB;
        int i = 0;
        JsonObject record = new JsonObject();
        while (resultSet.next()) {
            record = getAssignmentInUI.get(i).getAsJsonObject();
            assertThat("Verify Asset code: ", record.get("assetCode").getAsString(), equalTo(resultSet.getString("assetCode")));
            if (i == totalRecordsTest - 1)
                break;
            i++;
        }
        Thread.sleep(2000);
    }

    @Test(dataProvider = "getAdminOfHN")
    public void viewDefaultAssignmentListByAdminHN(JsonObject jsonObject) throws InterruptedException, SQLException {
        Response response = accountHelper.generateToken(jsonObject.get("userName").getAsString(),
                jsonObject.get("password").getAsString());
        loginPage.loginWithValidAccount(jsonObject.get("userName").getAsString(),
                jsonObject.get("password").getAsString());
        String userToken = response.jsonPath().getString("token");
        String location = response.jsonPath().getString("location");
        homePage.waitTabDisplayed();
        homePage.gotoManageAssignmentPage();
        Thread.sleep(2000);
        manageAssignment.clickSortFollowAssetCode();
        Thread.sleep(2000);
        int limit = Integer.parseInt(System.getProperty("ITEM_PAGE"));
        ResultSet getAssignmentListFollowLocationInDB = getAssignmentListInDFollowLocation(location);

        //backup data
        ResultSet resultSet = getAssignmentListInDFollowLocation(location);
        response = assignmentHelper.getAssignmentInPage(userToken, 1, limit, location);
        int totalRecordsInUI = response.jsonPath().getInt("totalRecords");
        int totalRecordsInDB = getSize(getAssignmentListFollowLocationInDB);
        assertThat("Verify number of assignment list follow location ", totalRecordsInUI,
                equalTo(totalRecordsInDB));
        // data in ui
        JsonArray getAssignmentInUI = new JsonArray();
        int totalPage = manageAssignment.getTotalPage();
        int totalPageTest = 3;
        if (totalPage < 3)
            totalPageTest = totalPage;
        boolean check = true;
        if (totalPage == 1)
            check = false;

        for (int i = 1; i <= totalPageTest; i++) {
            response = assignmentHelper.getAssignmentInPage(userToken, i, limit, location);
            if (response.statusCode() == 200) {
                if (check)
                    manageAssignment.waitBtnActive(i);
                manageAssignment.getAssetCodeAfterSearch(getAssignmentInUI);
            }
        }

        int totalRecordsTest = 15;
        if (totalRecordsInDB < 15)
            totalRecordsTest = totalRecordsInDB;
        int i = 0;
        JsonObject record = new JsonObject();
        while (resultSet.next()) {
            record = getAssignmentInUI.get(i).getAsJsonObject();
            assertThat("Verify Asset code: ", record.get("assetCode").getAsString(), equalTo(resultSet.getString("assetCode")));
            if (i == totalRecordsTest - 1)
                break;
            i++;
        }
        Thread.sleep(2000);
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
