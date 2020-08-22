package com.example.appcoursev1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT = "com.example.appcoursev1.EXTRA_TEXT";
    public static final String EXTRA_NUMBER = "com.example.appcoursev1.EXTRA_NUMBER";


    private Button btnLogin,btnNew;
    private EditText edtUser,edtPass;
    private DataBase dbLogin;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup
        //-----------------
        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnNew=(Button)findViewById(R.id.btnNew);
        edtPass=(EditText)findViewById(R.id.edtPass);
        edtUser=(EditText)findViewById(R.id.edtUser);

        dbLogin = new DataBase(this);


        //catching Error
        //-------------------
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

        //DataBase Test
        //---------------
        //Cursor dataLogin=db.rawQuery("SELECT * FROM ACCOUNT",null);
        /*
        Cursor dataLogin = dbLogin.GetData("SELECT * FROM ACCOUNT");
        while (dataLogin.moveToNext()){
            String user1=dataLogin.getString(1);
            Toast.makeText(this,user1,Toast.LENGTH_SHORT).show();
        }
        */

        //onCLick
        onClick();
    }

    private void onClick() { btnLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (edtPass.getText().length()!=0 && edtUser.getText().length()!=0){
                String loginPass=edtPass.getText().toString();
                String loginUser=edtUser.getText().toString();
                String passConfirm=dbLogin.getSingleEntry(loginUser); //confirm user + pass
                if (loginPass.equals(passConfirm)) {
                    Integer Id=dbLogin.getIdEntry(loginUser); //confirm id
                    if (Id==1) {
                        Toast.makeText(MainActivity.this,"tk admin",Toast.LENGTH_SHORT).show();
                        //LoginAdmin();
                        Intent intent=new Intent(MainActivity.this,AdminActivity.class);
                        startActivity(intent);
                    }
                    else {
                        //Toast.makeText(MainActivity.this,"tk user "+Id,Toast.LENGTH_SHORT).show();
                        //LoginUser();
                        Intent intent=new Intent(MainActivity.this,UserActivity.class);
                        intent.putExtra(EXTRA_NUMBER,Id);
                        startActivity(intent);
                    }
                }
                else {
                    Toast.makeText(MainActivity.this,"that bai",Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(MainActivity.this,"Nhap lai",Toast.LENGTH_SHORT).show();
            }

        }
    });
        btnNew.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogNew();
            }
        });

    }

    private void DialogNew() {
        final Dialog dialogNew = new Dialog(this);
        dialogNew.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_add_user, null);
        dialogNew.setContentView(layout);
        dialogNew.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        //-----
        final EditText edtNewUser = (EditText) dialogNew.findViewById(R.id.newUser);
        final EditText edtNewPass = (EditText) dialogNew.findViewById(R.id.newPass);
        Button btnCreate = (Button) dialogNew.findViewById(R.id.btnCreate);
        Button btnCancel = (Button) dialogNew.findViewById(R.id.btnCancel);
        //----
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String createUser = edtNewUser.getText().toString();
                String createPass = edtNewPass.getText().toString();
                if ((createPass.equals("")) || (createUser.equals(""))) {
                    Toast.makeText(MainActivity.this, "Vui long nhap day du", Toast.LENGTH_SHORT).show();
                } else {
                    if (dbLogin.getValidUser(createUser)) {
                        dbLogin.QueryData("INSERT INTO ACCOUNT VALUES(null, '" + createUser + "', '" + createPass + "')");
                        dbLogin.QueryData("INSERT INTO PROFILE VALUES(null,'null','1000-1-1','null','example@test.com','0','0')");
                        Toast.makeText(MainActivity.this, "Tao tai khoan thanh cong", Toast.LENGTH_SHORT).show();
                        dialogNew.dismiss();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Tai khoan da ton tai", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNew.dismiss();
            }
        });
        dialogNew.show();
    }
}


