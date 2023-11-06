package com.nashtech.assetmanagement.helpers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.nashtech.assetmanagement.constants.APIConstants;
import com.nashtech.assetmanagement.constants.APIConstants.RequestType;

import java.util.HashMap;

public class RequestHelper {
    public final APIConstants.RequestType method;
    private Object body = "";
    private final String url;
    public HashMap<String, String> headers = new HashMap<>();
    final RequestSpecification requestSpecification = RestAssured.given();

    public RequestHelper(RequestType method, HashMap<String, String> headers, Object body, String url) {
        this.method = method;
        this.body = body;
        this.headers = headers;
        this.url = url;
    }

    public RequestHelper(RequestType method, Object body, String url) {
        this.method = method;
        this.body = body;
        this.url = url;
    }

    public RequestHelper(RequestType method, String url) {
        this.method = method;
        this.url = url;
    }

    public RequestHelper(RequestType method, HashMap<String, String> headers, String url) {
        this.method = method;
        this.headers = headers;
        this.url = url;
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public static HashMap<String, String> getHeader(String token) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json ");
        headers.put("authorization", "Bearer " + token);
        return headers;
    }

    public static Headers getHeaders() {
        return new Headers(new Header("Content-Type", "application/json"));
    }

    public Response sendRequest() {
        Response response = null;
        System.setProperty("com.sun.security.enableAIAcaIssues", "true");
        switch (method) {
        case POST:
            if (null == this.body || this.body.toString().isEmpty()) {
                response = this.requestSpecification.contentType(ContentType.JSON).headers(headers).when()
                        .post(this.url);
            } else {
                response = this.requestSpecification.contentType(ContentType.JSON).headers(headers)
                        .body((body.toString())).when().post(url);
            }
            break;
        case PUT:
            response = this.requestSpecification.contentType(ContentType.JSON).headers(headers).body(body).when()
                    .put(url);
            break;
        case PATCH:
            response = this.requestSpecification.contentType(ContentType.JSON).headers(headers).body(body).when()
                    .patch(url);
            break;
        case DELETE:
            response = this.requestSpecification.contentType(ContentType.JSON).headers(headers).body(body).when()
                    .delete(url);
            break;
        default:
            response = this.requestSpecification.contentType(ContentType.JSON).headers(headers).body(body).when()
                    .get(url);

        }
        System.out.println(String.format("URL: %s\n", url));
        System.out.println(String.format("The request is sent with: %s\n", body.toString()));
        System.out.println(
                String.format("Teh response is return with \nStatuscode: %s\nResponse Body:", response.statusCode()));
        response.prettyPrint();
        return response;
    }

    public static void verifySchema(Response response, String path) {
        JsonSchemaValidator validator = JsonSchemaValidator.matchesJsonSchemaInClasspath(path);
        response.then().assertThat().body(validator);
    }

}
