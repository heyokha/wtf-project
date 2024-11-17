package com.example.Teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.Teamcity.ui.elements.BuildTypeElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.util.List;

public class ProjectPage extends BasePage{
    private static final String PROJECT_URL = "/project/%s";

    public SelenideElement title = $("span[class*='ProjectPageHeader']");
    public ElementsCollection buildTypeElements = $$("[class*=BuildTypes__item]");

    public static ProjectPage open(String projectId) {
        return Selenide.open(PROJECT_URL.formatted(projectId), ProjectPage.class);
    }

    public ProjectPage() {
        title.shouldBe(Condition.visible, BASE_WAITING);
    }

    public List<BuildTypeElement> getBuildTypes() {
        return generatePageElements(buildTypeElements, BuildTypeElement::new);
    }
}
