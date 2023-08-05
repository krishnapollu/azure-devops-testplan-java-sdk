package com.testautomation.azure.services;

import com.testautomation.azure.entity.AzureConfig;
import com.testautomation.azure.entity.TestCaseEntity;
import com.testautomation.azure.entity.TestPointEntity;
import com.testautomation.azure.entity.TestSuiteEntity;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;


public class TestPoint {

    static RequestSpecification httpRequest = RestAssured.given().log().everything()
            .auth().preemptive().basic("", AzureConfig.accessToken)
            .contentType("application/json").accept("application/json;charset=utf-8");

    public static TestPointEntity get(TestCaseEntity testCaseEntity, TestSuiteEntity testSuiteEntity){

        String endpoint = AzureConfig.baseURL+"test/Plans/"+ testSuiteEntity.getPlan_id()+"/Suites/"+ testSuiteEntity.getId()+"/points?testcaseid="+ testCaseEntity.getId()+"&api-version=7.0";
        Response response = httpRequest.get(endpoint);
        System.out.println("Status : "+response.statusCode());
        response.prettyPrint();
        TestPointEntity testPointEntity = new TestPointEntity();
        testPointEntity.setId(response.body().jsonPath().getInt("value.id[0]"));
        return testPointEntity;
    }

    public static List<TestPointEntity> getList(Integer testPlanId, Integer testSuiteId){

        String endpoint = AzureConfig.baseURL+"test/Plans/"+ testPlanId+"/Suites/"+ testSuiteId+"/points?api-version=5.0";

        Response response = httpRequest.get(endpoint);
        System.out.println("Status : "+response.statusCode());
        response.prettyPrint();

        JsonPath jsonPathEvaluator = response.jsonPath();
        List<TestPointEntity> points = jsonPathEvaluator.getList("value", TestPointEntity.class);
        return points;
    }
}
