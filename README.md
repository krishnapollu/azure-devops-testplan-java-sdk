# azure-testplan-sdk

Java based SDK to integrate your automation test results to Azure DevOps Test Plan. Add this library to your pom.xml and use the methods to POST your test results to Azure DevOps. This SDK internally uses the Azure DevOps REST APIs to update the results to your project.

### Maven Dependency
```
<dependency>
  <groupId>com.testautomation</groupId>
  <artifactId>azure-devops-testplan-java-sdk</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Usage
The below snippets are to be added in your hooks or listeners so that this gets executed after each test case execution.

Set basic details of your Azure DevOps server / project. To be set during the beginning of the execution.
```
 //init config class
        AzureConfig.organization = "";
        AzureConfig.project = "";
        AzureConfig.accessToken = "";
        AzureConfig.setBaseURL();
```

The Azure DevOps Test Execution has the below hierarchy
`` Test Plan --> Test Suite --> Test Run --> Test Results --> Test Points ``

Setting the Test Suite Entity
```
TestSuiteEntity testSuiteEntity = new TestSuiteEntity();
        testSuiteEntity.setPlan_id(606035); // update with your Test Plan ID
        testSuiteEntity.setParentSuiteId(606036); // update with your Test Suite ID
        testSuiteEntity.setQueryString("Automation_TC", AzureConfig.project);
        testSuiteEntity = TestSuite.create(testSuiteEntity);
```

Creating a Test Run
```
//create testrun
        TestRunEntity testRunEntity = new TestRunEntity();
        testRunEntity.setTestPlanId(606035); // update with your Test Plan ID
        testRunEntity.setName("Test Suite");
        testRunEntity = TestRun.create(testRunEntity);
```

Create a Test Result Entity against each Test Case you execute
```
//test case - 745460
        TestCaseEntity testCaseEntity1 = new TestCaseEntity();
        testCaseEntity1.setId(745460); // update with test case ID
        testCaseEntity1 = TestCase.get(testCaseEntity1, testSuiteEntity);
        TestPointEntity testPointEntity1 = TestPoint.get(testCaseEntity1, testSuiteEntity);

        TestResultEntity testResultEntity1 = new TestResultEntity();
        testResultEntity1.setOutcome("Passed"); // RUn status
        testResultEntity1.setTestCaseId(testCaseEntity1.getId());
        testResultEntity1.setTestPointId(testPointEntity1.getId());
        testResultEntity1.setTestCaseTitle(testCaseEntity1.getName());
        testResultEntity1.setDurationInMs(12000); // test case execution duration

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
```

Now add the Test Result Entities to Test Run
```
        TestResult.add(testResultEntity1, testRunEntity);
        TestResult.add(testResultEntity2, testRunEntity);
```

Update the Test Run
```
testRunEntity = TestRun.update(testRunEntity, "Completed",
                "Automated Test Run completed successfully.");
```
