package cn.edu.jnu.diagram.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }
    public DataBaseHelper(Context context,String name){
        this(context, name,VERSION);
        Log.d("debug","database helper");
    }
    public DataBaseHelper(Context context,String name,int version){
        this(context, name,null,version);
    }
    private final static String TABLE_INFO="create table if not exists  DIARY_INFO" +
            "(date TEXT,year TEXT,month TEXT,day TEXT,week TEXT,diaryinfo TEXT,id INTEGER PRIMARY KEY AUTOINCREMENT)";
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("debug","db create");
        // TODO Auto-generated method stub
        try {
            Log.d("db","exec");
            db.execSQL(TABLE_INFO);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }
}
