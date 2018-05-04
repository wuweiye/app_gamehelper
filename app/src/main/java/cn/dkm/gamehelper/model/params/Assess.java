package cn.dkm.gamehelper.model.params;

import lombok.Data;

/**
 * Created by dkm on 2018/4/18 0018.
 */

@Data
public class Assess {

    private Long id;

    private Long gid;

    private Long uid;

    private String status;

    private String createTime;

    private String createBy;

    private String updateTime;

    private String updateBy;
    
    private String gameName;

    private String userName;

    private String content;
}
