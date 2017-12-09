package cn.dkm.gamehelper.helper;

import android.content.Context;

import com.ab.db.orm.AbSDDBHelper;
import com.ab.util.AbFileUtil;

import cn.dkm.gamehelper.model.LocalUser;
import cn.dkm.gamehelper.model.Stock;
import cn.dkm.gamehelper.model.User;


public class DBSDHelper extends AbSDDBHelper {
	// 数据库名
	private static final String DBNAME = "andbasedemo.db";
    
    // 当前数据库的版本
	private static final int DBVERSION = 1;
	// 要初始化的表
	private static final Class<?>[] clazz = { User.class,LocalUser.class,Stock.class};

	public DBSDHelper(Context context) {
		super(context,AbFileUtil.getDbDownloadDir(context), DBNAME, null, DBVERSION, clazz);
	}

}



