package cn.dkm.gamehelper.user.dao;

import android.content.Context;

import com.ab.db.orm.dao.AbDBDaoImpl;

import cn.dkm.gamehelper.helper.DBSDHelper;
import cn.dkm.gamehelper.model.LocalUser;

/**
 * Created by Administrator on 2017/12/9 0009.
 * 描述：本地数据库在sd中
 */

public class UserDao extends AbDBDaoImpl<LocalUser> {
    public UserDao(Context context) {
        super(new DBSDHelper(context),LocalUser.class);
    }
}
