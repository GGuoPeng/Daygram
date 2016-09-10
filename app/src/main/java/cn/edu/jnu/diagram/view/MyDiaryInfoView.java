package cn.edu.jnu.diagram.view;

import android.widget.CheckBox;
import android.widget.TextView;

public class MyDiaryInfoView {
    public TextView date;
    public TextView week;
    public TextView weather;
    public TextView diaryInfo;
    public CheckBox checkBox;
    public TextView getDate() {
        return date;
    }
    public void setDate(TextView date) {
        this.date = date;
    }
    public TextView getWeek() {
        return week;
    }
    public void setWeek(TextView week) {
        this.week = week;
    }
    public TextView getDiaryInfo() {
        return diaryInfo;
    }
    public void setDiaryInfo(TextView diaryInfo) {
        this.diaryInfo = diaryInfo;
    }
    public CheckBox getCheckBox() {
        return checkBox;
    }
    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
}