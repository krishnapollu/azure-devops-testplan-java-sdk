package com.testautomation.azure.services;

import com.testautomation.azure.entity.AzureConfig;
import com.testautomation.azure.entity.TestResultEntity;
import com.testautomation.azure.entity.TestRunEntity;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;

public class TestResult {

    public static TestResultEntity add(TestResultEntity testResultEntity, TestRunEntity testRunEntity) {

        RequestSpecification httpRequest = RestAssured.given().log().everything()
                .auth().preemptive().basic("", AzureConfig.accessToken)
                .contentType("application/json").accept("application/json;charset=utf-8");

        testResultEntity.setStartedDate(Instant.now().minusMillis(testResultEntity.getDurationInMs()).toString());
        testResultEntity.setCompletedDate(Instant.now().toString());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("testCaseTitle", testResultEntity.getTestCaseTitle());
        jsonObject.put("outcome", testResultEntity.getOutcome());
        jsonObject.put("state", "Completed");
        jsonObject.put("comment", testResultEntity.getComment());
        jsonObject.put("completedDate", testResultEntity.getCompletedDate());
        jsonObject.put("startedDate", testResultEntity.getStartedDate());
        jsonObject.put("durationInMs", testResultEntity.getDurationInMs());
        if(testResultEntity.getErrorMessage().length()>0)
            jsonObject.put("errorMessage", testResultEntity.getErrorMessage());
        if(testResultEntity.getStacktrace().length()>0)
            jsonObject.put("stackTrace", testResultEntity.getStacktrace());

        JSONObject testPoint = new JSONObject();
        testPoint.put("id", testResultEntity.getTestPointId());
        jsonObject.put("testPoint", testPoint);

        JSONObject testCase = new JSONObject();
        testCase.put("id", testResultEntity.getTestCaseId());
        jsonObject.put("testCase", testCase);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);

        System.out.println(jsonArray.toString());
        String endpoint = AzureConfig.baseURL+"test/Runs/"+ testRunEntity.getId()+"/results?api-version=5.0";
        Response response = httpRequest.body(jsonArray.toString()).post(endpoint);
        System.out.println("Status Code : "+response.statusCode());
        response.prettyPrint();
        testResultEntity.setId( response.body().jsonPath().getInt("value.id[0]"));
        return testResultEntity;
    }

    public static TestResultEntity update(TestResultEntity testResultEntity, TestRunEntity testRunEntity) {

        RequestSpecification httpRequest = RestAssured.given().log().everything()
                .auth().preemptive().basic("", AzureConfig.accessToken)
                .contentType("application/json").accept("application/json;charset=utf-8");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", testResultEntity.getId());
        jsonObject.put("testCaseTitle", testResultEntity.getTestCaseTitle());
        jsonObject.put("outcome", testResultEntity.getOutcome());
        jsonObject.put("state", "Completed");
        if(testResultEntity.getErrorMessage().length()>0)
            jsonObject.put("errorMessage", testResultEntity.getErrorMessage());

        JSONObject testPoint = new JSONObject();
        testPoint.put("id", testResultEntity.getTestPointId());
        jsonObject.put("testPoint", testPoint);

        JSONObject testCase = new JSONObject();
        testCase.put("id", testResultEntity.getTestCaseId());
        jsonObject.put("testCase", testCase);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);

        System.out.println(jsonArray.toString());
        String endpoint = AzureConfig.baseURL+"test/Runs/"+ testRunEntity.getId()+"/results?api-version=5.0";
        Response response = httpRequest.body(jsonArray.toString()).patch(endpoint);
        System.out.println("Status Code : "+response.statusCode());
        response.prettyPrint();
        testResultEntity.setId( response.body().jsonPath().getInt("value.id[0]"));
        return testResultEntity;
    }
}
