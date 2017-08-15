package org.test.sms.common.enums.general;

import org.test.sms.common.log.AppLogger;
import org.test.sms.common.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public enum Translations {

    ADD,

    ADD_MODULE,

    ADDITIONAL_FINAL,

    ADDRESS,

    AUDITORIUM,

    AUDITORIUM_ALREADY_EXISTS,

    AUDITORIUMS,

    BACK,

    BIRTH_DATE,

    BUILDING,

    BUILDING_NAME,

    BUILDINGS,

    CANCEL,

    CLEAR,

    CONFIRM_DELETE,

    COURSE,

    COURSE_NAME,

    COURSES,

    DELETE,

    DESCRIPTION,

    EDIT,

    EMAIL,

    EN,

    END_DATE,

    ERROR_VIEW,

    EXAMS,

    FACULTIES,

    FACULTY,

    FACULTY_ASSIGNED_TO_STUDENT,

    FACULTY_EXISTS,

    FACULTY_HAS_COURSES,

    FEMALE,

    FIELD_CANNOT_BE_EMPTY,

    FILL_ALL_FIELDS,

    FINAL,

    FIRST_NAME,

    FOOTER,

    FROM,

    GENDER,

    HELLO,

    KA,

    KEY,

    LAST_NAME,

    LAT,

    LECTURER_EXISTS,

    LECTURERS,

    LOGIN,

    LOGOUT,

    LON,

    MAJOR_FACULTY,

    MALE,

    MAX_STUDENTS,

    MAX_GRADE,

    MIDTERM,

    MINOR_FACULTY,

    MODULES,

    NAME,

    NUM_CREDITS,

    NO_MAJOR,

    NO_MINOR,

    NUM_STUDENTS,

    OBJECT_CHANGED,

    OPERATION_SUCCESS,

    PASSWORD,

    PERSONAL_NUMBER,

    PHONE_NUMBER,

    PHOTO,

    PROFILE,

    RELOAD_CACHE,

    SAVE,

    SCHOLARSHIP,

    SEARCH,

    SEATS,

    SEMESTER,

    START_DATE,

    STUDENT_EXISTS,

    STUDENTS,

    SYSTEM_ERROR,

    TABS,

    TEXT_EXISTS,

    TEXTS,

    TO,

    TYPE,

    UNIVERSITY_EMAIL,

    USERGROUP,

    USER_GROUP_EXISTS,

    USER_GROUP_HAS_USERS,

    USER_GROUP_NAME,

    USER_GROUPS,

    USER_USERNAME,

    USERNAME,

    USERNAME_EXISTS,

    USERNAME_NOT_FOUND,

    USERS,

    VIEW,

    WRONG_PASSWORD,

    YES;

    private static AppLogger logger = AppLogger.getLogger(Translations.class);

    private static LanguageType language = LanguageType.EN;

    public static LanguageType getLanguage() {
        return language;
    }

    public static void setLanguage(LanguageType language) {
        Translations.language = language;
    }

    public String getTranslation(LanguageType language, Object... params) {
        Properties properties = new Properties();

        String filename = "/translations_" + language.toString().toLowerCase() + ".properties";
        InputStream inputStream = Translations.class.getResourceAsStream(filename);

        String key = toString();

        try (Reader reader = new InputStreamReader(inputStream, "UTF-8")) {
            properties.load(reader);
        } catch (IOException e) {
            logger.error(e);

            return key;
        }

        String result = properties.getProperty(key, key);

        return MessageFormat.format(result, Utils.toStringArray(params));
    }

    public static Map<String, Map<String, String>> getTranslations() {
        Map<String, Map<String, String>> result = new HashMap<>();

        Arrays.stream(LanguageType.values()).forEach(language -> {
            Properties properties = new Properties();

            String languageString = language.toString();
            String filename = "/translations_" + languageString.toLowerCase() + ".properties";
            InputStream inputStream = Translations.class.getResourceAsStream(filename);

            try (Reader reader = new InputStreamReader(inputStream, "UTF-8")) {
                properties.load(reader);

                Map<String, String> languageMap = new HashMap<>();
                properties.keySet().forEach(key -> {
                    String keyString = key.toString();
                    languageMap.put(keyString, properties.getProperty(keyString));
                });

                result.put(languageString, languageMap);
            } catch (IOException e) {
                logger.error(e);
            }
        });

        return result;
    }
}