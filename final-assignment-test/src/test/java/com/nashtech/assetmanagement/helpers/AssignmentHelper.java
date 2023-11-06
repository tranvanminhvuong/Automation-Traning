package com.nashtech.assetmanagement.helpers;

import com.nashtech.assetmanagement.constants.APIConstants;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

import static com.nashtech.assetmanagement.helpers.RequestHelper.getHeader;

public class AssignmentHelper {
    public String prefixUrl = APIConstants.ASSET_MANAGE_HOST;

    public Response getAssignmentInPage(String userToken, int page, int limit, String locaton) {

        String url = prefixUrl + String.format(APIConstants.GET_ASSIGNMENT_LOCATION_ENDPOINT, page, limit, locaton);
        return new RequestHelper(APIConstants.RequestType.GET, getHeader(userToken), url).sendRequest();
    }

    public Response searchAssignment(String userToken, int page, int limit, String location, String keyWord) {

        String url = prefixUrl + String.format(APIConstants.SEARCH_ASSIGNMENT_ENDPOINT, page, limit, location, keyWord);
        return new RequestHelper(APIConstants.RequestType.GET, getHeader(userToken), url).sendRequest();
    }

    public Response filterAssignment(String userToken, String location, int state) {

        String url = prefixUrl + String.format(APIConstants.FILTER_ASSIGNMENT_ENDPOINT, location, state);
        return new RequestHelper(APIConstants.RequestType.GET, getHeader(userToken), url).sendRequest();
    }

}
