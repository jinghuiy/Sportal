package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;


public class MainActivity extends Activity {
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(getApplicationContext(), MessageService.class));
                ParseUser.logOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_group_chat) {
            Start_GM();
        }

        else if (id == R.id.action_gpa_cal) {
            Start_cal();
        }

        else if (id == R.id.action_private_chat) {
            Start_PM();
        }

        return super.onOptionsItemSelected(item);
    }

    private void Start_PM() {
        Intent i = new Intent(MainActivity.this, ListUsersActivity.class);
        startActivity(i);
    }

    private void Start_GM() {
        Intent i = new Intent(MainActivity.this, ParseActivity.class);
        startActivity(i);
    }

    private void Start_cal() {
        Intent i = new Intent(MainActivity.this, SubjectActivity.class);
        startActivity(i);
    }
}
