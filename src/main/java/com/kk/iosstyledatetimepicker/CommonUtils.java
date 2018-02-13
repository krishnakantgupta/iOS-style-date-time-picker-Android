package com.kk.iosstyledatetimepicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by krishnakantgupta for iPOLPO on date 2/13/2018.
 */

public class CommonUtils {

    /**
     * Get Date object of  given details.
     *
     * @param year
     * @param months
     * @param days    - days of month
     * @param hours   - hour of day
     * @param minutes
     * @return Date Object
     */
    public static Date getDate(int year, int months, int days, int hours, int minutes) {
        Calendar c = Calendar.getInstance();
        c.set(year, months, days, hours, minutes);
        return c.getTime();
    }

    /**
     * Get Formatter date string in given pattern
     *
     * @param date          - DAte object
     * @param formatPattern Pattern for covert long date in to this pattern string.
     * @return formatted date string in giver pattern
     */
    public static String getDateInFormat(Date date, String formatPattern) {
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(formatPattern);
            System.out.println(date);
            return formatter.format(date);
        }
        return "";
    }
}
