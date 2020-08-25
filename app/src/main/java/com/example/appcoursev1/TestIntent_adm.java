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
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class TestIntent_adm extends AppCompatActivity implements View.OnClickListener {

    private DataBase dbLogin;
    private SQLiteDatabase db;
    private int data; //data = id of question
    private Cursor c;
    //private Button btnA,btnB,btnC,btnD;
    private Button btnNext,btnSave;
    private EditText questionTxt;
    private EditText ans[]=new EditText[4];
    private String newData[]=new String[4]; //number of column for each data
    private String newQues;
    private int maxQuestion=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_intent_adm);

        //setup
        //-------------------
        ans[0]=(EditText) findViewById(R.id.ansKey);
        ans[1]=(EditText) findViewById(R.id.ansDum1);
        ans[2]=(EditText) findViewById(R.id.ansDum2);
        ans[3]=(EditText) findViewById(R.id.ansDum3);
        btnNext=(Button) findViewById(R.id.nextQuestion_adm);
        questionTxt=(EditText) findViewById(R.id.question_adm);
        btnSave=(Button)findViewById(R.id.save_ques_adm);

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
        c=dbLogin.GetData("SELECT * FROM TESTING");
        data=getIntent().getIntExtra(MainActivity.EXTRA_NUMBER,0);
        //data equal id of question in sql, max question = max question + id -1 <=> id number of last question of this specific test
        c.moveToPosition(data);
        maxQuestion=maxQuestion+data-1;

        btnNext.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        reloadData();
    }

    private void reloadData() {
        questionTxt.setText(c.getString(1));
        ans[0].setText(c.getString(2));
        ans[1].setText(c.getString(3));
        ans[2].setText(c.getString(4));
        ans[3].setText(c.getString(5));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_ques_adm:
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
            case R.id.nextQuestion_adm:
                if (data==maxQuestion) {
                    finish();
                }
                else {
                    c.moveToNext();
                    reloadData();
                    data+=1;
                }
                break;
        }
    }

    private void getData() {
        int currentID=c.getInt(0);
        newQues=questionTxt.getText().toString();
        newData[0]=ans[0].getText().toString();
        newData[1]=ans[1].getText().toString();
        newData[2]=ans[2].getText().toString();
        newData[3]=ans[3].getText().toString();

        dbLogin.QueryData("UPDATE TESTING SET QUESTION='"+ newQues +"',ANSKEY='"+ newData[0] +"',ANSDUM1='"+ newData[1] +"',ANSDUM2='"+ newData[2] +"',ANSDUM3='"+ newData[3] +"' WHERE id ='"+ currentID +"'");

        c=dbLogin.GetData("SELECT * FROM TESTING");
        c.moveToPosition(currentID-1);
    }
}