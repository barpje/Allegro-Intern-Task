package com.pietka.bartosz.AllegroInternTask;

import com.pietka.bartosz.AllegroInternTask.utils.RepositoryUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RepositoryUtilTest {
    @Test
    public void shouldPassWhenNullGiven() {
        // when
        int result = RepositoryUtil.returnSumOfStars(null);
        // then
        assertThat(result).isEqualTo(0);
    }
    @Test
    public void shouldPassWhenEmptyListGiven() {
        // when
        int result = RepositoryUtil.returnSumOfStars(Collections.emptyList());
        // then
        assertThat(result).isEqualTo(0);
    }

}
