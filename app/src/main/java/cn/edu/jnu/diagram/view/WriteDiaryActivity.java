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
    private static final String TAG = "PRESSED";
    private int id;
    private TextView timeTextView = null;
    private TextView weekTextView = null;
    private Date date = null;
    private Button cancel = null;
    private Button confirm = null;
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
        timeTextView = (TextView) findViewById(R.id.time);
        timeTextView.setText(intent.getStringExtra("date"));
        weekTextView = (TextView) findViewById(R.id.week);
        weekTextView.setText(intent.getStringExtra("week"));
        diaryInfo = (EditText) findViewById(R.id.diary_info);
        diaryInfo.setTextSize(20f);
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
                backPressed();
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

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        backPressed();
    }

    public void backPressed() {
        //内容为空
        String dates = timeTextView.getText().toString();
        String[] datelist = dates.split("-");
        if (!diaryInfo.getText().toString().trim().equals("")) {
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
                    "' where id='" + id + "'";
            DataBaseHelper dbHelper = new DataBaseHelper(this, "mydiary.db");
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(updateInfo);
            db.close();
            this.finish();
        }
    }
}
