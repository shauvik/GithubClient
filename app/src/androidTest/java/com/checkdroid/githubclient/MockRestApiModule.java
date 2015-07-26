package com.checkdroid.githubclient;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import java.io.IOException;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Created by shauvik on 7/25/15.
 */
@Module
public class MockRestApiModule {

    @Provides
    @Singleton
    RestAdapter providesAdapter() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(webServer.getUrl("/").toString())
//                .setExecutors(AsyncTask.THREAD_POOL_EXECUTOR, new MainThreadExecutor())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return restAdapter;
    }

    MockWebServer webServer;

    public MockRestApiModule() {
        webServer = new MockWebServer();

        try{
            webServer.start();
        } catch (IOException e) {
            throw new RuntimeException("Error starting MockWebServer");
        }
    }

    public void stopServer() {
        try {
            webServer.shutdown();
        } catch (IOException e) {
            throw new RuntimeException("Error stopping MockWebServer");
        }
    }
}
