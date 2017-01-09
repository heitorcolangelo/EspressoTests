package com.example.heitorcolangelo.espressotests.network;

import com.example.heitorcolangelo.espressotests.network.model.Page;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface Api {

  @GET("/")
  Observable<Page> getUsers(@Query("page") int page, @Query("results") int results);
}
