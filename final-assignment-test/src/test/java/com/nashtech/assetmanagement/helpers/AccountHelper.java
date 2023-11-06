package com.nashtech.assetmanagement.helpers;

import com.nashtech.assetmanagement.constants.APIConstants;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

import static com.nashtech.assetmanagement.helpers.RequestHelper.getHeader;

public class AccountHelper {
    public String prefixUrl = APIConstants.ASSET_MANAGE_HOST;

    public Response getUserInPage(String userToken, int page, int limit, String locaton) {

        String url = prefixUrl + String.format(APIConstants.GET_USER_LOCATION_ENDPOINT, page, limit, locaton);
        return new RequestHelper(APIConstants.RequestType.GET, getHeader(userToken), url).sendRequest();
    }

    public Response searchUser(String userToken, int page, int limit, String locaton, String keyWord) {

        String url = prefixUrl + String.format(APIConstants.SEARCH_USER_ENDPOINT, page, limit, locaton, keyWord);
        return new RequestHelper(APIConstants.RequestType.GET, getHeader(userToken), url).sendRequest();
    }

    public Response createUser(String userToken, String firstName, String lastName, String dateOfBirth, String gender, String joinedDate, String type, String location) {

        String url = prefixUrl + APIConstants.CREATE_USER_ENDPOINT;
        JSONObject body = new JSONObject();
        body.put("firstName", firstName);
        body.put("lastName", lastName);
        body.put("dateOfBirth", dateOfBirth);
        body.put("gender", gender);
        body.put("joinedDate", joinedDate);
        body.put("type", type);
        body.put("locationID", location);
        return new RequestHelper(APIConstants.RequestType.POST, getHeader(userToken), body.toString(), url).sendRequest();
    }

    public Response generateToken(String userName, String password) {
        String url = prefixUrl + APIConstants.GENERATE_TOKEN_ENDPOINT;
        JSONObject body = new JSONObject();
        body.put("userName", userName);
        body.put("password", password);
        Response response = new RequestHelper(APIConstants.RequestType.POST, body.toString(), url).sendRequest();
        return response;
    }

}
