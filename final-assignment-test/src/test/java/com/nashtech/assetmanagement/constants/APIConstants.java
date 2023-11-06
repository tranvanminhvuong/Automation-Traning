package com.nashtech.assetmanagement.constants;

public class APIConstants {
    public enum RequestType {
        GET, POST, PUT, DELETE, PATCH
    }

    // Manage User
    public static String ASSET_MANAGE_HOST = "https://asset-management-team3-group1.azurewebsites.net";
    public static String PUBLIC_ACCOUNT_USERNAME = "trungdx";
    public static String PUBLIC_ACCOUNT_PASSWORD = "180301Trung";
    public static String CREATE_USER_ENDPOINT = "/api/User";
    public static String GENERATE_TOKEN_ENDPOINT = "/api/Security/login";
    public static String GET_USER_ENDPOINT = "/api/User?LocationId=%s";
    public static String SEARCH_USER_ENDPOINT = "/api/user?page=%d&limit=%d&Ascending=true&sortBy=SatffCode&locationId=%s&keyword=%s";
    public static String GET_USER_LOCATION_ENDPOINT = "/api/user?page=%d&limit=%d&Ascending=true&sortBy=SatffCode&locationId=%s";
    // Manage Asset
    public static String GET_ASSET_ENDPOINT = "/api/asset?LocationId=%s";
    public static String CREATE_ASSET_ENDPOINT = "/api/Asset";
    public static String SEARCH_ASSET_ENDPOINT = "/api/asset?page=%d&limit=%d&Ascending=true&sortBy=AssetCode&locationId=%s&keyword=%s";
    public static String GET_ASSET_LOCATION_ENDPOINT = "/api/asset?page=%d&limit=%d&Ascending=true&sortBy=AssetCode&locationId=%s";

    // Manage Assignment
    public static String GET_ASSIGNMENT_LOCATION_ENDPOINT = "/api/assignment?page=%d&limit=%d&Ascending=true&sortBy=AssetCode&locationId=%s";
    public static String SEARCH_ASSIGNMENT_ENDPOINT = "/api/assignment?page=%d&limit=%d&Ascending=true&sortBy=AssetCode&locationId=%s&keyword=%s";
    public static String FILTER_ASSIGNMENT_ENDPOINT = "/api/assignment?page=1&limit=5&Ascending=true&sortBy=&locationId=%s&State%5B0%5D=%d";

}
