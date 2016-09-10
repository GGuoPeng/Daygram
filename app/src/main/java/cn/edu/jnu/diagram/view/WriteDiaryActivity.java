package cn.edu.jnu.diagram.view;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.edu.jnu.diagram.R;
import cn.edu.jnu.diagram.db.DataBaseHelper;

/**
 * Created by lenovo on 2016/9/9.
 */
public class WriteDiaryActivity extends Activity {
    private int id;
    private TextView timeTextView=null;
    private TextView weekTextView=null;
    private Calendar cal=Calendar.getInstance();
    private Date date=null;
    private SimpleDateFormat simpleDateFormat=null;
    public static final int WEEKDAYS = 7;
    public static EditText diaryInfo=null;
    public static String[] WEEK = {
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
            "Sunday"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
    }

    public void init(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.write_diary);
        date=cal.getTime();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        timeTextView=(TextView)findViewById(R.id.time);
        timeTextView.setText(simpleDateFormat.format(date));
        weekTextView=(TextView)findViewById(R.id.week);
        weekTextView.setText(DateToWeek(date));
        diaryInfo=(EditText)findViewById(R.id.diary_info);
        diaryInfo.setTextSize(20f);
        try {
            Bundle bundle=getIntent().getExtras();
            if (bundle!=null) {
                String date=bundle.getString("date");
                String week=bundle.getString("week");
                String diary=bundle.getString("diaryinfo");
                id=bundle.getInt("id");
                timeTextView.setText(date);
                weekTextView.setText(week);
                diaryInfo.setText(diary);
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.d("WriteDiaryActivity", e.toString());
        }
    }

    public static String DateToWeek(Date date){
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

    public void backPressed(){
        if (id==0) {
            if (!diaryInfo.getText().toString().trim().equals("")&&diaryInfo!=null) {
                String insertInfo="insert into DIARY_INFO(date,week,diaryinfo) values" +
                        "('"+timeTextView.getText().toString()+"','"+weekTextView.getText().toString()+"','"+diaryInfo.getText().toString()+"','"+diaryInfo.getTextSize()+"')";
                try {
                    DataBaseHelper dbHelper=new DataBaseHelper(this, "mydiary.db");
                    SQLiteDatabase db=dbHelper.getWritableDatabase();
                    db.execSQL(insertInfo);
                    db.close();
                } catch (Exception e) {
                    Log.d("WriteDiaryActivity", e.toString());
                }
                this.finish();
            }
        }
        else {
            String updateInfo="UPDATE DIARY_INFO SET date='"+timeTextView.getText().toString()+
                    "',week='"+weekTextView.getText().toString()+"',diaryinfo='"+
                    diaryInfo.getText().toString()+
                    "' where id='"+id+"'";
            try {
                DataBaseHelper dbHelper=new DataBaseHelper(this, "mydiary.db");
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                db.execSQL(updateInfo);
                db.close();
            } catch (Exception e) {
                Log.d("WriteDiaryActivity", e.toString());
            }
            this.finish();
        }
    }
}
