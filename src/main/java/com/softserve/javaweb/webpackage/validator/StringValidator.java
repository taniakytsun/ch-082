package com.softserve.javaweb.webpackage.validator;

public class StringValidator {

    public static boolean validate(String value) {
        return !(value == null || value.isEmpty());
    }
}
