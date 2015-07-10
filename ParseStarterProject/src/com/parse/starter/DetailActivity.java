package com.parse.starter;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class DetailActivity extends ListActivity {

    private Subject subjectToShow;
    private double gp;
    private TextView showGP;
    private TextView showPer;
    private TextView subjectText;
    private ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity);

        Intent intentReceive = getIntent();
        subjectToShow = intentReceive.getParcelableExtra("DETAILS");

        gp = subjectToShow.getSubjectGP();
        showGP = (TextView)findViewById(R.id.gp_text);
        showGP.setText(String.valueOf(gp));
        showPer = (TextView)findViewById(R.id.percentage_text);
        if (subjectToShow.getmPercentage()%1 ==0) showPer.setText(String.valueOf(subjectToShow.getmPercentage())+"%");
        else showPer.setText(String.valueOf(Math.round(subjectToShow.getmPercentage() * 10) / 10)+"%");

        subjectText = (TextView)findViewById(R.id.subject_text);
        subjectText.setText(subjectToShow.getName());

        GPAItemAdapter adapter = new GPAItemAdapter(this, R.layout.assignment, subjectToShow.getmGPAItems());
        list = getListView();
        list.setAdapter(adapter);
    }

    private class GPAItemAdapter extends ArrayAdapter<GPAItem> {
        private int mResource;
        private ArrayList<GPAItem> mGPAItems;

        private GPAItemAdapter(Context context, int resource, ArrayList<GPAItem> gpaItems){
            super(context, resource, gpaItems);
            this.mResource = resource;
            this.mGPAItems = gpaItems;
        }

        @Override
        public View getView(int position, View row, ViewGroup parent){
            if (row == null){
                row = getLayoutInflater().inflate(mResource, parent, false);
            }

            GPAItem currentItem = mGPAItems.get(position);

            TextView textItemName = (TextView)row.findViewById(R.id.name);
            TextView textScore = (TextView)row.findViewById(R.id.marks);
            TextView textTotalScore = (TextView)row.findViewById(R.id.total);
            TextView textWeightage = (TextView)row.findViewById(R.id.weightage);

            textItemName.setText(currentItem.getmName());
            if (currentItem.getmMarks()%1 == 0) textScore.setText(String.valueOf(Math.round(currentItem.getmMarks())));
            else textScore.setText(String.valueOf(Math.round(currentItem.getmMarks() * 10) / 10));

            if (currentItem.getmTotal()%1 == 0) textTotalScore.setText(String.valueOf(Math.round(currentItem.getmTotal())));
            else textTotalScore.setText(String.valueOf(Math.round(currentItem.getmTotal() * 10) / 10));

            if (currentItem.getmWeightage()%1 == 0) textWeightage.setText(String.valueOf(Math.round(currentItem.getmWeightage()))+"%");
            else textWeightage.setText(String.valueOf(Math.round(currentItem.getmWeightage() * 10) / 10)+"%");

            return row;
        }
    }
}
