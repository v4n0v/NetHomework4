package com.example.avetc.nethomework4;

import com.example.avetc.nethomework4.dagger.DaggerTestComponent;
import com.example.avetc.nethomework4.dagger.TestComponent;
import com.example.avetc.nethomework4.dagger.model.TestRepoModule;
import com.example.avetc.nethomework4.mvp.presenter.MainPresenter;
import com.example.avetc.nethomework4.mvp.view.MainView;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.Timeout;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.TestScheduler;


public class MainPresenterUnitTest {


    private MainPresenter presenter;
    private TestScheduler scheduler;


    @Mock
    MainView view;

    @BeforeClass
    public static void setupClass() {

    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        scheduler = new TestScheduler();
        presenter = Mockito.spy(new MainPresenter(scheduler));

        TestComponent component = DaggerTestComponent.builder().testRepoModule(new TestRepoModule()).build();
        component.inject(presenter);

    }

    @AfterClass
    public static void tearDownClass() {

    }

    @Test
    public void onFirstViewAttach() {
        presenter.attachView(view);
        Mockito.verify(view).init();
    }

    @Test
    public void onPermissionsGranted() {
        presenter.onPermissionsGranted();
        scheduler.advanceTimeBy(5, TimeUnit.SECONDS);
        Mockito.verify(presenter).loadUserData();
    }


    @Test
    public void loadUserData() {

    }

    @Test
    public void loadUserDataSuccess() {
        presenter.loadUserData();
        scheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        Mockito.verify(view).setName("v4n0v");
        Mockito.verify(view).loadAvatar(null);
        Mockito.verify(view).updateList();

    }

    public void loadUserDataError() {

    }

    @After
    public void tearDown() {

    }

}
