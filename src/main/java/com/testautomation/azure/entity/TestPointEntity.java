package com.testautomation.azure.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestPointEntity {

	int id;
	int testSuiteId;
	int testCaseId;
	String outcome;
	Map<String, String> testCase;

	
}
