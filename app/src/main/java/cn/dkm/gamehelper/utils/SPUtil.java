package cn.dkm.gamehelper.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2018/1/17.
 */

public class SPUtil {

    private static SharedPreferences config;
    public static String getString(Context context, String key, String value) {
        if(config == null){
            config = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return  config.getString(key,value);
    }

    public static void putString(Context context, String key, String value) {

        if(config == null){
            config = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        config.edit().putString(key,value).commit();
    }

    public static void putInteger(Context context, String key, Integer value) {

        if(config == null){
            config = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        config.edit().putInt(key,value).commit();
    }

    public static Integer getInteger(Context context, String key, Integer value) {

        if(config == null){
            config = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return config.getInt(key,value);
    }

    public static boolean getBoolean(Context context, String key, boolean value) {
        if(config == null){
            config = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return  config.getBoolean(key,value);
    }
    public static void putBoolean(Context context, String key, boolean value) {

        if(config == null){
            config = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        config.edit().putBoolean(key,value).commit();
    }


    public static void remove(Context context,String key){
        if(config == null){
            config = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        config.edit().remove(key).commit();
    }
}
