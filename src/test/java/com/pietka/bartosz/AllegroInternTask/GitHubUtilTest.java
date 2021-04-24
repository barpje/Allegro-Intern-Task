package com.pietka.bartosz.AllegroInternTask;

import com.pietka.bartosz.AllegroInternTask.models.UserData;
import com.pietka.bartosz.AllegroInternTask.utils.GitHubUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GitHubUtilTest {
    @Test
    public void shouldPassWhenNullGiven() {
        // when
        int result = GitHubUtil.calculateNumberOfPages(null, 10);
        // then
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void shouldPassWhenNoRepositories() {
        // when
        int result = GitHubUtil.calculateNumberOfPages(new UserData("user", 0), 10);
        // then
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void shouldPassWhenZeroPerPage() {
        // when
        int result = GitHubUtil.calculateNumberOfPages(new UserData("user", 20), 0);
        // then
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void shouldPassWhenNegativePerPage() {
        // when
        int result = GitHubUtil.calculateNumberOfPages(new UserData("user", 20), -2);
        // then
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void cellingTest() {
        // when
        int result = GitHubUtil.calculateNumberOfPages(new UserData("user", 13), 3);
        // then
        assertThat(result).isEqualTo(5);
    }

    @Test
    public void shouldPassWhenListNull() {
        // when
        var result = GitHubUtil.concatenateMultipleLists(null);
        // then
        assertThat(result.size()).isEqualTo(0);
    }
}
