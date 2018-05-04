package cn.dkm.gamehelper.utils;

/**
 * Created by dkm on 2018/4/18 0018.
 */

public class StringUtils {

    /**
     * 判断value是否为空
     * @param value 传入的值
     * @return true 为空
     */
    public static boolean isEmpty(String value){

        if(value == null || value == ""){
            return true;
        }

        return false;
    }

    public static boolean isEmpty(Long value){

        if(value == null || value == 0){
            return true;
        }

        return false;
    }
}
