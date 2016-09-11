package cn.edu.jnu.diagram.db;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jnu.diagram.model.EmptyInfoModel;
import cn.edu.jnu.diagram.model.MyDiaryInfoModel;

/**
 * Created by lenovo on 2016/9/8.
 */
public class DataBaseOperate {
    private Activity activity;
    private ArrayList<Object> listInfo;
    private ArrayList<Object> listEmpty;

    public DataBaseOperate(Activity activity, ArrayList<Object> info, ArrayList<Object> empty) {
        super();
        this.activity = activity;
        this.listInfo = info;
        this.listEmpty = empty;
    }

    public void showInfo(String tableName, String current_year, String current_month) {
        DataBaseHelper dbHelper = new DataBaseHelper(activity.getBaseContext(), "mydiary.db");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(tableName, null, "year=? and month=?",
                new String[]{current_year, current_month}, null, null, "day desc");
        listInfo.clear();
        listEmpty.clear();
        //没有相应的数据，根据年月初始化不同数量的EmptyInfoModel
        if (cursor == null) {
            int number;
            if (current_month == "1" || current_month == "3" || current_month == "5" || current_month == "7"
                    || current_month == "8" || current_month == "10" || current_month == "12") {
                number = 31;
            } else if (current_month == "4" || current_month == "6" || current_month == "9"
                    || current_month == "11") {
                number = 30;
            }
            //闰年2月
            else if ((Integer.valueOf(current_year) % 4 == 0 && Integer.valueOf(current_year) % 100 != 0
                    || Integer.valueOf(current_year) % 400 == 0) && current_month == "2") {
                number = 29;
            } else {
                number = 28;
            }
            for (int i = 0; i < number; i++) {
                EmptyInfoModel myEmptyInfo = new EmptyInfoModel();
                myEmptyInfo.setYear(current_year);
                myEmptyInfo.setMonth(current_month);
                myEmptyInfo.setDay(String.valueOf(i + 1));
                String date = current_year + "-" + current_month + "-" + String.valueOf(i + 1);
                myEmptyInfo.setWeek(myEmptyInfo.getWeek(date));
                listEmpty.add(myEmptyInfo);
            }
        } else {
            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex("diaryinfo")) != "") {
                    MyDiaryInfoModel myDiaryInfo = new MyDiaryInfoModel();
                    String date = cursor.getString(cursor.getColumnIndex("date"));
                    String week = cursor.getString(cursor.getColumnIndex("week"));
                    String diaryInfo = cursor.getString(cursor.getColumnIndex("diaryinfo"));
                    String year = cursor.getString(cursor.getColumnIndex("year"));
                    String month = cursor.getString(cursor.getColumnIndex("month"));
                    String day = cursor.getString(cursor.getColumnIndex("day"));
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    myDiaryInfo.setDate(date);
                    myDiaryInfo.setWeek(week);
                    myDiaryInfo.setDiaryInfo(diaryInfo);
                    myDiaryInfo.setId(id);
                    listInfo.add(myDiaryInfo);
                }
                else{
                    EmptyInfoModel myEmptyInfo = new EmptyInfoModel();
                    String week = cursor.getString(cursor.getColumnIndex("week"));
                    String diaryInfo = cursor.getString(cursor.getColumnIndex("diaryinfo"));
                    String year = cursor.getString(cursor.getColumnIndex("year"));
                    String month = cursor.getString(cursor.getColumnIndex("month"));
                    String day = cursor.getString(cursor.getColumnIndex("day"));
                    myEmptyInfo.setYear(year);
                    myEmptyInfo.setMonth(month);
                    myEmptyInfo.setDay(day);
                    listEmpty.add(myEmptyInfo);
                }
            }
        }
        cursor.close();
        db.close();
    }

    public static void delete(int id, Context context) {
        try {
            String del_info = "delete from DIARY_INFO where id ='" + id + "'";
            DataBaseHelper dbHelper = new DataBaseHelper(context, "mydiary.db");
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(del_info);
            db.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void dimSearch(EditText editText) {
        DataBaseHelper dbHelper = new DataBaseHelper(activity.getBaseContext(), "mydiary.db");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("DIARY_INFO", null, "diaryinfo like '%" + editText.getText().toString() + "%'", null, null, null, "id desc");
        listInfo.clear();
        while (cursor.moveToNext()) {
            MyDiaryInfoModel myDiaryInfo = new MyDiaryInfoModel();
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String week = cursor.getString(cursor.getColumnIndex("week"));
            String weather = cursor.getString(cursor.getColumnIndex("weather"));
            String diaryInfo = cursor.getString(cursor.getColumnIndex("diaryinfo"));
            float fontSize = cursor.getFloat(cursor.getColumnIndex("fontsize"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            listInfo.add(myDiaryInfo);
        }
        cursor.close();
        db.close();
    }
}
