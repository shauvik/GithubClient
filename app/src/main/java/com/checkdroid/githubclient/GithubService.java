package com.checkdroid.githubclient;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by shauvik on 7/25/15.
 */
public interface GithubService {

    @GET("/users/{user}")
    void user_details (
            @Path("user") String user,
            Callback<GithubUser> callback
    );

}