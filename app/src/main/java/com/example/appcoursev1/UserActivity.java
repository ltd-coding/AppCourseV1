package com.example.appcoursev1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.Calendar;

public class UserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private DataBase dbLogin;
    private SQLiteDatabase db;
    private TextView userName,email;
    private int data;
    private Cursor c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ///setup
        //-----------------------
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


        //Get Data from profile
        data=getIntent().getIntExtra(MainActivity.EXTRA_NUMBER,0); //id user
        c=dbLogin.GetData("SELECT * FROM PROFILE WHERE id= '"+ data +"'");
        c.moveToFirst();
        Toast.makeText(this,""+data,Toast.LENGTH_SHORT).show();
        //-------------------------
        setHeaderValue();



        Toolbar toolbar = findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);

        drawer=findViewById(R.id.drawer_layout_user);
        NavigationView navigationView=findViewById(R.id.nav_view_user);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this, drawer, toolbar,R.string.nav_drawer_open,R.string.nav_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //set default fragment
        //------
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user,
                    new LessonFragmentUser()).commit();
            navigationView.setCheckedItem(R.id.nav_lesson);
        }



    }

    private void setHeaderValue() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_user);
        View headerView = navigationView.getHeaderView(0);
        TextView drawerUsername = (TextView) headerView.findViewById(R.id.drawer_profileUser);
        TextView drawerAccount = (TextView) headerView.findViewById(R.id.drawer_profileEmail);

        String dataProfileName=c.getString(1);
        String dataProfileEmail=c.getString(4);
        drawerUsername.setText(dataProfileName);
        drawerAccount.setText(dataProfileEmail);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Navigate
        switch (item.getItemId()){
            case R.id.nav_lesson:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user,
                        new LessonFragmentUser()).commit();
                break;
            case R.id.nav_test:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user,
                        new TestFragmentUser()).commit();
                break;
            case R.id.nav_profile:
                Toast.makeText(this,"Changing profile",Toast.LENGTH_SHORT).show();
                getProfile();
                break;
            case R.id.nav_exit:
                getExitCmd();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private void getProfile() {
        //setup
        //------------------
        final Dialog dialogEdit=new Dialog(this);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater=getLayoutInflater();
        View layout=inflater.inflate(R.layout.dialog_edit_profile_user,null);
        dialogEdit.setContentView(layout);

        //---------------------
        final EditText profileName=(EditText) dialogEdit.findViewById(R.id.profile_name);
        final EditText profileGender=(EditText) dialogEdit.findViewById(R.id.profile_gender);
        final EditText profileEmail=(EditText) dialogEdit.findViewById(R.id.profile_email);
        final TextView profileScore=(TextView) dialogEdit.findViewById(R.id.profile_score);
        final TextView profileProcess=(TextView) dialogEdit.findViewById(R.id.profile_process);
        final TextView profileBirth=(TextView) dialogEdit.findViewById(R.id.profile_birth);
        Button btnSave=(Button) dialogEdit.findViewById(R.id.btnSave);
        Button btnCancel=(Button) dialogEdit.findViewById(R.id.btncancel);
        final String[] date = new String[1];

        //Show data
        c=dbLogin.GetData("SELECT * FROM PROFILE WHERE id= '"+ data +"'");
        c.moveToFirst();
        profileName.setText(c.getString(1));
        profileBirth.setText(c.getString(2));
        profileGender.setText(c.getString(3));
        profileEmail.setText(c.getString(4));
        profileScore.setText(c.getString(5));
        profileProcess.setText(c.getString(6));

        //birthday
        date[0]=c.getString(2).trim();



        //--------
        DatePickerDialog.OnDateSetListener dateSetListener;
        profileBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Taking now as default
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);



                DatePickerDialog picker = new DatePickerDialog(UserActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                monthOfYear=monthOfYear+1;
                                date[0] =""+year+"-"+monthOfYear+"-"+dayOfMonth;
                                profileBirth.setText(date[0]);
                            }
                        }, year, month, day);
                picker.show();
            }
        });



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName=profileName.getText().toString();
                String newGender=profileGender.getText().toString();
                String newEmail=profileEmail.getText().toString();
                if (!isEmailValid(newEmail)) {
                    Toast.makeText(UserActivity.this,"Email khong hop le",Toast.LENGTH_SHORT).show();
                }
                else {
                    dbLogin.QueryData("UPDATE PROFILE SET HOTEN='" + newName + "',GIOITINH='" + newGender + "',MAIL='" + newEmail + "',NGAYSINH='" + date[0] + "' WHERE id='" + data + "'");
                    Toast.makeText(UserActivity.this,""+data,Toast.LENGTH_SHORT).show();
                    dialogEdit.dismiss();
                }
            }

            private boolean isEmailValid(CharSequence email) {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEdit.dismiss();
            }
        });


        //----------------------
        dialogEdit.show();
    }



    public void getExitCmd() {
        AlertDialog.Builder dialogXoa=new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có chắc chắn muốn đăng xuất không ??");
        dialogXoa.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialogXoa.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();

    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            getExitCmd();
        }
    }





}
