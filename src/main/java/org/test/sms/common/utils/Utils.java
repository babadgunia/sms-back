package org.test.sms.common.utils;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Utils {

    private Utils() {}

//    is blank

    public static boolean isBlank(String string) {
        return Objects.isNull(string) || string.trim().isEmpty();
    }

    public static <T> boolean isBlank(T[] array) {
        return array == null || array.length == 0;
    }

    public static <T> boolean isBlank(Collection<T> collection) {
        return Objects.isNull(collection) || collection.isEmpty();
    }

    public static <K, V> boolean isBlank(Map<K, V> map) {
        return Objects.isNull(map) || map.isEmpty();
    }

//    to string

    public static String toString(Object object) {
        return (object == null) ? "" : object.toString();
    }

    public static <T> String toString(T[] array, String delimiter) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            String element = toString(array[i]);

            result.append(i == array.length - 1 ? element : element + delimiter);
        }

        return result.toString();
    }

    public static <T> String toString(Collection<T> collection, String delimiter) {
        return toString(collection.toArray(), delimiter);
    }

//	misc

    public static String expandStringPattern(String pattern, int times) {
        if (Objects.isNull(pattern)) {
            throw new IllegalArgumentException("pattern cannot be null");
        }

        if (times < 0) {
            throw new IllegalArgumentException("times cannot be negative");
        }

        StringBuilder result = new StringBuilder();

        for (int i = 1; i <= times; i++) {
            result.append(pattern);
        }

        return result.toString();
    }

    public static String getStackTrace(Throwable throwable) {
        if (Objects.isNull(throwable)) {
            throw new IllegalArgumentException("throwable cannot be null");
        }

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter, true);

        throwable.printStackTrace(printWriter);
        printWriter.flush();

        return stringWriter.toString();
    }

    public static Throwable findRelevantCause(Throwable throwable) {
        if (Objects.isNull(throwable)) {
            throw new IllegalArgumentException("throwable cannot be null");
        }

        Throwable cause = throwable.getCause();

        if (Objects.isNull(cause)) {
            return throwable;
        }

        return findRelevantCause(cause);
    }

    public static Object[] toStringArray(Object... array) {
        return Arrays.stream(array).map(Object::toString).collect(Collectors.toList()).toArray();
    }

    //    generates a random password using the passay library
    public static String generateRandomPassword() {
        List<CharacterRule> rules = Arrays.asList(
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1)
        );

        return new PasswordGenerator().generatePassword(10, rules);
    }
}