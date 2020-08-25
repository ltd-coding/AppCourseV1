package com.example.appcoursev1;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterUserProfile extends BaseAdapter {
    Context context;
    ArrayList<ProfileUser> list;

    public AdapterUserProfile(Context context, ArrayList<ProfileUser> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.listview_row,null);

        ImageView imgAvatar=(ImageView) row.findViewById(R.id.imageAvatar);
        TextView txtTen=(TextView) row.findViewById(R.id.UserName);
        TextView txtId=(TextView) row.findViewById(R.id.UserName_id);
        TextView txtBirth=(TextView) row.findViewById(R.id.UserBirth);
        TextView txtEmail=(TextView) row.findViewById(R.id.UserEmail);
        TextView txtScore=(TextView) row.findViewById(R.id.UserScore);
        TextView txtProcess=(TextView) row.findViewById(R.id.UserProcess);

        imgAvatar.setImageResource(R.drawable.ic_launcher_foreground);//avatar temp,change if needed
        ProfileUser profileUser=list.get(position);
        txtId.setText(profileUser.idUser + "");
        txtTen.setText(profileUser.hoTen);
        txtEmail.setText(profileUser.eMail);
        txtBirth.setText(profileUser.ngaySinh);
        txtScore.setText(profileUser.score);
        txtProcess.setText(profileUser.process);

        return row;
    }
}
