package com.testautomation.azure.entity;

import lombok.Data;

@Data
public class AzureConfig {

	public static String organization = "";
	public static String project = "";
	public static String accessToken = "";
	public static String baseURL = "";

	public static void setBaseURL(){
		baseURL = "https://dev.azure.com/"+organization+"/"+project+"/_apis/";
	}
}
