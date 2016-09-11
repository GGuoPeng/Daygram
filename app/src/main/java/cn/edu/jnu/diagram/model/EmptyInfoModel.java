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
    private String week;        //周几

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

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWeek(String pTime) {
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "Sunday";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "Monday";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "Tuesday";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "Wednesday";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "Thursday";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "Friday";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "Saturday";
        }
        return Week;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImgResourceID() {
        return imgResourceID;
    }

    public void setImgResourceID(int imgID) {
        this.imgResourceID = imgID;
    }
}
