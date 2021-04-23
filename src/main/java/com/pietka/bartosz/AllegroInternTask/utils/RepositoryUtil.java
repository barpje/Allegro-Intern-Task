package com.pietka.bartosz.AllegroInternTask.utils;

import com.pietka.bartosz.AllegroInternTask.models.Repository;

import java.util.List;
import java.util.Objects;

public final class RepositoryUtil {
    public static int returnSumOfStars(List<Repository> repositories) {
        if (Objects.isNull(repositories)) {
            return 0;
        }
        return repositories.stream()
                .mapToInt(Repository::getStargazers_count)
                .sum();
    }
}