package cn.dkm.gamehelper.model.params;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by dkm on 2018/4/18 0018.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class Paste extends BaseParams{


    private  String gid;

    private String forumsName;

    private String title;

    private String content;

    private String author;

    private int replyNum;

    private int followNum;
}
