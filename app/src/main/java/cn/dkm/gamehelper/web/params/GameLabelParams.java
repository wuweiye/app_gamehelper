package cn.dkm.gamehelper.web.params;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GameLabelParams extends BaseParams {

    private String gid;

    private String lid;

    private String labelName;

    private String gameName;


}
