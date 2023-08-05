package com.testautomation.azure.services;

import com.testautomation.azure.entity.AzureConfig;
import com.testautomation.azure.entity.TestAttachmentEntity;
import com.testautomation.azure.entity.TestRunEntity;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

public class TestRunAttachment {

    public static void create(TestRunEntity testRunEntity,
                                 TestAttachmentEntity testAttachmentEntity) {

        RequestSpecification httpRequest = RestAssured.given().log().everything()
                .auth().preemptive().basic("", AzureConfig.accessToken)
                .contentType("application/json").accept("application/json;charset=utf-8");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("stream", testAttachmentEntity.getStream());
        jsonObject.put("fileName", testAttachmentEntity.getFileName());
        jsonObject.put("comment", testAttachmentEntity.getComment());
        jsonObject.put("attachmentType", testAttachmentEntity.getAttachmentType());

        String endpoint = AzureConfig.baseURL + "test/Runs/" + testRunEntity.getId() + "/attachments?api-version=5.0-preview.1";

        Response response = httpRequest.body(jsonObject.toString()).post(endpoint);
        System.out.println("Status Code : "+response.statusCode());
        response.prettyPrint();
    }
}
