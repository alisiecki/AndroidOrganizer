package com.ad44.organizer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AllData extends AppCompatActivity {

    ListView listView;
    Button detailedData;
    ArrayAdapter<String> mAdapter;

    ArrayList<Task> allTasksCollection = MainActivity.myDb.getTasksFromTableUsingProperSearchingAssumptions(new String[]{" 1,2,3", "1,2,3", "1,2,3", "0,1"});
    ArrayList<String> listOfStringsForAdapter = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_data);


        listView = findViewById(R.id.task_list);
        detailedData = findViewById(R.id.detailed_data);


        detailedData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.showSomeMessage("All data", MainActivity.myDb.getAllDataInOneString(getApplicationContext()),AllData.this);
            }
        });


        showAllTaskCollection();


    }


    public void deleteTask(View view) {

        View parent = (View) view.getParent();
        TextView textViewOfClickedElement = parent.findViewById(R.id.task_content);
        String textContentOfClickedElement = String.valueOf(textViewOfClickedElement.getText());

        int id = 0;
        for (Task elem : allTasksCollection) {
            if (elem.getText().equals(textContentOfClickedElement)) {
                id = elem.getId();
            }
        }

        MainActivity.myDb.deleteTask(id);

        textViewOfClickedElement.setText(getString(R.string.deleted));


    }


    public void showAllTaskCollection(){


        if(allTasksCollection !=null){
            for (Task elem : allTasksCollection) {
                listOfStringsForAdapter.add(elem.getText());
            }
            mAdapter = new ArrayAdapter<>(this, R.layout.task_item, R.id.task_content, listOfStringsForAdapter);
            listView.setAdapter(mAdapter);

        }


    }



}