package com.agh.reminder.reminder.custom;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static Date getMinDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        setTimeToBeginningOfDay(cal);

        return cal.getTime();
    }

    public static Date getMaxDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        setTimeToEndOfDay(cal);
        return cal.getTime();
    }

    public static Date getFirstDayOfWeek(){
        Calendar cal = GregorianCalendar.getInstance();
        setTimeToBeginningOfDay(cal);

        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

        return cal.getTime();
    }

    public static Date getLastDayOfWeek(){
        Calendar cal = GregorianCalendar.getInstance();
        setTimeToBeginningOfDay(cal);

        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        cal.add(Calendar.WEEK_OF_YEAR, 1);

        return cal.getTime();
    }

    public static Date getFirstDayOfMonth(){
        Calendar cal = GregorianCalendar.getInstance();
        setTimeToBeginningOfDay(cal);

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));

        return cal.getTime();
    }

    public static Date getLastDayOfMonth(){
        Calendar cal = GregorianCalendar.getInstance();
        setTimeToBeginningOfDay(cal);

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

        return cal.getTime();
    }

    private static void setTimeToBeginningOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static void setTimeToEndOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillisec = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillisec, TimeUnit.MILLISECONDS);
    }

    public static Date getRandomDateForDay(int day) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(2017, 4, day);
        calendar.set(Calendar.HOUR_OF_DAY, randomBetween(1, 23));
        calendar.set(Calendar.MINUTE, randomBetween(0, 59));
        calendar.set(Calendar.SECOND, randomBetween(0, 59));
        calendar.set(Calendar.MILLISECOND, randomBetween(0, 1000));

        return calendar.getTime();
    }

    public static int randomBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}
