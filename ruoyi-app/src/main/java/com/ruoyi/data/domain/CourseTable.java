package com.ruoyi.data.domain;

import com.ruoyi.data.config.AppConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 课程表对象
 */
public class CourseTable {
    // 周
    private List<Week> weeks;
    public CourseTable() {
        this.weeks = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(AppConfig.getFirstDay());
        for (int weekIndex = 0; weekIndex < AppConfig.getWeekCnt(); weekIndex++) {
            Week week = new Week();
            for (int dayIndex = 0;dayIndex < 7;dayIndex++) {
                SimpleDateFormat format = new SimpleDateFormat("MM-dd");
                week.getDates().add(format.format(calendar.getTime()));
                calendar.add(Calendar.DATE, 1);
            }
            this.weeks.add(week);
        }
    }
    public List<Week> getWeeks() {
        return weeks;
    }
    public void setWeeks(List<Week> weeks) {
        this.weeks = weeks;
    }

    @Override
    public String toString() {
        return "CourseTable{" +
                "weeks=" + weeks +
                '}';
    }
}
