package com.example.abhij.todoapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Event_Comment extends AppCompatActivity {

    EditText comment;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_comment);

        comment=findViewById(R.id.Comment_text);

        bundle=new Bundle();

    }

    public void Save_Comment(View view) {


        Intent intent=getIntent();
        bundle.putString(Constants.COMMENT_KEY,comment.getText().toString());
        intent.putExtras(bundle);
        setResult(Constants.COMMENT_SAVE_RESULT,intent);
        finish();
    }
}
