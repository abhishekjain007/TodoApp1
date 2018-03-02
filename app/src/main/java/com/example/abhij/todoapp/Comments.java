package com.example.abhij.todoapp;

/**
 * Created by abhij on 18-02-2018.
 */

public class Comments {

    int id;
    int Event_id;
    String comment;

    public Comments(int id, int event_id, String comment) {
        this.id = id;
        Event_id = event_id;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvent_id() {
        return Event_id;
    }

    public void setEvent_id(int event_id) {
        Event_id = event_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
