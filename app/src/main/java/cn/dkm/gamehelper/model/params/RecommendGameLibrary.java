package cn.dkm.gamehelper.model.params;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Administrator on 2017/12/25.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class RecommendGameLibrary extends BaseParams{

    private  String gid;

    private String name;

    private String content;

    private String logoUrl;


    private String assess;

    private Integer followCount;

    private int assessCount;

    private int isRecommend;

    private String recommendContent;

    private String recommendImageUrl;
}
