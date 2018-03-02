package com.example.abhij.todoapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Event_edit extends AppCompatActivity {


    Bundle bundle;

    EditText title;
    EditText content;
    OpenHelper openHelper ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        title = findViewById(R.id.edit_Title);
        content = findViewById(R.id.edit_Content);

        openHelper = OpenHelper.getOpenHelperInstance(this.getApplicationContext());

        Intent intent = getIntent();

            bundle = intent.getExtras();

            if(bundle!=null){
//            title.setText(bundle.getString(Constants.TITLE_KEY));
//            content.setText(bundle.getString(Constants.CONTENT_KEY));
                SQLiteDatabase db=openHelper.getReadableDatabase();
                int id= bundle.getInt(Constants.ID_KEY,-1);
                String selectionArgs[]={id+""};
                Cursor cursor =db.query(Contract.Events.TABLE_NAME,null," id = ?",selectionArgs,null,null,null,null);

                cursor.moveToFirst();
                title.setText(cursor.getString(cursor.getColumnIndex(Contract.Events.TITLE)));
                content.setText(cursor.getString(cursor.getColumnIndex(Contract.Events.CONTENT)));

        }
        else
        bundle=new Bundle();
    }

    public void Edit_save(View view) {

        String Title=title.getText().toString();
        String Content =content.getText().toString();

        if(ifStringisNullOrEmpty(Title))
        {
            Toast.makeText(this,"Title Can't Be Empty ",Toast.LENGTH_SHORT).show();
            return;
        }

        if(ifStringisNullOrEmpty(Content))
        {
            Toast.makeText(this,"Content Can't Be Empty ",Toast.LENGTH_SHORT).show();
            return;
        }

        bundle.putString(Constants.TITLE_KEY,Title);
        bundle.putString(Constants.CONTENT_KEY,Content);

        Intent intent=new Intent();
        intent.putExtras(bundle);

        setResult(Constants.SAVE_SUCCESS_RESULT,intent);
        finish();
    }


    public void Edit_discard(View view) {

        setResult(Constants.DISCARD_RESULT);
        finish();
    }
    private boolean ifStringisNullOrEmpty(String string) {
    if(string==null||string.equals(""))
        return true;
    else
        return false;
    }


}
