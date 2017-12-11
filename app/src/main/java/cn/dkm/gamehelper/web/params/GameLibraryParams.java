package cn.dkm.gamehelper.web.params;


import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class GameLibraryParams extends BaseParams{

    private  String gId;

    private String name;

    private String content;



}
