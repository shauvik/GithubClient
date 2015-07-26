package com.checkdroid.githubclient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Created by shauvik on 7/25/15.
 */
@Module
public class RestApiModule {
    @Provides
    @Singleton
    RestAdapter providesAdapter() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com")
//                .setLogLevel(RestAdapter.LogLevel.FULL) // Uncomment to see network data in logcat
                .build();
        return restAdapter;
    }
}
