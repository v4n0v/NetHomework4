package com.example.avetc.nethomework4;

import com.example.avetc.nethomework4.dagger.TestComponent;
import com.example.avetc.nethomework4.mvp.presenter.MainPresenter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

}