package cn.dkm.gamehelper.model.params;

import lombok.Data;

/**
 * Created by dkm on 2018/1/20 0020.
 */

@Data
public class Login {


    private int resultCode;

    private String resultMessage;

    private String userId;

    private String time;

    private String key;
}
