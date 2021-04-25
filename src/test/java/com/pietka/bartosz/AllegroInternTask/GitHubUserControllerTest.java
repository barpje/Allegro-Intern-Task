package com.pietka.bartosz.AllegroInternTask;

import com.pietka.bartosz.AllegroInternTask.api.GitHubUserController;
import com.pietka.bartosz.AllegroInternTask.api.GitHubUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GitHubUserController.class)
public class GitHubUserControllerTest {

    @MockBean
    GitHubUserService service;
    @Autowired
    private MockMvc mvc;

    @Test
    void shouldPassWhenEmptyUsername() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/user/{username}/repositories", "");
        mvc.perform(request).andExpect(status().is4xxClientError());
    }

    @Test
    void shouldPassWhenPerPageOverLimit() throws Exception {
        //github API set per_page to max value 100 when it is over 100
        RequestBuilder request = MockMvcRequestBuilders.
                get("/user/{username}/repositories?per_page={per_page}&page={page}", "github", "101", "1");
        mvc.perform(request).andExpect(status().is2xxSuccessful());
    }

    @Test
    void shouldPassWhenPageOverLimit() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.
                get("/user/{username}/repositories?per_page={per_page}&page={page}", "github", "100", "10000");
        String json = mvc.perform(request).
                andExpect(MockMvcResultMatchers.status().is(200)).
                andReturn().getResponse().getContentAsString();
        JSONAssert.assertEquals("[]", json, true);
    }
}
