package cc.sachsen.common.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cc.sachsen.common.R;


import cc.sachsen.common.base.BaseApp;

public class ChatTimeUtils {
    public static final long SECOND = 1000;
    public static final long MINUTE = 60 * SECOND;
    public static final long HOUR = 60 * MINUTE;
    public static final long DAY = 24 * HOUR;
    public static final long MONTH = 30 * DAY;
    public static final long YEAR = 365 * DAY;

    /**
     * 会话列表时间格式化
     *
     * @param time 时间戳
     * @return 会话列表改为与聊天列表一样的时间
     */
    public static String conversationFormatTime(long time) {
        Date date = new Date();
        date.setTime(time);
        if (isSameYear(date)) { //同一年 显示MM-dd HH:mm
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", LanguageUtils.getSystemLocale());
            if (isSameDay(date)) { //同一天 显示HH:mm
                int minute = minutesAgo(time);
                if (minute < 60) {//1小时之内 显示n分钟前
                    if (minute <= 1) {//一分钟之内，显示刚刚
                        return Utils.getApp().getString(R.string.just);
                    } else {
                        return minute + Utils.getApp().getString(R.string.minutes_ago);
                    }
                } else {
                    return simpleDateFormat.format(date);
                }
            } else {
                if (isYesterday(date)) {//昨天，显示昨天+HH:mm
                    return Utils.getApp().getString(R.string.yesterday) + simpleDateFormat.format(date);
                } else if (isSameWeek(date)) {//本周,显示周几+HH:mm
                    String weekday = null;
                    if (date.getDay() == 1) {
                        weekday = Utils.getApp().getString(R.string.monday);
                    }
                    if (date.getDay() == 2) {
                        weekday = Utils.getApp().getString(R.string.tuesday);
                    }
                    if (date.getDay() == 3) {
                        weekday = Utils.getApp().getString(R.string.wednesday);
                    }
                    if (date.getDay() == 4) {
                        weekday = Utils.getApp().getString(R.string.thursday);
                    }
                    if (date.getDay() == 5) {
                        weekday = Utils.getApp().getString(R.string.friday);
                    }
                    if (date.getDay() == 6) {
                        weekday = Utils.getApp().getString(R.string.saturday);
                    }
                    if (date.getDay() == 0) {
                        weekday = Utils.getApp().getString(R.string.sunday);
                    }
                    return weekday + " " + simpleDateFormat.format(date);
                } else {//同一年 显示MM-dd HH:mm
                    SimpleDateFormat sdf = new SimpleDateFormat("M-d HH:mm", LanguageUtils.getSystemLocale());
                    return sdf.format(date);
                }
            }
        } else {//不是同一年 显示完整日期yyyy-MM-dd HH:mm
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm", LanguageUtils.getSystemLocale());
            return sdf.format(date);
        }
    }

    /**
     * 聊天列表时间格式化
     * 和会话列表时间的区别在于当日时间显示和年月日格式
     *
     * @param time 时间戳
     * @return
     */
    public static String chatFormatTime(long time) {
        Date date = new Date();
        date.setTime(time);
        if (isSameYear(date)) { //同一年 显示MM-dd HH:mm
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", LanguageUtils.getSystemLocale());
            if (isSameDay(date)) { //同一天 显示HH:mm
                return simpleDateFormat.format(date);
            } else {
                if (isYesterday(date)) {//昨天，显示昨天+HH:mm
                    return Utils.getApp().getString(R.string.yesterday) + simpleDateFormat.format(date);
                } else if (isSameWeek(date)) {//本周,显示周几+HH:mm
                    String weekday = null;
                    if (date.getDay() == 1) {
                        weekday = Utils.getApp().getString(R.string.monday);
                    }
                    if (date.getDay() == 2) {
                        weekday = Utils.getApp().getString(R.string.tuesday);
                    }
                    if (date.getDay() == 3) {
                        weekday = Utils.getApp().getString(R.string.wednesday);
                    }
                    if (date.getDay() == 4) {
                        weekday = Utils.getApp().getString(R.string.thursday);
                    }
                    if (date.getDay() == 5) {
                        weekday = Utils.getApp().getString(R.string.friday);
                    }
                    if (date.getDay() == 6) {
                        weekday = Utils.getApp().getString(R.string.saturday);
                    }
                    if (date.getDay() == 0) {
                        weekday = Utils.getApp().getString(R.string.sunday);
                    }
                    return weekday + " " + simpleDateFormat.format(date);
                } else {//同一年 显示MM-dd HH:mm
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm", LanguageUtils.getSystemLocale());
                    return sdf.format(date);
                }
            }
        } else {//不是同一年 显示完整日期yyyy-MM-dd HH:mm
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", LanguageUtils.getSystemLocale());
            return sdf.format(date);
        }
    }


    /**
     * 通知内日程开始和结束时间
     *
     * @param time 时间戳
     * @return
     */
    public static String scheNoticeFormatTime(long time) {
        Date date = new Date(time);

        if (LanguageUtils.systemIsChineseLauguage()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日，EEE，HH:mm", LanguageUtils.getSystemLocale());
            return sdf.format(date);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE,MMM dd,yyyy,HH:mm", LanguageUtils.getSystemLocale());
            return sdf.format(date);
        }
    }

    public static String scheNoticeFormatTimeAllDay(long time) {
        Date date = new Date(time);

        if (LanguageUtils.systemIsChineseLauguage()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日，EEE，全天", LanguageUtils.getSystemLocale());
            return sdf.format(date);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE,MMM dd,yyyy, All day", LanguageUtils.getSystemLocale());
            return sdf.format(date);
        }
    }

    private static String getWeekDay( Date date){
        String weekday = null;
        if (date.getDay() == 1) {
            weekday = Utils.getApp().getString(R.string.monday_);
        }
        if (date.getDay() == 2) {
            weekday = Utils.getApp().getString(R.string.tuesday_);
        }
        if (date.getDay() == 3) {
            weekday = Utils.getApp().getString(R.string.wednesday_);
        }
        if (date.getDay() == 4) {
            weekday = Utils.getApp().getString(R.string.thursday_);
        }
        if (date.getDay() == 5) {
            weekday = Utils.getApp().getString(R.string.friday_);
        }
        if (date.getDay() == 6) {
            weekday = Utils.getApp().getString(R.string.saturday_);
        }
        if (date.getDay() == 0) {
            weekday = Utils.getApp().getString(R.string.sunday_);
        }
        return weekday;
    }

    /**
     * 聊天列表时间格式化
     * 和会话列表时间的区别在于当日时间显示和年月日格式
     *
     * @param time 时间戳
     * @return
     */
    public static String taskFormatTime(long time) {
        Date date = new Date();
        date.setTime(time);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("MM-dd", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        if (isSameDay(date)) { //同一天 显示HH:mm
            return Utils.getApp().getString(R.string.today) + simpleDateFormat.format(date);
        } else {
            if (isTomorrow(date)) {//明天，显示明天+HH:mm
                return Utils.getApp().getString(R.string.tomorrow) + simpleDateFormat.format(date);
            } else if (isYesterday(date)) {//昨天，显示昨天+HH:mm
                return Utils.getApp().getString(R.string.yesterday) + simpleDateFormat.format(date);
            } else {//本周,显示周几+HH:mm
                String weekday = null;
                int dayWeek = date.getDay();
                switch (dayWeek) {
                    case 1:
                        weekday = Utils.getApp().getString(R.string.monday_);
                        break;
                    case 2:
                        weekday = Utils.getApp().getString(R.string.tuesday_);
                        break;
                    case 3:
                        weekday = Utils.getApp().getString(R.string.wednesday_);
                        break;
                    case 4:
                        weekday = Utils.getApp().getString(R.string.thursday_);
                        break;
                    case 5:
                        weekday = Utils.getApp().getString(R.string.friday_);
                        break;
                    case 6:
                        weekday = Utils.getApp().getString(R.string.saturday_);
                        break;
                    case 0:
                        weekday = Utils.getApp().getString(R.string.sunday_);
                        break;
                }

                String day;
                if (isSameYear(date)) {
                    day = dayFormat.format(date);
                } else {
                    day = yearFormat.format(date);
                }
                return day + "，" + weekday + "，" + simpleDateFormat.format(date);
            }
        }
    }

    public static String scheRemindFormatTime(long time) {
        long count;
        String unit;

        if (time < HOUR) {
            count = time / MINUTE;
            unit = Utils.getApp().getString(R.string.minutes);
            if (count <= 0) {
                count = 1;
            }
        } else if (time < DAY) {
            count = time / HOUR;
            unit = Utils.getApp().getString(R.string.hour);
        } else {
            count = time / DAY;
            unit = Utils.getApp().getString(R.string.day);
        }

        return count + unit;
    }

    public static String taskTimeoutFormatTime(long time) {
        String ret = BaseApp.context.getString(R.string.task_timeout_start);
        time = Math.abs(time);
        long count;
        String unit;

        if (time < HOUR) {
            count = time / MINUTE;
            unit = Utils.getApp().getString(R.string.minutes);
            if (count <= 0) {
                count = 1;
            }
        } else if (time < DAY) {
            count = time / HOUR;
            unit = Utils.getApp().getString(R.string.hour);
        } else {
            count = time / DAY;
            unit = Utils.getApp().getString(R.string.day);
        }

        return ret + count + unit;
    }

    public static String timeAgo(long time) {
        String ago = "";
        Date date = new Date();
        date.setTime(time);
        if (isSameDay(date)) {
            ago = Utils.getApp().getString(R.string.today_str);
        } else if (isSameWeek(date)) {
            ago = Utils.getApp().getString(R.string.a_day_ago);
        } else if (isSameMonth(date)) {
            ago = Utils.getApp().getString(R.string.a_week_ago);
        } else {
            ago = Utils.getApp().getString(R.string.a_month_ago);
        }
        return ago;
    }

    /**
     * 几分钟前
     *
     * @param time
     * @return
     */
    public static int minutesAgo(long time) {
        return (int) ((System.currentTimeMillis() - time) / (60000));
    }

    /**
     * 与当前时间是否在同一周
     * 先判断是否在同一年，然后根据Calendar.DAY_OF_YEAR判断所得的周数是否一致
     *
     * @param date
     * @return
     */
    public static boolean isSameWeek(Date date) {
        if (isSameYear(date)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int a = calendar.get(Calendar.DAY_OF_YEAR);

            Date now = new Date();
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(now);
            int b = calendar1.get(Calendar.DAY_OF_YEAR);
            return (a + 7) > b;
        } else {
            return false;
        }
    }

    /**
     * 是否是当前时间的昨天
     * 获取指定时间的后一天的日期，判断与当前日期是否是同一天
     *
     * @param date
     * @return
     */
    public static boolean isYesterday(Date date) {
        Date yesterday = getNextDay(date, 1);
        return isSameDay(yesterday);
    }

    /**
     * 是否是当前时间的昨天
     * 获取指定时间的后一天的日期，判断与当前日期是否是同一天
     *
     * @param date
     * @return
     */
    public static boolean isTomorrow(Date date) {
        Date yesterday = getNextDay(date, -1);
        return isSameDay(yesterday);
    }

    /**
     * 判断与当前日期是否是同一天
     *
     * @param date
     * @return
     */
    public static boolean isSameDay(Date date) {
        return isEquals(date, "yyyy-MM-dd");
    }

    /**
     * 判断与当前日期是否是同一月
     *
     * @param date
     * @return
     */
    public static boolean isSameMonth(Date date) {
        return isEquals(date, "yyyy-MM");
    }

    /**
     * 判断与当前日期是否是同一年
     *
     * @param date
     * @return
     */
    public static boolean isSameYear(Date date) {
        return isEquals(date, "yyyy");
    }


    /**
     * 格式化Date，判断是否相等
     *
     * @param date
     * @return 是返回true，不是返回false
     */
    private static boolean isEquals(Date date, String format) {
        //当前时间
        Date now = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sf = new SimpleDateFormat(format);
        //获取今天的日期
        String nowDay = sf.format(now);
        //对比的时间
        String day = sf.format(date);
        return day.equals(nowDay);
    }

    /**
     * 时间对比
     *
     * @param date1    第一个要对比的时间
     * @param date2Str 第二个要对比的时间字符串
     * @param format   第二个时间的字符串格式，供DateFormat解析
     * @return 结果有1、0、-1，分别代表前者大于、等于、小于后者；-2代表格式转化出错
     */
    public static int compare(Date date1, String date2Str, String format) {
        try {
            Date date2 = new SimpleDateFormat(format, Locale.getDefault()).parse(date2Str);
            return date1.compareTo(date2);
        } catch (ParseException e) {
            e.printStackTrace();
            return -2;
        }
    }

    /**
     * 获取某个date第n天的日期
     * n<0 表示前n天
     * n=0 表示当天
     * n>1 表示后n天
     *
     * @param date
     * @param n
     * @return
     */
    public static Date getNextDay(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, n);
        date = calendar.getTime();
        return date;
    }

    /**
     * 两个时间差（分钟）
     *
     * @param timeOld
     * @param timeNew
     * @return
     */
    public static int minutesCompare(long timeOld, long timeNew) {
        return (int) ((timeNew - timeOld) / (60000));
    }
}
