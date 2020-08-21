package com.example.appcoursev1;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBase extends SQLiteOpenHelper {
    private static String DB_NAME = "dataBaseCourseApp.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase mDataBase;
    private final Context mContext;
    private boolean mNeedUpdate = false;

    public DataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;

        copyDataBase();

        this.getReadableDatabase();
    }

    //Truy vấn dataBase
    public void QueryData(String sql){
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);
    }
    //truy vấn có trả kq: SELECT
    public Cursor GetData(String  sql){
        SQLiteDatabase database=getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        //InputStream mInput = mContext.getResources().openRawResource(R.raw.info);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException {
        mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }

    public String getSingleEntry(String userName)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String password="";
        Cursor c=db.query("ACCOUNT", null, " user=?", new String[]{userName}, null, null, null);
        if(c.moveToFirst())
            do {

                password=c.getString(2);
            }
            while (c.moveToNext());
        c.close();
        return password;
    }

    public Integer getIdEntry(String userName)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Integer ID=-1;
        Cursor c=db.query("ACCOUNT", null, " user=?", new String[]{userName}, null, null, null);
        if(c.moveToFirst())
            do {

                ID=c.getInt(0);
            }
            while (c.moveToNext());
        c.close();
        return ID;
    }

    public boolean getValidUser(String userName)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT user FROM ACCOUNT WHERE user= '"+ userName +"'",null);
        if (c.getCount()==0) {
            c.close();
            return true;
        }
        else {
            c.close();
            return false;
        }
    }



}