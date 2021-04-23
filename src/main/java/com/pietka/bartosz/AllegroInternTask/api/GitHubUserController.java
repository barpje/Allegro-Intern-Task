package com.pietka.bartosz.AllegroInternTask.api;

import com.google.gson.JsonObject;
import com.pietka.bartosz.AllegroInternTask.models.Repository;
import com.pietka.bartosz.AllegroInternTask.models.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class GitHubUserController {

    private final GitHubUserService githubUserService;

    @Autowired
    public GitHubUserController(GitHubUserService githubUserService) {
        this.githubUserService = githubUserService;
    }

    @GetMapping(path = "{username}/repositories", produces = "application/json")
    public List<Repository> getUserRepositories(@PathVariable String username) throws ResponseStatusException {
        return githubUserService.getRepositories(username);
    }

    @GetMapping(path = "{username}/repositories", params = {"per_page", "page"}, produces = "application/json")
    public List<Repository> getUserRepositoriesByPage(@PathVariable String username,
                                                      @RequestParam int per_page,
                                                      @RequestParam int page) throws ResponseStatusException {
        return githubUserService.getRepositories(username, per_page, page);
    }

    @GetMapping(path = "{username}/repositories/all", produces = "application/json")
    public List<Repository> getAllUserRepositories(@PathVariable String username) throws ResponseStatusException {
        return githubUserService.getAllRepositories(username);
    }

    @GetMapping(path = "{username}/stars", produces = "application/json")
    public String getUserStars(@PathVariable String username) throws ResponseStatusException {
        JsonObject json = new JsonObject();
        json.addProperty("totalStars", githubUserService.getUserStars(username));
        return json.toString();
    }
}