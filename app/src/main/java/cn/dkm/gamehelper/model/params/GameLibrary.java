package cn.dkm.gamehelper.model.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Administrator on 2017/12/25.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class GameLibrary extends BaseParams{

    private  String gid;

    private String name;

    private String content;
}
