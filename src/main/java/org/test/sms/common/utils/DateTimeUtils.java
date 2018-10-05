package org.test.sms.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

public class DateTimeUtils {

    public static final long SECOND = 1000;

    public static final long MINUTE = SECOND * 60;

    public static final long HOUR = MINUTE * 60;

    private DateTimeUtils() {}

    public static String formatDuration(long duration) {
        if (duration < 0) {
            throw new IllegalArgumentException("duration cannot be negative");
        }

        String result;

        if (duration < SECOND) {
            result = duration + "ms";
        } else if (duration < MINUTE) {
            result = (duration / SECOND) + "s " + formatDuration(duration % SECOND);
        } else if (duration < HOUR) {
            result = (duration / MINUTE) + "m " + formatDuration(duration % MINUTE);
        } else {
            result = (duration / HOUR) + "h " + formatDuration(duration % HOUR);
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