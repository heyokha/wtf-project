package com.example.Teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.Teamcity.ui.elements.ProjectElement;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProjectsPage extends BasePage{
    private static final String PROJECTS_URL = "/favourite/projects";

    private ElementsCollection projectElements = $$("div[class*='Subproject__container']");

    private SelenideElement header = $(".MainPanel__router--gF > div");

    public static ProjectsPage open() {
        return Selenide.open(PROJECTS_URL, ProjectsPage.class);
    }

    public List<ProjectElement> getProject(){
        return generatePageElements(projectElements, ProjectElement::new);
    }

    public ProjectsPage() {
        header.shouldBe(Condition.visible, BASE_WAITING);
    }
}
