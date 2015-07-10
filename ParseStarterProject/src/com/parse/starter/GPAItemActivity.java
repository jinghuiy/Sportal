package com.parse.starter;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class GPAItemActivity extends ActionBarActivity {

    private Button mCorrectButton;
    private Button mNewButton;

    private EditText assignmentEditText;
    private EditText scoreEditText;
    private EditText maxScoreEditText;
    private EditText weightageEditText;

    private Toast mToast;

    private ArrayList<GPAItem> mGPAItems;
    private GPAItem x;

    private double totalMarks = 0;
    private double totalTotal = 0;
    private int finalScore;
    private double checkWeightage = 0;

    private Subject newSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa_item);

        // get action bar
        ActionBar actionBar = getActionBar();

        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);

        mCorrectButton = (Button)findViewById(R.id.correct);
        mNewButton = (Button)findViewById(R.id.add);
        assignmentEditText = (EditText)findViewById(R.id.inputname);
        scoreEditText = (EditText)findViewById(R.id.inputmarks);
        maxScoreEditText = (EditText)findViewById(R.id.inputtotal);
        weightageEditText = (EditText)findViewById(R.id.inputweightage);
        mGPAItems = new ArrayList<GPAItem>();

        mNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (assignmentEditText.getText().toString().equals("") || scoreEditText.getText().toString().equals("") || maxScoreEditText.getText().toString().equals("") || weightageEditText.getText().toString().equals("")) {
                    showDialog("Incomplete", "Incomplete", "Please fill in all fields before adding an assignment.", "", "Okay");
                }
                else {
                    x = new GPAItem(assignmentEditText.getText().toString(), Double.valueOf(scoreEditText.getText().toString()), Double.valueOf(maxScoreEditText.getText().toString()), Double.valueOf(weightageEditText.getText().toString()));
                    compute();
                    checkWeightage = totalTotal + x.getmWeightage();
                    newCheck();
                }
            }
        });

        mCorrectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGPAItems.size() == 0) {
                    //so GPA won't be calculated if no entries have been filled in yet
                    showDialog("No assignments", "No assignments", "You have not added any assignments.", "", "Okay");
                }
                else {
                    compute();
                    correctCheck();
                }
            }
        });
    }

    private void newCheck(){
        if (x.getmMarks() > x.getmTotal()){
            showDialog("a", "Scored more than full marks", "Your score is "+(x.getmMarks()-x.getmTotal())+" marks higher than the total marks. Are you sure this is correct?", "Yes", "Cancel");
        }
        else addToGPAItem();
    }

    private void correctCheck(){
        if (totalTotal != 100) {
            showDialog("b", "Not 100%", "Your total weightage adds up to " + String.valueOf(totalTotal) + " and your GPA will be calculated based on this weightage.", "", "Okay");
        }
        else calculateGPA();
    }

    private void calculateGPA(){
        compute();
        final double gpa;
        if (finalScore == 0) gpa = 0;
        else if (finalScore < 40) gpa = 0.8;
        else if (finalScore < 45) gpa = 1.2;
        else if (finalScore < 50) gpa = 1.6;
        else if (finalScore < 55) gpa = 2.0;
        else if (finalScore < 60) gpa = 2.4;
        else if (finalScore < 65) gpa = 2.8;
        else if (finalScore < 70) gpa = 3.2;
        else if (finalScore < 80) gpa = 3.6;
        else if (finalScore < 101) gpa = 4.0;
        else gpa = 4.4;
        if (mToast != null) mToast.cancel();

        final Intent intentSend = new Intent(GPAItemActivity.this,SubjectActivity.class);
        intentSend.putExtra("NEWSUB", true);
        final EditText newSubjectText = new EditText(this);

        new AlertDialog.Builder(this)
            .setTitle("New Subject")
            .setMessage("Name of new subject:")
            .setView(newSubjectText)
            .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String newName;
                    if (newSubjectText.getText().toString().equals(""))
                        newName = "<New Subject>";
                    else
                        newName = newSubjectText.getText().toString();
                    newSubject = new Subject(newName, gpa, mGPAItems, finalScore);
                    intentSend.putExtra("NEWSUBJECT",newSubject);
                    startActivity(intentSend);
                }
            })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
    }

    public void addToGPAItem(){
        mGPAItems.add(x);
        compute();
        assignmentEditText.setText(null);
        scoreEditText.setText(null);
        maxScoreEditText.setText(null);
        weightageEditText.setText(null);
        x = null;
    }

    public void compute(){
        totalMarks = 0;
        totalTotal = 0;
        finalScore = 0;
        for (int i = 0; i < mGPAItems.size(); i++) {
            GPAItem item = mGPAItems.get(i);
            totalMarks += (item.getmMarks() / item.getmTotal()) * item.getmWeightage();
            totalTotal += item.getmWeightage();
        }
        finalScore = (int) Math.round((totalMarks / totalTotal) * 100);
    }

    public void showDialog(final String name,String title, String message, String posOption, String negOption){
        new AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(posOption, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (name.equals("a")) addToGPAItem();
                }
            })
            .setNegativeButton(negOption, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (name.equals("b")) calculateGPA();
                }
            })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
    }

}
