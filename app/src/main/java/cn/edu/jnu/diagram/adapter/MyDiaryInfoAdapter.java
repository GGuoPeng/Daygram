package cn.edu.jnu.diagram.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.edu.jnu.diagram.R;
import cn.edu.jnu.diagram.model.EmptyInfoModel;
import cn.edu.jnu.diagram.model.MyDiaryInfoModel;

public class MyDiaryInfoAdapter extends BaseAdapter {
    private static final int TYPE_INFO = 0;
    private static final int TYPE_EMPTY = 1;
    /**
     * item info 的Viewholder
     */
    private static class ViewHolderInfo {
        TextView date;
        TextView week;
        TextView describe;
    }
    /**
     * item empty 的Viewholder
     */
    private static class ViewHolderEmpty {
        TextView date;
        ImageView img;
    }
    private Context context;
    private LayoutInflater mInflater;
    //整合数据
    private List<Object> data = new ArrayList<>();
    public MyDiaryInfoAdapter(Context context, List<Object> info, List<Object> empty) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        //把数据装载同一个list里面
        //这里把所有数据都转为object类型是为了装载同一个list里面好进行排序
        data.addAll(info);
        data.addAll(empty);
        //按时间排序来填充数据
        Collections.sort(data, new MyComparator());
    }

    /**
     * 获得itemView的type
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        int result = 0;
        if (data.get(position) instanceof MyDiaryInfoModel) {
            result = TYPE_INFO;
        } else if (data.get(position) instanceof EmptyInfoModel) {
            result = TYPE_EMPTY;
        }
        return result;
    }

    /**
     * 获得有多少中view type
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //创建两种不同种类的viewHolder变量
        ViewHolderInfo holderInfo = null;
        ViewHolderEmpty holderEmpty = null;
        //根据position获得View的type
        int type = getItemViewType(position);
        if (convertView == null) {
            //实例化
            holderInfo = new ViewHolderInfo();
            holderEmpty = new ViewHolderEmpty();
            //根据不同的type 来inflate不同的item layout
            //然后设置不同的tag
            //这里的tag设置是用的资源ID作为Key
            switch (type) {
                case TYPE_INFO:
                    convertView = mInflater.inflate( R.layout.mydiay_info_view,null);
                    holderInfo.date = (TextView) convertView.findViewById(R.id.view_date);
                    holderInfo.week = (TextView) convertView.findViewById(R.id.view_week);
                    holderInfo.describe = (TextView) convertView.findViewById(R.id.view_diary_info);
                    convertView.setTag(R.id.tag_info, holderInfo);
                    break;
                case TYPE_EMPTY:
                    convertView = mInflater.inflate( R.layout.empty_diary,null);
                    holderEmpty.img = (ImageView) convertView.findViewById(R.id.empty_img);
                    holderEmpty.img.setTag(R.drawable.emptyimg);
                    convertView.setTag(R.id.tag_empty, holderEmpty);
                    break;
            }

        } else {
            //根据不同的type来获得tag
            switch (type) {
                case TYPE_INFO:
                    holderInfo = (ViewHolderInfo) convertView.getTag(R.id.tag_info);
                    break;
                case TYPE_EMPTY:
                    holderEmpty = (ViewHolderEmpty) convertView.getTag(R.id.tag_empty);
                    break;
            }
        }
        Object o = data.get(position);
        //根据不同的type设置数据
        switch (type) {
            case TYPE_INFO:
                MyDiaryInfoModel a = (MyDiaryInfoModel) o;
                holderInfo.describe.setText(a.getDiaryInfo());
                holderInfo.date.setText(a.getDate());
                holderInfo.week.setText(a.getWeek());
                break;
            case TYPE_EMPTY:
                EmptyInfoModel b = (EmptyInfoModel) o;
                holderEmpty.img.setImageResource((Integer)holderEmpty.img.getTag());
                break;
        }
        return convertView;
    }

    public class MyComparator implements Comparator {
        public int compare(Object arg0, Object arg1) {
            //根据不同的情况来进行排序
            if (arg0 instanceof MyDiaryInfoModel && arg1 instanceof EmptyInfoModel) {
                MyDiaryInfoModel a0 = (MyDiaryInfoModel) arg0;
                EmptyInfoModel a1 = (EmptyInfoModel) arg1;
                return Integer.valueOf(a0.getDate().split("-")[2]).compareTo(Integer.valueOf(a1.getDate().split("-")[2]));
            } else if (arg0 instanceof EmptyInfoModel && arg1 instanceof MyDiaryInfoModel) {
                EmptyInfoModel a0 = (EmptyInfoModel) arg0;
                MyDiaryInfoModel a1 = (MyDiaryInfoModel) arg1;
                return Integer.valueOf(a1.getDate().split("-")[2]).compareTo(Integer.valueOf(a0.getDate().split("-")[2]));
            } else if (arg0 instanceof MyDiaryInfoModel && arg1 instanceof MyDiaryInfoModel) {
                MyDiaryInfoModel a0 = (MyDiaryInfoModel) arg0;
                MyDiaryInfoModel a1 = (MyDiaryInfoModel) arg1;
                return Integer.valueOf(a0.getDate().split("-")[2]).compareTo(Integer.valueOf(a1.getDate().split("-")[2]));
            } else {
                EmptyInfoModel b0 = (EmptyInfoModel) arg0;
                EmptyInfoModel b1 = (EmptyInfoModel) arg1;
                return Integer.valueOf(b0.getDate().split("-")[2]).compareTo(Integer.valueOf(b1.getDate().split("-")[2]));
            }
        }
    }
}