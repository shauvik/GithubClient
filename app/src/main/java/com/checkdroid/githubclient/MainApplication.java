package com.checkdroid.githubclient;

import android.app.Application;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import retrofit.RestAdapter;

/**
 * Created by shauvik on 7/25/15.
 */
public class MainApplication extends Application {

    @Singleton
    @Component(modules = RestApiModule.class)
    public interface ProdComponent extends RestApiComponent {
    }

    @Inject
    RestAdapter adapter;

    RestApiComponent component = null;

    @Override
    public void onCreate() {
        super.onCreate();
        if(component == null) {
            component = DaggerMainApplication_ProdComponent.builder()
                    .restApiModule(new RestApiModule())
                    .build();

        }
    }

    public RestApiComponent getComponent() {
        return component;
    }

    public void setComponent(RestApiComponent component) {
        this.component = component;
    }
}
