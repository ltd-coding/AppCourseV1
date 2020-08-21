package com.example.appcoursev1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MessageFragmentAdmin extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Code for this fragment
        Context context=getActivity();
        Toast.makeText(context, "Admin do something", Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_message_admin,container,false);
    }
}
