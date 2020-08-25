package com.example.appcoursev1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TestIntent extends AppCompatActivity implements View.OnClickListener {
    private DataBase dbLogin;
    private SQLiteDatabase db;
    private int data,key,choose,score; //data = id of question, key = right ans, choose =btn clickec
    private Cursor c;
    //private Button btnA,btnB,btnC,btnD;
    private Button btnNext;
    private Button btn[]=new Button[4];


    private TextView questionTxt,txtScore;
    private boolean clicked;
    private int maxQuestion=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_intent);
        //setup
        //-------------------
        btn[0]=(Button) findViewById(R.id.ansA);
        btn[1]=(Button) findViewById(R.id.ansB);
        btn[2]=(Button) findViewById(R.id.ansC);
        btn[3]=(Button) findViewById(R.id.ansD);
        btnNext=(Button) findViewById(R.id.nextQuestion);
        questionTxt=(TextView) findViewById(R.id.question);
        txtScore=(TextView) findViewById(R.id.testScore);
        score=0;
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
        clicked=false;

        btn[0].setOnClickListener(this);
        btn[1].setOnClickListener(this);
        btn[2].setOnClickListener(this);
        btn[3].setOnClickListener(this);
        btnNext.setOnClickListener(this);

        reloadData();
    }

    private void reloadData() {
        //Shuffle data
        ArrayList<String> list=new ArrayList<>();
        list.add(c.getString(2));
        list.add(c.getString(3));
        list.add(c.getString(4));
        list.add(c.getString(5));
        Collections.shuffle(list);
        //--------------------
        questionTxt.setText(c.getString(1));
        //btn[0].setText(c.getString(2));
        //btn[1].setText(c.getString(3));
        //btn[2].setText(c.getString(4));
        //btn[3].setText(c.getString(5));
        for (int i=0;i<4;i++) {
            btn[i].setText(list.get(i));
        }
        choose=-1;
        key=list.indexOf(c.getString(2));
        clicked=false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ansA:
                clicked=true;
                choose=0;
                break;
            case R.id.ansB:
                clicked=true;
                choose=1;
                break;
            case R.id.ansC:
                choose=2;
                clicked=true;
                break;
            case R.id.ansD:
                choose=3;
                clicked=true;
                break;
            case R.id.nextQuestion:
                if (data==maxQuestion) {
                    clicked=false;
                    finish();
                }
                else {
                    //Button color back to default
                    if (clicked) {
                        for (int i=0;i<4;i++) {
                            btn[i].setEnabled(true);
                        }
                        btn[choose].setBackgroundResource(R.drawable.lightblu_rb);
                        btn[key].setBackgroundResource(R.drawable.lightblu_rb);
                        //btn[choose].setBackgroundResource(android.R.drawable.btn_default);
                        //btn[key].setBackgroundResource(android.R.drawable.btn_default);

                    }
                    c.moveToNext();
                    reloadData();
                    data+=1;
                }
                break;

        }

        if (clicked) {
            if (choose == key ) {
                score++;
            }
            btn[choose].setBackgroundResource(R.drawable.rest_rb);
            btn[key].setBackgroundResource(R.drawable.dellow_rb);
            //btn[choose].setBackgroundColor(Color.red());
            //btn[key].setBackgroundColor(Color.green());
            for (int i=0;i<4;i++) {
                btn[i].setEnabled(false);
            }
            txtScore.setText("Score:"+String.valueOf(score));
        }
    }
}