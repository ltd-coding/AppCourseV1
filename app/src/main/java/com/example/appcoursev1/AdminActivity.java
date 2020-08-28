package com.example.appcoursev1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = findViewById(R.id.toolbar_admin);
        setSupportActionBar(toolbar);

        //setup for drawer
        drawer=findViewById(R.id.drawer_layout_admin);
        NavigationView navigationView=findViewById(R.id.nav_view_admin);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this, drawer, toolbar,R.string.nav_drawer_open,R.string.nav_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //set default fragment
        //------
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,
                    new ProfileFragmentAdmin()).commit();
            navigationView.setCheckedItem(R.id.nav_profile_adm);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_lesson_adm:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,
                        new LessonFragmentAdmin()).commit();
                break;
            case R.id.nav_test_adm:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,
                        new TestFragmentAdmin()).commit();
                break;
            case R.id.nav_profile_adm:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,
                        new ProfileFragmentAdmin()).commit();
                break;
            case R.id.nav_exit_adm:
                getExitCmd();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            getExitCmd();
        }
    }

    private void getExitCmd() {
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
}

