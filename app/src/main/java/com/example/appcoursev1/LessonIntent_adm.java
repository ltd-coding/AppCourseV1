package com.example.appcoursev1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class LessonIntent_adm extends AppCompatActivity implements View.OnClickListener{
    private DataBase dbLogin;
    private SQLiteDatabase db;
    private int data,numLesson,id; //data = id of lesson, numLesson= number of id used to save the lesson, id current pos
    private Cursor c;
    private EditText name,pos,neg,ques;
    private Button btnPre,btnNext,btnSave;
    private ImageButton btnExit;
    private String newData[]=new String[4]; //number of column for each data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_intent_adm);

        //setup
        name=(EditText) findViewById(R.id.lessonName_adm);
        pos=(EditText) findViewById(R.id.lessonPos_adm);
        neg=(EditText) findViewById(R.id.lessonNeg_adm);
        ques=(EditText) findViewById(R.id.lessonQues_adm);

        btnPre=(Button)findViewById(R.id.previousL_adm);
        btnNext=(Button) findViewById(R.id.nextL_adm);
        btnExit=(ImageButton)findViewById(R.id.lesson_exit_adm);
        btnSave=(Button) findViewById(R.id.save_adm);

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
        //loading data
        numLesson=3;
        data=getIntent().getIntExtra(MainActivity.EXTRA_NUMBER,0);
        c=dbLogin.GetData("SELECT * FROM LESSON LIMIT '"+ data +"','"+ numLesson +"'");
        c.moveToFirst();

        btnNext.setOnClickListener(this);
        btnPre.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        reloadData();
    }


    private void reloadData() {
        id=c.getInt(0);
        name.setText(c.getString(1));
        pos.setText(c.getString(2));
        neg.setText(c.getString(3));
        ques.setText(c.getString(4));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.previousL_adm:
                if (c.isFirst()) {
                    c.moveToLast();
                }
                else {
                    c.moveToPrevious();
                }
                reloadData();
                break;
            case R.id.nextL_adm:
                if (c.isLast()) {
                    c.moveToFirst();
                }
                else {
                    c.moveToNext();
                }
                reloadData();
                break;
            case R.id.lesson_exit_adm:
                finish();
                break;
            case R.id.save_adm:
                AlertDialog.Builder dialogLesson=new AlertDialog.Builder(this);
                dialogLesson.setMessage("Bạn chắc chắn muốn thay đổi dữ liệu ?");
                dialogLesson.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getData();
                        reloadData();
                    }
                });
                dialogLesson.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reloadData();
                    }
                });
                dialogLesson.show();
                break;
        }

    }

    private void getData() {
        int currentID=c.getPosition();
        newData[0]=name.getText().toString();
        newData[1]=pos.getText().toString();
        newData[2]=neg.getText().toString();
        newData[3]=ques.getText().toString();

        dbLogin.QueryData("UPDATE LESSON SET NAME='"+ newData[0] +"',POS='"+ newData[1] +"',NEG='"+ newData[2] +"',QUES='"+ newData[3] +"' WHERE id ='"+ id +"' ");
        //renew data
        c=dbLogin.GetData("SELECT * FROM LESSON LIMIT '"+ data +"','"+ numLesson +"'");
        //go back to current position
        c.moveToPosition(currentID);
    }
}