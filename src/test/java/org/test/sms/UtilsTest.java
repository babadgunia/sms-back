package org.test.sms;

import org.junit.jupiter.api.Test;
import org.test.sms.common.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.test.sms.exception.ExceptionAsserter.assertThrows;

public class UtilsTest {

    @Test
    public void testIsBlankForStrings() {
        String[] strings = null;
        assertTrue(Utils.isBlank(strings));

        String string = null;
        assertTrue(Utils.isBlank(string));
        assertTrue(Utils.isBlank(""));
        assertTrue(Utils.isBlank(" "));
        assertFalse(Utils.isBlank("a"));
    }

    @Test
    public void testIsBlankForCollections() {
        Collection<String> collection = null;
        assertTrue(Utils.isBlank(collection));
        assertTrue(Utils.isBlank(new ArrayList<>()));

        assertFalse(Utils.isBlank(Arrays.asList("a")));
    }

    @Test
    public void testIsBlankForMaps() {
        Map<String, String> map = null;
        assertTrue(Utils.isBlank(map));
        assertTrue(Utils.isBlank(new HashMap<>()));

        assertFalse(Utils.isBlank(new HashMap<String, String>() {

            {
                put("a", "a");
            }
        }));
    }

    @Test
    public void testExpandStringPattern() {
        assertThrows(() -> Utils.expandStringPattern(null, 0)).expect(IllegalArgumentException.class).expectMessage("pattern cannot be null");

        assertThrows(() -> Utils.expandStringPattern("a", -1)).expect(IllegalArgumentException.class).expectMessage("times cannot be negative");

        assertEquals("", Utils.expandStringPattern("a", 0));

        assertEquals("a", Utils.expandStringPattern("a", 1));

        assertEquals("aa", Utils.expandStringPattern("a", 2));
    }

    @Test
    public void testGetStackTrace() {
        assertThrows(() -> Utils.getStackTrace(null)).expect(IllegalArgumentException.class).expectMessage("throwable cannot be null");

//        assertThat(Utils.getStackTrace(new RuntimeException("a")),
//                allOf(containsString("java.lang.RuntimeException"), containsString("a"), containsString("at org.test.sms.UtilsTest.testGetStackTrace")));
    }

    @Test
    public void testFindRelevantCause() {
        assertThrows(() -> Utils.findRelevantCause(null)).expect(IllegalArgumentException.class).expectMessage("throwable cannot be null");

        assertEquals("NoSuchElementException", Utils.findRelevantCause(new RuntimeException(new IllegalStateException(new NoSuchElementException()))).getClass().getSimpleName());
    }
}