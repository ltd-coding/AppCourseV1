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

public class LessonFragmentUser extends Fragment implements View.OnClickListener{
    private Button btn[]=new Button[4];
    private Context context;
    private View view;
    private Intent intent;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Code for this fragment
        context=getActivity();
        view=inflater.inflate(R.layout.fragment_lesson_user,container,false);
        btn[0]=(Button) view.findViewById(R.id.lesson1);
        btn[1]=(Button) view.findViewById(R.id.lesson2);
        btn[2]=(Button) view.findViewById(R.id.lesson3);
        btn[3]=(Button) view.findViewById(R.id.lesson4);
        //Toast.makeText(context, "User do something", Toast.LENGTH_SHORT).show();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        for (int i=0;i<4;i++) {
            btn[i].setOnClickListener(this);
        }
    }

    //calling LessonIntent with extra_Number for id
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lesson1:
                intent=new Intent(context,LessonIntent.class);
                intent.putExtra(MainActivity.EXTRA_NUMBER,0);
                startActivity(intent);
                break;
            case R.id.lesson2:
                intent=new Intent(context,LessonIntent.class);
                intent.putExtra(MainActivity.EXTRA_NUMBER,3);
                startActivity(intent);
                break;
            case R.id.lesson3:
                intent=new Intent(context,LessonIntent.class);
                intent.putExtra(MainActivity.EXTRA_NUMBER,6);
                startActivity(intent);
                break;
            case R.id.lesson4:
                intent=new Intent(context,LessonIntent.class);
                intent.putExtra(MainActivity.EXTRA_NUMBER,9);
                startActivity(intent);
                break;
        }

    }
}
