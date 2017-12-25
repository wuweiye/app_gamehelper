package cn.dkm.gamehelper.model.params;

import lombok.Data;

/**
 * Created by Administrator on 2017/12/25.
 */

@Data
public class BaseParams {

    private Long id;

    private  String status ;

    private String createTime ;

    private String createBy ;

    private String updateTime ;

    private String updateBy ;
}
