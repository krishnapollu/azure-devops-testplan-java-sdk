package com.testautomation.test.azure;

import com.testautomation.azure.services.TestCase;
import com.testautomation.azure.services.TestPoint;
import com.testautomation.azure.services.TestResult;
import com.testautomation.azure.services.TestRun;
import com.testautomation.azure.services.TestSuite;
import com.testautomation.azure.entity.*;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class Test {

    public static void main(String[] args) throws InterruptedException {

        //init config class
        AzureConfig.organization = "";
        AzureConfig.project = "";
        AzureConfig.accessToken = "";
        AzureConfig.setBaseURL();

        //create test suite

        TestSuiteEntity testSuiteEntity = new TestSuiteEntity();
        testSuiteEntity.setPlan_id(606035);
        testSuiteEntity.setParentSuiteId(606036);
        testSuiteEntity.setQueryString("Automation_TC");
        testSuiteEntity = TestSuite.create(testSuiteEntity);

        //create testrun
        TestRunEntity testRunEntity = new TestRunEntity();
        testRunEntity.setTestPlanId(606035);
        testRunEntity.setName("Test Suite");
        testRunEntity = TestRun.create(testRunEntity);

        //test case - 745460
        TestCaseEntity testCaseEntity1 = new TestCaseEntity();
        testCaseEntity1.setId(745460);
        testCaseEntity1 = TestCase.get(testCaseEntity1, testSuiteEntity);
        TestPointEntity testPointEntity1 = TestPoint.get(testCaseEntity1, testSuiteEntity);

        TestResultEntity testResultEntity1 = new TestResultEntity();
        testResultEntity1.setOutcome("Passed");
        testResultEntity1.setTestCaseId(testCaseEntity1.getId());
        testResultEntity1.setTestPointId(testPointEntity1.getId());
        testResultEntity1.setTestCaseTitle(testCaseEntity1.getName());
        testResultEntity1.setDurationInMs(12000);

        testResultEntity1 = TestResult.add(testResultEntity1, testRunEntity);

        //test case - 745466
        TestCaseEntity testCaseEntity2 = new TestCaseEntity();
        testCaseEntity2.setId(745466);
        testCaseEntity2 = TestCase.get(testCaseEntity2, testSuiteEntity);
        TestPointEntity testPointEntity2 = TestPoint.get(testCaseEntity2, testSuiteEntity);

        TestResultEntity testResultEntity2 = new TestResultEntity();
        testResultEntity2.setOutcome("Failed");
        testResultEntity2.setErrorMessage("java.lang.NullPointerException");
        testResultEntity2.setStacktrace(ExceptionUtils.getStackTrace(new Exception()));
        testResultEntity2.setTestCaseId(testCaseEntity2.getId());
        testResultEntity2.setTestPointId(testPointEntity2.getId());
        testResultEntity2.setTestCaseTitle(testCaseEntity2.getName());
        testResultEntity2.setDurationInMs(1000);

        TestResult.add(testResultEntity2, testRunEntity);

        //test case - 745468
        TestCaseEntity testCaseEntity3 = new TestCaseEntity();
        testCaseEntity3.setId(745468);
        testCaseEntity3 = TestCase.get(testCaseEntity3, testSuiteEntity);
        TestPointEntity testPointEntity3 = TestPoint.get(testCaseEntity3, testSuiteEntity);

        TestResultEntity testResultEntity3 = new TestResultEntity();
        testResultEntity3.setOutcome("Passed");
        testResultEntity3.setTestCaseId(testCaseEntity3.getId());
        testResultEntity3.setTestPointId(testPointEntity3.getId());
        testResultEntity3.setTestCaseTitle(testCaseEntity3.getName());
        testResultEntity3.setDurationInMs(2000);

        TestResult.add(testResultEntity3, testRunEntity);

        //test case - 746528
        TestCaseEntity testCaseEntity4 = new TestCaseEntity();
        testCaseEntity4.setId(746528);
        testCaseEntity4 = TestCase.get(testCaseEntity4, testSuiteEntity);
        TestPointEntity testPointEntity4 = TestPoint.get(testCaseEntity4, testSuiteEntity);

        TestResultEntity testResultEntity4 = new TestResultEntity();
        testResultEntity4.setOutcome("Passed");
//        testResultEntity4.setErrorMessage("java.lang.AssertionError");
        testResultEntity4.setTestCaseId(testCaseEntity4.getId());
        testResultEntity4.setTestPointId(testPointEntity4.getId());
        testResultEntity4.setTestCaseTitle(testCaseEntity4.getName());
        testResultEntity4.setDurationInMs(12000);

        TestResult.add(testResultEntity4, testRunEntity);

        testRunEntity = TestRun.update(testRunEntity, "Completed",
                "Automated Test Run completed successfully.");
    }
}
