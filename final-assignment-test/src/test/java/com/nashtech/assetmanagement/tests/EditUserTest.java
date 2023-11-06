package com.nashtech.assetmanagement.tests;

import com.google.gson.JsonObject;
import com.nashtech.assetmanagement.constants.APIConstants;
import com.nashtech.assetmanagement.constants.UrlConstants;
import com.nashtech.assetmanagement.helpers.AccountHelper;
import com.nashtech.assetmanagement.pages.*;
import com.nashtech.assetmanagement.utils.JsonUtil;
import io.restassured.response.Response;
import org.json.simple.parser.JSONParser;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import static com.nashtech.assetmanagement.utils.JsonUtil.readJsonFile;
import static java.lang.System.getProperty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class EditUserTest extends BaseTest {
    LoginPage loginPage;
    EditUserPage editUserPage;
    DetailUserInformation detailUserInformation;
    AccountHelper accountHelper;
    HomePage homePage;
    ManageUserPage manageUserPage;

    @BeforeMethod
    public void beforeCreate(ITestContext context) {
        loginPage = new LoginPage();
        manageUserPage = new ManageUserPage();
        accountHelper = new AccountHelper();
        Response response = accountHelper.generateToken(APIConstants.PUBLIC_ACCOUNT_USERNAME, APIConstants.PUBLIC_ACCOUNT_PASSWORD);
        String token = response.jsonPath().getString("token");
        JsonObject jsonObject = readJsonFile("src/test/resources/testdata/user_registration/user_registration_api.json");
        response = accountHelper.createUser(token, jsonObject.get("firstName").getAsString(), jsonObject.get("lastName").getAsString(), jsonObject.get("dateOfBirth").getAsString(), jsonObject.get("gender").getAsString(), jsonObject.get("joinedDate").getAsString(), jsonObject.get("type").getAsString(), response.jsonPath().getString("location"));
        context.setAttribute("staffCode", response.jsonPath().getString("staffCode"));
        BasePage.navigate(UrlConstants.LOGIN_PAGE);
        loginPage.loginWithValidAccount(getProperty("USERNAME"), getProperty("PASSWORD"));
    }

    @Test(dataProvider = "getData")
    public void editUser(ITestContext context, HashMap<String, String> hashMap) throws InterruptedException {
        editUserPage = new EditUserPage();
        homePage = new HomePage();
        detailUserInformation = new DetailUserInformation();
        BasePage.navigate(UrlConstants.LOGIN_PAGE);
        loginPage.loginWithValidAccount(getProperty("USERNAME"), getProperty("PASSWORD"));
        homePage.gotoManageUserPage();
        String staffCode = (String) context.getAttribute("staffCode");
        String dateOfBirth = editData(hashMap.get("DATEOFBIRTH"));
        String gender = editData(hashMap.get("GENDER"));
        String joinDate = editData(hashMap.get("JOINDATE"));
        String type = editData(hashMap.get("TYPE"));
        manageUserPage.searchUserWithCriteria(staffCode);
        manageUserPage.clickEditUser();
        editUserPage.inputBirthday(dateOfBirth);
        editUserPage.selectGender(gender);
        editUserPage.inputJoindate(joinDate);
        editUserPage.selectType(type);
        editUserPage.clickSubmit();
        manageUserPage.seeFirstRecordInfo();
        assertThat("Verify date of birth", detailUserInformation.getDateOfBirth(), equalTo(dateOfBirth));
        assertThat("Verify gender", detailUserInformation.getGender(), equalTo(gender));
        assertThat("Verify join date", detailUserInformation.getJoinDate(), equalTo(joinDate));
        assertThat("Verify type", detailUserInformation.getType(), equalTo(type));
    }

    @DataProvider(name = "getData")
    public Object[][] getData() throws FileNotFoundException {
        JSONParser parser = new JSONParser();
        JsonObject jsonObject;

        Object object = JsonUtil.readJsonFile("src/test/resources/testdata/edit_user/data_edit.json");
        jsonObject = (JsonObject) object;

        Object[] data = new Object[1];
        HashMap<String, String> hashMap = new LinkedHashMap<>();
        Set<String> jsonObjKeys = jsonObject.keySet();
        for (String jsonObjKey : jsonObjKeys) {
            hashMap.put(jsonObjKey, String.valueOf(jsonObject.get(jsonObjKey)));
        }
        data[0] = hashMap;
        return new Object[][]{data};
    }

    public String editData(String data) {
        return data = data.replaceAll("\"", "");
    }
}
