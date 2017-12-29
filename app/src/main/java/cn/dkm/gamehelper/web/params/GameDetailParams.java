package cn.dkm.gamehelper.web.params;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Administrator on 2017/12/29.
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class GameDetailParams extends BaseParams{

    private String gameName;

    private String developStore;

    private int followCount;

    private String urlPath;

    private List<String> labels;

    private String desc;

    private int oneStarNum;

    private int twoStarNum;

    private int thereStarNum;

    private int fourStarNum;

    private int fiveStarNum;

    private int total;

    private int scale;


}
