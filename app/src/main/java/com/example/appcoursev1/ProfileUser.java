package com.example.appcoursev1;

import java.sql.Date;

public class ProfileUser {
    public int idUser;
    public String hoTen;
    public String ngaySinh;
    public String gioiTinh;
    public String eMail;
    public String score;
    public String process;

    //constructor
    public ProfileUser(int idUser, String hoTen, String ngaySinh, String gioiTinh, String eMail, String score, String process) {
        this.idUser = idUser;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.eMail = eMail;
        this.score = score;
        this.process = process;
    }
}
