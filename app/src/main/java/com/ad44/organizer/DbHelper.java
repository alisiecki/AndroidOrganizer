package com.ad44.organizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;


import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;


public class DbHelper extends SQLiteOpenHelper {

    public static final String tableName = "MojaTabela";


    public DbHelper(@Nullable Context context) {
        super(context, tableName, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + tableName + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, task TEXT, complexity INT, volume INT, urgency INT, enjoyment INT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+tableName);
        onCreate(db);

    }


    public boolean addTask(String task, int complexity, int volume, int urgency, int enjoyment){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newRowInTable = new ContentValues();
        newRowInTable.put("task",task);
        newRowInTable.put("complexity", complexity);
        newRowInTable.put("volume", volume);
        newRowInTable.put("urgency", urgency);
        newRowInTable.put("enjoyment", enjoyment);

        long isItDone = db.insert(tableName, null, newRowInTable);
        db.close();

        if(isItDone == -1)
            return false;
        else
            return true;

    }


    public boolean deleteTask(int id){
        SQLiteDatabase db = getWritableDatabase();
        int a = db.delete(tableName, "ID="+id, null);
        if(a==1) return true;
        else return false;
    }


    public boolean editTaskValues(int id, String column, int value){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(column,value);
        int a = db.update(tableName,cv,"id="+id,null);
        if(a==1)
            return true;
        else
            return false;

    }


    public boolean editTaskText(int id, String column, String value){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(column,value);
        int a = db.update(tableName,cv,"id="+id,null);
        if(a==1)
            return true;
        else
            return false;

    }


    public String getAllDataInOneString(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM "+tableName, null);

        if(result.getCount() == 0){
            return "no data found";
        }
        else {

            StringBuffer buffer = new StringBuffer();
            while (result.moveToNext()) {
                buffer.append("ID: " + result.getString(0) + "\n");
                buffer.append("Task: " + result.getString(1) + "\n");
                buffer.append("Complexity: " + result.getString(2) + "\n");
                buffer.append("Volume: " + result.getString(3) + "\n");
                buffer.append("Urgency: " + result.getString(4) + "\n");
                buffer.append("Enjoyment: " + result.getString(5) + "\n");
            }
            result.close();
            db.close();
            return buffer.toString();
        }


    }


    public ContentValues getRandomRowFromTable(String[] assumptionsTab){

        SQLiteDatabase db = getWritableDatabase();
        Cursor result = db.query(tableName,null,"complexity IN ("+assumptionsTab[0]+") AND volume IN ("+assumptionsTab[1]+") AND urgency IN ("+assumptionsTab[2]+") AND enjoyment IN ("+assumptionsTab[3]+")",null,null,null,"RANDOM()", "1");

        if(result.getCount() == 0){
            return null;
        }
        else {

            ContentValues rowFromTable = new ContentValues();
            result.moveToFirst();


            rowFromTable.put("ID", result.getInt(0));
            rowFromTable.put("Task", result.getString(1));
            rowFromTable.put("Complexity", result.getInt(2));
            rowFromTable.put("Volume", result.getInt(3));
            rowFromTable.put("Urgency", result.getInt(4));
            rowFromTable.put("Enjoyment", result.getInt(5));

            result.close();
            db.close();
            return rowFromTable;
        }


    }


    public ArrayList<Task> getTasksFromTableUsingProperSearchingAssumptions(String[] assumptionsTab){

        SQLiteDatabase db = getWritableDatabase();
        Cursor result = db.query(tableName,null,"complexity IN ("+assumptionsTab[0]+") AND volume IN ("+assumptionsTab[1]+") AND urgency IN ("+assumptionsTab[2]+") AND enjoyment IN ("+assumptionsTab[3]+")",null,null,null,null, null);

        if(result.getCount() == 0){
            return null;
        }
        else {

            ArrayList<Task> lista = new ArrayList<>();
            Task task;
            while(result.moveToNext()){
                task = new Task(result.getInt(0),result.getString(1),result.getInt(2),result.getInt(3),result.getInt(4),result.getInt(5));
                lista.add(task);
            }

            result.close();
            db.close();

            return lista;
        }


    }


    public ContentValues getDataPerIdInContentValue(int id){


        id=id-1;
        if(id==-1)
            return null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM "+tableName+" WHERE enjoyment=0 ORDER BY ID LIMIT 1 OFFSET "+id, null);

        if(result.getCount() == 0){
            return null;
        }
        else {

            ContentValues rowFromTable = new ContentValues();
            result.moveToFirst();


            rowFromTable.put("ID", result.getInt(0));
            rowFromTable.put("Task", result.getString(1));
            rowFromTable.put("Complexity", result.getInt(2));
            rowFromTable.put("Volume", result.getInt(3));
            rowFromTable.put("Urgency", result.getInt(4));
            rowFromTable.put("Enjoyment", result.getInt(5));

            result.close();
            db.close();

            return rowFromTable;
        }
    }


    public String getDataPerIdInString(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + tableName + " ORDER BY ID LIMIT 1 OFFSET " + id, null);

        if (result.getCount() == 0) {
            return "no data found";
        } else {

            StringBuffer buffer = new StringBuffer();
            while (result.moveToNext()) {
                buffer.append("ID: " + result.getString(0) + "\n");
                buffer.append("Task: " + result.getString(1) + "\n");
                buffer.append("Complexity: " + result.getString(2) + "\n");
                buffer.append("Volume: " + result.getString(3) + "\n");
                buffer.append("Urgency: " + result.getString(4) + "\n");
                buffer.append("Enjoyment: " + result.getString(5) + "\n");
            }

            result.close();
            db.close();

            return buffer.toString();
        }
    }


    public int getTableLenght(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT COUNT(*) FROM "+tableName, null);
        result.moveToFirst();
        return result.getInt(0);


    }






}
