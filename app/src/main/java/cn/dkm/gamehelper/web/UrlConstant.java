package cn.dkm.gamehelper.web;

/**
 * Created by Administrator on 2017/12/11.
 */

public class UrlConstant {



    public static final int LOGIN_SUCCESS = 1001;

    public static final int ASSESS_SUCCESS = 1002;

    public static final String BASE = "http://dingkunming.cn/";

    //public static final String BASE = "http://169.254.187.73:8866/";

    /*public static final String BASE = "http://192.168.1.107:8866/app/";*/



    public static final String GAMES = "games";

    public static final String ARTICLE = "article";

    public static final String DATA = "data";

    public static final String ITEMS = "items";

    public static final String GAME_LABEL = "game_label";

    public static final String LOGIN = "login";





    public static final String LOGIN_URL = BASE + "app/user/manage/login";

    public static final String GAMES_URL = BASE + "app/manage/get/games";

    public static final String GAMES_DETAIL_URL = BASE + "app/game/get/detail";

    public static final String ARTICLE_URL = BASE + "app/game/article/manage/query";

    public static final String DATA_URL = BASE + "app/game/data/manage/query";

    public static final String ITEMS_URL = BASE + "app/game/items/query";

    public static final String LABEL_URL = BASE + "app/game/label/manage/query";

    public static final String GAME_LABEL_URL = BASE + "app/game/label/manage/game/query";


    public enum UrlType{
        GAMES("games",BASE +"app/user/manage/login"),
        ARTICLE("article", BASE + "app/manage/get/games"),
        ASSESS("assess",BASE + "game/assess/query"),
        ASSESS_SUMBIT("assess_sumbit", BASE + "/game/assess/add"),
        LOGIN("login",BASE + "app/user/manage/login");

        private String type;
        private String url;

        UrlType(String type, String url) {
            this.type = type;
            this.url = url;
        }


        public String getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }

    }


}
