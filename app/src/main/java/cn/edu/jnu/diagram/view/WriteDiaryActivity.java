package cn.edu.jnu.diagram.view;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import cn.edu.jnu.diagram.R;
import cn.edu.jnu.diagram.db.DataBaseHelper;
import cn.edu.jnu.diagram.model.RichTextEditor;

import android.util.Log;

/**
 * Created by lenovo on 2016/9/9.
 */

public class WriteDiaryActivity extends Activity {
    private TextView timeTextView = null;
    private TextView weekTextView = null;
    private Button cancel = null;
    private Button confirm = null;
    private ImageView clock = null;
    String diaryinfo = "";
    public static EditText diaryInfo = null;
    public static String[] WEEK = {
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
            "Sunday",
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
        String date = year + "-" + month + "-" + day;
        timeTextView.setText(date);
        weekTextView = (TextView) findViewById(R.id.week);
        weekTextView.setText(DateToWeek(year, month, day));
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
        //取消按钮
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(WriteDiaryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //确定按钮
        confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                backPressed(diaryinfo);
                Intent intent = new Intent();
                intent.setClass(WriteDiaryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //时钟
        clock = (ImageView) findViewById(R.id.clock);
        clock.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                long time = System.currentTimeMillis();
                final Calendar mCalendar = Calendar.getInstance();
                mCalendar.setTimeInMillis(time);
                int mHour = mCalendar.get(Calendar.HOUR);
                int mMinutes = mCalendar.get(Calendar.MINUTE);
                int mSeconds = mCalendar.get(Calendar.SECOND);
                String current_time = String.valueOf(mHour)+":"+String.valueOf(mMinutes)+":"+String.valueOf(mSeconds);
                int index = diaryInfo.getSelectionStart();//获取光标所在位置
                Editable edit = diaryInfo.getEditableText();//获取EditText的文字
                if (index < 0 || index >= edit.length() ){
                    edit.append(current_time);
                }else{
                    edit.insert(index,current_time);//光标所在位置插入文字
                }
            }
        });


    }

    public void backPressed(String diaryinfo) {
        //内容为空
        String dates = timeTextView.getText().toString();
        String[] datelist = dates.split("-");
        if (diaryinfo == "" && !diaryInfo.getText().toString().equals(diaryinfo)) {
            String insertSql = "insert into DIARY_INFO(date,year,month,day,week,diaryinfo) values" +
                    "('" + timeTextView.getText().toString() + "','" + datelist[0] + "','" + datelist[1] + "','"
                    + datelist[2] + "','" + weekTextView.getText().toString() +
                    "','" + diaryInfo.getText().toString() + "')";
            DataBaseHelper dbHelper = new DataBaseHelper(this, "mydiary.db");
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(insertSql);
            db.close();
            this.finish();
        } else {
            String updateInfo = "UPDATE DIARY_INFO SET date='" + timeTextView.getText().toString() +
                    "',week='" + weekTextView.getText().toString() + "',diaryinfo='" +
                    diaryInfo.getText().toString() +
                    "' where year='" + datelist[0] + "'" + " and month = '" + datelist[1] + "'"
                    + "and day = '" + datelist[2] + "'";
            DataBaseHelper dbHelper = new DataBaseHelper(this, "mydiary.db");
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(updateInfo);
            db.close();
            this.finish();
        }
    }

    public String DateToWeek(String year, String monthOfYear, String dayOfMonth) {
        int y = Integer.valueOf(year);
        int m = Integer.valueOf(monthOfYear);
        int c = 20;
        int d = Integer.valueOf(dayOfMonth) + 12;
        int w = (y + (y / 4) + (c / 4) - 2 * c + (26 * (m + 1) / 10) + d - 1) % 7;
        return WEEK[w];
    }

    private static final int REQUEST_CODE_PICK_IMAGE = 1023;
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 1022;
    private RichTextEditor editor;
    private View btn1, btn2, btn3;
    private View.OnClickListener btnListener;

    private static final File PHOTO_DIR = new File(
            Environment.getExternalStorageDirectory() + "/DCIM/Camera");
    private File mCurrentPhotoFile;// 照相机拍照得到的图片


}
