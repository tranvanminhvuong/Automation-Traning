package com.nashtech.assetmanagement.tests;

import com.google.gson.JsonObject;
import com.nashtech.assetmanagement.constants.UrlConstants;
import com.nashtech.assetmanagement.pages.*;
import com.nashtech.assetmanagement.utils.JsonUtil;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import static java.lang.System.getProperty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DisableUserTest extends BaseTest {
    LoginPage loginPage;
    HomePage homePage;
    ManageUserPage manageUserPage;
    ModelDialogPage disableUserPage;

    @BeforeMethod
    public void beforeCreate() {
        loginPage = new LoginPage();
        manageUserPage = new ManageUserPage();
        disableUserPage = new ModelDialogPage();
        homePage = new HomePage();
        BasePage.navigate(UrlConstants.LOGIN_PAGE);
        loginPage.loginWithValidAccount(getProperty("USERNAME"), getProperty("PASSWORD"));
    }

    @Test(dataProvider = "getUserInformation")
    public void createUser(HashMap<String, String> hashMap) throws InterruptedException {
        homePage.gotoManageUserPage();
        manageUserPage.searchUserWithCriteria(editData(hashMap.get("STAFFCODE")));
        manageUserPage.clickDisableUser();
        disableUserPage.clickConfirm();
        manageUserPage.searchUserWithCriteria(editData(hashMap.get("STAFFCODE")));
        assertThat("Verify type", manageUserPage.getMessageNoRecord(), equalTo("No data found"));
    }

    @DataProvider(name = "getUserInformation")
    public Object[][] getData() {

        JsonObject jsonObject;

        Object object = JsonUtil
                .readJsonFile("src/test/resources/testdata/disable_user/disable_user.json");
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
        return data.replaceAll("\"", "");
    }
}
