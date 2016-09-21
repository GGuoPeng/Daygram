package cn.edu.jnu.diagram.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.edu.jnu.diagram.R;
import cn.edu.jnu.diagram.adapter.MyDiaryInfoAdapter;
import cn.edu.jnu.diagram.db.DataBaseHelper;
import cn.edu.jnu.diagram.db.DataBaseOperate;
import cn.edu.jnu.diagram.model.EmptyInfoModel;
import cn.edu.jnu.diagram.model.MyDiaryInfoModel;

public class MainActivity extends Activity {
    private ListView myDiaryInfoListView = null;
    private List<Object> listInfo = null;
    private List<Object> listEmpty = null;
    private DatePicker datePicker = null;
    private TextView diaryShowDate = null;
    private DataBaseOperate dbOperate = null;
    private MyDiaryInfoAdapter myDiaryInfoAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshDiaryInfo(myDiaryInfoListView, datePicker, "DIARY_INFO");
    }

    public void datePickInit(DatePicker dPicker, final TextView showDate) {
        Calendar cal = Calendar.getInstance();
        // 获取年月日时分秒的信息  
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1; // 注意点，一月是从0开始计算的！！！  
        int day = cal.get(Calendar.DAY_OF_MONTH);
        showDate.setText(String.valueOf(year) + "-" + String.valueOf(month));
        dPicker.init(year, cal.get(Calendar.MONTH), day, new DatePicker.OnDateChangedListener() {
            @Override
            // 参数依次是：view，year,monthOfYear,dayOfMonth  
            public void onDateChanged(DatePicker arg0, int arg1, int arg2, int arg3) {
                String date = String.valueOf(arg0.getYear()) + "-" + String.valueOf(arg0.getMonth() + 1);
                showDate.setText(date);
                refreshDiaryInfo(myDiaryInfoListView, datePicker, "DIARY_INFO");
            }
        });
        hidDay(dPicker);
    }

    public void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        myDiaryInfoListView = (ListView) findViewById(R.id.diary_info_list);
        datePicker = (DatePicker) findViewById(R.id.dpPicker);
        diaryShowDate = (TextView) findViewById(R.id.diary_show_date);
        datePickInit(datePicker, diaryShowDate);
        listInfo = new ArrayList<>();
        listEmpty = new ArrayList<>();
        dbOperate = new DataBaseOperate(this, listInfo, listEmpty);
        refreshDiaryInfo(myDiaryInfoListView, datePicker, "DIARY_INFO");
    }

    public void refreshDiaryInfo(ListView listView, DatePicker dpPicker, String tableName) {
        String current_year = String.valueOf(dpPicker.getYear());
        String current_month = String.valueOf(dpPicker.getMonth() + 1);
        dbOperate.showInfo(tableName, current_year, current_month);
        myDiaryInfoAdapter = new MyDiaryInfoAdapter(this, listInfo, listEmpty);
        listView.setAdapter(myDiaryInfoAdapter);
        listView.setVerticalScrollBarEnabled(true);
        listView.setOnItemClickListener(new ListClickListener());
        listView.setSelection(0);
    }

    class ListClickListener implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View v, final int position,
                                long id) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, WriteDiaryActivity.class);
            //点击了有信息的日记
            if (myDiaryInfoListView.getAdapter().getItemViewType(position) == 0) {
                intent.putExtra("year", String.valueOf(datePicker.getYear()));
                intent.putExtra("month", String.valueOf(datePicker.getMonth() + 1));
                intent.putExtra("day", String.valueOf(position + 1));
                startActivity(intent);
            }
            //无信息的日记
            else {
                intent.putExtra("year", String.valueOf(datePicker.getYear()));
                intent.putExtra("month", String.valueOf(datePicker.getMonth() + 1));
                intent.putExtra("day", String.valueOf(position + 1));
                startActivity(intent);
            }
        }
    }

    private void hidDay(DatePicker mDatePicker) {
        Field[] datePickerfFields = mDatePicker.getClass().getDeclaredFields();
        for (Field datePickerField : datePickerfFields) {
            if ("mDaySpinner".equals(datePickerField.getName())) {
                datePickerField.setAccessible(true);
                Object dayPicker = new Object();
                try {
                    dayPicker = datePickerField.get(mDatePicker);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
                // datePicker.getCalendarView().setVisibility(View.GONE);
                ((View) dayPicker).setVisibility(View.GONE);
            }
        }
    }
}