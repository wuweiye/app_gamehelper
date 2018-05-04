package cn.dkm.gamehelper.web;

import com.ab.util.AbJsonUtil;

import lombok.Data;

/**
 * Created by dkm on 2018/4/18 0018.
 */

@Data
public class ResultSecond {

    private int resultCode;

    private String resultMessage;


    public ResultSecond() {
    }

    public ResultSecond(int resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public ResultSecond(String json) {
        ResultSecond result = (ResultSecond) AbJsonUtil.fromJson(json, this.getClass());
        this.resultCode = result.getResultCode();
        this.resultMessage = result.getResultMessage();
    }
}
