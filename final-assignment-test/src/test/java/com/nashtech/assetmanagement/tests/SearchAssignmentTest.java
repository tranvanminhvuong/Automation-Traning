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

import static com.nashtech.assetmanagement.repos.ManageAssignmentFunction.*;
import static com.nashtech.assetmanagement.utils.JsonUtil.readJsonFile;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SearchAssignmentTest extends BaseTest {
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
        Response response = accountHelper.generateToken(System.getProperty("USERNAME_HCM"), System.getProperty("PASSWORD_HCM"));
        String userToken = response.jsonPath().getString("token");
        String location = response.jsonPath().getString("location");
        context.setAttribute("userToken", userToken);
        context.setAttribute("location", location);
        loginPage = new LoginPage();
        navigationPage = new NavigationPage();
        homePage = new HomePage();
        manageAssignment = new ManageAssignmentPage();
        BasePage.navigate(UrlConstants.LOGIN_PAGE);
        loginPage.loginWithValidAccount(System.getProperty("USERNAME_HCM"), System.getProperty("PASSWORD_HCM"));
    }

    @Test(dataProvider = "getData")
    public void searchAssignmentListWithCriteria(ITestContext context, JsonObject jsonObject) throws InterruptedException, SQLException {
        String userToken = (String) context.getAttribute("userToken");
        String criteria = jsonObject.get("criteria").getAsString();
        homePage.waitTabDisplayed();
        homePage.gotoManageAssignmentPage();
        String location = (String) context.getAttribute("location");
        int limit = Integer.parseInt(System.getProperty("ITEM_PAGE"));
        manageAssignment.searchAssignmentWithCriteria(criteria);
        Thread.sleep(2000);
        manageAssignment.clickSortFollowAssetCode();
        Thread.sleep(1000);

        ResultSet getAssignmentListFollowLocationInDB = searchAssignmentListInDFollowLocation(location, criteria);
        // backup data
        ResultSet resultSet = searchAssignmentListInDFollowLocation(location, criteria);


        Response response = assignmentHelper.searchAssignment(userToken, 1, limit, location, criteria);
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

    @DataProvider(name = "getData")
    public Object[][] getAdminOfHCM() throws FileNotFoundException {
        JsonObject jsonObject = readJsonFile("src/test/resources/testdata/search_assignment/data_search.json");
        JsonArray jsonArray = jsonObject.getAsJsonArray("dataSearch");
        Object[][] objects = new Object[jsonArray.size()][1];
        for (int i = 0; i < jsonArray.size(); i++) {
            objects[i][0] = jsonArray.get(i).getAsJsonObject();
        }
        return objects;
    }
}
