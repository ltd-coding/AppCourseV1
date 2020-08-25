package com.example.appcoursev1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TestFragmentAdmin extends Fragment implements View.OnClickListener {
    private Button btnTest1,btnTest2;
    private Context context;
    private View view;
    private Intent intent;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Code for this fragment
        context=getActivity();
        view=inflater.inflate(R.layout.fragment_test_admin,container,false);
        btnTest1=(Button) view.findViewById(R.id.test1_adm);
        btnTest2=(Button) view.findViewById(R.id.test2_adm);
        //Toast.makeText(context, "User do something", Toast.LENGTH_SHORT).show();
        return view ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnTest1.setOnClickListener(this);
        btnTest2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.test1_adm:
                intent=new Intent(context,TestIntent_adm.class);
                intent.putExtra(MainActivity.EXTRA_NUMBER,0);
                startActivity(intent);
                break;
            case R.id.test2_adm:
                intent=new Intent(context,TestIntent_adm.class);
                intent.putExtra(MainActivity.EXTRA_NUMBER,5);
                startActivity(intent);
                break;
        }


    }
}
