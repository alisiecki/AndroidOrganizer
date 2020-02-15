package com.ad44.organizer;

import android.content.ContentValues;

public class Task{

    String text;
    int id, complexity, volume, urgency, enjoyment;

    public Task(int id, String text, int complexity, int volume, int urgency, int enjoyment){

        this.id = id;
        this.text = text;
        this.complexity = complexity;
        this.volume = volume;
        this.urgency = urgency;
        this.enjoyment = enjoyment;

    }

    public Task(ContentValues cv){

        this.id = cv.getAsInteger("Id");
        this.text = cv.getAsString("Task");
        this.complexity = cv.getAsInteger("Complexity");
        this.volume = cv.getAsInteger("Volume");
        this.urgency = cv.getAsInteger("Urgency");
        this.enjoyment = cv.getAsInteger("Enjoyment");

    }


    public String getText() {
        return text;
    }

    public int getComplexity() {
        return complexity;
    }

    public int getVolume() {
        return volume;
    }

    public int getUrgency() {
        return urgency;
    }

    public int getEnjoyment() {
        return enjoyment;
    }

    public int getId() {
        return id;
    }
}
