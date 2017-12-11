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
import cn.dkm.gamehelper.web.params.GameArticleParams;

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


	public void findQueryList(AbRequestParams params,String type, final AbHttpListener abHttpListener){

		String url = getUrl(type);
		Log.d("url",url);
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int statusCode, String content) {

				//AbResult result = new AbResult(content);

				Result result = new Result(content);

				Log.d("====","ok");
				Log.d("====",result.getErrorCode()+":"+result.getErrorMessage());


				if (result.getErrorCode()>=0) {
					//成功

					ArticleListResult mArticleListResult = (ArticleListResult) AbJsonUtil.fromJson(content,ArticleListResult.class);

					List<GameArticleParams> gameArticleParams = mArticleListResult.getRows();

					for (GameArticleParams gameArticleParams1 : gameArticleParams){
						Log.d("====",gameArticleParams1.getTitle());
					}
					//GameArticleParams gameArticleParams = (GameArticleParams) AbJsonUtil.fromJson(content,ArticleListResult.class);
					/*List<Article> articleList = mArticleListResult.getItems()*/;

					Log.d("====","ok");
					//将结果传递回去
					/*abHttpListener.onSuccess(articleList);*/
				} else {
					//将错误信息传递回去
						/*abHttpListener.onFailure(result.getResultMessage());*/
					Log.d("====","error");
				}

			}

			@Override
			public void onStart() {

			}

			@Override
			public void onFinish() {

			}

			@Override
			public void onFailure(int i, String s, Throwable throwable) {
				//失败状态返回
				abHttpListener.onFailure(throwable.getMessage());
			}
		});





	}

	/**
	 * 调用一个列表请求
	 * @param abHttpListener 请求的监听器
	 */
	public void findLogList(AbRequestParams params,final AbHttpListener abHttpListener){


		final String result = AbFileUtil.readAssetsByName(mContext, "article_list.json","UTF-8");
		// 一个url地址
	    String urlString = "http://www.baidu.com";
	    mAbHttpUtil.get(urlString,params,new AbStringHttpResponseListener(){

			@Override
			public void onSuccess(int statusCode, String content) {
				try {
					//模拟数据
					content = result;
					
					AbResult result = new AbResult(content);
					if (result.getResultCode()>0) {
						//成功

						ArticleListResult mArticleListResult = (ArticleListResult) AbJsonUtil.fromJson(content,ArticleListResult.class);
						List<Article> articleList = mArticleListResult.getItems();

						//将结果传递回去
						abHttpListener.onSuccess(articleList);
					} else {
						//将错误信息传递回去
						/*abHttpListener.onFailure(result.getResultMessage());*/
						Log.d("====","error");
					}
				} catch (Exception e) {
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
			case UrlConstant.ARTICLE :
				url = UrlConstant.ARTICLE_URL;
				break;
			case UrlConstant.DATA:
				url = UrlConstant.DATA_URL;
				break;
			case UrlConstant.ITEMS:
				url = UrlConstant.ITEMS_URL;
				break;
			case UrlConstant.GAME_LABEL:
				url = UrlConstant.GAME_LABEL_URL;
				break;
			default:
				url = UrlConstant.LABEL_URL;
				break;
		}


		return url;
	};
	
}