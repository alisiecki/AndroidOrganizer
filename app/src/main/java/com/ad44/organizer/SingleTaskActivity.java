package com.ad44.organizer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SingleTaskActivity extends AppCompatActivity {


    TextView drawnTaskText;
    Button delete, crash, work, random, edit;

    Task randomTaskFromTable;
    String taskContent;
    String complexity="";
    String volume="";
    String urgency="";
    String enjoyment="";
    String[] assumptions=new String[4];

    SwitchCompat complexitySwitch1, complexitySwitch2, complexitySwitch3;
    SwitchCompat volumeSwitch1, volumeSwitch2, volumeSwitch3;
    SwitchCompat urgencySwitch1, urgencySwitch2, urgencySwitch3;
    SwitchCompat enjoymentSwitch1, enjoymentSwitch2;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);


        drawnTaskText = findViewById(R.id.singleTaskTextView);
        delete = findViewById(R.id.singleActivityDelete);
        crash = findViewById(R.id.singleActivityCrash);
        work = findViewById(R.id.singleActivityWork);
        random = findViewById(R.id.singleActivityRandom);
        edit = findViewById(R.id.Edit);

        complexitySwitch1=findViewById(R.id.complexitySwitch1);
        complexitySwitch2=findViewById(R.id.complexitySwitch2);
        complexitySwitch3=findViewById(R.id.complexitySwitch3);
        volumeSwitch1 = findViewById(R.id.volumeSwitch1);
        volumeSwitch2 = findViewById(R.id.volumeSwitch2);
        volumeSwitch3 = findViewById(R.id.volumeSwitch3);
        urgencySwitch1=findViewById(R.id.urgencySwitch1);
        urgencySwitch2=findViewById(R.id.urgencySwitch2);
        urgencySwitch3=findViewById(R.id.urgencySwitch3);
        enjoymentSwitch1=findViewById(R.id.enjoymentSwitch1);
        enjoymentSwitch2=findViewById(R.id.enjoymentSwitch2);


        taskContent =getString(R.string.text_showed_before_task_draw);
        drawnTaskText.setText(taskContent);



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWarningAfterDeleteClick(getString(R.string.task_completed), getString(R.string.sure_about_deleting));

            }
        });

        crash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), TaskAddingActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),getString(R.string.after_crash), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),getString(R.string.after_crash_2), Toast.LENGTH_LONG).show();
            }
        });


        work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),getString(R.string.after_work), Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSearchingAssumptions();
                randomTaskFromTable = MainActivity.myDb.getRandomTaskFromTable(assumptions);
                setTaskContentButNotOnScreenForAlertDialogPurpose();
                showDialogWithTaskRandomisation();
                complexity="";
                urgency="";
                volume="";
                enjoyment="";

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(randomTaskFromTable !=null){

                    showEditingSettings(randomTaskFromTable.getId(),SingleTaskActivity.this);


                }
                else{
                    Toast.makeText(SingleTaskActivity.this, getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


    public void setTaskContentButNotOnScreenForAlertDialogPurpose(){

        if(randomTaskFromTable !=null) {
            taskContent = "ID: " + randomTaskFromTable.getId() + "\n" +
                    "Task:" + randomTaskFromTable.getText() + "\n" +
                    "Complexity: " + randomTaskFromTable.getComplexity() + "\n" +
                    "Volume: " + randomTaskFromTable.getVolume() + "\n" +
                    "Urgency: " + randomTaskFromTable.getUrgency() + "\n" +
                    "Enjoyment: " + randomTaskFromTable.getEnjoyment();
        }
        else{
            taskContent =getString(R.string.no_data_found);

        }

    }


    public void showWarningAfterDeleteClick(String title, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton(getString(R.string.no), null);

        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                MainActivity.myDb.deleteTask(randomTaskFromTable.getId());
                Toast.makeText(getApplicationContext(),getString(R.string.after_deleting), Toast.LENGTH_SHORT).show();
                finish();


            }
        });


        builder.show();


    }


    public void showDialogWithTaskRandomisation(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(getString(R.string.task_will_be));

        if(randomTaskFromTable !=null) {
            builder.setMessage(getString(R.string.complexity) + randomTaskFromTable.getComplexity() + "\n" +
                    getString(R.string.volume) + randomTaskFromTable.getVolume() +
                    Utility.getTaskDescription(randomTaskFromTable.getComplexity(), randomTaskFromTable.getVolume(),this) + "\n" +
                    getString(R.string.urgency) + randomTaskFromTable.getUrgency() + "\n" +
                    getString(R.string.enjoyment) + randomTaskFromTable.getEnjoyment());

        }
        else{
            builder.setMessage(getString(R.string.no_data_found));
        }


        builder.setNegativeButton(getString(R.string.next), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                randomTaskFromTable = MainActivity.myDb.getRandomTaskFromTable(assumptions);
                setTaskContentButNotOnScreenForAlertDialogPurpose();
                showDialogWithTaskRandomisation();
            }
        });

        builder.setPositiveButton(getString(R.string.make_it_happened), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                drawnTaskText.setText(taskContent);
            }
        });
        builder.show();

    }


    public void setSearchingAssumptions(){

        if(complexitySwitch1.isChecked()){
            complexity+="1,";
        }
        if(complexitySwitch2.isChecked()){
            complexity+="2,";
        }
        if(complexitySwitch3.isChecked()){
            complexity+="3,";
        }
        if(!complexitySwitch1.isChecked() && !complexitySwitch2.isChecked() && !complexitySwitch3.isChecked()){
            complexity="1,2,3,";
        }
        complexity=complexity.substring(0,complexity.length()-1);


        if(volumeSwitch1.isChecked()){
            volume+="1,";
        }
        if(volumeSwitch2.isChecked()){
            volume+="2,";
        }
        if(volumeSwitch3.isChecked()){
            volume+="3,";
        }
        if(!volumeSwitch1.isChecked() && !volumeSwitch2.isChecked() && !volumeSwitch3.isChecked()){
            volume="1,2,3,";
        }
        volume=volume.substring(0,volume.length()-1);


        if(urgencySwitch1.isChecked()){
            urgency+="1,";
        }
        if(urgencySwitch2.isChecked()){
            urgency+="2,";
        }
        if(urgencySwitch3.isChecked()){
            urgency+="3,";
        }
        if(!urgencySwitch1.isChecked() && !urgencySwitch2.isChecked() && !urgencySwitch3.isChecked()){
            urgency="1,2,3,";
        }
        urgency=urgency.substring(0,urgency.length()-1);


        if(enjoymentSwitch1.isChecked()){
            enjoyment+="0,";
        }
        if(enjoymentSwitch2.isChecked()){
            enjoyment+="1,";
        }
        if(!enjoymentSwitch1.isChecked() && !enjoymentSwitch2.isChecked()){
            enjoyment="0,1,";
        }
        enjoyment=enjoyment.substring(0,enjoyment.length()-1);

        assumptions[0]=complexity;
        assumptions[1]=volume;
        assumptions[2]=urgency;
        assumptions[3]=enjoyment;

    }


    public void showEditingSettings(final int id, final Context context) {

        final CharSequence[] featuresToBeEdited = { "Task", "Complexity", "Volume", "Urgency", "Enjoyment" };
        final EditText editText = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle(getString(R.string.choose_feature_to_edit))
                .setItems(featuresToBeEdited, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int which) {
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(context).setTitle(getString(R.string.choose_value)).setView(editText)
                                .setPositiveButton(getString(R.string.done), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which2) {
                                        if(featuresToBeEdited[which].equals("Task")){
                                            MainActivity.myDb.editTaskText(id, (String) featuresToBeEdited[which], editText.getText().toString());
                                        }
                                        else{
                                            MainActivity.myDb.editTaskValues(id, (String) featuresToBeEdited[which],Integer.parseInt(editText.getText().toString()));
                                        }
                                    }
                                })
                                .setNegativeButton(getString(R.string.return_button), null);
                        builder2.show();

                    }})
                .setNegativeButton(getString(R.string.return_button), null);
        builder.show();

    }


}
