package org.test.sms.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

public class DateTimeUtils {

    public static final int MILLIS_IN_SECOND = 1000;

    public static final int SECONDS_IN_MINUTE = 60;

    public static final int MINUTES_IN_HOUR = 60;

    public static final int HOURS_IN_DAY = 24;

    public static final long MILLIS_IN_MINUTE = MILLIS_IN_SECOND * SECONDS_IN_MINUTE;

    public static final long MILLIS_IN_HOUR = MILLIS_IN_MINUTE * MINUTES_IN_HOUR;

    public static final long MILLIS_IN_DAY = MILLIS_IN_HOUR * HOURS_IN_DAY;

    private DateTimeUtils() {}

    public static String formatDuration(long duration) {
        if (duration < 0) {
            throw new IllegalArgumentException("duration cannot be negative");
        }

        String result;

        if (duration < MILLIS_IN_SECOND) {
            result = duration + "ms";
        } else if (duration < MILLIS_IN_MINUTE) {
            result = (duration / MILLIS_IN_SECOND) + "s " + formatDuration(duration % MILLIS_IN_SECOND);
        } else if (duration < MILLIS_IN_HOUR) {
            result = (duration / MILLIS_IN_MINUTE) + "m " + formatDuration(duration % MILLIS_IN_MINUTE);
        } else {
            result = (duration / MILLIS_IN_HOUR) + "h " + formatDuration(duration % MILLIS_IN_HOUR);
        }

        return result;
    }

    public static LocalDateTime truncateDate(LocalDateTime date, ChronoField timeUnit) {
        if (Objects.isNull(date)) {
            throw new IllegalArgumentException("date cannot be null");
        }
        if (Objects.isNull(timeUnit)) {
            throw new IllegalArgumentException("timeUnit cannot be null");
        }

        int monthOfYear = (int) ChronoField.MONTH_OF_YEAR.range().getMinimum();
        int dayOfMonth = (int) ChronoField.DAY_OF_MONTH.range().getMinimum();

        switch (timeUnit) {
            case MONTH_OF_YEAR: {
                return date.withMonth(monthOfYear).withDayOfMonth(dayOfMonth).truncatedTo(ChronoUnit.DAYS);
            }
            case DAY_OF_MONTH: {
                return date.withDayOfMonth(dayOfMonth).truncatedTo(ChronoUnit.DAYS);
            }
            case HOUR_OF_DAY: {
                return date.truncatedTo(ChronoUnit.DAYS);
            }
            case MINUTE_OF_HOUR: {
                return date.truncatedTo(ChronoUnit.HOURS);
            }
            case SECOND_OF_MINUTE: {
                return date.truncatedTo(ChronoUnit.MINUTES);
            }
            case MILLI_OF_SECOND: {
                return date.truncatedTo(ChronoUnit.SECONDS);
            }
            default: {
                throw new IllegalArgumentException("unsupported timeUnit type");
            }
        }
    }

    public static Date converToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}