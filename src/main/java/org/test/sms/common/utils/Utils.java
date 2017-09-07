package org.test.sms.common.utils;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Utils {

    private Utils() {}

//	is blank

    public static boolean isBlank(String... strings) {
        if (Objects.isNull(strings) || strings.length == 0) {
            return true;
        }

        return Arrays.stream(strings).anyMatch(string -> (Objects.isNull(string) || string.trim().isEmpty()));
    }

    public static <T> boolean isBlank(Collection<T> collection) {
        return Objects.isNull(collection) || collection.isEmpty();
    }

    public static <K, V> boolean isBlank(Map<K, V> map) {
        return Objects.isNull(map) || map.isEmpty();
    }

//	to list

    public static <T> List<T> toList(String input, String delimiter, Class<T> resultClass) {
        validateToListArguments(input, delimiter, resultClass);

        List<T> result = new ArrayList<>();

        for (String token : input.split(delimiter)) {
            if (isBlank(token = token.trim())) {
                continue;
            }

            Object object;

            if (resultClass == String.class) {
                object = token;
            } else if (resultClass == Integer.class) {
                object = Integer.parseInt(token);
            } else if (resultClass == Long.class) {
                object = Long.parseLong(token);
            } else if (resultClass == Double.class) {
                object = Double.parseDouble(token);
            } else {
                throw new IllegalArgumentException("unsupported resultClass type");
            }

            result.add(resultClass.cast(object));
        }

        return result;
    }

    private static <T> void validateToListArguments(String input, String delimiter, Class<T> resultClass) {
        if (Objects.isNull(input)) {
            throw new IllegalArgumentException("input cannot be null");
        }
        if (Objects.isNull(delimiter)) {
            throw new IllegalArgumentException("delimiter cannot be null");
        }
        if (Objects.isNull(resultClass)) {
            throw new IllegalArgumentException("resultClass cannot be null");
        }
    }

    public static <T extends Enum<T>> List<T> toEnumList(String input, String delimiter, Class<T> resultClass) {
        validateToListArguments(input, delimiter, resultClass);

        List<T> result = new ArrayList<>();

        for (String token : input.split(delimiter)) {
            if (isBlank(token = token.trim())) {
                continue;
            }

            result.add(Enum.valueOf(resultClass, token));
        }

        return result;
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