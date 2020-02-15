package com.ad44.organizer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.ContentValues;
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

    ContentValues randomTaskFromTable;
    String taskInfo="Let's start";
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





        drawnTaskText.setText(taskInfo);




        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWarningAfterDeleteClick("Task completed!", "Czy na pewno chcesz usunać?");

            }
        });

        crash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), TaskAddingActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Crash! Jest moc!", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Pamietaj o dodaniu enjoy tasku", Toast.LENGTH_LONG).show();
            }
        });


        work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Great, give it a try", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSearchingAssumptions();
                randomTaskFromTable = MainActivity.myDb.getRandomRowFromTable(assumptions);
                setTaskInfoButNotOnScreenForAlertDialogPurpose();
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
                if(randomTaskFromTable!=null){
                    //showEditingSettings(randomTaskFromTable.getAsInteger("ID"),getApplicationContext());
                    //showEditingSettings(1,getApplicationContext());

                    //randomTaskFromTable=MainActivity.myDb.getDataPerIdInContentValue(randomTaskFromTable.getAsInteger("ID"));
                    //setTaskInfoButNotOnScreenForAlertDialogPurpose();
                    showEditingSettings(randomTaskFromTable.getAsInteger("ID"),SingleTaskActivity.this);
                }
                else{
                    Toast.makeText(SingleTaskActivity.this, "randomTaskFromTable=null", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


    public void setTaskInfoButNotOnScreenForAlertDialogPurpose(){

        if(randomTaskFromTable!=null) {
            taskInfo = "ID: " + randomTaskFromTable.getAsInteger("ID") + "\n" +
                    "Task:" + randomTaskFromTable.getAsString("Task") + "\n" +
                    "Complexity: " + randomTaskFromTable.getAsInteger("Complexity") + "\n" +
                    "Volume: " + randomTaskFromTable.getAsInteger("Volume") + "\n" +
                    "Urgency: " + randomTaskFromTable.getAsInteger("Urgency") + "\n" +
                    "Enjoyment: " + randomTaskFromTable.getAsInteger("Enjoyment");
        }
        else{
            taskInfo="brak taskow o podanych zalozeniach";

        }

    }


    public void showWarningAfterDeleteClick(String title, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("Nie", null);

        builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                MainActivity.myDb.deleteTask(randomTaskFromTable.getAsInteger("ID"));
                Toast.makeText(getApplicationContext(),"Done! good job.", Toast.LENGTH_SHORT).show();
                finish();


            }
        });


        builder.show();


    }


    public void showDialogWithTaskRandomisation(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Task will be:");

        if(randomTaskFromTable!=null) {
            builder.setMessage("Complexity: " + randomTaskFromTable.get("Complexity") + "\n" +
                    "Volume: " + randomTaskFromTable.get("Volume") +
                    Utility.getTaskDescription(randomTaskFromTable.getAsInteger("Complexity"), randomTaskFromTable.getAsInteger("Volume")) + "\n" +
                    "Urgency: " + randomTaskFromTable.get("Urgency") + "\n" +
                    "Enjoyment: " + randomTaskFromTable.get("Enjoyment"));

        }
        else{
            builder.setMessage("no data");
        }


        builder.setNegativeButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                randomTaskFromTable = MainActivity.myDb.getRandomRowFromTable(assumptions);
                setTaskInfoButNotOnScreenForAlertDialogPurpose();
                showDialogWithTaskRandomisation();
            }
        });

        builder.setPositiveButton("Make it happened", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                drawnTaskText.setText(taskInfo);
            }
        });
        builder.show();

    }


    public void setSearchingAssumptions(){

        if(complexitySwitch1.isChecked()==true){
            complexity+="1,";
        }
        if(complexitySwitch2.isChecked()==true){
            complexity+="2,";
        }
        if(complexitySwitch3.isChecked()==true){
            complexity+="3,";
        }
        if(complexitySwitch1.isChecked()==false && complexitySwitch2.isChecked()==false && complexitySwitch3.isChecked()==false){
            complexity="1,2,3,";
        }
        complexity=complexity.substring(0,complexity.length()-1);


        if(volumeSwitch1.isChecked()==true){
            volume+="1,";
        }
        if(volumeSwitch2.isChecked()==true){
            volume+="2,";
        }
        if(volumeSwitch3.isChecked()==true){
            volume+="3,";
        }
        if(volumeSwitch1.isChecked()==false && volumeSwitch2.isChecked()==false && volumeSwitch3.isChecked()==false){
            volume="1,2,3,";
        }
        volume=volume.substring(0,volume.length()-1);


        if(urgencySwitch1.isChecked()==true){
            urgency+="1,";
        }
        if(urgencySwitch2.isChecked()==true){
            urgency+="2,";
        }
        if(urgencySwitch3.isChecked()==true){
            urgency+="3,";
        }
        if(urgencySwitch1.isChecked()==false && urgencySwitch2.isChecked()==false && urgencySwitch3.isChecked()==false){
            urgency="1,2,3,";
        }
        urgency=urgency.substring(0,urgency.length()-1);


        if(enjoymentSwitch1.isChecked()==true){
            enjoyment+="0,";
        }
        if(enjoymentSwitch2.isChecked()==true){
            enjoyment+="1,";
        }
        if(enjoymentSwitch1.isChecked()==false && enjoymentSwitch2.isChecked()==false){
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

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Choose feature to edit:")
                .setItems(featuresToBeEdited, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int which) {
                        Toast.makeText(context, "m: "+featuresToBeEdited[which], Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(context)
                                .setTitle("Choose value: ")
                                .setView(editText)
                                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
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
                                .setNegativeButton("Return", null);
                        builder2.show();

                    }})
                .setNegativeButton("Return", null);
        builder.show();

    }


}
