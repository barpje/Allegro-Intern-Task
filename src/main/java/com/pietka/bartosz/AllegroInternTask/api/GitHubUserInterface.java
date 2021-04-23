package com.pietka.bartosz.AllegroInternTask.api;

import com.pietka.bartosz.AllegroInternTask.models.Repository;
import com.pietka.bartosz.AllegroInternTask.models.UserData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface GitHubUserInterface {

    @GET("/users/{username}")
    Call<UserData> getUserData(@Header("Accept") String apiVersionSpec,
                               @Path("username") String username);
    @GET("/users/{username}/repos")
    Call<List<Repository>> getUserRepositories(@Header("Accept") String apiVersionSpec,
                                               @Path("username") String username);

    @GET("/users/{username}/repos")
    Call<List<Repository>> getUserRepositories(@Header("Accept") String apiVersionSpec,
                                               @Path("username") String username,
                                               @Query("per_page") int per_page,
                                               @Query("page") int page);

}