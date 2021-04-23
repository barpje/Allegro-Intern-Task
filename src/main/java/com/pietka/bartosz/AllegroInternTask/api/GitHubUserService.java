package com.pietka.bartosz.AllegroInternTask.api;

import com.pietka.bartosz.AllegroInternTask.models.Repository;
import com.pietka.bartosz.AllegroInternTask.models.UserData;
import com.pietka.bartosz.AllegroInternTask.utils.GitHubUtil;
import com.pietka.bartosz.AllegroInternTask.utils.RepositoryUtil;
import com.pietka.bartosz.AllegroInternTask.utils.RetrofitUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import retrofit2.Call;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GitHubUserService implements GitHubAPIConfiguration {

    private final GitHubUserInterface service;

    public GitHubUserService() {
        service = GitHubServiceFactory.createService(GitHubUserInterface.class);
    }

    public UserData getUserData(String username) throws ResponseStatusException {
        Call<UserData> retrofitCall = service.getUserData(API_VERSION_SPEC, username);
        return RetrofitUtils.executeRetrofitCall(retrofitCall)
                .orElseGet(() -> new UserData("login", 0));
    }

    public List<Repository> getRepositories(String username) throws ResponseStatusException {
        Call<List<Repository>> retrofitCall = service.getUserRepositories(API_VERSION_SPEC, username);
        return RetrofitUtils.executeRetrofitCall(retrofitCall).orElseGet(Collections::emptyList);
    }

    public List<Repository> getRepositories(String username, int per_page, int page) throws ResponseStatusException {
        Call<List<Repository>> retrofitCall = service.getUserRepositories(API_VERSION_SPEC, username, per_page, page);
        return RetrofitUtils.executeRetrofitCall(retrofitCall).orElseGet(Collections::emptyList);
    }

    public List<Repository> getAllRepositories(String username) throws ResponseStatusException {
        UserData userdata = getUserData(username);
        int pages = GitHubUtil.calculateNumberOfPages(userdata);
        List<List<Repository>> repositories = new ArrayList<>(pages);
        for (int page = 0; page < pages; page++) {
            repositories.add(getRepositories(userdata.getLogin(), MAX_PER_PAGE, page + 1));
        }
        return GitHubUtil.concatenateMultipleLists(repositories);
    }
    /*
    public int getUserStars(String username) throws ResponseStatusException {
        List<Repository> repositories = getAllRepositories(username);
        return RepositoryUtil.returnSumOfStars(repositories);
    }
    */
    public int getUserStars(String username) throws ResponseStatusException {
        UserData userdata = getUserData(username);
        int pages = GitHubUtil.calculateNumberOfPages(userdata);
        int sumOfStars = 0;
        for (int page = 0; page < pages; page++) {
            sumOfStars += RepositoryUtil.returnSumOfStars(getRepositories(userdata.getLogin(), MAX_PER_PAGE, page + 1));
        }
        return sumOfStars;
    }
}