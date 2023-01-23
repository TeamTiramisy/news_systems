package ru.rndev.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constant {

    public static final String EXCEPTION_MESSAGE = "Requested resource not found (%s = %s)";
    public static final Integer ERROR_CODE_VALID = 40400;
    public static final Integer ERROR_CODE = 40401;
    public static final Integer AUTH_ERROR_CODE = 40403;
    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_NAME_USERNAME = "username";
    public static final String FIELD_NAME_TITLE = "title";
    public static final String FIELD_NAME_TEXT = "text";
    public static final Integer PAGE = 0;
    public static final Integer SIZE = 20;

    public static final Integer TEST_ID = 1;
    public static final String TEST_TITLE = "Soccer World Cup";
    public static final String TEST_TITLE_CREATE = "Title test";
    public static final String TEST_TITLE_CONTAINS = "le";
    public static final String TEST_TEXT = "Championship final";
    public static final String TEST_TEXT_CREATE = "test text";
    public static final String TEST_TEXT_CONTAINS = "ex";
    public static final String TEST_USERNAME = "alex@mail.ru";
    public static final String TEST_USERNAME_PRESENT = "ruslan@mail.ru";
    public static final String TEST_USERNAME_EMPTY = "test@mail.ru";
    public static final String TEST_FIRSTNAME = "Alex";
    public static final String TEST_LASTNAME = "Ivanov";
    public static final String TEST_PASSWORD = "123";
    public static final String URL_USER = "/v1/users";
    public static final String URL_USER_ID = "/v1/users/{id}";
    public static final String URL_NEWS = "/v1/news";
    public static final String URL_NEWS_ID = "/v1/news/{id}";
    public static final String URL_COMMENT = "/v1/comments";
    public static final String URL_COMMENT_ID = "/v1/comments/{id}";
    public static final String URL_USER_USERNAME = "/v1/users/user/{username}";
    public static final String MESSAGE = "Requested resource not found (id = 100)";
    public static final String MESSAGE_EXCEPTION = "Requested resource not found (username = test@mail.ru)";
    public static final String PATH_ROLE = "$.role";
    public static final String PATH_USERNAME = "$.username";
    public static final String PATH_TITLE = "$.title";
    public static final String PATH_TEXT = "$.text";
    public static final String PATH_USER_ID = "$.user.id";
    public static final Integer TEST_FAILED_ID = 100;
    public static final String COMMENT = "Cool";
    public static final String AUTH_EXCEPTION = "Failed to retrieve user: ";
    public static final String AUTHOR_EXCEPTION = "The user does not have access rights";

}
