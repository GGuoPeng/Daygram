package cn.edu.jnu.diagram.model;

/**
 * Created by lenovo on 2016/9/8.
 */
public class MyDiaryInfoModel {
    private int id;
    private String date;        //日期
    private String week;        //周几
    private String diaryInfo;   //日记内容
    private boolean checked;
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getWeek() {
        return week;
    }
    public void setWeek(String week) {
        this.week = week;
    }
    public String getDiaryInfo() {
        return diaryInfo;
    }
    public void setDiaryInfo(String diaryInfo) {
        this.diaryInfo = diaryInfo;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public boolean getChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
