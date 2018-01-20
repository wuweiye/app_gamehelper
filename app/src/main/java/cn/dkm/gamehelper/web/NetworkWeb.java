package cn.dkm.gamehelper.web;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.ab.http.AbHttpListener;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.model.AbResult;
import com.ab.util.AbFileUtil;
import com.ab.util.AbJsonUtil;

import cn.dkm.gamehelper.model.Article;
import cn.dkm.gamehelper.model.ArticleListResult;
import cn.dkm.gamehelper.model.params.BaseListResult;
import cn.dkm.gamehelper.model.params.GameLibrary;
import cn.dkm.gamehelper.web.params.GameArticleParams;

import static android.content.ContentValues.TAG;

public class NetworkWeb {

	private AbHttpUtil mAbHttpUtil = null;
	private Context mContext = null;
	
	public NetworkWeb(Context context) {
		// 创建Http工具类
		mContext = context;
		mAbHttpUtil = AbHttpUtil.getInstance(context);
	}

	/**
	 * Create a new instance of SettingWeb.
	 */
	public static NetworkWeb newInstance(Context context) {
		NetworkWeb web = new NetworkWeb(context);
		return web;
	}

	/**
	 * 调用请求的模版
	 * @param param1  参数1
	 * @param param2 参数2
	 * @param abHttpListener 请求的监听器
	 */
	public void testHttpGet(String param1,String param2,final AbHttpListener abHttpListener){
        
		// 一个url地址
		String urlString = "http://www.amsoft.cn/rss.php";
		mAbHttpUtil.get(urlString, new AbStringHttpResponseListener(){

			@Override
			public void onSuccess(int statusCode, String content) {
				//将结果传递回去
				abHttpListener.onSuccess(content);
			}

			@Override
			public void onStart() {
				
			}

			@Override
			public void onFinish() {
				
			}

			@Override
			public void onFailure(int statusCode, String content,
					Throwable error) {
				//将失败错误信息传递回去
				abHttpListener.onFailure(content);
			}
			
		});
	}

	/**
	 * 请求数据
	 * @param params
	 * @param type
	 * @param abHttpListener
	 */
	public void findQueryList(AbRequestParams params, final String type, final AbHttpListener abHttpListener){

		String url = getUrl(type);

		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int statusCode, String content) {

				Result result = new Result(content);
				if (result.getErrorCode()>=0) {

					BaseListResult baseListResult = (BaseListResult) AbJsonUtil.fromJson(content,BaseListResult.class);

					if(type.equals(UrlConstant.GAMES)){
						List<GameLibrary> list = baseListResult.getRows();
						abHttpListener.onSuccess(list);
					}




				} else {
					//将错误信息传递回去
					abHttpListener.onFailure(result.getErrorMessage());
				}

			}

			@Override
			public void onStart() {

			}

			@Override
			public void onFinish() {

			}

			@Override
			public void onFailure(int statusCode, String s, Throwable throwable) {
				//失败状态返回
				abHttpListener.onFailure(throwable.getMessage());
			}
		});




	}
	
	/**
	 * 调用一个列表请求
	 * @param abHttpListener 请求的监听器
	 */
	public void findLogList(final String type, AbRequestParams params, final AbHttpListener abHttpListener){
		
		final String result = AbFileUtil.readAssetsByName(mContext, "article_list.json","UTF-8");

		final String url = getUrl(type);
		Log.d(TAG, "url: " + url);


		mAbHttpUtil.get(url,params,new AbStringHttpResponseListener(){

			@Override
			public void onSuccess(int statusCode, String content) {
				try {

					/*if(url == null){
						//无数据模拟数据
						content = result;
					}*/

					Log.d(TAG, "onSuccess: ------enter---");
					AbResult result = new AbResult(content);
					if (result.getResultCode() == 0) {
						Log.d(TAG, "onSuccess: ------ok---");
						//成功
						if(type.equals("games")){
							BaseListResult baseListResult = (BaseListResult) AbJsonUtil.fromJson(content,BaseListResult.class);
							List<GameLibrary> gameLibraries = (List<GameLibrary>) baseListResult.getRows();
							abHttpListener.onSuccess(content);

						}else{
							Log.d(TAG, "onSuccess: ------ok2---");
							ArticleListResult mArticleListResult = (ArticleListResult) AbJsonUtil.fromJson(content,ArticleListResult.class);
							List<Article> articleList = mArticleListResult.getItems();
							//将结果传递回去
							abHttpListener.onSuccess(articleList);
						}

					} else {
						//将错误信息传递回去
						/*abHttpListener.onFailure(result.getResultMessage());*/
						Log.d("====","error");
					}
				} catch (Exception e) {
					Log.d("====","error");
					e.printStackTrace();
					abHttpListener.onFailure(e.getMessage());
				}	
			}

			@Override
			public void onStart() {
				//开始的状态传递回去
			}

			@Override
			public void onFinish() {
				//完成的状态传递回去
			}

			@Override
			public void onFailure(int statusCode, String content,
					Throwable error) {
				//将失败错误信息传递回去
				Log.d("====","onFailure" + content);
			}
	    	
	    });
		
	}

	private String getUrl(String type){

		String url;
		switch (type){
			case UrlConstant.GAMES :
				url = UrlConstant.GAMES_URL;
				break;
			default:
				url = "http://www.baidu.com";
				break;
		}


		return url;
	};

	/**
	 * 根据post请求
	 * @param params
	 * @param url
	 * @param abHttpListener
	 */
	public void urlPost(AbRequestParams params, String url, final AbHttpListener abHttpListener) {


		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int statusCode, String content) {

				Result result = new Result(content);
				if (result.getErrorCode()>=0) {
					
					abHttpListener.onSuccess(content);

				} else {
					//将错误信息传递回去
					abHttpListener.onFailure(result.getErrorMessage());
				}

			}

			@Override
			public void onStart() {

			}

			@Override
			public void onFinish() {

			}

			@Override
			public void onFailure(int statusCode, String s, Throwable throwable) {
				//失败状态返回
				abHttpListener.onFailure(throwable.getMessage());
			}
		});
	}

}
