package com.nashtech.assetmanagement.tests;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
;
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
import static com.nashtech.assetmanagement.repos.ManageUserFunction.getUserList;
import static com.nashtech.assetmanagement.repos.ManageUserFunction.getUserListInDFollowLocation;
import static java.lang.System.getProperty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest extends BaseTest {
    LoginPage loginPage;
    CreateUserPage createUserPage;
    DetailUserInformation detailUserInformation;
    HomePage homePage;
    ManageUserPage manageUserPage;
    AccountHelper accountHelper;
    @BeforeMethod
    public void beforeCreate() {
        loginPage = new LoginPage();
        manageUserPage = new ManageUserPage();
        accountHelper = new AccountHelper();
        createUserPage = new CreateUserPage();
        homePage = new HomePage();
        detailUserInformation = new DetailUserInformation();
        BasePage.navigate(UrlConstants.LOGIN_PAGE);
        loginPage.loginWithValidAccount(getProperty("USERNAME"), getProperty("PASSWORD"));
        homePage.gotoManageUserPage();
    }

    @Test(dataProvider = "getUserInformation")
    public void createUser(HashMap<String, String> userData) throws InterruptedException, SQLException {
        manageUserPage.clickCreateBtn();
        Response response = accountHelper.generateToken(getProperty("USERNAME"), getProperty("PASSWORD"));
        String firstName = userData.get("FIRSTNAME");
        String lastName = userData.get("LASTNAME");
        String dateOfBirth = userData.get("DATEOFBIRTH");
        String gender = userData.get("GENDER");
        String joinDate = userData.get("JOINDATE");
        String type = userData.get("TYPE");
        String location = response.jsonPath().getString("location");
        createUserPage.inputFirstName(firstName);
        createUserPage.inputLastName(lastName);
        createUserPage.inputBirthday(dateOfBirth);
        createUserPage.selectGender(gender);
        createUserPage.inputJoindate(joinDate);
        createUserPage.selectType(type);
        createUserPage.clickSubmit();
        manageUserPage.waitFirstRecordEnable();
        Integer total = getUserListInDFollowLocation(location).size();
        JsonArray getUserListFollowLocationInDB = getUserListInDFollowLocation(location);
        String staffCode = getUserListFollowLocationInDB.get(total-1).getAsJsonObject().get("staffCode").getAsString();
        assertThat("Verify staff code", manageUserPage.getStaffCodeFirstRecord(), equalTo(staffCode));
        assertThat("Verify full name", manageUserPage.getFullNameFirstRecord(), equalTo(firstName + " " + lastName));
        assertThat("Verify joined date", manageUserPage.getJoinedDateFirstRecord(), equalTo(joinDate));
        assertThat("Verify type", manageUserPage.getTypeFirstRecord(), equalTo(type));
        manageUserPage.seeFirstRecordInfo();
        assertThat("Verify staff code", detailUserInformation.getStaffCode(), equalTo(staffCode));
        assertThat("Verify full name", detailUserInformation.getFullname(), equalTo(firstName + " " + lastName));
        assertThat("Verify date of birth", detailUserInformation.getDateOfBirth(), equalTo(dateOfBirth));
        assertThat("Verify gender", detailUserInformation.getGender(), equalTo(gender));
        assertThat("Verify join date", detailUserInformation.getJoinDate(), equalTo(joinDate));
        assertThat("Verify type", detailUserInformation.getType(), equalTo(type));
        assertThat("Verify location", detailUserInformation.getLocation(), equalTo(location));

    }

    @DataProvider(name = "getUserInformation")
    public Object[][] getUserData() {
        String path = "src/test/resources/testdata/user_registration/user_registration_form.json";
        return JsonUtil.getData(path);
    }


}
