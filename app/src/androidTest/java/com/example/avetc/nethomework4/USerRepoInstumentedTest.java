package com.example.avetc.nethomework4;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.example.avetc.nethomework4.dagger.DaggerTestComponent;
import com.example.avetc.nethomework4.dagger.TestComponent;
import com.example.avetc.nethomework4.dagger.modules.ApiModule;
import com.example.avetc.nethomework4.entities.User;
import com.example.avetc.nethomework4.mvp.model.repos.UserRepo;
import com.example.avetc.nethomework4.mvp.presenter.MainPresenter;
import com.example.avetc.nethomework4.mvp.view.MainView;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert.assertEquals;

public class USerRepoInstumentedTest {


    private MainPresenter presenter;
    private TestScheduler scheduler;

    private static MockWebServer webServer;
    @Inject
    UserRepo userRepo;

    @Inject
    UserRepo.RepoStrategy strategy;

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.avetc.nethomework4", appContext.getPackageName());
    }

    @BeforeClass
    public static void setupClass() throws IOException {
        webServer = new MockWebServer();
        webServer.start();

    }

    @Before
    public void setup() {
        TestComponent testComponent = DaggerTestComponent.builder()
                .apiModule(new ApiModule() {
                    @Override
                    public String endpoint() {
                        return webServer.url("/").toString();
                    }
                }).build();
        testComponent.inject(this);
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        webServer.shutdown();

    }

    @Test
    public void getUser() {
        webServer.enqueue(createUserResponse("somelogin", "someurl"));
        TestObserver<User> observer = new TestObserver<>();
        userRepo.getUser("somelogin").subscribe(observer);
        observer.awaitTerminalEvent();
        observer.assertValueCount(1);
        assertEquals(observer.values().get(0).getLogin(), "somelogin");
        assertEquals(observer.values().get(0).getAvatarUrl(), "someurl");
    }

    @Test
    public void cacheTest(){


    }

    private MockResponse createUserResponse(String login, String avatar) {
        String body = "{\"login\":\"" + login + "\", \"avatar_url\":\"" + avatar + "\"}";
        return new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody(body);
    }


    @After
    public void tearDown() {

    }

}
