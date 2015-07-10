package com.parse.starter;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class ParseActivity extends Activity {
    public static final String USER_ID_KEY = "userId";
    private static final String TAG = ParseApplication.class.getName();
    private static String sUserId;
    private static final int MAX_CHAT_MESSAGES_TO_SHOW = 100;

    private Handler mHandler = new Handler();
    private Button mSendButton;
    private EditText mMessage;
    private ListView mListview;
    private ArrayList<Message> mMessages;
    private ChatListAdapter mChatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // get action bar
        ActionBar actionBar = getActionBar();

        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);

        mSendButton = (Button) findViewById(R.id.send_button);
        mMessage = (EditText) findViewById(R.id.message);
        mListview = (ListView) findViewById(R.id.list);
        mMessages = new ArrayList<Message>();
        mChatAdapter = new ChatListAdapter(ParseActivity.this, sUserId, mMessages);
        mListview.setAdapter(mChatAdapter);

        // User login
        if (ParseUser.getCurrentUser() != null) { // start with existing user
            startWithCurrentUser();
        } else { // If not logged in, login as a new anonymous user
            login();
        }
        // Run the runnable object defined every 100ms
        mHandler.postDelayed(runnable, 100);
    }

    // Get the userId from the cached currentUser object
    private void startWithCurrentUser() {
        sUserId = ParseUser.getCurrentUser().getObjectId();
        setupMessagePosting();
    }

    // Create an anonymous user using ParseAnonymousUtils and set sUserId
    private void login() {
        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.d(TAG, "Anonymous login failed: " + e.toString());
                } else {
                    startWithCurrentUser();
                }
            }
        });
    }

    private void setupMessagePosting() {
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = mMessage.getText().toString();
                Message message = new Message();
                message.put(USER_ID_KEY, sUserId);
                message.put("body", data);
                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(ParseActivity.this, "Successfully created message on Parse",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                mMessage.setText("");
            }
        });
    }

    // Query messages from Parse so we can load them into the chat adapter
    private void receiveMessage() {
        // Construct query to execute
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        // Configure limit and sort order
        query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);
        query.orderByAscending("createdAt");
        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL
        query.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> messages, ParseException e) {
                if (e == null) {
                    mMessages.clear();
                    mMessages.addAll(messages);
                    mChatAdapter.notifyDataSetChanged(); // update adapter
                    mListview.invalidate(); // redraw listview
                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
    }

    // Defines a runnable which is run every 100ms
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            refreshMessages();
            mHandler.postDelayed(this, 100);
        }
    };

    private void refreshMessages() {
        receiveMessage();
    }
}