package com.example.Teamcity.api.enums;

import com.example.Teamcity.api.models.BaseModel;
import com.example.Teamcity.api.models.BuildType;
import com.example.Teamcity.api.models.Project;
import com.example.Teamcity.api.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Endpoint {
    BUILD_TYPES("/app/rest/buildTypes", BuildType.class),
    PROJECT("app/rest/projects", Project.class),
    USERS("app/rest/users", User.class);

    private final String url;
    private final Class<? extends BaseModel> modelClass;
}
