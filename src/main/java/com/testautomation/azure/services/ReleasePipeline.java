package com.testautomation.azure.services;

import com.testautomation.azure.entity.AzureConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

public class ReleasePipeline {

    static RequestSpecification httpRequest = RestAssured.given().log().everything()
            .auth().preemptive().basic("", AzureConfig.accessToken)
            .contentType("application/json").accept("application/json;charset=utf-8");

    public static String get(Integer releaseId){
        String endpoint = "https://vsrm.dev.azure.com/"+AzureConfig.organization+"/"+AzureConfig.project+"/_apis/" +
                "release/releases/"+releaseId+"?api-version=7.1-preview.8";
        Response response = httpRequest.get(endpoint);
        System.out.println("Status Code : "+response.statusCode());
        response.prettyPrint();
        return response.body().asString();
    }

    public static void update(Integer releaseId, String releaseJson){
        String endpoint = "https://vsrm.dev.azure.com/"+AzureConfig.organization+"/"+AzureConfig.project+"/_apis/" +
                "release/releases/"+releaseId+"?api-version=5.1";
        Response response = httpRequest.body(releaseJson).put(endpoint);
        System.out.println("Status Code : "+response.statusCode());
        response.prettyPrint();
    }

    public static void updateReleaseVariable(Integer releaseId, String releaseVariable, String value){

        String release = ReleasePipeline.get(releaseId);
        JSONObject releaseJson = new JSONObject(release);
        JSONObject presentVariables = (JSONObject)releaseJson.get("variables");
        JSONObject releaseVariableJSON = new JSONObject();
        releaseVariableJSON.put("value", value);
        presentVariables.put(releaseVariable, releaseVariableJSON);
        releaseJson.put("variables", presentVariables);
        ReleasePipeline.update(releaseId, releaseJson.toString());
    }
}
