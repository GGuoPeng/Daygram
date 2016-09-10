package cn.edu.jnu.diagram.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jnu.diagram.R;
import cn.edu.jnu.diagram.adapter.MyDiaryInfoAdapter;
import cn.edu.jnu.diagram.db.DataBaseOperate;
import cn.edu.jnu.diagram.model.MyDiaryInfoModel;

public class MainActivity extends Activity {
    private ListView myDiaryInfolListView=null;
    private List<MyDiaryInfoModel> listInfo=null;

    private DataBaseOperate dbOperate=null;
    private SharedPreferences sp=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        myDiaryInfolListView = (ListView)findViewById(R.id.diary_info_list);
        listInfo = new ArrayList<MyDiaryInfoModel>();
        dbOperate = new DataBaseOperate(this, listInfo);
        refreshDiaryInfo(myDiaryInfolListView, "DIARY_INFO");
    }

    public void refreshDiaryInfo(ListView listView,String tableName){
        dbOperate.showInfo(tableName);
        MyDiaryInfoAdapter myDiaryInfoAdapter = new MyDiaryInfoAdapter(this, listInfo);
        listView.setAdapter(myDiaryInfoAdapter);
        listView.setVerticalScrollBarEnabled(true);
        listView.setOnItemClickListener(new ListClickListener());
        listView.setSelection(0);
    }

    class ListClickListener implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View v, final int position,
                                long id) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this, WriteDiaryActivity.class);
            intent.putExtra("date", listInfo.get(position).getDate());
            intent.putExtra("week", listInfo.get(position).getWeek());
            intent.putExtra("diaryinfo", listInfo.get(position).getDiaryInfo());
            intent.putExtra("id", listInfo.get(position).getId());
            startActivity(intent);
        }
    }
}