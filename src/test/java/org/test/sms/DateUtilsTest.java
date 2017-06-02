package org.test.sms;

import org.junit.Test;
import org.test.sms.common.utils.DateUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

import static org.junit.Assert.assertEquals;
import static org.test.sms.common.exception.ExceptionAsserter.assertThrows;

public class DateUtilsTest {

    @Test
    public void testFormatDuration() {
        assertThrows(() -> DateUtils.formatDuration(-1)).expect(IllegalArgumentException.class).expectMessage("duration cannot be negative");

        assertEquals("999ms", DateUtils.formatDuration(999));

        assertEquals("1s 1ms", DateUtils.formatDuration(DateUtils.SECOND + 1));

        assertEquals("1m 1s 1ms", DateUtils.formatDuration(DateUtils.MINUTE + DateUtils.SECOND + 1));

        assertEquals("1h 1m 1s 1ms", DateUtils.formatDuration(DateUtils.HOUR + DateUtils.MINUTE + DateUtils.SECOND + 1));
    }

    @Test
    public void testTruncateDate() {
        assertThrows(() -> DateUtils.truncateDate(null, null)).expect(IllegalArgumentException.class).expectMessage("date cannot be null");

        assertThrows(() -> DateUtils.truncateDate(LocalDateTime.now(), null)).expect(IllegalArgumentException.class).expectMessage("timeUnit cannot be null");

        assertThrows(() -> DateUtils.truncateDate(LocalDateTime.now(), ChronoField.ERA)).expect(IllegalArgumentException.class).expectMessage("unsupported timeUnit type");

        LocalDateTime now = LocalDateTime.of(2016, 6, 11, 7, 29, 3, 1);

        assertEquals("2016-06-11T07:29:03", (DateUtils.truncateDate(now, ChronoField.MILLI_OF_SECOND)).toString());

        assertEquals("2016-06-11T07:29", (DateUtils.truncateDate(now, ChronoField.SECOND_OF_MINUTE)).toString());

        assertEquals("2016-06-11T07:00", (DateUtils.truncateDate(now, ChronoField.MINUTE_OF_HOUR)).toString());

        assertEquals("2016-06-11T00:00", (DateUtils.truncateDate(now, ChronoField.HOUR_OF_DAY)).toString());

        assertEquals("2016-06-01T00:00", (DateUtils.truncateDate(now, ChronoField.DAY_OF_MONTH)).toString());

        assertEquals("2016-01-01T00:00", (DateUtils.truncateDate(now, ChronoField.MONTH_OF_YEAR)).toString());
    }
}