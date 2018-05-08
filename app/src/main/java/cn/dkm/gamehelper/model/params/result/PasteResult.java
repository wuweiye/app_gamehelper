package cn.dkm.gamehelper.model.params.result;

import com.ab.model.AbResult;

import java.util.List;

import cn.dkm.gamehelper.model.params.Assess;
import cn.dkm.gamehelper.model.params.Paste;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Created by Administrator on 2017/12/25.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class PasteResult extends AbResult {
    private List<Paste> rows;
}
