package com.example.heitorcolangelo.espressotests.network;

import com.example.heitorcolangelo.espressotests.network.model.Page;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

  @GET("/")
  Call<Page> getUsers(@Query("page") int page, @Query("results") int results);
}
