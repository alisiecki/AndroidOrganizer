package com.ad44.organizer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

public class Utility {


    public static String getTaskDescription(int complexity, int volume) {
        if (complexity==1){
            switch(volume){
                case 1: return "     Easy and quick";
                case 2: return "     Easy";
                case 3: return "     Probably boredom";
            }
        }
        if (complexity==2){
            switch(volume){
                case 1: return "     Chance for FLOW";
                case 2: return "     Chance for FLOW";
                case 3: return "     Pretty hard, crash it";
            }
        }
        if (complexity==3){
            switch(volume){
                case 1: return "     Chance for FLOW";
                case 2: return "     Chance for FLOW";
                case 3: return "     Hard, crash it";
            }
        }
        return "";

    }

    public static void showSomeMessage(String title, String message, Context context){

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message);

        builder.setCancelable(true);
        builder.setNegativeButton("Return",null);
        builder.show();

    }


    public static void showInflatedLayout(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View editionView = inflater.inflate(R.layout.activity_task_adding, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(editionView)
                .setTitle("tytul");

        builder.setCancelable(true);
        builder.setNegativeButton("Return", null);
        builder.show();

    }


    public static void copyFile(FileInputStream fromFile, FileOutputStream toFile) throws IOException {
        FileChannel fromChannel = null;
        FileChannel toChannel = null;

        fromChannel = fromFile.getChannel();
        toChannel = toFile.getChannel();
        fromChannel.transferTo(0, fromChannel.size(), toChannel);

        fromChannel.close();
        toChannel.close();

        }
    }
