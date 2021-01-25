package com.example.heitorcolangelo.espressotests.network;

import com.example.heitorcolangelo.espressotests.BuildConfig;
import com.example.heitorcolangelo.espressotests.network.model.ErrorVO;
import com.example.heitorcolangelo.espressotests.network.model.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class UsersApi {

  public static final Gson GSON = new GsonBuilder()
      .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SS'Z'")
      .create();
  private static final int RESULTS = 20;

  private final Api api;

  private static UsersApi INSTANCE;

  /**
   * Sets up the singleton instance
   *
   * @return Singleton instance
   */
  public static UsersApi getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new UsersApi();
    }
    return INSTANCE;
  }

  /**
   * Get list of users in page passed as parameter
   */
  public void getUsers(int page) {
    Call<Page> userResponsePage = api.getUsers(page, RESULTS);
    userResponsePage.enqueue(new Callback<Page>() {
      @Override
      public void onResponse(@NotNull Call<Page> call, @NotNull Response<Page> response) {
        if (response.isSuccessful())
          EventBus.getDefault().post(response.body());
        else {
          ErrorVO error = null;
          if (response.errorBody() != null) {
            error = GSON.fromJson(response.errorBody().charStream(), ErrorVO.class);
          }
          EventBus.getDefault().post(error);
        }
      }

      @Override
      public void onFailure(@NotNull Call<Page> call, @NotNull Throwable t) {
        EventBus.getDefault().post(new ErrorVO());
      }
    });
  }

  private UsersApi() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(BuildConfig.DEBUG ?
        HttpLoggingInterceptor.Level.BODY :
        HttpLoggingInterceptor.Level.NONE);
    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

    api = new Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GSON))
        .client(client)
        .build()
        .create(Api.class);
  }
}
