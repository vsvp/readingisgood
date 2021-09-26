package com.example.readingisgood.util;

import com.example.readingisgood.model.Result;

public interface ReadingUtil {

    String GENERIC_SUCCESS_RESULT = "SUCCESS";
    String GENERIC_FAIL_RESULT = "FAIL";
    String SUCCESS_CODE_GENERIC_SUCCESS = "100";
    String ERROR_CODE_GENERIC_FAIL = "001";
    String ERROR_CODE_FAIL_DUPLICATE_ENTITY = "002";
    String ERROR_CODE_FAIL_NO_MATCH = "003";
    String ERROR_CODE_FAIL_NO_NEGATIVE_INTEGER_ALLOWED = "004";
    String ERROR_CODE_FAIL_NO_STOCK_LEFT = "005";


    static Result buildGeneralFailResult(){
        Result result = new Result();
        result.setResult(GENERIC_FAIL_RESULT);
        result.setResultCode(ERROR_CODE_GENERIC_FAIL);
        result.setResultDesc(ErrorConstants.readErrorMessageCache(ERROR_CODE_GENERIC_FAIL));

        return result;
    }

    static Result buildGeneralSuccessResult(){
        Result result = new Result();
        result.setResult(GENERIC_SUCCESS_RESULT);
        result.setResultCode(SUCCESS_CODE_GENERIC_SUCCESS);
        result.setResultDesc(ErrorConstants.readErrorMessageCache(SUCCESS_CODE_GENERIC_SUCCESS));

        return result;
    }

    static Result buildResult(boolean isSuccess, String resultCode){
        Result result = new Result();
        result.setResult(isSuccess ? GENERIC_SUCCESS_RESULT : GENERIC_FAIL_RESULT);
        result.setResultCode(resultCode);
        result.setResultDesc(ErrorConstants.readErrorMessageCache(resultCode));

        return result;
    }

}
