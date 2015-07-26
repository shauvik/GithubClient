package com.checkdroid.githubclient;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "GITHUB_CLIENT";

    @Inject RestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MainApplication) getApplication()).getComponent().inject(this);
    }

    public void callGithubApi(View v) {
        String username = ((EditText) findViewById(R.id.username)).getText().toString();
        final TextView detailsView = (TextView) findViewById(R.id.details);

        if(!TextUtils.isEmpty(username)) {

            GithubService service = adapter.create(GithubService.class);
            service.user_details(username, new Callback<GithubUser>() {
                @Override
                public void success(GithubUser user, Response response) {
                    String details =  "Username: " + user.login
                                    + "\nName: " + user.name
                                    + "\nLocation: " + user.location
                                    + "\nGithub URL: " + user.html_url
                                    + "\nBlog: " + user.blog
                                    + "\nFollowers: " + user.followers
                                    + "\nFollowing: " + user.following
                                    + "\nRepos: " + user.public_repos
                                    + "\nGists: " + user.public_gists
                                    + "\nJoined: " + user.created_at;
                    Log.d(TAG, details);
                    detailsView.setText(details);
                }

                @Override
                public void failure(RetrofitError error) {
                    detailsView.setText(error.getMessage());
                }
            });


        }
    }
}
