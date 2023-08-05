package com.testautomation.azure.services;

import com.testautomation.azure.entity.AzureConfig;
import com.testautomation.azure.entity.TestCaseEntity;
import com.testautomation.azure.entity.TestSuiteEntity;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestCase {

    public static TestCaseEntity get(TestCaseEntity testCaseEntity, TestSuiteEntity testSuiteEntity){

        RequestSpecification httpRequest = RestAssured.given().log().everything()
                .auth().preemptive().basic("", AzureConfig.accessToken)
                .contentType("application/json").accept("application/json;charset=utf-8");

        String endpoint = AzureConfig.baseURL+"testplan/Plans/"+ testSuiteEntity.getPlan_id()+"/Suites/"+ testSuiteEntity.getId()+"/TestCase?testcaseid="+ testCaseEntity.getId()+"&api-version=7.0";
        Response response = httpRequest.get(endpoint);
        System.out.println("Status : "+response.statusCode());
        response.prettyPrint();
        testCaseEntity.setName(response.body().jsonPath().getString("value.workItem[0].name"));
        return testCaseEntity;
    }

}
