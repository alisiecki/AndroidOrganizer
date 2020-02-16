package com.ad44.organizer;

import android.content.ContentValues;

public class Task implements TaskInterface{

    private String text;
    private int id, complexity, volume, urgency, enjoyment;

    public Task(int id, String text, int complexity, int volume, int urgency, int enjoyment){

        this.id = id;
        this.text = text;
        this.complexity = complexity;
        this.volume = volume;
        this.urgency = urgency;
        this.enjoyment = enjoyment;

    }

    public Task(ContentValues cv){

        this.id = cv.getAsInteger("ID");
        this.text = cv.getAsString("Task");
        this.complexity = cv.getAsInteger("Complexity");
        this.volume = cv.getAsInteger("Volume");
        this.urgency = cv.getAsInteger("Urgency");
        this.enjoyment = cv.getAsInteger("Enjoyment");

    }

    @Override
    public String getText() {
        return text;
    }
    @Override
    public int getComplexity() {
        return complexity;
    }
    @Override
    public int getVolume() {
        return volume;
    }
    @Override
    public int getUrgency() {
        return urgency;
    }
    @Override
    public int getEnjoyment() {
        return enjoyment;
    }
    @Override
    public int getId() {
        return id;
    }
}
