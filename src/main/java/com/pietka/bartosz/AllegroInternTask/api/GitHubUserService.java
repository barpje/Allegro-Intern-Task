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
import java.util.concurrent.CompletableFuture;

@Service
public class GitHubUserService implements GitHubAPIConfiguration {

    private final GitHubUserInterface service;

    public GitHubUserService() {
        service = GitHubServiceFactory.createService(GitHubUserInterface.class);
    }

    public UserData getUserData(String username) throws ResponseStatusException {
        Call<UserData> retrofitCall = service.getUserData(API_VERSION_SPEC, username);
        return RetrofitUtils.executeSyncRetrofitCall(retrofitCall)
                .orElseGet(() -> new UserData("login", 0));
    }

    public List<Repository> getRepositories(String username) throws ResponseStatusException {
        Call<List<Repository>> retrofitCall = service.getUserRepositories(API_VERSION_SPEC, username);
        return RetrofitUtils.executeSyncRetrofitCall(retrofitCall).orElseGet(Collections::emptyList);
    }

    public List<Repository> getRepositories(String username, int per_page, int page) throws ResponseStatusException {
        Call<List<Repository>> retrofitCall = service.getUserRepositories(API_VERSION_SPEC, username, per_page, page);
        return RetrofitUtils.executeSyncRetrofitCall(retrofitCall).orElseGet(Collections::emptyList);
    }

    public int getUserStars(String username) throws ResponseStatusException {
        List<Repository> repositories = getAllRepositories(username);
        return RepositoryUtil.returnSumOfStars(repositories);
    }

    public List<Repository> getAllRepositories(String username) throws ResponseStatusException {
        UserData userdata = getUserData(username);
        int pages = GitHubUtil.calculateNumberOfPages(userdata, MAX_PER_PAGE);
        List<CompletableFuture<List<Repository>>> requests = new ArrayList<>(pages);

        for (int page = 0; page < pages; page++) {
            requests.add(RetrofitUtils.executeAsyncRetrofitCall(
                    service.getUserRepositories(API_VERSION_SPEC, username, MAX_PER_PAGE, page + 1)));
        }

        List<List<Repository>> repositories = RetrofitUtils.getResultFromFutures(requests);
        return GitHubUtil.concatenateMultipleLists(repositories);
    }

}