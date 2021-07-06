package com.sophiaxiang.simplegram;

import com.parse.Parse;
import android.app.Application;

public class SimpleGramApplication extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("fSxM58al7RnMcOp7vwFyxRpduHsEO5aqkxasmniz")
                .clientKey("1hTHFmD6VF0kEB7hqPE9CYOE6Gf0G0sl6CEkDVTz")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}