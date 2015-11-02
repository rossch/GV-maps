package com.example.rossch.gv_maps;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Christian on 11/1/2015.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Enable Local datastore
        Parse.enableLocalDatastore(this);

        Parse.initialize(this,"DwUBHdgdhazm5riFcZ2zkutcM8qp7HWzEXIjylPg","ZWLMtKhnfMFZWWeD7eAUCcJ1h0qK7HJwFhrhtliM");
    }
}
