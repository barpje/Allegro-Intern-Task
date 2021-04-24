package com.pietka.bartosz.AllegroInternTask.utils;

import okhttp3.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public final class RetrofitUtils {
    public static <E> Optional<E> executeSyncRetrofitCall(Call<E> call) throws ResponseStatusException {
        try {
            Response<E> response = call.execute();
            if (response.isSuccessful()) {
                return Objects.isNull(response.body()) ? Optional.empty() : Optional.of(response.body());
            } else {
                ResponseBody errorBody = response.errorBody();
                throw new ResponseStatusException(
                        HttpStatus.valueOf(response.code()),
                        Objects.isNull(errorBody) ? response.message() : errorBody.string());
            }
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }
    }

    public static <E> CompletableFuture<E> executeAsyncRetrofitCall(final Call<E> call) throws ResponseStatusException {
        final CompletableFuture<E> future = new CompletableFuture<>();
        call.enqueue(
                new Callback<>() {
                    @Override
                    public void onResponse(Call<E> call, Response<E> response) {
                        if (response.isSuccessful()) {
                            future.complete(response.body());
                        } else {
                            future.completeExceptionally(new ResponseStatusException(
                                    HttpStatus.valueOf(response.code()), response.message()));
                        }
                    }

                    @Override
                    public void onFailure(Call<E> call, Throwable t) {
                        future.completeExceptionally(new ResponseStatusException(
                                HttpStatus.INTERNAL_SERVER_ERROR, t.getMessage()));
                    }
                });
        return future;
    }
}

