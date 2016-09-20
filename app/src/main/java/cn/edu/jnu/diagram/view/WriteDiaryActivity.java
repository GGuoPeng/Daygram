package cn.edu.jnu.diagram.view;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import cn.edu.jnu.diagram.R;
import cn.edu.jnu.diagram.db.DataBaseHelper;
import android.util.Log;
/**
 * Created by lenovo on 2016/9/9.
 */

public class WriteDiaryActivity extends Activity {
    private TextView timeTextView = null;
    private TextView weekTextView = null;
    private int id;
    private Button cancel = null;
    private Button confirm = null;
    String diaryinfo = "";
    private SimpleDateFormat simpleDateFormat = null;
    public static final int WEEKDAYS = 7;
    public static EditText diaryInfo = null;
    public static String[] WEEK = {
            "Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.write_diary);
        Intent intent = getIntent();
        String year = intent.getStringExtra("year");
        String month = intent.getStringExtra("month");
        String day = intent.getStringExtra("day");
        timeTextView = (TextView) findViewById(R.id.time);
        String date = year +"-"+month+"-"+day;
        timeTextView.setText(date);
        weekTextView = (TextView) findViewById(R.id.week);
        weekTextView.setText(intent.getStringExtra("week"));
        DataBaseHelper dbHelper = new DataBaseHelper(this, "mydiary.db");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] selectionArgs = new String[]{year, month, day};
        Cursor cursor = db.query("DIARY_INFO", null, "year=? and month=? and day=?",
                selectionArgs, null, null, "day desc");
        diaryInfo = (EditText) findViewById(R.id.diary_info);
        diaryInfo.setTextSize(20f);
        //不空
        if (cursor.moveToFirst()) {
            diaryInfo.setText(cursor.getString(cursor.getColumnIndex("diaryinfo")));
            diaryinfo = cursor.getString(cursor.getColumnIndex("diaryinfo"));
        }
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(WriteDiaryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                backPressed(diaryinfo);
                Intent intent = new Intent();
                intent.setClass(WriteDiaryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public static String DateToWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayIndex < 1 || dayIndex > WEEKDAYS) {
            return null;
        }
        return WEEK[dayIndex - 1];
    }

    public void backPressed(String diaryinfo) {
        //内容为空
        String dates = timeTextView.getText().toString();
        String[] datelist = dates.split("-");
        Log.d("information",diaryinfo);
        Log.d("information",diaryInfo.getText().toString());
        if (diaryinfo==""&& !diaryInfo.getText().toString().equals(diaryinfo)) {
            String insertSql = "insert into DIARY_INFO(date,year,month,day,week,diaryinfo) values" +
                    "('" + timeTextView.getText().toString() + "','" + datelist[0] + "','" + datelist[1] + "','"
                    + datelist[2] + "','" + weekTextView.getText().toString() +
                    "','" + diaryInfo.getText().toString() + "')";
            DataBaseHelper dbHelper = new DataBaseHelper(this, "mydiary.db");
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(insertSql);
            Log.d("debug",insertSql);
            db.close();
            this.finish();
        } else {
            String updateInfo = "UPDATE DIARY_INFO SET date='" + timeTextView.getText().toString() +
                    "',week='" + weekTextView.getText().toString() + "',diaryinfo='" +
                    diaryInfo.getText().toString() +
                    "' where year='" + datelist[0] + "'" +" and month = '"+datelist[1] +"'"
                    +"and day = '"+datelist[2]+"'";
            Log.d("write",updateInfo);
            DataBaseHelper dbHelper = new DataBaseHelper(this, "mydiary.db");
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(updateInfo);
            db.close();
            this.finish();
        }
    }
}
