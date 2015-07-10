package com.parse.starter;

        import android.app.Activity;
        import android.app.Application;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.parse.LogInCallback;
        import com.parse.Parse;
        import com.parse.ParseACL;
        import com.parse.ParseAnonymousUtils;
        import com.parse.ParseCrashReporting;
        import com.parse.ParseException;
        import com.parse.ParseObject;
        import com.parse.ParseUser;
        import com.parse.SaveCallback;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Register parse models here
        ParseObject.registerSubclass(Message.class);

        // Initialize Crash Reporting.
        ParseCrashReporting.enable(this);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(this, "vyxGhI6pS3AQcY7Kj5nFK313kDYBqfxrZ7vuiUDm", "zaIquEWzdZzxtUnSvUhl6AXPub5VcsTu4vHXGuJY");
    }
}
