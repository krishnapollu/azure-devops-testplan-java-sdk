package com.testautomation.azure.services;

import com.testautomation.azure.entity.AzureConfig;
import com.testautomation.azure.entity.TestRunEntity;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

public class TestRun {

    static String endpoint = AzureConfig.baseURL+"test/runs?api-version=5.0";

    public static TestRunEntity create(TestRunEntity testRunEntity){

        RequestSpecification httpRequest = RestAssured.given().log().everything()
                .auth().preemptive().basic("", AzureConfig.accessToken)
                .contentType("application/json").accept("application/json;charset=utf-8");

        JSONObject jsonObject = new JSONObject();
        if(testRunEntity.getName().length() == 0)
            jsonObject.put("name", "AutoRun_"+System.currentTimeMillis());
        else
            jsonObject.put("name", testRunEntity.getName());
        jsonObject.put("type", "Automated");
        jsonObject.put("isAutomated", "true");

        JSONObject plan = new JSONObject();
        plan.put("id", testRunEntity.getTestPlanId());
        jsonObject.put("plan", plan);
        System.out.println(jsonObject.toString());

        Response response = httpRequest.body(jsonObject.toString()).post(endpoint);
        System.out.println("Status Code : "+response.statusCode());
        response.prettyPrint();
        testRunEntity.setId( response.body().jsonPath().getInt("id"));
        return testRunEntity;
    }

    public static TestRunEntity update(TestRunEntity testRunEntity, String state, String comment){

        RequestSpecification httpRequest = RestAssured.given().log().everything()
                .auth().preemptive().basic("", AzureConfig.accessToken)
                .contentType("application/json").accept("application/json;charset=utf-8");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", state);
        jsonObject.put("comment", comment);
        System.out.println(jsonObject.toString());

        String endpoint = AzureConfig.baseURL+"test/runs/"+ testRunEntity.getId()+"?api-version=5.0";
        Response response = httpRequest.body(jsonObject.toString()).patch(endpoint);
        System.out.println("Status Code : "+response.statusCode());
        testRunEntity.setId( response.body().jsonPath().getInt("id"));
        return testRunEntity;

    }

    public static Integer getTotalTestCount(TestRunEntity testRunEntity){

        RequestSpecification httpRequest = RestAssured.given().log().everything()
                .auth().preemptive().basic("", AzureConfig.accessToken)
                .contentType("application/json").accept("application/json;charset=utf-8");

        String endpoint = AzureConfig.baseURL+"test/runs/"+ testRunEntity.getId()+"?api-version=5.0";
        Response response = httpRequest.get(endpoint);
        System.out.println("Status Code : "+response.statusCode());
        int value = response.body().jsonPath().getInt("totalTests");
        return value;
    }

    public static Integer getTotalPassCount(TestRunEntity testRunEntity){

        RequestSpecification httpRequest = RestAssured.given().log().everything()
                .auth().preemptive().basic("", AzureConfig.accessToken)
                .contentType("application/json").accept("application/json;charset=utf-8");

        String endpoint = AzureConfig.baseURL+"test/runs/"+ testRunEntity.getId()+"?api-version=5.0";
        Response response = httpRequest.get(endpoint);
        System.out.println("Status Code : "+response.statusCode());
        int value = response.body().jsonPath().getInt("passedTests");
        return value;
    }
    
}
