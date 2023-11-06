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
import java.util.*;
import static com.nashtech.assetmanagement.utils.JsonUtil.readJsonFile;
import static java.lang.System.getProperty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateAssignmentTest extends BaseTest{
    LoginPage loginPage;
    AccountHelper accountHelper;
    AssetHelper assetHelper;
    ManageAssignmentPage manageAssignment;
    SelectUserPage selectUserPage;
    SelectAssetPage selectAssetPage;
    HomePage homePage;
    CreateAssignmentPage createAssignmentPage;
    AssignmentDetailPage assignmentDetailPage;

    @BeforeMethod
    public void beforeCreate(ITestContext context) {
        loginPage = new LoginPage();
        manageAssignment = new ManageAssignmentPage();
        accountHelper = new AccountHelper();
        assetHelper = new AssetHelper();
        selectAssetPage = new SelectAssetPage();
        selectUserPage = new SelectUserPage();
        homePage = new HomePage();
        createAssignmentPage = new CreateAssignmentPage();
        assignmentDetailPage = new AssignmentDetailPage();
        Response response = accountHelper.generateToken(APIConstants.PUBLIC_ACCOUNT_USERNAME, APIConstants.PUBLIC_ACCOUNT_PASSWORD);
        String token = response.jsonPath().getString("token");
        JsonObject jsonObject = readJsonFile("src/test/resources/testdata/assignment_create/assignment_create_data.json");
        Response userResponse = accountHelper.createUser(token, jsonObject.get("firstName").getAsString(), jsonObject.get("lastName").getAsString(), jsonObject.get("dateOfBirth").getAsString(), jsonObject.get("gender").getAsString(), jsonObject.get("joinedDate").getAsString(), jsonObject.get("type").getAsString(), response.jsonPath().getString("location"));
        context.setAttribute("staffCode", userResponse.jsonPath().getString("staffCode"));
        context.setAttribute("userName",userResponse.jsonPath().getString("userName"));

        Response assetResponse = assetHelper.createAsset(token, jsonObject.get("assetName").getAsString(), jsonObject.get("specification").getAsString(), jsonObject.get("installedDay").getAsString(), jsonObject.get("categoryID").getAsString(), jsonObject.get("assetState").getAsInt(), response.jsonPath().getString("location"));
        Map<String, Object> responseObj = assetResponse.jsonPath().get("result");
        context.setAttribute("assetCode", responseObj.get("assetCode").toString());
        context.setAttribute("assetName", responseObj.get("assetName").toString());
        context.setAttribute("specification", responseObj.get("specification").toString());
        context.setAttribute("assignedDate", jsonObject.get("assignedDate").getAsString());
        context.setAttribute("note", jsonObject.get("note").getAsString());
        BasePage.navigate(UrlConstants.LOGIN_PAGE);
        loginPage.loginWithValidAccount(getProperty("USERNAME"), getProperty("PASSWORD"));
        homePage.gotoManageAssignmentPage();
    }
    @Test
    public void createAssignment(ITestContext context){
        manageAssignment.clickCreateBtn();
        createAssignmentPage.clickToSelectUser();
        selectUserPage.searchKey(context.getAttribute("staffCode").toString());
        selectUserPage.selectFirstRecord(context.getAttribute("staffCode").toString());
        selectUserPage.clickSave();
        createAssignmentPage.clickToSelectAsset();
        selectAssetPage.searchKey(context.getAttribute("assetCode").toString());
        selectAssetPage.selectFirstRecord();
        selectAssetPage.clickSave();
        createAssignmentPage.inputAssignedDate(context.getAttribute("assignedDate").toString());
        createAssignmentPage.inputNote(context.getAttribute("note").toString());
        createAssignmentPage.clickSave();
        assertThat("Verify asset code", manageAssignment.getAssetCodeFirstRecord(), equalTo(context.getAttribute("assetCode")));
        assertThat("Verify asset name", manageAssignment.getAssetNameFirstRecord(), equalTo(context.getAttribute("assetName")));
        assertThat("Verify assigned to", manageAssignment.getAssignToFirstRecord(), equalTo(context.getAttribute("userName")));
        assertThat("Verify assigned by", manageAssignment.getAssignByFirstRecord(), equalTo(getProperty("USERNAME")));
        assertThat("Verify assigned date", manageAssignment.getAssignDateFirstRecord(), equalTo(context.getAttribute("assignedDate")));
        assertThat("Verify state", manageAssignment.getStateFirstRecord(), equalTo("Waiting for acceptance"));
        manageAssignment.clickOnFirstRecord();
        assertThat("Verify asset code", assignmentDetailPage.getAssetCode(), equalTo(context.getAttribute("assetCode")));
        assertThat("Verify asset name", assignmentDetailPage.getAssetName(), equalTo(context.getAttribute("assetName")));
        assertThat("Verify specification", assignmentDetailPage.getSpecification(), equalTo(context.getAttribute("specification")));
        assertThat("Verify assigned to", assignmentDetailPage.getAssignedTo(), equalTo(context.getAttribute("userName")));
        assertThat("Verify assigned by", assignmentDetailPage.getAssignedBy(), equalTo(getProperty("USERNAME")));
        assertThat("Verify assigned date", assignmentDetailPage.getAssignedDate(), equalTo(context.getAttribute("assignedDate")));
        assertThat("Verify state", assignmentDetailPage.getState(), equalTo("Waiting for acceptance"));
        assertThat("Verify note", assignmentDetailPage.getNote(), equalTo(context.getAttribute("note")));

    }

}
