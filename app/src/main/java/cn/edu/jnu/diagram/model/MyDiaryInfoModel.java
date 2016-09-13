package cn.edu.jnu.diagram.model;

/**
 * Created by lenovo on 2016/9/8.
 */
public class MyDiaryInfoModel {
    private int id;
    private String date;        //日期
    private String week;        //周几
    private String diaryInfo;   //日记内容
    private String year;
    private String month;
    private String day;
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
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getMonth() {
        return month;
    }
    public void setMonth(String month) {
        this.month = month;
    }
    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
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
}
