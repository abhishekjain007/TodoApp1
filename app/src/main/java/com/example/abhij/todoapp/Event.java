package com.example.abhij.todoapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by abhij on 14-02-2018.
 */

public class Event  {


  

    String title;
    String content;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    Event(String title, String content, int id)
    {
        this.title=title;
        this.content=content;

        this.id=id;
    }

    Event(String title, String content)
    {
        this.title=title;
        this.content=content;
        id=-1;
    }

    public static ArrayList<Event> getEventArrayList(int size)
    {
        ArrayList<Event> eventArray=new ArrayList<>();
        for(int i=0;i<size;i++ )
        {
            eventArray.add(new Event("This is Title "+i,"this is the content of the event having the " +
                    "title"+i));
        }

        return eventArray;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
