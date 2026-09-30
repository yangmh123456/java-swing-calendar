package com.yangmh.calendar;

import java.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;

public class SimpleCalendarTest {
    @Test public void centuryLeapYearsFollowGregorianRules() {
        assertTrue(SimpleCalendar.isLeapYear(2000));
        assertTrue(SimpleCalendar.isLeapYear(2024));
        assertFalse(SimpleCalendar.isLeapYear(1900));
        assertFalse(SimpleCalendar.isLeapYear(2100));
    }

    @Test public void monthLengthsCoverAllMonths() {
        int[] expected = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        for (int month = 1; month <= 12; month++) {
            assertEquals(expected[month - 1], SimpleCalendar.getMonthDays(2023, month));
        }
        assertEquals(29, SimpleCalendar.getMonthDays(2024, 2));
    }

    @Test public void firstWeekdayMatchesJavaTimeAcross400Years() {
        for (int year = 1600; year < 2000; year++) {
            for (int month = 1; month <= 12; month++) {
                assertEquals(LocalDate.of(year, month, 1).getDayOfWeek().getValue() % 7,
                        SimpleCalendar.getFirstDayOfWeek(year, month));
            }
        }
    }

    @Test public void earlyAndBoundaryYearsUseConsistentGregorianCalendar() {
        for (int year : new int[]{1, 100, 400, 1582, 9999}) {
            assertEquals(LocalDate.of(year, 10, 1).getDayOfWeek().getValue() % 7,
                    SimpleCalendar.getFirstDayOfWeek(year, 10));
        }
    }

    @Test public void renderedLeapFebruaryContains29AndNo30() {
        String calendar = SimpleCalendar.getMonthCalendar(2024, 2);
        assertTrue(calendar.contains("29 "));
        assertFalse(calendar.contains("30 "));
        assertTrue(calendar.contains("2024年 2月"));
    }

    @Test public void fullYearContainsAllMonthTitles() {
        String calendar = SimpleCalendar.getYearCalendar(2025);
        for (int month = 1; month <= 12; month++) assertTrue(calendar.contains("2025年 " + month + "月"));
    }

    @Test(expected = IllegalArgumentException.class) public void rejectsZeroYear() {
        SimpleCalendar.getYearCalendar(0);
    }

    @Test(expected = IllegalArgumentException.class) public void rejectsMonth13() {
        SimpleCalendar.getMonthCalendar(2025, 13);
    }

    @Test(expected = IllegalArgumentException.class) public void rejectsMonthZero() {
        SimpleCalendar.getFirstDayOfWeek(2025, 0);
    }

    @Test(expected = IllegalArgumentException.class) public void rejectsHugeYear() {
        SimpleCalendar.isLeapYear(10000);
    }
}
