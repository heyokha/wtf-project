package com.example.teamcity;

import com.example.Teamcity.api.models.TestData;
import com.example.Teamcity.api.requests.CheckedRequests;
import com.example.Teamcity.api.requests.UncheckedRequests;
import com.example.Teamcity.api.spec.Specifications;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import static com.example.Teamcity.api.generators.TestDataGenerator.generate;

public class BaseTest {
    protected SoftAssert softy;
    protected CheckedRequests superUserCheckRequests = new CheckedRequests(Specifications.superuserauth());
    protected UncheckedRequests superUserUnCheckedRequests = new UncheckedRequests(Specifications.superuserauth());
    protected TestData testData;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        softy = new SoftAssert();
        testData = generate();
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest() {
        softy.assertAll();
    }
}