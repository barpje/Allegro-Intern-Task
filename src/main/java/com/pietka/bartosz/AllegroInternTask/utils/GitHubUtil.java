package com.pietka.bartosz.AllegroInternTask.utils;

import com.pietka.bartosz.AllegroInternTask.models.UserData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class GitHubUtil {

    public static int calculateNumberOfPages(UserData userdata, int per_page) {
        if (Objects.isNull(userdata) || per_page < 1) {
            return 0;
        }
        var dividend = BigDecimal.valueOf(userdata.getPublic_repos());
        var divisor = BigDecimal.valueOf(per_page);
        return dividend.divide(divisor, RoundingMode.CEILING).intValue();
    }

    public static <T> List<T> concatenateMultipleLists(List<? extends List<T>> listOfLists) {
        if (Objects.isNull(listOfLists)) {
            return Collections.emptyList();
        }
        List<T> result = new ArrayList<>();
        listOfLists.stream().forEach(result::addAll);
        return result;
    }
}
