package com.pietka.bartosz.AllegroInternTask.api;

public interface GitHubAPIConfiguration {
    static final String API_BASE_URL = "https://api.github.com/";
    static final String API_VERSION_SPEC = "application/vnd.github.v3+json";
    static final String JSON_CONTENT_TYPE = "application/json";
    static final int MAX_PER_PAGE = 100;
}
