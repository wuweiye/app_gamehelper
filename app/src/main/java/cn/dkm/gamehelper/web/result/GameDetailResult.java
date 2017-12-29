package cn.dkm.gamehelper.web.result;

import com.ab.model.AbResult;

import java.util.List;

import cn.dkm.gamehelper.web.params.GameDetailParams;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Created by Administrator on 2017/12/25.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class GameDetailResult extends AbResult {
    private List<GameDetailParams> rows;
}
