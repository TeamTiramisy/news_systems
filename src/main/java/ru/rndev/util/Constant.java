package ru.rndev.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constant {

    public static final String EXCEPTION_MESSAGE = "Requested resource not found (%s = %s)";
    public static final Integer ERROR_CODE = 40401;
    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_NAME_USERNAME = "username";
    public static final String FIELD_NAME_TITLE = "title";
    public static final String FIELD_NAME_TEXT = "text";

    public static final Integer PAGE = 0;
    public static final Integer SIZE = 20;

    public static final Integer TEST_ID = 1;
    public static final String TEST_TITLE = "Title";
    public static final String TEST_TITLE_CONTAINS = "le";
    public static final String TEST_TEXT = "Text";
    public static final String TEST_TEXT_CONTAINS = "ex";
    public static final String TEST_USERNAME = "ivan@mail.ru";
    public static final String TEST_FIRSTNAME = "Ivan";
    public static final String TEST_LASTNAME= "Ivanov";

}
