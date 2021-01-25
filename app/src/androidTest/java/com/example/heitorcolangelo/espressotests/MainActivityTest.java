package com.example.heitorcolangelo.espressotests;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.heitorcolangelo.espressotests.mocks.Mocks;
import com.example.heitorcolangelo.espressotests.network.Api;
import com.example.heitorcolangelo.espressotests.network.UsersApi;
import com.example.heitorcolangelo.espressotests.ui.activity.MainActivity;

import net.vidageek.mirror.dsl.Mirror;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

  private MockWebServer server;

  @Before
  public void setUp() throws Exception {
    server = new MockWebServer();
    server.start();
    setupServerUrl();
  }

  @Test
  public void whenResultWithSuccess_shouldDisplayListWithUsers() {
    server.enqueue(new MockResponse().setResponseCode(200).setBody(Mocks.SUCCESS));
    ActivityScenario.launch(MainActivity.class);
    onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
  }

  @Test
  public void whenResultWithError_shouldDisplayErrorView() {
    server.enqueue(new MockResponse().setResponseCode(400).setBody(Mocks.ERROR));
    ActivityScenario.launch(MainActivity.class);
    onView(withId(R.id.error_view)).check(matches(isDisplayed()));
  }

  @After
  public void tearDown() throws IOException {
    server.shutdown();
  }

  private void setupServerUrl() {
    String url = server.url("/").toString();

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

    final UsersApi usersApi = UsersApi.getInstance();

    final Api api = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create(UsersApi.GSON))
        .client(client)
        .build()
        .create(Api.class);

    setField(usersApi, "api", api);
  }

  private void setField(Object target, String fieldName, Object value) {
    new Mirror()
        .on(target)
        .set()
        .field(fieldName)
        .withValue(value);
  }
}