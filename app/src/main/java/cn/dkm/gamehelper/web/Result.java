package cn.dkm.gamehelper.web;

import com.ab.util.AbJsonUtil;

import lombok.Data;

/**
 * Created by Administrator on 2017/12/11 0011.
 */

@Data
public class Result {
    private int errorCode;

    private String errorMessage;


    public Result() {
    }

    public Result(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public Result(String json) {
        Result result = (Result) AbJsonUtil.fromJson(json, this.getClass());
        this.errorCode = result.getErrorCode();
        this.errorMessage = result.getErrorMessage();
    }
}
