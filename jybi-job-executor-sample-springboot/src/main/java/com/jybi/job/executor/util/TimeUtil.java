package com.jybi.job.executor.util;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/6/6.
 */
public class TimeUtil {
    public static Map<String,Object> getDateList(){
        Map<String,Object> map = new HashMap<String,Object>();
        Calendar currentTime = Calendar.getInstance();
        //当前年份
        int currentYear = currentTime.get(Calendar.YEAR);
        currentTime.set(Calendar.YEAR, currentYear-1);
        //上一年年份
        int lastYear = currentTime.get(Calendar.YEAR);
        currentTime.set(Calendar.YEAR, currentYear-2);
        int twoYearsBefore = currentTime.get(Calendar.YEAR);
        //上上年年份
        List<Integer> yearList = new ArrayList<Integer>();
        yearList.add(twoYearsBefore);
        yearList.add(lastYear);
        yearList.add(currentYear);
        map.put("year", yearList);
        List<Integer> monthList = new ArrayList<Integer>();
        for(int i=1;i<=12;i++){
            monthList.add(i);
        }
        map.put("month", monthList);
        return map;
    }

    /**
     * 将日期转化为字符串
     * @param date 待转换日期
     * @param style 转换格式
     */
    public static String dateToStr(Date date,String style){
        SimpleDateFormat df = new SimpleDateFormat(style);//设置日期格式
        String dateStr = df.format(date);
        return dateStr;
    }

    /**
     * 将字符串转化为日期
     * @param date 待转换日期String
     * @param style 转换格式
     */
    public static Date strToDate(String date,String style) throws Exception{
        SimpleDateFormat df = new SimpleDateFormat(style);//设置日期格式
        Date retrun = df.parse(date);
        return retrun;
    }

    /**
     * 根据指定的日期找出该日期当前月份的第一天和最后一天
     */
    public static Map<String,String> getFirstAndLastDayOfMonth(String ymd){
        Map<String,String> map = new HashMap<String,String>();
        String firstDay = ymd.substring(0,6)+"01";
        //年
        int year = Integer.parseInt(ymd.substring(0,4));
        //月
        int month = Integer.parseInt(ymd.substring(4,6));
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month-1);
        c.set(Calendar.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH));
        int maxDay = c.get(Calendar.DAY_OF_MONTH);
        //最后一天
        String lastDay = ymd.substring(0,6) + maxDay;
        map.put("firstDay",firstDay);
        map.put("lastDay",lastDay);
        return map;
    }

    /**
     * 根据指定的日期找出该日期当前月份的第一天和最后一天
     */
    public static String getLastDayOfMonth(Integer ym){
        //年
        int year = Integer.parseInt(ym.toString().substring(0,4));
        //月
        int month = Integer.parseInt(ym.toString().substring(4,6));
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month-1);
        c.set(Calendar.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH));
        int maxDay = c.get(Calendar.DAY_OF_MONTH);
        //最后一天
        String lastDay = ym.toString() + maxDay;
        return lastDay;
    }

    /**
     * 根据指定日期找出该日期所在年份的第一天和最后一天
     */
    public static Map<String,String> getFirstAndLastDayOfYear(String ymd){
        Map<String,String> map = new HashMap<String,String>();
        //第一天
        String firstDay = ymd.substring(0,4)+"0101";
        //最后一天
        String lastDay = ymd.substring(0,4) + "1231";
        map.put("firstDay",firstDay);
        map.put("lastDay",lastDay);
        return map;
    }

}
