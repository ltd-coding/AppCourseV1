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
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.ArrayList;

public class ProfileFragmentAdmin extends Fragment {
    private DataBase dbLogin;
    private SQLiteDatabase db;
    private View view;
    private Context context;
    private ListView listView;
    private Cursor c;
    //for listview
    ArrayList<ProfileUser> list;
    AdapterUserProfile adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Code for this fragment
        context=getActivity();
        view=inflater.inflate(R.layout.fragment_profile_admin,container,false);
        //Toast.makeText(context, "Admin do something", Toast.LENGTH_SHORT).show();
        addControl();
        return view;
    }

    private void addControl() {
        dbLogin = new DataBase(context);
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
        listView=(ListView) view.findViewById(R.id.listView);
        list=new ArrayList<>();
        adapter=new AdapterUserProfile(context,list);
        listView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        c=dbLogin.GetData("SELECT * FROM PROFILE");
        //clear old list, reload whole new data
        list.clear();
        for (int i=1;i<c.getCount();i++) {
            c.moveToPosition(i);
            int id=c.getInt(0);
            String txtTen=c.getString(1);
            String txtNgay=c.getString(2);
            String txtGioi=c.getString(3);
            String txtEmail=c.getString(4);
            String txtScore=c.getString(5);
            String txtProcess=c.getString(6);
            list.add(new ProfileUser(id,txtTen,txtNgay,txtGioi,txtEmail,txtScore,txtProcess));
        }
        adapter.notifyDataSetChanged();
    }
}
