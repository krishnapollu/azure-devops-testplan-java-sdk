package com.testautomation.azure.services;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;

import com.testautomation.azure.entity.AzureConfig;
import com.testautomation.azure.entity.TestSuiteEntity;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.List;

public class TestSuite {

	static RequestSpecification httpRequest = RestAssured.given().log().everything()
			.auth().preemptive().basic("", AzureConfig.accessToken)
			.contentType("application/json").accept("application/json;charset=utf-8");

	public static TestSuiteEntity create(TestSuiteEntity testSuiteEntity) {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("suiteType", testSuiteEntity.getSuiteType());
		jsonObject.put("queryString", testSuiteEntity.getQueryString());
		jsonObject.put("name", "Execution_Suite_"+System.currentTimeMillis());
		jsonObject.put("inheritDefaultConfigurations", "true");

		JSONObject parentSuite = new JSONObject();
		parentSuite.put("id", testSuiteEntity.getParentSuiteId());

		jsonObject.put("parentSuite", parentSuite);


		String endpoint = AzureConfig.baseURL+"testplan/Plans/"+ testSuiteEntity.getPlan_id()+"/suites?api-version=5.0-preview.1";
		Response response = httpRequest.body(jsonObject.toString()).post(endpoint);
		System.out.println("Status Code : "+response.statusCode());
		response.prettyPrint();
		testSuiteEntity.setId( response.body().jsonPath().getInt("id"));
		return testSuiteEntity;

	}

	public static List<Integer> getSuiteEntries(Integer testSuiteId){

		String endpoint = AzureConfig.baseURL+"test/suiteentry/"+ testSuiteId+"?api-version=5.0-preview.1";
		Response response = httpRequest.get(endpoint);
		System.out.println("Status Code : "+response.statusCode());
		response.prettyPrint();

		JsonPath jsonPathEvaluator = response.jsonPath();
		List<Integer> suiteEntries = jsonPathEvaluator.getList("value.childSuiteId");
		List<Integer> suiteIds = new ArrayList<Integer>();
		//suiteIds.addAll(suiteEntries);

		for(Integer id : suiteEntries){
			try{
				if(id!=0){
					suiteIds.add(id);
					suiteIds.addAll(TestSuite.getSuiteEntries(id));
				}
			}catch (NullPointerException e){
				continue;
			}
		}
		return suiteIds;
	}
}
