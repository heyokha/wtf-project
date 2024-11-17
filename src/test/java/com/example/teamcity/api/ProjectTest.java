package com.example.teamcity.api;

import org.testng.annotations.Test;

import static com.example.Teamcity.api.generators.TestDataGenerator.generate;

public class ProjectTest {
    @Test(description = "User should be able to create project")
    public void projectCreation() {};

    @Test(description = "User without permisstion cannot create project")
    public void projectCreationDenied() {};

    @Test(description = "Create project with existing name")
    public void projectCreationWithExistingName() {};
}
