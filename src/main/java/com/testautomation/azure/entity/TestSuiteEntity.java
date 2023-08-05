package com.testautomation.azure.entity;

import lombok.Data;

@Data
public class TestSuiteEntity {

	int id;
	String name;
	int plan_id;
	String plan_name;
	int testCaseCount;
	String suiteType = "dynamicTestSuite";
	int parentSuiteId;

	String queryString = "";

	public void setQueryString(String tag, String project){

		queryString = "select [System.Id], [System.WorkItemType], [System.Title], " +
				"[Microsoft.VSTS.Common.Priority], [System.AssignedTo], [System.AreaPath] " +
				"from WorkItems where [System.TeamProject] = @project and " +
				"[System.WorkItemType] in group 'Microsoft.TestCaseCategory' and " +
				"[System.AreaPath] under '"+project+"' " +
				"and [System.Tags] contains '"+tag+"'";

	}
	
}
