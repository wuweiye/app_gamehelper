package cn.dkm.gamehelper.web.params;


import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class LabelLibraryParams extends BaseParams {


    private String gid;

    private String name;

}
