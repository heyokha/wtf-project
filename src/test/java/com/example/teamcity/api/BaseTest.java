package com.example.teamcity.api;

import com.example.Teamcity.api.requests.CheckedRequests;
import com.example.Teamcity.api.spec.Specifications;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

public class BaseTest {
    protected SoftAssert softy;
    protected CheckedRequests superUserCheckRequests = new CheckedRequests(Specifications.superuserauth());

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        softy = new SoftAssert();
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest() {
        softy.assertAll();
    }
}