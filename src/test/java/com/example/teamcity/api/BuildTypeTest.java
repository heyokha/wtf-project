package com.example.teamcity.api;
import com.example.Teamcity.api.models.BuildType;
import com.example.Teamcity.api.models.Project;
import com.example.Teamcity.api.models.Role;
import com.example.Teamcity.api.models.Roles;
import com.example.Teamcity.api.requests.CheckedRequests;
import com.example.Teamcity.api.requests.UncheckedRequests;
import com.example.Teamcity.api.requests.unchecked.UncheckedBase;
import com.example.Teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;

import static com.example.Teamcity.api.enums.Endpoint.*;
import static com.example.Teamcity.api.generators.TestDataGenerator.generate;

public class BuildTypeTest extends BaseApiTest{
    @Test(description = "User should be able to create build type", groups = {"Positive", "CRUD"})
    public void userCreatesBuildTypeTest() {
        var testData = generate();

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECT).create(testData.getProject());

        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());

        var createdBuildType = userCheckRequests.<BuildType>getRequest(BUILD_TYPES).read(testData.getBuildType().getId());

        softy.assertEquals(testData.getBuildType()
                .getName(), createdBuildType.getName(), "Build type name is not correct");
    }

    @Test(description = "User should not be able to create two build types with the same id",
            groups = {"Negative", "CRUD"})
    public void userCreatesTwoBuildTypesWithTheSameIdTest() {
        var testData = generate();
        var buildTypeWithSameId = generate(Arrays.asList(testData.getProject()), BuildType.class, testData.getBuildType()
                .getId());

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECT).create(testData.getProject());

        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());
        new UncheckedBase(Specifications.authSpec(testData.getUser()), BUILD_TYPES)
                .create(buildTypeWithSameId)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("The build configuration / template ID \"%s\" is already used by another configuration or template"
                        .formatted(testData.getBuildType().getId())));
    }

    @Test(description = "Project admin should be able to create build type for their project",
            groups = {"Positive", "Roles"})
    public void projectAdminCreatesBuildTypeTest() {
        var testData = generate();
        var checkedUserRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        testData.getUser().setRoles(generate(Roles.class, "PROJECT_ADMIN", "p:" + testData.getProject()
                .getId()));

        superUserCheckRequests.getRequest(PROJECT).create(testData.getProject());
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        checkedUserRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());

        var createdBuildType = checkedUserRequests.<BuildType>getRequest(BUILD_TYPES)
                .read(testData.getBuildType().getId());
        softy.assertEquals(testData.getBuildType()
                .getName(), createdBuildType.getName(), "Build type name is correct");
    }

    @Test(description = "Project admin should not be able to create build type for not their project", groups = {"Negative", "Roles"})
    public void projectAdminCreatesBuildTypeForAnotherUserProjectTest() {
        var testData = generate();

        var project1 = testData.getProject();
        var project2 = generate(Project.class);
        var buildTypeOfProject2 = generate(Arrays.asList(project2), BuildType.class);
        var userForProject1 = testData.getUser();

        superUserCheckRequests.<Project>getRequest(PROJECT).create(testData.getProject());
        superUserCheckRequests.<Project>getRequest(PROJECT).create(project2);

        Role projectAdminRole = new Role("PROJECT_ADMIN", "p:" + project1.getId());
        Roles roles = new Roles(Collections.singletonList(projectAdminRole));
        userForProject1.setRoles(roles);

        superUserCheckRequests.<Project>getRequest(USERS).create(userForProject1);

        new UncheckedRequests(Specifications.authSpec(userForProject1))
                .getRequest(BUILD_TYPES).create(buildTypeOfProject2)
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .body(Matchers.containsString("You do not have enough permissions to edit project with id: %s".formatted(project2.getId())));
    }
}
