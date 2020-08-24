package com.example.appcoursev1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LessonFragmentAdmin extends Fragment implements View.OnClickListener{
    private Button btn[]=new Button[4];
    private Context context;
    private View view;
    private Intent intent;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Code for this fragment
        context=getActivity();
        view=inflater.inflate(R.layout.fragment_lesson_admin,container,false);
        btn[0]=(Button) view.findViewById(R.id.lesson1_adm);
        btn[1]=(Button) view.findViewById(R.id.lesson2_adm);
        btn[2]=(Button) view.findViewById(R.id.lesson3_adm);
        btn[3]=(Button) view.findViewById(R.id.lesson4_adm);
        //Toast.makeText(context, "Admin do something", Toast.LENGTH_SHORT).show();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        for (int i=0;i<4;i++) {
            btn[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lesson1_adm:
                intent=new Intent(context,LessonIntent_adm.class);
                intent.putExtra(MainActivity.EXTRA_NUMBER,0);
                startActivity(intent);
                break;
            case R.id.lesson2_adm:
                intent=new Intent(context,LessonIntent_adm.class);
                intent.putExtra(MainActivity.EXTRA_NUMBER,3);
                startActivity(intent);
                break;
            case R.id.lesson3_adm:
                intent=new Intent(context,LessonIntent_adm.class);
                intent.putExtra(MainActivity.EXTRA_NUMBER,6);
                startActivity(intent);
                break;
            case R.id.lesson4_adm:
                intent=new Intent(context,LessonIntent_adm.class);
                intent.putExtra(MainActivity.EXTRA_NUMBER,9);
                startActivity(intent);
                break;
        }


    }
}
