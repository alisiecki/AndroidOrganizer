package com.ad44.organizer;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {


    public static DbHelper myDb;
    Button openSingleTaskPage, getAllData;
    FloatingActionButton fab;
    Toolbar toolbar;

    int CREATE_DESTINATION_FILE_FOR_DATABASE_EXPORTED = 1;
    int RUN_DATABASE_COPYING = 2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myDb = new DbHelper(getApplicationContext());


        openSingleTaskPage =findViewById(R.id.Losuj);
        getAllData = findViewById(R.id.getAllData);
        fab = findViewById(R.id.fab);
        toolbar = findViewById(R.id.toolbar);




        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_name));
        setSupportActionBar(toolbar);



        getAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AllData.class);
                startActivity(intent);
            }
        });


        openSingleTaskPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSingleTaskActivity();
            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTaskAddingActivity();
            }
        });


    }




    public void createDestinationFileAndExportDatabase() {


        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent,CREATE_DESTINATION_FILE_FOR_DATABASE_EXPORTED);

    }


    public void continueDatabaseExporting() {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent,RUN_DATABASE_COPYING);



    }


    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        super.onActivityResult(requestCode, resultCode, resultData);
        Uri uriOfDestinationFile;

        if (resultCode == Activity.RESULT_OK) {

            switch (requestCode) {
                case 1:
                    if (resultData != null) {
                        Toast.makeText(this, getString(R.string.destination_file_for_database_created), Toast.LENGTH_SHORT).show();

                        continueDatabaseExporting();
                        break;
                    }

                case 2:
                    if (resultData != null) {

                        try {
                            File dbToBeExported = new File(getString(R.string.path_to_table_to_be_exporter));
                            FileInputStream fileInputStream = new FileInputStream(dbToBeExported);

                            uriOfDestinationFile = resultData.getData();
                            FileDescriptor fd = this.getContentResolver().openFileDescriptor(uriOfDestinationFile,"w").getFileDescriptor();
                            FileOutputStream fileOutputStream = new FileOutputStream(fd);

                            Utility.copyFile(fileInputStream,fileOutputStream);
                            Toast.makeText(this, getString(R.string.export_completed), Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }

            }
        }
    }



    public void openTaskAddingActivity(){

        Intent intent = new Intent(this, TaskAddingActivity.class);
        startActivity(intent);
    }


    public void openSingleTaskActivity(){

        Intent intent = new Intent(this, SingleTaskActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch(item.getItemId()){
            case R.id.export_database:

                createDestinationFileAndExportDatabase();

                return true;

            case R.id.add_task:
                return true;

            case R.id.info:
                AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle(getString(R.string.info_title)).setMessage(getString(R.string.info_content)).setNegativeButton(getString(R.string.return_button),null);
                builder.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }














}
