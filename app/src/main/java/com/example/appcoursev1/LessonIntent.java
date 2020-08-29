package com.example.appcoursev1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class LessonIntent extends AppCompatActivity implements View.OnClickListener {
    private DataBase dbLogin;
    private SQLiteDatabase db;
    private int data,numLesson; //data = id of lesson, numLesson= number of id used to save the lesson
    private Cursor c;
    private TextView name,pos,neg,ques;
    private Button btnPre,btnNext;
    private ImageButton btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_intent);

        //setup
        name=(TextView) findViewById(R.id.lessonName);
        pos=(TextView) findViewById(R.id.lessonPos);
        neg=(TextView) findViewById(R.id.lessonNeg);
        ques=(TextView) findViewById(R.id.lessonQues);

        btnPre=(Button)findViewById(R.id.previousL);
        btnNext=(Button) findViewById(R.id.nextL);
        btnExit=(ImageButton)findViewById(R.id.lesson_exit);


        //---
        //data from sql
        dbLogin = new DataBase(this);
        try {
            dbLogin.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        try {
            db = dbLogin.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        numLesson=3;
        //getData
        data=getIntent().getIntExtra(MainActivity.EXTRA_NUMBER,0);
        //from data, taking numLesson row
        c=dbLogin.GetData("SELECT * FROM LESSON LIMIT '"+ data +"','"+ numLesson +"'");
        c.moveToFirst();

        btnNext.setOnClickListener(this);
        btnPre.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        reloadData();
    }

    private void reloadData() {
        name.setText(c.getString(1));
        pos.setText(c.getString(2));
        neg.setText(c.getString(3));
        ques.setText(c.getString(4));

    }

    //navigate cursor
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.previousL:
                if (c.isFirst()) {
                    c.moveToLast();
                }
                else {
                    c.moveToPrevious();
                }
                reloadData();
                break;
            case R.id.nextL:
                if (c.isLast()) {
                    c.moveToFirst();
                }
                else {
                    c.moveToNext();
                }
                reloadData();
                break;
            case R.id.lesson_exit:
                finish();
                break;
        }

    }
}