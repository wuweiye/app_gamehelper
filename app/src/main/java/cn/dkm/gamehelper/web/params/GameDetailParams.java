package cn.dkm.gamehelper.web.params;

import android.util.Log;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.ab.network.toolbox.VolleyLog.TAG;

/**
 * Created by Administrator on 2017/12/29.
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class GameDetailParams extends BaseParams{

    private String gameName;

    private String developStore;

    private int followCount;

    private String urlPath;

    private String urlPaths;

    private List<String> labels;

    private String desc;

    private int oneStarNum;

    private int twoStarNum;

    private int thereStarNum;

    private int fourStarNum;

    private int fiveStarNum;

    private int total;

    private int scale;




    public int getTotalAll(){
        int result = oneStarNum + thereStarNum + twoStarNum + fiveStarNum + fourStarNum;

        return result;
    }

    public String getAccessScore(){

        if(getTotalAll() == 0){
            Log.d(TAG, "getAccessScore: "+ 10.0);
            return  "10.0";
        }


        Double score = (double)10/getTotalAll();
        Double d = oneStarNum * score * 0.1 + twoStarNum * score * 0.4 +thereStarNum * score *0.6 + fourStarNum *score * 0.8 + fiveStarNum * score;
        BigDecimal result = new BigDecimal(d);
        result = result.setScale(1, BigDecimal.ROUND_HALF_UP);
        Log.d(TAG, "getAccessScore: "+ result.toString());
        return result.toString();
    }


}
