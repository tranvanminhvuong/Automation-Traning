package com.nashtech.assetmanagement.helpers;

import com.nashtech.assetmanagement.constants.APIConstants;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

import static com.nashtech.assetmanagement.helpers.RequestHelper.getHeader;

public class AssetHelper {
    public String prefixUrl = APIConstants.ASSET_MANAGE_HOST;

    public Response getAssetInPage(String userToken, int page, int limit, String locaton) {

        String url = prefixUrl + String.format(APIConstants.GET_ASSET_LOCATION_ENDPOINT, page, limit, locaton);
        System.out.println(url);
        return new RequestHelper(APIConstants.RequestType.GET, getHeader(userToken), url).sendRequest();
    }

    public Response searchAsset(String userToken, int page, int limit, String locaton, String keyWord) {

        String url = prefixUrl + String.format(APIConstants.SEARCH_ASSET_ENDPOINT, page, limit, locaton, keyWord);
        System.out.println(url);
        return new RequestHelper(APIConstants.RequestType.GET, getHeader(userToken), url).sendRequest();
    }

    public Response createAsset(String userToken, String assetName, String specification, String installedDay, String categoryID, int assetState, String locationID) {

        String url = prefixUrl + APIConstants.CREATE_ASSET_ENDPOINT;
        JSONObject body = new JSONObject();
        body.put("assetName", assetName);
        body.put("specification", specification);
        body.put("installedDay", installedDay);
        body.put("categoryID", categoryID);
        body.put("assetState", assetState);
        body.put("locationID", locationID);
        return new RequestHelper(APIConstants.RequestType.POST, getHeader(userToken), body.toString(), url).sendRequest();
    }

}
