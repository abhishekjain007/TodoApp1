package com.example.abhij.todoapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Event_display extends AppCompatActivity {


    Bundle bundle;
    TextView title;
    TextView content;

    OpenHelper openHelper ;

    ArrayList<String> commentsArrayList;
    ArrayAdapter<String> commentAdapter;
    ListView commentListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_display);


        title=findViewById(R.id.display_Title);
        content=findViewById(R.id.display_Content);
        commentListView= findViewById(R.id.display_commentListView);


        openHelper= OpenHelper.getOpenHelperInstance(this.getApplicationContext());
        commentsArrayList=new ArrayList<>();

        bundle = getIntent().getExtras();

        populateDetailsAndComments();

        commentAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,commentsArrayList);

        commentListView.setAdapter(commentAdapter);
        commentListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int position=i;
                AlertDialog.Builder builder =new AlertDialog.Builder(Event_display.this);
                builder.setTitle("Alert !")
                        .setMessage("DO YOU REALLY WANT TO DELETE THE COMMENT ");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        commentsArrayList.remove(position);
                        commentAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                builder.show();
                return true;
            }
        });

        }

    private void populateDetailsAndComments() {
        SQLiteDatabase db=openHelper.getReadableDatabase();
        int id= bundle.getInt(Constants.ID_KEY,-1);
        String selectionArgs[]={id+""};
        Cursor cursor =db.query(Contract.Events.TABLE_NAME,null, Contract.Events.ID + " = ?",selectionArgs,null,null,null,null);

        cursor.moveToFirst();
        title.setText(cursor.getString(cursor.getColumnIndex(Contract.Events.TITLE)));
        content.setText(cursor.getString(cursor.getColumnIndex(Contract.Events.CONTENT)));


        cursor=db.query(Contract.Comments.TABLE_NAME,null,Contract.Comments.EVENT_ID +" = ? ",selectionArgs,null,null,Contract.Comments.ID +" ASC ");
        while(cursor.moveToNext())
        {
            String comment=cursor.getString(cursor.getColumnIndex(Contract.Comments.COMMENT));
            commentsArrayList.add(comment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater =new MenuInflater(this);
        inflater.inflate(R.menu.display_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.Menu_Add_Comment:
                Intent intent=new Intent(this,Event_Comment.class);
                intent.putExtras(bundle);

                startActivityForResult(intent,Constants.ADD_COMMENT_REQUEST);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    public void Display_edit(View view) {


        Intent intent=new Intent(this,Event_edit.class);

        intent.putExtras(bundle);
        startActivityForResult(intent,Constants.EDIT_ACTIVITY_REQUEST);

    }

    public void Display_delete(View view) {
        Intent intent=new Intent();
        intent.putExtras(bundle);
        setResult(Constants.DELETE_RESULT,intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==Constants.EDIT_ACTIVITY_REQUEST)
        {
            if(resultCode==Constants.SAVE_SUCCESS_RESULT)
            {
                Bundle bundle=data.getExtras();
                title.setText(bundle.getString(Constants.TITLE_KEY));
                content.setText(bundle.getString(Constants.CONTENT_KEY));

                setResult(Constants.SAVE_SUCCESS_RESULT,data);
                finish();

            }
            if(resultCode==Constants.DISCARD_RESULT)
            {
                setResult(Constants.DISCARD_RESULT);
                finish();
            }
        }
        else if(requestCode==Constants.ADD_COMMENT_REQUEST)
        {
            if(resultCode==Constants.COMMENT_SAVE_RESULT)
            {
                Bundle bundle=data.getExtras();

                SQLiteDatabase db=openHelper.getWritableDatabase();
                ContentValues contentValues=new ContentValues();
                contentValues.put(Contract.Comments.COMMENT,bundle.getString(Constants.COMMENT_KEY));
                contentValues.put(Contract.Comments.EVENT_ID,bundle.getInt(Constants.ID_KEY));
                db.insert(Contract.Comments.TABLE_NAME,null,contentValues);
                fetchCommentsFromDb();
                commentAdapter.notifyDataSetChanged();
            }

        }

    }

    private void  fetchCommentsFromDb() {

        SQLiteDatabase db=openHelper.getReadableDatabase();

        String selectionArgs[]={bundle.getInt(Contract.Events.ID,-1)+""};
        Cursor cursor=db.query(Contract.Comments.TABLE_NAME,null,Contract.Comments.EVENT_ID,selectionArgs,null,null,Contract.Comments.ID );
        while(cursor.moveToNext())
        {
            String comment=cursor.getString(cursor.getColumnIndex(Contract.Comments.COMMENT));
            commentsArrayList.add(comment);
        }

    }
}


