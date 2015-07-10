package com.parse.starter;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class SubjectActivity extends ListActivity {
    private TextView showGPA;
    public static ArrayList<Subject> mAllSubjects;
    private Button addSubjectButton;
    private ListView list;

    public double finalGPA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);
        showGPA = (TextView)findViewById(R.id.gpatext);
        addSubjectButton = (Button)findViewById(R.id.addSubjectButton);
        Intent intentReceive = getIntent();

        addSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newSubIntent = new Intent(SubjectActivity.this, GPAItemActivity.class);
                startActivity(newSubIntent);
            }
        });

        if (mAllSubjects==null && savedInstanceState!=null) {
            mAllSubjects = savedInstanceState.getParcelableArrayList("ALLSUBJECTS");
        }
        else if (mAllSubjects==null && savedInstanceState==null) {
            mAllSubjects = new ArrayList<Subject>();

            ArrayList<GPAItem> mathItems = new ArrayList<GPAItem>();
            mathItems.add(new GPAItem("Test 1", 29, 30, 5));
            mathItems.add(new GPAItem("PT", 12, 15, 5));
            mathItems.add(new GPAItem("Test 2", 29, 40, 5));
            mathItems.add(new GPAItem("Test 3", 24, 30, 10));
            mAllSubjects.add(new Subject("Math", 4.0, mathItems, 90));

            ArrayList<GPAItem> englishItems = new ArrayList<GPAItem>();
            englishItems.add(new GPAItem("Comprehension", 35, 50, 7));
            englishItems.add(new GPAItem("Speech", 24, 24, 5));
            englishItems.add(new GPAItem("Blog", 17, 20, 5));
            mAllSubjects.add(new Subject("English", 3.6, englishItems, 79));

            ArrayList<GPAItem> chineseItems = new ArrayList<GPAItem>();
            chineseItems.add(new GPAItem("Email", 15, 20, 3));
            chineseItems.add(new GPAItem("Composition", 24, 40, 3));
            chineseItems.add(new GPAItem("Paper 2", 26, 50, 4));
            mAllSubjects.add(new Subject("Chinese", 2.8, chineseItems, 63));

            ArrayList<GPAItem> physicsItems = new ArrayList<GPAItem>();
            physicsItems.add(new GPAItem("Quiz 1", 18, 20, 5));
            physicsItems.add(new GPAItem("Quiz 2", 17, 20, 5));
            physicsItems.add(new GPAItem("PT", 17, 20, 5));
            mAllSubjects.add(new Subject("Physics", 4.0, physicsItems, 83));

        }

        if (intentReceive.getBooleanExtra("NEWSUB", false)) {
            mAllSubjects.add((Subject)intentReceive.getParcelableExtra("NEWSUBJECT"));
        }

        if(mAllSubjects.size()!=0){
            compute();
            showGPA.setText(String.valueOf(finalGPA));
            SubjectAdapter subAdapt = new SubjectAdapter(this, R.layout.subject, mAllSubjects);
            list = getListView();
            list.setAdapter(subAdapt);
        }
        else {
            showGPA.setText("nil");
        }
    }

    @Override
    protected void onListItemClick(ListView list, View view, int position, long id){
        super.onListItemClick(list, view, position, id);
        Subject details = mAllSubjects.get(position);
        Intent detailsIntent = new Intent(SubjectActivity.this, DetailActivity.class);
        detailsIntent.putExtra("DETAILS", details);
        startActivity(detailsIntent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("ALLSUBJECTS", mAllSubjects);
    }

    public void compute(){
        double marks = 0;
        double value = 0;
        for (int i=0; i<mAllSubjects.size(); i ++){
            Subject sub = mAllSubjects.get(i);
            marks += sub.getSubjectGP();
        }
        value = marks/mAllSubjects.size();
        finalGPA = Math.round(value * 100)/100;
    }

    private class SubjectAdapter extends ArrayAdapter<Subject> {
        private int mResource;
        private ArrayList<Subject> mSubjects;

        private SubjectAdapter(Context context, int resource, ArrayList<Subject> subjects){
            super(context, resource, subjects);
            this.mResource = resource;
            this.mSubjects = subjects;
        }

        @Override
        public View getView(int position, View row, ViewGroup parent){
            if (row == null){
                row = getLayoutInflater().inflate(mResource, parent, false);
            }

            Subject currentSubject = mSubjects.get(position);

            TextView subjectItemTextView = (TextView)row.findViewById(R.id.subject_name);
            TextView percentageItemTextView = (TextView)row.findViewById(R.id.percentage_name);
            TextView gpaItemTextView = (TextView)row.findViewById(R.id.gp_name);

            subjectItemTextView.setText(currentSubject.getName());
            percentageItemTextView.setText(String.valueOf(Math.round(currentSubject.getmPercentage())) + "%");
            gpaItemTextView.setText(String.valueOf(currentSubject.getSubjectGP()));

            return row;
        }
    }
}
