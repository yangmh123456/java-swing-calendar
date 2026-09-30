package com.yangmh.calendar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/** 日历计算与文本渲染，使用公历规则，不依赖图形界面。 */
public final class SimpleCalendar {
    private SimpleCalendar() { }

    /**
     * 判断闰年：可被 4 整除但不可被 100 整除，或者可被 400 整除。
     * @param year 年份，范围 1 至 9999
     * @return 是否为闰年
     */
    public static boolean isLeapYear(int year) {
        validateYear(year);
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    /**
     * 获取指定月份天数，二月根据闰年规则计算。
     * @param year 年份，范围 1 至 9999
     * @param month 月份，范围 1 至 12
     * @return 当月天数
     */
    public static int getMonthDays(int year, int month) {
        validateYear(year);
        validateMonth(month);
        switch (month) {
            case 4: case 6: case 9: case 11: return 30;
            case 2: return isLeapYear(year) ? 29 : 28;
            default: return 31;
        }
    }

    /**
     * 计算当月首日星期，0 为周日，1 至 6 为周一至周六。
     * 对早期年份也使用统一公历规则，避免默认 1582 年历法切换。
     * @param year 年份，范围 1 至 9999
     * @param month 月份，范围 1 至 12
     * @return 首日星期编号
     */
    public static int getFirstDayOfWeek(int year, int month) {
        validateYear(year);
        validateMonth(month);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setGregorianChange(new Date(Long.MIN_VALUE));
        calendar.clear();
        calendar.setLenient(false);
        calendar.set(year, month - 1, 1);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 生成指定月份的文本日历，按首日星期偏移，每周六换行。
     * @param year 年份
     * @param month 月份
     * @return 日历文本
     */
    public static String getMonthCalendar(int year, int month) {
        int firstDay = getFirstDayOfWeek(year, month);
        int days = getMonthDays(year, month);
        StringBuilder text = new StringBuilder();
        text.append("============================\n");
        text.append("       ").append(year).append("年 ").append(month).append("月\n");
        text.append("日  一  二  三  四  五  六\n");
        for (int offset = 0; offset < firstDay; offset++) text.append("   ");
        for (int day = 1; day <= days; day++) {
            text.append(String.format("%2d ", day));
            if ((day + firstDay) % 7 == 0) text.append('\n');
        }
        if ((days + firstDay) % 7 != 0) text.append('\n');
        text.append("============================\n");
        return text.toString();
    }

    /**
     * 依次拼接十二个月，生成全年日历。
     * @param year 年份
     * @return 全年日历文本
     */
    public static String getYearCalendar(int year) {
        validateYear(year);
        StringBuilder text = new StringBuilder();
        for (int month = 1; month <= 12; month++) {
            text.append(getMonthCalendar(year, month)).append('\n');
        }
        return text.toString();
    }

    private static void validateYear(int year) {
        if (year < 1 || year > 9999) throw new IllegalArgumentException("年份须为 1 至 9999！");
    }

    private static void validateMonth(int month) {
        if (month < 1 || month > 12) throw new IllegalArgumentException("月份须为 1 至 12！");
    }
}
