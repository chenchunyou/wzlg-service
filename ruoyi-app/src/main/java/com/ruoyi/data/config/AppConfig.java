package com.ruoyi.data.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 读取APP相关配置
 *
 * @author ruoyi
 */
@Component
@ConfigurationProperties(prefix = "app")
@PropertySource(value = "classpath:app.yml")
public class AppConfig {
    /** 一个学期的周数 */
    private static int weekCnt;
    /** 开学第一天 */
    private static Date firstDay;

    public static int getWeekCnt()
    {
        return weekCnt;
    }

    @Value("${weekCnt}")
    public void setWeekCnt(int weekCnt) {
        AppConfig.weekCnt = weekCnt;
    }

    public static Date getFirstDay() {
        return firstDay;
    }

    @Value("${firstDay}")
    public void setFirstDay(Date firstDay) {
        AppConfig.firstDay = firstDay;
    }
}
