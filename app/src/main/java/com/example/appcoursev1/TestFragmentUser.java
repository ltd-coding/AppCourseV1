package com.example.appcoursev1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;

import static com.example.appcoursev1.MainActivity.EXTRA_NUMBER;

public class TestFragmentUser extends Fragment implements View.OnClickListener {
   private Button btnTest1,btnTest2;
   private Context context;
   private View view;
   private Intent intent;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Code for this fragment
        context=getActivity();
        view=inflater.inflate(R.layout.fragment_test_user,container,false);
        btnTest1=(Button) view.findViewById(R.id.test1);
        btnTest2=(Button) view.findViewById(R.id.test2);
        //Toast.makeText(context, "User do something", Toast.LENGTH_SHORT).show();
        return view ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnTest1.setOnClickListener(this);
        btnTest2.setOnClickListener(this);
    }

    //calling TestIntent class with extra_number as id
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test1:
                intent=new Intent(context,TestIntent.class);
                intent.putExtra(MainActivity.EXTRA_NUMBER,0);
                startActivity(intent);
                break;
            case R.id.test2:
                intent=new Intent(context,TestIntent.class);
                intent.putExtra(MainActivity.EXTRA_NUMBER,5);
                startActivity(intent);
                break;
        }
    }
}
