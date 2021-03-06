package com.example.readingisgood.util;

import java.util.HashMap;
import java.util.Map;

public class ErrorConstants {

    static Map<String, String> errorMap;

    static {
        errorMap = new HashMap<>();
        errorMap.put(ReadingUtil.SUCCESS_CODE_GENERIC_SUCCESS, "Operation done successfully");
        errorMap.put(ReadingUtil.ERROR_CODE_GENERIC_FAIL, "Operation failed");
        errorMap.put(ReadingUtil.ERROR_CODE_FAIL_DUPLICATE_ENTITY, "This entity already created");
        errorMap.put(ReadingUtil.ERROR_CODE_FAIL_NO_MATCH, "No data retreived for corresponding id");
        errorMap.put(ReadingUtil.ERROR_CODE_FAIL_NO_NEGATIVE_INTEGER_ALLOWED, "Can't enter a negative integer");
        errorMap.put(ReadingUtil.ERROR_CODE_FAIL_NO_STOCK_LEFT, "No stock left for corresponding book");

    }

    static String readErrorMessageCache(String code) {
        return errorMap.get(code);
    }
}
