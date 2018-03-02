package com.example.abhij.todoapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowAllComments extends AppCompatActivity {

    ListView listView_Comments;
    OpenHelper openHelper;
    ArrayList<String> commentsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_comments);


        listView_Comments=findViewById(R.id.listView_Comments);
        commentsArrayList=new ArrayList<>();

        openHelper=OpenHelper.getOpenHelperInstance(this.getApplicationContext());
        SQLiteDatabase db=openHelper.getReadableDatabase();


        Cursor cursor=db.query(Contract.Comments.TABLE_NAME,null,null,null,null,null,Contract.Comments.ID );
        while(cursor.moveToNext())
        {
            String comment=cursor.getString(cursor.getColumnIndex(Contract.Comments.COMMENT));
            commentsArrayList.add(comment);
        }

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,commentsArrayList);
        listView_Comments.setAdapter(adapter);


    }
}
