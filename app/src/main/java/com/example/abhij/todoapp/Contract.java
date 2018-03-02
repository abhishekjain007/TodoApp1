package com.example.abhij.todoapp;

/**
 * Created by abhij on 17-02-2018.
 */

public class Contract {

    public static final String DATABASE_NAME="Event.db";
    public static final int  VERSION =1;

    static class Events{
        public static final String TABLE_NAME = "event";
        public static final String TITLE="title";
        public static final String CONTENT ="content";
        public static final String ID="id";
    }
    static class Comments{
        public static final String TABLE_NAME = "comment";
        public static final String COMMENT ="comment";
        public static final String ID="id";
        public static final String EVENT_ID="event_id";
    }

}
