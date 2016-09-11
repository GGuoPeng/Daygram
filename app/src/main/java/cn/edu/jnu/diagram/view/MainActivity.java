package cn.edu.jnu.diagram.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jnu.diagram.R;
import cn.edu.jnu.diagram.adapter.MyDiaryInfoAdapter;
import cn.edu.jnu.diagram.db.DataBaseOperate;
import cn.edu.jnu.diagram.model.EmptyInfoModel;
import cn.edu.jnu.diagram.model.MyDiaryInfoModel;

public class MainActivity extends Activity {
    private ListView myDiaryInfoListView = null;
    private ArrayList<Object> listInfo = null;
    private ArrayList<Object> listEmpty = null;
    private DatePicker datePicker = null;
    private TextView diaryShowDate = null;
    private DataBaseOperate dbOperate = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        myDiaryInfoListView = (ListView) findViewById(R.id.diary_info_list);
        datePicker = (DatePicker) findViewById(R.id.dpPicker);
        diaryShowDate = (TextView) findViewById(R.id.diary_show_date);
        datePicker.init(2015, 1, 1, new DatePicker.OnDateChangedListener() {
            @Override
            // 参数依次是：view，year,monthOfYear,dayOfMonth  
            public void onDateChanged(DatePicker arg0, int arg1, int arg2, int arg3) {
                String date = String.valueOf(arg0.getYear()) + "-" + String.valueOf(arg0.getMonth()+1) + "-"
                        + String.valueOf(arg0.getDayOfMonth());
                diaryShowDate.setText(date);
            }
        });
        listInfo = new ArrayList<>();
        listEmpty = new ArrayList<>();
        dbOperate = new DataBaseOperate(this, listInfo, listEmpty);
        refreshDiaryInfo(myDiaryInfoListView, datePicker, "DIARY_INFO");
    }

    public void refreshDiaryInfo(ListView listView, DatePicker dpPicker, String tableName) {
        String current_year = String.valueOf(dpPicker.getYear());
        String current_month = String.valueOf(dpPicker.getMonth());
        dbOperate.showInfo(tableName, current_year, current_month);
        MyDiaryInfoAdapter myDiaryInfoAdapter = new MyDiaryInfoAdapter(this, listInfo, listEmpty);
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
            if (v.getTag() instanceof MyDiaryInfoModel) {
                //强制类型转换
                MyDiaryInfoModel temp_info = (MyDiaryInfoModel) listInfo.get(position);
                intent.putExtra("date", temp_info.getDate());
                intent.putExtra("week", temp_info.getWeek());
                intent.putExtra("diaryinfo", temp_info.getDiaryInfo());
                intent.putExtra("id", temp_info.getId());
                startActivity(intent);
            } else {
                EmptyInfoModel temp_empty = (EmptyInfoModel) listEmpty.get(position);
                intent.putExtra("date", temp_empty.getDate());
                intent.putExtra("week", temp_empty.getWeek());
                intent.putExtra("diaryinfo", "");
                intent.putExtra("id", temp_empty.getId());
                startActivity(intent);
            }
        }
    }
}