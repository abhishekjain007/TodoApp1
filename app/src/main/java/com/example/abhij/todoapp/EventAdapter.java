package com.example.abhij.todoapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by abhij on 14-02-2018.
 */

public class EventAdapter extends BaseAdapter  {



    Context context;
    ArrayList<Event> eventArrayList;
    OpenHelper openHelper;

    public EventAdapter(Context context,ArrayList<Event> eventArrayList) {

        this.context=context;
        this.eventArrayList=eventArrayList;

        openHelper= OpenHelper.getOpenHelperInstance(context.getApplicationContext());
    }

    @Override
    public int getCount() {

        return eventArrayList.size();
    }

    @Override
    public Object getItem(int i)
    {
        return eventArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {

        Event event=(Event)getItem(i);
        return event.getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View eventView = view;
        if(view==null)
        {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            eventView =inflater.inflate(R.layout.list_item,viewGroup,false);

            ViewHolder holder=new ViewHolder();
            holder.title=eventView.findViewById(R.id.LText_title);
            holder.content=eventView.findViewById(R.id.LText_context);

            holder.delete =eventView.findViewById(R.id.LButton_Delete);
            eventView.setTag(holder);
        }


        final int position =i;
        ViewHolder holder1 = (ViewHolder)eventView.getTag();
        Event event=eventArrayList.get(i);
        //event=(Event)getItem(i);
        holder1.title.setText(event.title);
        holder1.content.setText(event.content);
        holder1.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder =new AlertDialog.Builder(context);
                builder.setTitle("Alert").setMessage("Do you really want to delete this Event .");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity object=(MainActivity)context;
                        object.onButton_deletePressed(position,eventArrayList.get(position).getId());
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
            }
        });
        return eventView;
    }

//
//    public class customClickListener implements View.OnClickListener
//    {
//        int position ;
//        int id;
//        customClickListener(int position,int id)
//        {
//            this.position =position;
//            this.id=id;
//        }
//        @Override
//        public void onClick(View view) {
//
//            SQLiteDatabase db=openHelper.getWritableDatabase();
//            String whereArgs[]={id+""};
//            db.delete(Contract.Events.TABLE_NAME,Contract.Events.ID+"= ?",whereArgs);
//            eventArrayList.remove(position);
//            context.onButton_
//
//        }
//    }

    class ViewHolder
    {
        TextView title;
        TextView content;
        Button delete;
    }





}
