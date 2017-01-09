package com.example.heitorcolangelo.espressotests.network;

import com.example.heitorcolangelo.espressotests.BuildConfig;
import com.example.heitorcolangelo.espressotests.network.model.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class UsersApi {

  public static final Gson GSON = new GsonBuilder()
      .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SS'Z'")
      .create();
  private static final int RESULTS = 20;

  private Api api;

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
  public Observable<Page> getUsers(int page) {
    return api.getUsers(page, RESULTS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  private UsersApi() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(BuildConfig.DEBUG ?
        HttpLoggingInterceptor.Level.BODY :
        HttpLoggingInterceptor.Level.NONE);
    OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(interceptor).build();

    api = new Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GSON))
        .client(client)
        .build()
        .create(Api.class);
  }
}
