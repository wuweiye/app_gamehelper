package cn.dkm.gamehelper.web.params;



import lombok.Data;

@Data
public class BaseParams {


    private String id;

    private  String status;

    private String createTime ;

    private String createBy;

    private String updateTime;

    private String updateBy;
}
