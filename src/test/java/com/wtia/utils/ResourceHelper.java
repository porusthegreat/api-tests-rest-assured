package com.wtia.utils;

import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class ResourceHelper {

    public static Response get(String url) {
        return given()
                .when()
                .log()
                .all()
                .get(url);
    }

    public static Response get(String url, HashMap<String, String> params) {
        return given()
                .params(params)
                .when()
                .log().all()
                .get(url);
    }

}
