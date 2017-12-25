package cn.dkm.gamehelper.web.params;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GameItemsParams extends BaseParams {


    private String name;

    private String desc;

    private String gid;

    private String gameName;

    private String urlPath;

}
