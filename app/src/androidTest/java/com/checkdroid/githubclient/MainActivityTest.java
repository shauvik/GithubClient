package com.checkdroid.githubclient;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.squareup.okhttp.mockwebserver.MockResponse;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static org.hamcrest.core.StringContains.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import retrofit.RestAdapter;

/**
 * Created by shauvik on 7/25/15.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Inject
    RestAdapter adapter;

    @Singleton
    @Component(modules = MockRestApiModule.class)
    public interface TestComponent extends RestApiComponent {
        void inject(MainActivityTest mainActivityTest);
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    MockRestApiModule mockModule;

    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        MainApplication app = (MainApplication) instrumentation.getTargetContext().getApplicationContext();

        mockModule = new MockRestApiModule();

        TestComponent component = DaggerMainActivityTest_TestComponent.builder()
                .mockRestApiModule(mockModule)
                .build();
        app.setComponent(component);
        component.inject(this); // From Dagger2 sample, probably optional to do this

    }

    @After
    public void tearDown() {
        mockModule.stopServer();
    }

    @Test
    public void testUserShauvik() throws InterruptedException {
        String response = "{\"login\":\"shauvik\",\"id\":141353,\"avatar_url\":\"https://avatars.githubusercontent.com/u/141353?v=3\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/shauvik\",\"html_url\":\"https://github.com/shauvik\",\"followers_url\":\"https://api.github.com/users/shauvik/followers\",\"following_url\":\"https://api.github.com/users/shauvik/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/shauvik/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/shauvik/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/shauvik/subscriptions\",\"organizations_url\":\"https://api.github.com/users/shauvik/orgs\",\"repos_url\":\"https://api.github.com/users/shauvik/repos\",\"events_url\":\"https://api.github.com/users/shauvik/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/shauvik/received_events\",\"type\":\"User\",\"site_admin\":false,\"name\":\"Shauvik Roy Choudhary\",\"company\":\"\",\"blog\":\"http://shauvik.com\",\"location\":\"Atlanta, GA\",\"email\":\"\",\"hireable\":false,\"bio\":null,\"public_repos\":33,\"public_gists\":26,\"followers\":15,\"following\":23,\"created_at\":\"2009-10-18T17:43:33Z\",\"updated_at\":\"2015-07-22T18:42:12Z\"}";
        mockModule.webServer.enqueue(new MockResponse().setBody(response));

        onView(withId(R.id.username)).perform(clearText(), typeText("shauvik"));
        onView(withId(R.id.button)).perform(click());

        Thread.sleep(1000); // TODO: Use idling resource here

        onView(withId(R.id.details)).check(matches(withText(containsString("Shauvik Roy Choudhary"))));

    }


}
