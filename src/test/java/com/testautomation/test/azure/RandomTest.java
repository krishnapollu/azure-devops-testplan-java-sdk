package com.testautomation.test.azure;

import com.testautomation.azure.entity.AzureConfig;
import com.testautomation.azure.entity.TestPointEntity;
import com.testautomation.azure.services.TestPoint;
import com.testautomation.azure.services.TestSuite;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RandomTest {

    public static void main(String[]  args){

        //init config class
        AzureConfig.organization = "";
        AzureConfig.project = "";
        AzureConfig.accessToken = "";
        AzureConfig.setBaseURL();
        Map<String, Integer> outcome = new HashMap<String, Integer>();
        outcome.put("Passed", 0);
        outcome.put("Failed", 0);
        outcome.put("Blocked", 0);
        outcome.put("Active", 0);
        outcome.put("Not run", 0);
        outcome.put("Not applicable", 0);
        outcome.put("Unspecified", 0);
        int tpCount = 0;
//        ReleasePipeline.updateReleaseVariable(3111, "testvariable", "3111");
//        ReleasePipeline.updateReleaseVariable(3111, "testvariable2", "3112");
        List<Integer> list = TestSuite.getSuiteEntries(766222);
        list.add(766222);
        System.out.println("Total Test Suites: "+list.size());

        List<TestPointEntity> obj;
        for(Integer id : list) {
            try {
                obj = TestPoint.getList(606035, id);
                obj.size();
            } catch (NullPointerException e) {
                System.out.println("No points inside suite " + id);
                continue;
            }
            for (TestPointEntity tpe : obj) {
                tpCount++;
                outcome.put(tpe.getOutcome(), outcome.get(tpe.getOutcome()) + 1);
            }
        }
        System.out.println("Total Passed: "+outcome.get("Passed"));
        System.out.println("Total Failed: "+outcome.get("Failed"));
        System.out.println("Total Blocked: "+outcome.get("Blocked"));
        System.out.println("Total Not run: "+outcome.get("Not run"));
        System.out.println("Total Not applicable: "+outcome.get("Not applicable"));
        System.out.println("Total Unspecified: "+outcome.get("Unspecified"));
        System.out.println("Pass Percentage: "+outcome.get("Passed")/tpCount);
    }
}
