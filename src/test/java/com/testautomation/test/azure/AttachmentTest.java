package com.testautomation.test.azure;

import com.testautomation.azure.entity.*;
import com.testautomation.azure.services.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class AttachmentTest {

    public static void main(String[] args) throws InterruptedException, IOException {
//init config class
        AzureConfig.organization = "";
        AzureConfig.project = "";
        AzureConfig.accessToken = "";
        AzureConfig.setBaseURL();

        //create test suite

        TestSuiteEntity testSuiteEntity = new TestSuiteEntity();
        testSuiteEntity.setPlan_id(606035);
        testSuiteEntity.setParentSuiteId(763834);
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

        testResultEntity1 = TestResult.add(testResultEntity1, testRunEntity);

        TestAttachmentEntity testAttachmentEntity = new TestAttachmentEntity();
        testAttachmentEntity.setAttachmentType("GeneralAttachment");
        testAttachmentEntity.setComment("Test Attachment Uploaded.");
        byte[] fileContent = FileUtils.readFileToByteArray(new File("src\\test\\resources\\734637.png"));
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        testAttachmentEntity.setStream(encodedString);
        testAttachmentEntity.setFileName(testCaseEntity1.getName().replace(" ", "_")+".png");

        TestResultAttachment.create(testRunEntity, testResultEntity1, testAttachmentEntity);
        TestRunAttachment.create(testRunEntity, testAttachmentEntity);

        testRunEntity = TestRun.update(testRunEntity, "Completed", "Automated Test Run completed successfully.");

    }
}
