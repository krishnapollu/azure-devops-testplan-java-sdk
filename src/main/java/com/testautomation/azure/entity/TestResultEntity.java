package com.testautomation.azure.entity;

import lombok.Data;

@Data
public class TestResultEntity {

	int id;
	String testCaseTitle;
	String outcome;
	int testPointId;
	int testCaseId;
	String errorMessage = "";
	String stacktrace = "";
	long durationInMs = 0;
	String startedDate = "";
	String completedDate = "";
	String comment = "";
}
