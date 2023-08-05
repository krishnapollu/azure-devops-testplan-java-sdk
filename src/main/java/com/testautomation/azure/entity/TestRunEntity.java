package com.testautomation.azure.entity;

import lombok.Data;

@Data
public class TestRunEntity {

	int id;
	String name;
	String type;
	String isAutomated;
	String state;
	int totalTestCases;
	int testPlanId;
	int[] pointIds;
}
