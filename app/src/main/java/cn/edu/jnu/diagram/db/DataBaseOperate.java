package cn.edu.jnu.diagram.db;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jnu.diagram.R;
import cn.edu.jnu.diagram.model.EmptyInfoModel;
import cn.edu.jnu.diagram.model.MyDiaryInfoModel;

public class DataBaseOperate {
    private Activity activity;
    private List<Object> listInfo;
    private List<Object> listEmpty;

    public DataBaseOperate(Activity activity, List<Object> info, List<Object> empty) {
        super();
        this.activity = activity;
        this.listInfo = info;
        this.listEmpty = empty;
    }

    public void showInfo(String tableName, String current_year, String current_month) {
        DataBaseHelper dbHelper = new DataBaseHelper(activity.getBaseContext(), "mydiary.db");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        listInfo.clear();
        listEmpty.clear();
        String[] selectionArgs;
        Cursor cursor=null;
        int number = dayNumbersOfMonth(current_year, current_month);
        //对该月所有的天都查询一遍，如果有则显示，没有则生成空视图
        for (int i = 0; i < number; i++) {
            selectionArgs = new String[]{current_year, current_month, String.valueOf(i + 1)};
            cursor = db.query(tableName, null, "year=? and month=? and day=?",
                    selectionArgs, null, null, "day desc");
            if (!cursor.moveToFirst()) {
                EmptyInfoModel myEmptyInfo = new EmptyInfoModel();
                myEmptyInfo.setYear(current_year);
                myEmptyInfo.setMonth(current_month);
                myEmptyInfo.setDay(String.valueOf(i + 1));
                myEmptyInfo.setImgResourceID(R.id.empty_img);
                listEmpty.add(myEmptyInfo);
            } else {
                MyDiaryInfoModel myDiaryInfo = new MyDiaryInfoModel();
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String week = cursor.getString(cursor.getColumnIndex("week"));
                String year = cursor.getString(cursor.getColumnIndex("year"));
                String month = cursor.getString(cursor.getColumnIndex("month"));
                String day = cursor.getString(cursor.getColumnIndex("day"));
                String diaryInfo = cursor.getString(cursor.getColumnIndex("diaryinfo"));
                myDiaryInfo.setDate(date);
                myDiaryInfo.setYear(year);
                myDiaryInfo.setMonth(month);
                myDiaryInfo.setDay(day);
                myDiaryInfo.setWeek(week);
                myDiaryInfo.setDiaryInfo(diaryInfo);
                myDiaryInfo.setId(id);
                listInfo.add(myDiaryInfo);
            }
        }
        cursor.close();
        db.close();
    }

    public int dayNumbersOfMonth(String current_year, String current_month) {
        int number;
        if (current_month.equals("1") || current_month.equals("3") || current_month.equals("5") ||
                current_month.equals("7") || current_month.equals("8") || current_month.equals("10") ||
                current_month.equals("12")) {
            number = 31;
        } else if (current_month.equals("4") || current_month.equals("6") || current_month.equals("9")
                || current_month.equals("11")) {
            number = 30;
        } else if ((Integer.valueOf(current_year) % 4 == 0 && Integer.valueOf(current_year) % 100 != 0
                || Integer.valueOf(current_year) % 400 == 0) && current_month.equals("2")) {
            number = 29;
        } else {
            number = 28;
        }
        return number;
    }
}
