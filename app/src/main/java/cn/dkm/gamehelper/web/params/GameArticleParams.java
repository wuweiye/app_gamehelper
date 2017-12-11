package cn.dkm.gamehelper.web.params;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GameArticleParams extends BaseParams {

    private String gid;

    private String gameName;

    private String title;


    private String content;

    private int isShow;




}
