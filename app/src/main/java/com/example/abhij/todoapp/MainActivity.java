package com.example.abhij.todoapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    ListView listView;
    public  EventAdapter eventAdapter;
    public ArrayList<Event> eventArrayList=null;

    Bundle bundle;
    OpenHelper openHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView=findViewById(R.id.listView);

        openHelper=OpenHelper.getOpenHelperInstance(this);

        eventArrayList=fetchEventsArrayListFromDB();

        //eventArrayList=Event.getEventArrayList(10);

        eventAdapter = new EventAdapter(this,eventArrayList);
        listView.setAdapter(eventAdapter);
        bundle=new Bundle();



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent=new Intent(MainActivity.this,Event_display.class);
                Event event=eventArrayList.get(i);
//                bundle.putString(Constants.TITLE_KEY,event.getTitle());
//                bundle.putString(Constants.CONTENT_KEY,event.getContent());

                bundle.putInt(Constants.ID_KEY,event.getId());
                intent.putExtras(bundle);
                startActivityForResult(intent,Constants.DETAIL_ACTIVITY_REQUEST);
                }
        });

    }

    private ArrayList<Event> fetchEventsArrayListFromDB() {

        ArrayList<Event> eventArrayList=new ArrayList<>();
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor =db.query(Contract.Events.TABLE_NAME , null,null,null,null,null,null);

        while(cursor.moveToNext())
        {
            String title=cursor.getString(cursor.getColumnIndex(Contract.Events.TITLE));
            String content=cursor.getString(cursor.getColumnIndex(Contract.Events.CONTENT));
            int id= cursor.getInt(cursor.getColumnIndex(Contract.Events.ID));

            Event event =new Event( title,content,id);

            eventArrayList.add(event);
        }
        return eventArrayList;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.list_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.Menu_Add:
                Intent intent=new Intent(this,Event_edit.class);
                startActivityForResult(intent,Constants.ADD_EVENT_REQUEST);
                break;

            case R.id.Menu_ShowAllComments:
                Intent intent1=new Intent(this,ShowAllComments.class);
                startActivity(intent1);
                break;
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        SQLiteDatabase db= openHelper.getWritableDatabase();

        if(requestCode==Constants.ADD_EVENT_REQUEST) {
            if (resultCode == Constants.SAVE_SUCCESS_RESULT) {
                bundle = data.getExtras();
//                Event event = new Event(bundle.getString(Constants.TITLE_KEY), bundle.getString(Constants.CONTENT_KEY));

                ContentValues contentValues=new ContentValues();
                contentValues.put(Contract.Events.TITLE,bundle.getString(Constants.TITLE_KEY));
                contentValues.put(Contract.Events.CONTENT,bundle.getString(Constants.CONTENT_KEY));

                db.insert(Contract.Events.TABLE_NAME,null,contentValues);

//                event.setId(id);
//                eventArrayList.add(event);

                eventArrayList.clear();
                eventArrayList.addAll(fetchEventsArrayListFromDB());
//                eventArrayList=fetchEventsArrayListFromDB();
                eventAdapter.notifyDataSetChanged();

                Toast.makeText(this,"Event Added",Toast.LENGTH_SHORT).show();

            }

            if (requestCode == Constants.DISCARD_RESULT) {
            }

        }
        else if(requestCode==Constants.DETAIL_ACTIVITY_REQUEST){

            if (resultCode == Constants.SAVE_SUCCESS_RESULT) {
                bundle = data.getExtras();
//                Event event = new Event(bundle.getString(Constants.TITLE_KEY), bundle.getString(Constants.CONTENT_KEY));


                int id=bundle.getInt(Constants.ID_KEY);

                ContentValues contentValues=new ContentValues();
                contentValues.put(Contract.Events.TITLE,bundle.getString(Constants.TITLE_KEY));
                contentValues.put(Contract.Events.CONTENT,bundle.getString(Constants.CONTENT_KEY));

                String whereArgs[]={id+""};
                db.update(Contract.Events.TABLE_NAME,contentValues," id = ?",whereArgs);


//                eventArrayList.set(bundle.getInt(Constants.POSITION_KEY), event);
                eventArrayList.clear();
                eventArrayList.addAll(fetchEventsArrayListFromDB());
                eventAdapter.notifyDataSetChanged();

                Toast.makeText(this,"Data Updated",Toast.LENGTH_SHORT).show();

            }

        if(resultCode==Constants.DELETE_RESULT)
            {
                bundle=data.getExtras();
                int id=bundle.getInt(Constants.ID_KEY);

                String  whereArgs[]={id+""};
                db.delete(Contract.Events.TABLE_NAME,"id = ?",whereArgs);

//                eventArrayList.remove(bundle.getInt(Constants.POSITION_KEY));

                eventArrayList.clear();
                eventArrayList.addAll(fetchEventsArrayListFromDB());
                eventAdapter.notifyDataSetChanged();
                Toast.makeText(this,"Event deleted ",Toast.LENGTH_SHORT).show();


            }
        if (requestCode == Constants.DISCARD_RESULT) {
        }
        }
    }
    public void onButton_deletePressed(int position,int id)
    {
        SQLiteDatabase db=openHelper.getWritableDatabase();
        String whereArgs[]={id+""};
        db.delete(Contract.Events.TABLE_NAME,Contract.Events.ID+"= ?",whereArgs);
        eventArrayList.remove(position);
        eventAdapter.notifyDataSetChanged();
        Toast.makeText(this,"Event deleted",Toast.LENGTH_SHORT).show();

    }




}
