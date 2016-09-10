package cn.edu.jnu.diagram.db;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.widget.EditText;

import java.util.List;

import cn.edu.jnu.diagram.model.MyDiaryInfoModel;

/**
 * Created by lenovo on 2016/9/8.
 */
public class DataBaseOperate {
    private Activity activity;
    private List<MyDiaryInfoModel> listInfo;

    public DataBaseOperate(Activity activity, List<MyDiaryInfoModel> listInfo) {
        super();
        this.activity = activity;
        this.listInfo = listInfo;
    }

    public void showInfo(String tableName){
        DataBaseHelper dbHelper=new DataBaseHelper(activity.getBaseContext(), "mydiary.db");
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query(tableName, null, null, null, null, null, "id desc");
        listInfo.clear();
        while(cursor.moveToNext()){
            MyDiaryInfoModel myDiaryInfo = new MyDiaryInfoModel();
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String week=cursor.getString(cursor.getColumnIndex("week"));
            String diaryInfo=cursor.getString(cursor.getColumnIndex("diaryinfo"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            listInfo.add(myDiaryInfo);
        }
        cursor.close();
        db.close();
    }

    public static void delete(int id,Context context){
        try {
            String del_info="delete from DIARY_INFO where id ='"+id+"'";
            DataBaseHelper dbHelper=new DataBaseHelper(context, "mydiary.db");
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            db.execSQL(del_info);
            db.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void dimSearch(EditText editText){
        DataBaseHelper dbHelper=new DataBaseHelper(activity.getBaseContext(), "mydiary.db");
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor = db.query("DIARY_INFO", null, "diaryinfo like '%" + editText.getText().toString() + "%'", null, null, null, "id desc");
        listInfo.clear();
        while(cursor.moveToNext()){
            MyDiaryInfoModel myDiaryInfo = new MyDiaryInfoModel();
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String week=cursor.getString(cursor.getColumnIndex("week"));
            String weather = cursor.getString(cursor.getColumnIndex("weather"));
            String diaryInfo=cursor.getString(cursor.getColumnIndex("diaryinfo"));
            float fontSize=cursor.getFloat(cursor.getColumnIndex("fontsize"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            listInfo.add(myDiaryInfo);
        }
        cursor.close();
        db.close();
    }
}
