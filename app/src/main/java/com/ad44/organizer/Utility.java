package com.ad44.organizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;


public class Utility {



    public static String getTaskDescription(int complexity, int volume,Context context) {
        if (complexity==1){
            switch(volume){
                case 1: return "     "+context.getString(R.string.easy_and_quick);
                case 2: return "     "+context.getString(R.string.easy);
                case 3: return "     "+context.getString(R.string.probably_boredom);
            }
        }
        if (complexity==2){
            switch(volume){
                case 1: return "     "+context.getString(R.string.chance_for_flow);
                case 2: return "     "+context.getString(R.string.chance_for_flow);
                case 3: return "     "+context.getString(R.string.pretty_hard);
            }
        }
        if (complexity==3){
            switch(volume){
                case 1: return "     "+context.getString(R.string.chance_for_flow);
                case 2: return "     "+context.getString(R.string.chance_for_flow);
                case 3: return "     "+context.getString(R.string.hard);
            }
        }

        return "";

    }

    public static void showSomeMessage(String title, String message, Context context){

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message);

        builder.setCancelable(true);
        builder.setNegativeButton(context.getString(R.string.return_button),null);
        builder.show();

    }


    public static void showInflatedLayout(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View editionView = inflater.inflate(R.layout.activity_task_adding, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(editionView)
                .setTitle("tytul");

        builder.setCancelable(true);
        builder.setNegativeButton(context.getString(R.string.return_button), null);
        builder.show();

    }


    public static void copyFile(FileInputStream fromFile, FileOutputStream toFile) throws IOException {
        FileChannel fromChannel;
        FileChannel toChannel;

        fromChannel = fromFile.getChannel();
        toChannel = toFile.getChannel();
        fromChannel.transferTo(0, fromChannel.size(), toChannel);

        fromChannel.close();
        toChannel.close();

        }
    }
