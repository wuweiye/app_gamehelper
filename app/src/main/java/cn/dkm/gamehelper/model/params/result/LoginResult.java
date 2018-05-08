package cn.dkm.gamehelper.model.params.result;

import com.ab.model.AbResult;

import java.util.List;

import cn.dkm.gamehelper.model.params.Login;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Created by Administrator on 2017/12/25.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class LoginResult extends AbResult {
    private List<Login> rows;
}
