package cn.edu.jnu.diagram.model;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;

/**
 * Created by lenovo on 2016/9/11.
 */
public class EmptyInfoModel {
    private int id;
    private int imgResourceID;
    private String year;
    private String month;
    private String day;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDate() {
        return year + "-" + month + "-" + day;
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
    public int getImgResourceID() {
        return imgResourceID;
    }
    public void setImgResourceID(int imgID) {
        this.imgResourceID = imgID;
    }
}
