package com.parse.starter;

import android.app.Application;
import com.parse.Parse;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "rV3CvHDoa8RE8p2065jridaCsb521g58Y8rJsU6m", "2lLxyn8CBMNHg25CqblU0CPK1CXYMrrcg0YDFcc7");
    }
}
