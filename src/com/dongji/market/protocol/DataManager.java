package com.dongji.market.protocol;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.myjson.JSONArray;
import org.myjson.JSONException;
import org.myjson.JSONObject;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;

import com.dongji.market.database.MarketDatabase;
import com.dongji.market.helper.AndroidUtils;
import com.dongji.market.helper.DJMarketUtils;
import com.dongji.market.pojo.ApkItem;
import com.dongji.market.pojo.ChannelListInfo;
import com.dongji.market.pojo.HistoryApkItem;
import com.dongji.market.pojo.InstalledAppInfo;
import com.dongji.market.pojo.NavigationInfo;
import com.dongji.market.pojo.StaticAddress;
import com.dongji.market.pojo.SubjectInfo;
import com.dongji.market.pojo.SubjectItem;

public class DataManager {
	private static DataManager dataManager;

	// private String
	// bannerUrl="http://apptest.uni.me:8002/MarketDataService/WebRoot/data/banner.json";
	// private String
	// appsUrl="http://apptest.uni.me:8002/MarketDataService/WebRoot/data/apps.json";
	// private String
	// gemasUrl="http://apptest.uni.me:8002/MarketDataService/WebRoot/data/games.json";
	// private String
	// searchUrl="http://apptest.uni.me:8002/MarketDataService/WebRoot/data/search.json";
	// private String
	// updateUrl="http://apptest.uni.me:8002/MarketDataService/WebRoot/data/update.json";
	// private String
	// searchDropUrl="http://apptest.uni.me:8002/MarketDataService/WebRoot/data/search_drop.json";
	// private String
	// cloudRecoverUrl="http://apptest.uni.me:8002/MarketDataService/WebRoot/data/cloudrecover.json";

	private static final String SINA_WEIBO_SHORT_URL_API = "http://api.t.sina.com.cn/short_url/shorten.json?source=2849184197&url_long=";

	// private static final String DOMAIN_NAME = "http://192.168.1.200/cms/"
	private static final String DOMAIN_NAME = "http://www.91dongji.com/";

	private static final String NAV_URL = "json2/nav.txt";
	private static final String APP_CHANNEL_URL = "json2/cat.txt";
	private static final String UPDATE_URL = "index.php?g=Api&m=Soft3&a=softUpdate"; // http://192.168.0.101/wuxiuwu/index.php?g=Api&m=Soft2&a=softUpdate
	private static final String SEARCH_RESULT_URL = "index.php?g=Api&m=Soft3&a=softSearch&param="; // http://192.168.0.101/wuxiuwu/index.php?g=Api&m=Soft2&a=softSearch&param=
	private static final String STATISTICS_INSTALL = "index.php?g=Api&m=AppCount&a=writeIn&catid=";
	// private static final String
	// BANNER_URL="index.php?g=Api&m=Soft2&a=bannerShow";
	// //http://192.168.0.101/wuxiuwu/index.php?g=Api&m=Soft2&a=bannerShow
	private static final String BANNER_URL = "index.php?g=Api&m=MobileApi2&a=bannerShow";
	private static final String CLOUND_BACKUP_URL = "index.php?g=Api&m=Soft2&a=userBackup"; // http://192.168.0.101/wuxiuwu/index.php?g=Api&m=Soft2&a=userBackup
	private static final String CLOUND_RECOVER_URL = "index.php?g=Api&m=Soft2&a=userRestoration"; // http://192.168.0.101/wuxiuwu/index.php?g=Api&m=Soft2&a=userRestoration
	private static final String HOTWORD_URL = "index.php?g=Api&m=MobileApi2&a=hotword"; // index.php?g=Api&m=Soft2&a=displayhotword
	private static final String LOGIN_URL = "index.php?g=api&m=userApi2&a=login"; // 登录url
	private static final String REGISTER_URL = "index.php?g=api&m=userApi2&a=register"; // 注册url
	private static final String CHANGE_PWD_URL = "index.php?g=api&m=userApi2&a=changepwd"; // 修改密码url
	private static final String FIND_PWD_URL = "index.php?g=api&m=userApi2&a=findpwd"; // 找回密码url
	private static final String FEEDBACK_URL = "index.php?g=api&m=feedBack&a=Fbwrite"; // 反馈url
	private static final String GRADE_URL = "index.php?g=api&m=soft2&a=SoftScore"; // 评分url
	private static final String TOP_50_URL = "json2/top50/top50.txt"; // Top50
	private static final String MAIN_DATA_URL = "index.php?g=Api&m=MobileApi2&a=index&catid="; // 编辑推荐、最近更新、装机必备、软件分类url
	private static final String DJ_ADRESS_URL = "index.php?g=Api&m=MobileApi2&a=Marketapp";// 动机市场详情地址
	private static final String WX_DATA_URL = "index.php?g=Api&m=MobileApi2&a=Wechatinfo";// 微信详情信息
	private static final String SOFT_SORT_URL = "index.php?g=Api&m=MobileApi2&a=Sortdata&type="; // 软件分类列表url
	private static final String SOFT_DETAIL_URL = "index.php?g=Api&m=MobileApi2&a=Appdata&id="; // 软件详情url
	private static final String SHAKE_GUESSLIKE_URL = "index.php?g=Api&m=MobileApi2&a=Rocklike"; // 摇一摇、猜你喜欢url
	private static final String COLLECT_LOCAL_INFO_URL = "index.php?g=Api&m=PI&a=Recive"; // 本地信息收集url
	private static final String ALLSUBJECT_URL = "index.php?g=Api&m=MobileApi2&a=Gettopic"; // 所有的专题url
	private static final String SUBJECTITEM_URL = "index.php?g=Api&m=MobileApi2&a=Topicinfo&id="; // 专题详情url
	private static final String SUBJECTCOUNT_URL = "index.php?g=Api&m=MobileApi2&a=Writevists&id="; // 专题详情url
	private static final String ONLINE_DOMAIN_NAME = "http://dl.91dongji.com/file/"; // http://www.91dongji.com/app/d/file/
	// private static final String
	// ONLINE_DOMAIN_NAME="http://192.168.1.200/cms/d/file/";
	// //http://www.91dongji.com/app/d/file/ 测试更新地址

	private static final String ONLINE_STATIC_DOMAIN_NAME = "http://www.91dongji.com/"; // http://tools.mille.us:8888/

	private static final String NAVIGATION_NAME = "navigation";
	private static final String CHANNEL_NAME = "channel";
	private static final String BANNER_NAME = "banner";

	private static final String CHANNEL_PREFIX = "channel_";

	private static final int MAX_SEARCH_RESULT_COUNT = 50;

	private static final String NULL_STRING = "null";

	private static final int SORT_BY_TIME = 3; // 按时间排序
	private static final int SORT_BY_SCORE = 2; // 按评分排序
	private static final int SORT_BY_DOWNLOAD = 1; // 按下载次数排序

	public static final int EDITOR_RECOMMEND_ID = 1; // 编辑推荐
	public static final int RECENT_UPDATA_ID = 2; // 最近更新
	public static final int ESSENTIAL_ID = 3; // 装机必备
	public static final int SOFT_CHANNEL_ID = 4; // 软件分类

	private DataManager() {
		super();
	}

	public static DataManager newInstance() {
		if (dataManager == null) {
			dataManager = new DataManager();
		}
		return dataManager;
	}

	/**
	 * 获取导航数据
	 * 
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public ArrayList<NavigationInfo> getNavigationList() throws JSONException {
		ArrayList<NavigationInfo> list = null;
		HttpClientApi httpClientApi = HttpClientApi.getInstance();
		String result = null;
		boolean isLocal = false;
		try {
			result = httpClientApi.getContentFromUrl(DOMAIN_NAME + NAV_URL);// DOMAIN_NAME
		} catch (IOException e) {
			System.out.println("getnavigation " + e);
			result = FsCache.getCacheString(NAVIGATION_NAME);
			isLocal = true;
		}
		if (!TextUtils.isEmpty(result)) {
			JSONArray jsonArray = new JSONArray(result);
			int length = jsonArray.length();
			if (jsonArray != null && length > 0) {
				list = new ArrayList<NavigationInfo>();
				for (int i = 0; i < length; i++) {
					NavigationInfo info = new NavigationInfo();
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					info.id = jsonObject.getInt("catid");
					info.name = jsonObject.getString("catname");
					JSONObject childJsonObject = jsonObject.getJSONObject("datalist");
					info.staticAddress = new StaticAddress[2];
					StaticAddress gameStaticAddress = new StaticAddress();
					gameStaticAddress.url = childJsonObject.getString("game_url");
					gameStaticAddress.md5Value = childJsonObject.getString("game_md5");
					StaticAddress appStaticAddress = new StaticAddress();
					appStaticAddress.url = childJsonObject.getString("soft_url");
					appStaticAddress.md5Value = childJsonObject.getString("soft_md5");

					info.staticAddress[0] = appStaticAddress;
					info.staticAddress[1] = gameStaticAddress;
					list.add(info);
				}
			}
			if (!isLocal) {
				FsCache.cacheFileByMd5(result, NAVIGATION_NAME);
			}
		}
		return list;
	}

	/**
	 * 获取导航数据
	 * 
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public ArrayList<NavigationInfo> getNavigationList(int catid) throws JSONException {
		ArrayList<NavigationInfo> list = null;
		HttpClientApi httpClientApi = HttpClientApi.getInstance();
		String result = null;
		boolean isLocal = false;
		try {
			result = httpClientApi.getContentFromUrl(DOMAIN_NAME + NAV_URL);// DOMAIN_NAME
		} catch (IOException e) {
			System.out.println("getnavigation " + e);
			result = FsCache.getCacheString(NAVIGATION_NAME);
			isLocal = true;
		}
		if (!TextUtils.isEmpty(result)) {
			JSONArray jsonArray = new JSONArray(result);
			int length = jsonArray.length();
			if (jsonArray != null && length > 0) {
				list = new ArrayList<NavigationInfo>();
				for (int i = 0; i < length; i++) {
					NavigationInfo info = new NavigationInfo();
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					info.id = jsonObject.getInt("catid");
					info.name = jsonObject.getString("catname");
					JSONObject childJsonObject = jsonObject.getJSONObject("datalist");
					info.staticAddress = new StaticAddress[2];
					StaticAddress gameStaticAddress = new StaticAddress();
					gameStaticAddress.url = childJsonObject.getString("game_url");
					gameStaticAddress.md5Value = childJsonObject.getString("game_md5");
					StaticAddress appStaticAddress = new StaticAddress();
					appStaticAddress.url = childJsonObject.getString("soft_url");
					appStaticAddress.md5Value = childJsonObject.getString("soft_md5");

					info.staticAddress[0] = appStaticAddress;
					info.staticAddress[1] = gameStaticAddress;
					list.add(info);
				}
			}
			if (!isLocal) {
				FsCache.cacheFileByMd5(result, NAVIGATION_NAME);
			}
		}
		return list;
	}

	/**
	 * 获取列表分类数据
	 * 
	 * @return
	 * @throws JSONException
	 */
	public ArrayList<ChannelListInfo> getChannelListData(Context context) throws JSONException {
		ArrayList<ChannelListInfo> list = null;
		HttpClientApi httpClientApi = HttpClientApi.getInstance();
		String result = null;
		boolean isLocal = false;
		try {
			// result = httpClientApi.getContentFromUrl(DOMAIN_NAME
			// + APP_CHANNEL_URL);
			result = httpClientApi.getContentFromUrl(DOMAIN_NAME + MAIN_DATA_URL + SOFT_CHANNEL_ID + getLanguageType());
			System.out.println("channel data -------> " + result);
		} catch (IOException e) {
			isLocal = true;
			result = FsCache.getCacheString(CHANNEL_NAME);
		}
		if (!TextUtils.isEmpty(result)) {
			JSONArray jsonArray = new JSONArray(result);
			if (jsonArray != null && jsonArray.length() > 0) {
				list = new ArrayList<ChannelListInfo>();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					ChannelListInfo info = new ChannelListInfo();
					info.id = jsonObject.getInt("catid");
					info.name = jsonObject.getString("catname");
					info.parentId = jsonObject.getInt("parentid");
					String iconUrl = jsonObject.getString("img");
					// String iconUrl=jsonObject.getString("image");
					if (!TextUtils.isEmpty(iconUrl)) {
						info.iconUrl = DOMAIN_NAME + iconUrl; // ONLINE_DOMAIN_NAME
					}
					info.pageCount = jsonObject.getInt("total");
					list.add(info);
				}
				if (!isLocal) {
					FsCache.cacheFileByMd5(result, CHANNEL_NAME);
				}
			}
		}
		return list;
	}

	public ArrayList<ApkItem> getApps(Context context, int catid, boolean isApp) throws JSONException {
		ArrayList<ApkItem> list = null;
		String result = null;
		String cacheSuffix = catid + (isApp ? "soft" : "game");
		if (!AndroidUtils.isNetworkAvailable(context)) {
			result = FsCache.getCacheString(cacheSuffix);
		} else {
			String type = "&type=" + (isApp ? "soft" : "game");
			String suffixUrl = null;
			switch (catid) {
			case EDITOR_RECOMMEND_ID: // 编辑推荐
				suffixUrl = MAIN_DATA_URL + EDITOR_RECOMMEND_ID + type + getLanguageType();
				break;
			case RECENT_UPDATA_ID: // 最近更新
				suffixUrl = MAIN_DATA_URL + RECENT_UPDATA_ID + type + getLanguageType();
				break;
			case ESSENTIAL_ID: // 装机必备
				suffixUrl = MAIN_DATA_URL + ESSENTIAL_ID + type + getLanguageType();
				break;
			default:
				break;
			}

			System.out.println("=========suffixUrl=========" + DOMAIN_NAME + suffixUrl);
			HttpClientApi httpClientApi = HttpClientApi.getInstance();
			try {
				result = httpClientApi.getContentFromUrl(DOMAIN_NAME + suffixUrl);
			} catch (IOException e) {
				result = FsCache.getCacheString(cacheSuffix);
				System.out.println("getApps:" + e);
			}
			if (!TextUtils.isEmpty(result)) {
				FsCache.deleteCacheFileByMd5Value(cacheSuffix);
				FsCache.cacheFileByMd5(result, cacheSuffix);
			}
		}
		if (!TextUtils.isEmpty(result)) {
			JSONArray jsonArray = new JSONArray(result);
			if (jsonArray != null && jsonArray.length() > 0) {
				list = new ArrayList<ApkItem>();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					ApkItem item = new ApkItem();
					item.appId = jsonObject.getInt("id");
					String category = jsonObject.getString("catcid");
					if (!TextUtils.isEmpty(category)) {
						item.category = Integer.parseInt(category);
					}
					// item.classx=jsonObject.getInt("catpid");
					String language = jsonObject.getString("language");
					if (TextUtils.isEmpty(language)) {
						item.language = 1;
					} else {
						item.language = Integer.parseInt(language);
					}
					item.company = jsonObject.getString("developer");
					String apkUrl = jsonObject.getString("down_url");
					if (!TextUtils.isEmpty(apkUrl)) {
						item.apkUrl = ONLINE_STATIC_DOMAIN_NAME + apkUrl;
						// item.apkUrl = DOMAIN_NAME + apkUrl;
					}
					item.downloadNum = jsonObject.getLong("down_count");
					String iconUrl = jsonObject.getString("apk_icon");

					if (!TextUtils.isEmpty(iconUrl)) {
						item.appIconUrl = ONLINE_DOMAIN_NAME + iconUrl;
					}
					item.appName = jsonObject.getString("apk_name");
					item.fileSize = jsonObject.getLong("apk_size");
					item.versionCode = jsonObject.getInt("apk_versioncode");
					item.version = jsonObject.getString("apk_versionname");
					item.packageName = jsonObject.getString("apk_packagename");
					if (jsonObject.has("heavy")) {
						item.heavy = jsonObject.getInt("heavy");
					}
					list.add(item);
				}
			}

		}
		return list;
	}

	// private StringBuilder sb=new StringBuilder();
	/**
	 * 
	 * @param channelInfo
	 * @param context
	 * @param page
	 * @param isApp
	 * @return
	 * @throws JSONException
	 */
	public ArrayList<ApkItem> getApps(ChannelListInfo channelInfo, Context context, int sortType) throws JSONException {
		ArrayList<ApkItem> list = null;
		// if(sb.length()>0) {
		// sb.delete(0, sb.length());
		// }
		// sb.append(DOMAIN_NAME).append("json2/cat/").append(channelInfo.id+"/").append(String.valueOf(channelInfo.currentPage)).append("_").append(sortType).append(".txt");
		String sb = DOMAIN_NAME + SOFT_SORT_URL + sortType + "&catid=" + channelInfo.id + "&page=" + channelInfo.currentPage + getLanguageType();
		System.out.println("sort url ===========> " + sb);
		String result = null;
		if (TextUtils.isEmpty(result)) {
			HttpClientApi httpClientApi = HttpClientApi.getInstance();
			try {
				result = httpClientApi.getContentFromUrl(sb.toString());
				// System.out.println("sort data ========> " + result);
			} catch (IOException e) {
				System.out.println("getApps error:" + e + ", " + result);
			}
		}
		if (!TextUtils.isEmpty(result)) {
			JSONArray jsonArray = new JSONArray(result);
			if (jsonArray != null && jsonArray.length() > 0) {
				list = new ArrayList<ApkItem>();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					ApkItem item = new ApkItem();
					item.appId = jsonObject.getInt("id");
					item.category = jsonObject.getInt("catcid");
					// item.classx=jsonObject.getInt("catpid");
					String language = jsonObject.getString("language");
					if (TextUtils.isEmpty(language)) {
						item.language = 1;
					} else {
						item.language = Integer.parseInt(language);
					}
					item.company = jsonObject.getString("developer");
					String apkUrl = jsonObject.getString("down_url");
					if (!TextUtils.isEmpty(apkUrl)) {
						// item.apkUrl = ONLINE_DOMAIN_NAME + apkUrl;
						item.apkUrl = ONLINE_STATIC_DOMAIN_NAME + apkUrl;
					}
					String downloadNum = jsonObject.getString("down_count");
					if (!TextUtils.isEmpty(downloadNum)) {
						item.downloadNum = Long.valueOf(downloadNum);
					}
					String iconUrl = jsonObject.getString("apk_icon");

					if (!TextUtils.isEmpty(iconUrl)) {
						item.appIconUrl = ONLINE_DOMAIN_NAME + iconUrl;
					}
					item.appName = jsonObject.getString("apk_name");
					String fileSize = jsonObject.getString("apk_size");
					if (!TextUtils.isEmpty(fileSize)) {
						item.fileSize = Long.valueOf(fileSize);
					}
					String versionCode = jsonObject.getString("apk_versioncode");
					if (!TextUtils.isEmpty(versionCode)) {
						item.versionCode = Integer.valueOf(versionCode);
					}
					item.version = jsonObject.getString("apk_versionname");
					item.packageName = jsonObject.getString("apk_packagename");
					if (jsonObject.has("heavy")) {
						item.heavy = jsonObject.getInt("heavy");
					}
					list.add(item);
				}
			}
		}
		return list;
	}

	/**
	 * 清除失效的垃圾缓存数据
	 */
	public void clearRubbishCacheData(Context context, List<ChannelListInfo> list) {
		/*
		 * MarketDatabase db=new MarketDatabase(context); List<ChannelListInfo>
		 * saveList=db.getAllChannel(); if(saveList==null) {
		 * FsCache.deleteChannelCacheFile(); // 删除所有分类缓存文件 }else { for(int
		 * i=0;i<list.size();i++) { ChannelListInfo info=list.get(i); for(int
		 * j=0;j<saveList.size();j++) { ChannelListInfo
		 * saveInfo=saveList.get(j); if (info.equals(saveInfo)) {
		 * if(info.staticAddress!=null) { if(saveInfo!=null &&
		 * saveInfo.staticAddress!=null) { int
		 * length1=info.staticAddress.length; int
		 * length2=saveInfo.staticAddress.length; for(int
		 * k=length1;k<length2;k++) { db.deleteChannelRefByIndex(saveInfo.id,
		 * k); FsCache .deleteCacheFileByMd5Value(CHANNEL_PREFIX +
		 * ((StaticAddress)saveInfo.staticAddress[k]).md5Value); } } }else {
		 * if(saveInfo!=null && saveInfo.staticAddress!=null) {
		 * db.deleteChannelRefByChannelId(info.id); for(int
		 * k=0;k<saveInfo.staticAddress.length;k++) { FsCache
		 * .deleteCacheFileByMd5Value(CHANNEL_PREFIX +
		 * ((StaticAddress)saveInfo.staticAddress[k]).md5Value); } } } } } } }
		 */
	}

	/**
	 * 通过分类id,以及应用id获取应用详情信息
	 * 
	 * @param category
	 * @param appId
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public ApkItem getApkItemDetailByAppId(int category, int appId) throws IOException, JSONException {
		HttpClientApi httpClientApi = HttpClientApi.getInstance();
		String url = DOMAIN_NAME + SOFT_DETAIL_URL + appId + "&catid=" + category + getLanguageType();
		System.out.println("detail url -------------> " + url);
		String result = httpClientApi.getContentFromUrl(url);
		System.out.println("detail data=====> " + result);
		if (!TextUtils.isEmpty(result)) {
			ApkItem item = new ApkItem();
			JSONObject jsonObject = new JSONObject(result);
			item.appId = appId;
			item.category = category;
			item.updateDate = jsonObject.getString("time");
			item.discription = jsonObject.getString("contents");
			String language = jsonObject.getString("language");
			if (TextUtils.isEmpty(language)) {
				item.language = 1;
			} else {
				item.language = Integer.parseInt(language);
			}
			item.company = jsonObject.getString("developer");
			item.score = jsonObject.getDouble("score");
			JSONArray screenShotJsonArray = jsonObject.getJSONArray("screenshot");
			if (screenShotJsonArray != null && screenShotJsonArray.length() > 0) {
				for (int j = 0; j < screenShotJsonArray.length(); j++) {
					String screenShot = screenShotJsonArray.getString(j);
					if (!TextUtils.isEmpty(screenShot)) {
						item.appScreenshotUrl.add(DOMAIN_NAME + screenShot);
					}
				}
			}
			JSONArray historyJsonArray = jsonObject.getJSONArray("history");
			if (historyJsonArray != null && historyJsonArray.length() > 0) {
				item.historys = new HistoryApkItem[historyJsonArray.length()];
				for (int j = 0; j < historyJsonArray.length(); j++) {
					HistoryApkItem historyItem = new HistoryApkItem();
					JSONObject historyJsonObject = historyJsonArray.getJSONObject(j);
					historyItem.appId = historyJsonObject.getInt("id");
					historyItem.category = item.category;
					historyItem.appName = historyJsonObject.getString("apk_name");
					String historyIcon = historyJsonObject.getString("apk_icon");
					if (!TextUtils.isEmpty(historyIcon)) {
						historyItem.appIconUrl = ONLINE_DOMAIN_NAME + historyIcon;
					}
					historyItem.updateDate = historyJsonObject.getString("time");
					historyItem.versionName = historyJsonObject.getString("apk_versionname");
					historyItem.downloadNum = historyJsonObject.getLong("down_count");
					historyItem.appSize = historyJsonObject.getLong("apk_size");
					String historyUrl = historyJsonObject.getString("down_url");
					if (!TextUtils.isEmpty(historyUrl)) {
						historyItem.url = ONLINE_STATIC_DOMAIN_NAME + historyUrl;
					}
					historyItem.versionCode = historyJsonObject.getInt("apk_versioncode");
					if (historyJsonObject.has("heavy")) {
						historyItem.heavy = historyJsonObject.getInt("heavy");
					}

					item.historys[j] = historyItem;
				}
			}

			String apkUrl = jsonObject.getString("down_url");
			if (!TextUtils.isEmpty(apkUrl)) {
				item.apkUrl = ONLINE_STATIC_DOMAIN_NAME + apkUrl;
			}

			item.downloadNum = jsonObject.getLong("down_count");

			String iconUrl = jsonObject.getString("apk_icon");

			if (!TextUtils.isEmpty(iconUrl)) {
				item.appIconUrl = ONLINE_DOMAIN_NAME + iconUrl;
			}

			item.fileSize = jsonObject.getLong("apk_size");
			item.appName = jsonObject.getString("apk_name");
			JSONArray permisionJsonArray = jsonObject.getJSONArray("apk_permision");
			if (permisionJsonArray != null && permisionJsonArray.length() > 0) {
				for (int j = 0; j < permisionJsonArray.length(); j++) {
					item.permisions.add(permisionJsonArray.getString(j));
				}
			}
			item.minSdkVersion = jsonObject.getInt("apk_sdkversion");
			item.versionCode = jsonObject.getInt("apk_versioncode");
			item.version = jsonObject.getString("apk_versionname");
			item.packageName = jsonObject.getString("apk_packagename");
			if (jsonObject.has("heavy")) {
				item.heavy = jsonObject.getInt("heavy");
			}

			JSONArray likeJsonArray = jsonObject.getJSONArray("like");
			if (likeJsonArray != null && likeJsonArray.length() > 0) {
				for (int i = 0; i < likeJsonArray.length(); i++) {
					JSONObject likeJSONObject = likeJsonArray.getJSONObject(i);
					ApkItem likeItem = new ApkItem();
					likeItem.appId = likeJSONObject.getInt("id");
					likeItem.category = likeJSONObject.getInt("catcid");
					likeItem.appName = likeJSONObject.getString("apk_name");
					String likeIconUrl = likeJSONObject.getString("apk_icon");
					if (!TextUtils.isEmpty(likeIconUrl)) {
						likeItem.appIconUrl = ONLINE_DOMAIN_NAME + likeIconUrl;
					}
					item.likeList.add(likeItem);
				}
			}

			return item;
		}
		return null;
	}

	/**
	 * 获取推荐 banner
	 * 
	 * @return
	 */
	public List<ApkItem> getBanners() throws JSONException {
		HttpClientApi httpClientApi = HttpClientApi.getInstance();
		List<ApkItem> list = null;
		String result = null;
		try {
			result = httpClientApi.getContentFromUrl(DOMAIN_NAME + BANNER_URL);
			System.out.println("banner data -------> " + result);
		} catch (IOException e) {
			result = FsCache.getCacheString(BANNER_NAME);
		}
		if (!TextUtils.isEmpty(result)) {
			JSONArray jsonArray = new JSONArray(result);
			list = new ArrayList<ApkItem>();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				ApkItem item = new ApkItem();
				item.appId = obj.getInt("id");
				item.appName = obj.getString("title");
				String bannerUrl = obj.getString("recommend_banner");
				if (!TextUtils.isEmpty(bannerUrl)) {
					item.bannerUrl = ONLINE_DOMAIN_NAME + bannerUrl;
				}
				item.category = obj.getInt("catid");
				list.add(item);
			}
			FsCache.cacheFileByMd5(result, BANNER_NAME);
		}
		return list;
	}

	/**
	 * 返回搜索关键字
	 * 
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public List<String> getKeywords() throws IOException, JSONException {
		HttpClientApi httpClientApi = HttpClientApi.getInstance();
		List<String> list = null;
		String result = httpClientApi.getContentFromUrl(DOMAIN_NAME + HOTWORD_URL + getLanguageType());
		System.out.println("0717....keyword...." + DOMAIN_NAME + HOTWORD_URL + getLanguageType());
		System.out.println("hotword ========> " + result);
		JSONArray jsonArray = new JSONArray(result);
		list = new ArrayList<String>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			list.add(obj.getString("hotword"));
		}
		return list;
	}

	/**
	 * 根据关键字和页码，返回搜索结果
	 * 
	 * @param keyword
	 * @param currentPage
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public List<ApkItem> getSearchResult(String keyword) throws IOException, JSONException {
		HttpClientApi httpClientApi = HttpClientApi.getInstance();
		List<ApkItem> list = null;
		keyword = URLEncoder.encode(keyword, "UTF-8");
		System.out.println("keyword:" + keyword);
		String searchUrl = DOMAIN_NAME + SEARCH_RESULT_URL + keyword + "&numList=" + MAX_SEARCH_RESULT_COUNT + getLanguageType();
		System.out.println("search url ====> " + searchUrl);
		String result = httpClientApi.getContentFromUrl(searchUrl);
		System.out.println("search result =====> " + result);
		if (!TextUtils.isEmpty(result) && !NULL_STRING.equalsIgnoreCase(result)) {
			JSONArray jsonArray = new JSONArray(result);
			if (jsonArray != null && jsonArray.length() > 0) {
				list = new ArrayList<ApkItem>();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					ApkItem item = new ApkItem();
					item.appId = obj.getInt("id");
					item.appName = obj.getString("apk_name");
					item.category = obj.getInt("catid");
					String downloadUrl = obj.getString("down_url");
					if (!TextUtils.isEmpty(downloadUrl)) {
						item.apkUrl = ONLINE_STATIC_DOMAIN_NAME + downloadUrl;
					}
					item.packageName = obj.getString("apk_packagename");
					// item.firmwareVersion=obj.getString("firmware_version");
					item.version = obj.getString("apk_versionname");
					// item.permission=obj.getString("permision");
					// item.classx=obj.getInt("classz");
					item.language = obj.getInt("language");
					item.company = obj.getString("developer");

					String iconUrl = obj.getString("apk_icon");
					if (!TextUtils.isEmpty(iconUrl)) {
						item.appIconUrl = ONLINE_DOMAIN_NAME + iconUrl;
					}
					// item.appScreenshotUrl=obj.getString("app_screenshot");
					item.discription = obj.getString("contents");
					item.updateDate = obj.getString("updatetime");
					item.fileSize = obj.getLong("apk_size");
					item.versionCode = obj.getInt("apk_versioncode");
					if (obj.has("heavy")) {
						item.heavy = obj.getInt("heavy");
					}
					list.add(item);
				}
			}
		}
		return list;
	}

	private ArrayList<ApkItem> parseJson(String result) throws JSONException {
		JSONArray jsonArray = new JSONArray(result);
		ArrayList<ApkItem> list = new ArrayList<ApkItem>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			ApkItem item = new ApkItem();
			item.appId = obj.getInt("id");
			item.category = obj.getInt("catid");
			item.appName = obj.getString("title");
			String downloadUrl = obj.getString("down_url");
			if (!TextUtils.isEmpty(downloadUrl)) {
				item.apkUrl = ONLINE_STATIC_DOMAIN_NAME + downloadUrl;
			}
			item.packageName = obj.getString("apk_packagename");
			item.firmwareVersion = obj.getString("apk_sdkversion");
			item.version = obj.getString("apk_versionname");
			String iconUrl = obj.getString("apk_icon");
			if (!TextUtils.isEmpty(iconUrl)) {
				item.appIconUrl = ONLINE_DOMAIN_NAME + iconUrl;
			}
			item.fileSize = obj.getLong("apk_size");
			item.versionCode = obj.getInt("apk_versioncode");
			if (obj.has("heavy")) {
				item.heavy = obj.getInt("heavy");
			}
			list.add(item);
		}
		return list;
	}

	private List<String[]> initRequestUpdateList(Context context) {
		List<PackageInfo> infos = AndroidUtils.getInstalledPackages(context);
		List<String[]> items = null;
		if (infos != null && infos.size() > 0) {
			items = new ArrayList<String[]>();
			for (int i = 0; i < infos.size(); i++) {
				PackageInfo info = infos.get(i);
				String[] arr = new String[2];
				arr[1] = info.packageName;
				arr[0] = String.valueOf(info.versionCode);
				items.add(arr);
			}
		}
		System.out.println("=============initRequestUpdateList:" + items.size());
		return items;
	}

	String requestSoftUpdateList(Context context) throws IOException {
		List<String[]> list = initRequestUpdateList(context);
		HttpClientApi httpClientApi = HttpClientApi.getInstance();
		String result = httpClientApi.getContentFromUrlByPost(DOMAIN_NAME + UPDATE_URL + getLanguageType(), list);
		if (!TextUtils.isEmpty(result)) {
			FsCache.cacheSofeUpdateData(result);
		}
		return result;
	}

	public ArrayList<ApkItem> getUpdateList(Context context) throws IOException, JSONException {
		String result = requestSoftUpdateList(context);
		if (!TextUtils.isEmpty(result)) {
			System.out.println("update:" + result);
			ArrayList<ApkItem> list = parseJson(result);
			return list;
		}
		return null;
	}

	/**
	 * 获取单条更新
	 * 
	 * @param context
	 * @param data
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public ApkItem getUpdateBySingle(Context context, String[] data) throws IOException, JSONException {
		HttpClientApi httpClientApi = HttpClientApi.getInstance();
		List<String[]> list = new ArrayList<String[]>();
		list.add(data);
		String result = httpClientApi.getContentFromUrlByPost(DOMAIN_NAME + UPDATE_URL + getLanguageType(), list);
		if (!TextUtils.isEmpty(result)) {
			ArrayList<ApkItem> resultList = parseJson(result);
			if (resultList != null && resultList.size() > 0) {
				return resultList.get(0);
			}
		}
		return null;
	}

	/*
	 * public ArrayList<String> getSearchList(String str) throws IOException,
	 * JSONException { HttpClientApi httpClientApi=HttpClientApi.getInstance();
	 * ArrayList<String> list=null; String
	 * result=httpClientApi.getContentFromUrl(searchDropUrl); JSONObject
	 * jsonObj=new JSONObject(result); JSONArray
	 * jsonArray=jsonObj.getJSONArray("imagine"); list=new ArrayList<String>();
	 * int num=3; for(int i=0;i<jsonArray.length();i++) { JSONObject
	 * obj=jsonArray.getJSONObject(i); if(i<=num+str.length()) {
	 * list.add(obj.getString("item")); }else { break; } } return list; }
	 */

	/**
	 * 安装统计
	 * 
	 * @param appId
	 * @param category
	 */
	public boolean statisticsForInstall(int appId, int category) {
		String url = DOMAIN_NAME + STATISTICS_INSTALL + category + "&appid=" + appId;
		HttpClientApi httpClientApi = HttpClientApi.getInstance();
		try {
			httpClientApi.getContentFromUrl(url);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * 云备份
	 * 
	 * @param list
	 *            1:versioncode 2:packageName
	 * @param userSessionId
	 * @return
	 * @throws IOException
	 */
	public boolean cloudBackup(List<String[]> list, String userSessionId, Context context) throws IOException {
		HttpClientApi httpClientApi = HttpClientApi.getInstance();
		boolean result = httpClientApi.postCloundBackup(DOMAIN_NAME + CLOUND_BACKUP_URL + getLanguageType(), list, userSessionId, context);
		System.out.println("cloundBackup:" + result);
		return result;
	}

	public ArrayList<ApkItem> getCloudRecoverList(String value) throws IOException, JSONException {
		HttpClientApi httpClientApi = HttpClientApi.getInstance();
		ArrayList<ApkItem> list = null;
		String result = httpClientApi.postCloundRestore(DOMAIN_NAME + CLOUND_RECOVER_URL + getLanguageType(), value);
		if (!TextUtils.isEmpty(result)) {
			JSONArray jsonArray = new JSONArray(result);
			list = new ArrayList<ApkItem>();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				ApkItem item = new ApkItem();
				item.appId = obj.getInt("id");
				item.category = obj.getInt("catid");
				item.appName = obj.getString("title");
				String downloadUrl = obj.getString("down_url");
				if (!TextUtils.isEmpty(downloadUrl)) {
					item.apkUrl = ONLINE_STATIC_DOMAIN_NAME + downloadUrl;
					// item.apkUrl = ONLINE_DOMAIN_NAME + downloadUrl;
				}
				item.packageName = obj.getString("apk_packagename");
				item.version = obj.getString("apk_versionname");
				String iconUrl = obj.getString("apk_icon");
				if (!TextUtils.isEmpty(iconUrl)) {
					item.appIconUrl = ONLINE_DOMAIN_NAME + iconUrl;
				}
				item.fileSize = obj.getLong("apk_size");
				item.versionCode = obj.getInt("apk_versioncode");
				if (obj.has("heavy")) {
					item.heavy = obj.getInt("heavy");
				}
				list.add(item);
			}
		}
		return list;
	}

	/**
	 * 检查市场版本更新
	 * 
	 * @param context
	 * @return
	 */
	public String checkAppUpdate(Context context) {
		String packageName = context.getPackageName();
		PackageManager pm = context.getPackageManager();
		PackageInfo packageInfo;
		try {
			packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			int versionCode = packageInfo.versionCode == 0 ? 1 : packageInfo.versionCode;
			String url = DOMAIN_NAME + "index.php?g=Api&m=Soft2&a=upgrade&versioncode=" + versionCode + "&packagename=" + packageName;
			// String
			// url="http://192.168.0.101/wuxiuwu/index.php?g=Api&m=Soft22&a=upgrade&versioncode=1&packagename=cn.com.wali.walisms";
			HttpClientApi httpClientApi = HttpClientApi.getInstance();
			String result = httpClientApi.getContentFromUrl(url);
			System.out.println("json result ===> " + result);
			if (!TextUtils.isEmpty(result)) {
				JSONObject jsonObject = new JSONObject(result);
				String downloadUrl = jsonObject.getString("down_url");
				if (!TextUtils.isEmpty(downloadUrl)) {
					return ONLINE_DOMAIN_NAME + downloadUrl;
				}
			}
		} catch (NameNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		} catch (JSONException e) {
			System.out.println(e);
		}
		return null;
	}

	/**
	 * 将长地址转成短地址
	 * 
	 * @param longUrl
	 * @return
	 */
	public String getShortUrlByLongUrl(String longUrl) {
		String url = DOMAIN_NAME;
		try {
			url = URLEncoder.encode(longUrl, "utf-8");
		} catch (UnsupportedEncodingException e) {
		}
		HttpClientApi httpClientApi = HttpClientApi.getInstance();
		try {
			String result = httpClientApi.getContentFromUrl(SINA_WEIBO_SHORT_URL_API + url);
			if (!TextUtils.isEmpty(result)) {
				JSONArray jsonArray = new JSONArray(result);
				if (jsonArray.length() > 0) {
					JSONObject jsonObject = jsonArray.getJSONObject(0);
					url = jsonObject.getString("url_short");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
		}
		return url;
	}

	/**
	 * 登录
	 * 
	 * @param emailStr
	 *            邮箱名
	 * @param passwdStr
	 *            密码
	 * @return 0:邮箱或密码为空 1:登陆成功 -1:用户不存在 -2:密码错误 -3:会员注册登陆状态失败
	 *         -10000:登陆失败(服务器错误) -9999:提交方式错误
	 * @throws IOException
	 * @throws JSONException
	 */
	public int login(String emailStr, String passwdStr) throws IOException, JSONException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("email", emailStr);
		params.put("password", passwdStr);
		String result = HttpClientApi.getInstance().postResponseData(DOMAIN_NAME + LOGIN_URL, params);
		System.out.println("login response ===> " + result);
		if (!TextUtils.isEmpty(result)) {
			JSONObject jsonObject = new JSONObject(result);
			return jsonObject.getInt("type");
		}
		return -9999;
	}

	/**
	 * 注册
	 * 
	 * @param emailStr
	 *            邮箱
	 * @param passwdStr
	 *            密码
	 * @param repeatPwd
	 *            重复密码
	 * @return 0:邮箱或密码为空 1:注册成功 -1:用户名不合法 -2:包含不允许注册的词语 -3:用户名已经存在 -4:Email格式有误
	 *         -5:Email不允许注册 -6:该 Email已经被注册 -7:会员添加失败 -8:两次密码不一致
	 *         -9:密码长度在6-15位之间 -10000:登陆失败(服务器错误) -9999:提交方式错误
	 * @throws IOException
	 * @throws JSONException
	 */
	public int register(String emailStr, String passwdStr, String repeatPwd) throws IOException, JSONException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("email", emailStr);
		params.put("password", passwdStr);
		params.put("rpassword", repeatPwd);
		String result = HttpClientApi.getInstance().postResponseData(DOMAIN_NAME + REGISTER_URL, params);
		System.out.println("register response ===> " + result);
		if (!TextUtils.isEmpty(result)) {
			JSONObject jsonObject = new JSONObject(result);
			return jsonObject.getInt("type");
		}
		return -9999;
	}

	/**
	 * 修改密码
	 * 
	 * @param email
	 *            邮箱密码
	 * @param oldPwd
	 *            旧密码
	 * @param newPwd
	 *            新密码
	 * @param rNewPwd
	 *            重复新密码
	 * @return 0:必填项不能为空 1:修改成功 -1:旧密码不正确 -2:两次密码不一致 -4:Email格式有误 -5:Email不允许注册
	 *         -6:该 Email已经被注册 -8:该用户受保护无权限更改 -9:密码长度在6-15位之间 -10000:登陆失败(服务器错误)
	 *         -9999:提交方式错误
	 * @throws IOException
	 * @throws JSONException
	 */
	public int modifyPwd(String email, String oldPwd, String newPwd, String rNewPwd) throws IOException, JSONException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("email", email);
		params.put("oldpwd", oldPwd);
		params.put("newpwd", newPwd);
		params.put("newrpwd", rNewPwd);
		String result = HttpClientApi.getInstance().postResponseData(DOMAIN_NAME + CHANGE_PWD_URL, params);
		System.out.println("modify pwd response ===> " + result);
		if (!TextUtils.isEmpty(result)) {
			JSONObject jsonObject = new JSONObject(result);
			return jsonObject.getInt("type");
		}
		return -9999;
	}

	/**
	 * 找回密码
	 * 
	 * @param email
	 *            邮箱
	 * @return 1:发送成功 -1:发送失败 -2:无此用户
	 * @throws IOException
	 * @throws JSONException
	 */
	public int findPwd(String email) throws IOException, JSONException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("email", email);
		String result = HttpClientApi.getInstance().postResponseData(DOMAIN_NAME + FIND_PWD_URL, params);
		System.out.println("find pwd response ===> " + result); // 无返回值
		if (!TextUtils.isEmpty(result)) {
			JSONObject jsonObject = new JSONObject(result);
			return jsonObject.getInt("type");
		}
		return -1;
	}

	/**
	 * 反馈
	 * 
	 * @param appName
	 *            应用名称
	 * @param version
	 *            应用版本
	 * @param type
	 *            设备型号
	 * @param sysType
	 *            系统版本
	 * @param contact
	 *            联系方式
	 * @param content
	 *            内容
	 * @return 0:提交失败 1:提交成功
	 * @throws IOException
	 * @throws JSONException
	 */
	public int feedback(String appName, String version, String type, String sysType, String contact, String content) throws IOException, JSONException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("aplicname", appName);
		params.put("version", version);
		params.put("type", type);
		params.put("systemtype", sysType);
		params.put("contact", contact);
		params.put("content", content);
		String result = HttpClientApi.getInstance().postResponseData(DOMAIN_NAME + FEEDBACK_URL, params);
		System.out.println("feedback response ===> " + result);
		if (!TextUtils.isEmpty(result)) {
			JSONObject jsonObject = new JSONObject(result);
			return jsonObject.getInt("success");
		}
		return 0;
	}

	/**
	 * 评分
	 * 
	 * @param catId
	 *            分类ID
	 * @param appId
	 *            自身ID
	 * @param score
	 *            分数
	 * @return 0:打分失败 1:打分成功
	 * @throws IOException
	 */
	public int appGrade(String catId, String appId, String score) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("catid", catId);
		params.put("appid", appId);
		params.put("score", score);
		String result = HttpClientApi.getInstance().postResponseData(DOMAIN_NAME + GRADE_URL, params);
		System.out.println("app grade response ===> " + Integer.valueOf(result));
		return Integer.valueOf(result != null ? result : "0");
	}

	/**
	 * Top50 数据
	 * 
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public List<ApkItem> getTop50() throws IOException, JSONException {
		HttpClientApi httpClientApi = HttpClientApi.getInstance();
		// String
		// result=httpClientApi.getContentFromUrl(DOMAIN_NAME+TOP_50_URL);
		String result = httpClientApi.getContentFromUrl(DOMAIN_NAME + SHAKE_GUESSLIKE_URL + getLanguageType());
		System.out.println("top 50 ====> " + result);
		if (!TextUtils.isEmpty(result)) {
			JSONArray jsonArray = new JSONArray(result);
			List<ApkItem> list = new ArrayList<ApkItem>();
			if (jsonArray != null && jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					ApkItem item = new ApkItem();
					item.appId = jsonObject.getInt("id");
					item.category = jsonObject.getInt("catcid");
					item.appName = jsonObject.getString("apk_name");
					String downloadUrl = jsonObject.getString("down_url");
					if (!TextUtils.isEmpty(downloadUrl)) {
						item.apkUrl = ONLINE_STATIC_DOMAIN_NAME + downloadUrl;
					}
					item.packageName = jsonObject.getString("apk_packagename");
					item.version = jsonObject.getString("apk_versionname");
					String iconUrl = jsonObject.getString("apk_icon");
					if (!TextUtils.isEmpty(iconUrl)) {
						item.appIconUrl = ONLINE_DOMAIN_NAME + iconUrl;
					}
					item.company = jsonObject.getString("developer");
					item.versionCode = jsonObject.getInt("apk_versioncode");
					item.language = jsonObject.getInt("language");
					item.downloadNum = jsonObject.getLong("down_count");
					item.fileSize = jsonObject.getLong("apk_size");
					if (jsonObject.has("heavy")) {
						item.heavy = jsonObject.getInt("heavy");
					}
					list.add(item);
				}
				return list;
			}
		}
		return null;
	}

	/**
	 * 通过包名和版本号动态获取应用详情
	 * 
	 * @param packageName
	 * @param versionCode
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public ApkItem getApkItemDetailByPackage(String packageName, int versionCode) throws IOException, JSONException {
		HttpClientApi httpClientApi = HttpClientApi.getInstance();
		String url = DOMAIN_NAME + "index.php?g=Api&m=Soft2&a=Appdetail&packagename=" + packageName + "&versioncode=" + versionCode;
		String result = httpClientApi.getContentFromUrl(url);
		if (!TextUtils.isEmpty(result)) {
			ApkItem item = new ApkItem();
			JSONObject jsonObject = new JSONObject(result);
			item.appId = jsonObject.getInt("id");
			item.category = jsonObject.getInt("catcid");
			item.updateDate = jsonObject.getString("time");
			item.discription = jsonObject.getString("contents");
			String language = jsonObject.getString("language");
			if (TextUtils.isEmpty(language)) {
				item.language = 1;
			} else {
				item.language = Integer.parseInt(language);
			}
			item.company = jsonObject.getString("developer");
			item.score = jsonObject.getDouble("score");
			JSONArray screenShotJsonArray = jsonObject.getJSONArray("screenshot");
			if (screenShotJsonArray != null && screenShotJsonArray.length() > 0) {
				for (int j = 0; j < screenShotJsonArray.length(); j++) {
					String screenShot = screenShotJsonArray.getString(j);
					if (!TextUtils.isEmpty(screenShot)) {
						item.appScreenshotUrl.add(DOMAIN_NAME + screenShot);
					}
				}
			}
			JSONArray historyJsonArray = jsonObject.getJSONArray("history");
			if (historyJsonArray != null && historyJsonArray.length() > 0) {
				item.historys = new HistoryApkItem[historyJsonArray.length()];
				for (int j = 0; j < historyJsonArray.length(); j++) {
					HistoryApkItem historyItem = new HistoryApkItem();
					JSONObject historyJsonObject = historyJsonArray.getJSONObject(j);
					historyItem.appId = historyJsonObject.getInt("id");
					historyItem.category = item.category;
					historyItem.appIconUrl = historyJsonObject.getString("apk_icon");
					historyItem.updateDate = historyJsonObject.getString("time");
					historyItem.versionName = historyJsonObject.getString("apk_versionname");
					historyItem.downloadNum = historyJsonObject.getLong("down_count");
					historyItem.appSize = historyJsonObject.getLong("apk_size");
					String historyUrl = historyJsonObject.getString("down_url");
					if (!TextUtils.isEmpty(historyUrl)) {
						historyItem.url = ONLINE_STATIC_DOMAIN_NAME + historyUrl;
					}
					historyItem.versionCode = historyJsonObject.getInt("apk_versioncode");
					if (historyJsonObject.has("heavy")) {
						historyItem.heavy = historyJsonObject.getInt("heavy");
					}
					item.historys[j] = historyItem;
				}
			}

			String apkUrl = jsonObject.getString("down_url");
			if (!TextUtils.isEmpty(apkUrl)) {
				item.apkUrl = ONLINE_STATIC_DOMAIN_NAME + apkUrl;
			}

			item.downloadNum = jsonObject.getLong("down_count");

			String iconUrl = jsonObject.getString("apk_icon");

			if (!TextUtils.isEmpty(iconUrl)) {
				item.appIconUrl = ONLINE_DOMAIN_NAME + iconUrl;
			}

			item.fileSize = jsonObject.getLong("apk_size");
			item.appName = jsonObject.getString("apk_name");
			JSONArray permisionJsonArray = jsonObject.getJSONArray("apk_permision");
			if (permisionJsonArray != null && permisionJsonArray.length() > 0) {
				for (int j = 0; j < permisionJsonArray.length(); j++) {
					item.permisions.add(permisionJsonArray.getString(j));
				}
			}
			item.minSdkVersion = jsonObject.getInt("apk_sdkversion");
			item.versionCode = jsonObject.getInt("apk_versioncode");
			item.version = jsonObject.getString("apk_versionname");
			item.packageName = jsonObject.getString("apk_packagename");
			if (jsonObject.has("heavy")) {
				item.heavy = jsonObject.getInt("heavy");
			}

			JSONArray likeJsonArray = jsonObject.getJSONArray("like");
			if (likeJsonArray != null && likeJsonArray.length() > 0) {
				for (int i = 0; i < likeJsonArray.length(); i++) {
					JSONObject likeJSONObject = likeJsonArray.getJSONObject(i);
					ApkItem likeItem = new ApkItem();
					likeItem.appId = likeJSONObject.getInt("id");
					likeItem.category = likeJSONObject.getInt("catcid");
					likeItem.appName = likeJSONObject.getString("apk_name");
					String likeIconUrl = likeJSONObject.getString("apk_icon");
					if (!TextUtils.isEmpty(likeIconUrl)) {
						likeItem.appIconUrl = ONLINE_DOMAIN_NAME + likeIconUrl;
					}
					if (likeJSONObject.has("heavy")) {
						likeItem.heavy = likeJSONObject.getInt("heavy");
					}
					item.likeList.add(likeItem);
				}
			}
			return item;
		}
		return null;
	}

	public int collectLocalData(Context cxt) throws IOException {
		Map<String, String> deviceInfo = AndroidUtils.getDeviceInfo(cxt);
		Map<String, String> dataMap = new HashMap<String, String>();
		List<InstalledAppInfo> installedAppsList = DJMarketUtils.getInstalledApps(cxt);
		// for (InstalledAppInfo info : installedAppsList) {
		// installedApps.put(info.getPkgName(), info.getName());
		// }
		// Set<Entry<String, String>> set = deviceInfo.entrySet();
		// for (Entry<String, String> entry : set) {
		// System.out.println(entry.getKey() + ": " + entry.getValue());
		// }
		JSONObject deviceInfoJson = new JSONObject(deviceInfo);
		JSONArray appJsonArray = new JSONArray();
		for (InstalledAppInfo info : installedAppsList) {
			JSONObject jsonObj = new JSONObject();
			try {
				jsonObj.put("app_name", info.getName());
				jsonObj.put("pkg_name", info.getPkgName());
				// AndroidUtils.parseSignature(AndroidUtils.getSignInfo(cxt,
				// info.getPkgName()));
				appJsonArray.put(jsonObj);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// System.out.println("device info ====> " + deviceInfoJson.toString());
		// System.out.println("installed app ====> " + jsonArray.toString());
		dataMap.put("device_info", deviceInfoJson.toString());
		dataMap.put("app_info", appJsonArray.toString());
		String result = HttpClientApi.getInstance().postResponseData(DOMAIN_NAME + COLLECT_LOCAL_INFO_URL, dataMap);
		System.out.println("collect info =====> " + result);
		if (null != result) {
			try {
				int i = Integer.valueOf(result);
				return i;
			} catch (Exception e) {
				return 0;
			}

		}
		return 0;
		// return Integer.valueOf(result != null ? result : "0");
	}

	public List<SubjectInfo> getAllSubject() throws IOException, JSONException {
		HttpClientApi httpClientApi = HttpClientApi.getInstance();
		String result = httpClientApi.getContentFromUrl(DOMAIN_NAME + ALLSUBJECT_URL + getLanguageType());
		System.out.println("DOMAIN_NAME2  --- > " + DOMAIN_NAME + ALLSUBJECT_URL + getLanguageType() + "result ----- > " + result);
		if (!TextUtils.isEmpty(result)) {
			JSONArray jsonArray = new JSONArray(result);
			List<SubjectInfo> list = new ArrayList<SubjectInfo>();
			if (jsonArray != null && jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					SubjectInfo subjectInfo = new SubjectInfo();
					subjectInfo.subjectId = jsonObject.getInt("id");
					subjectInfo.title = jsonObject.getString("title");
					subjectInfo.subjectIconUrl = DOMAIN_NAME + "d/file/" + jsonObject.getString("pic");
					list.add(subjectInfo);
				}
				return list;
			}
		}
		return null;
	}

	public SubjectInfo getSubjectApk(int subjectId) throws JSONException {
		HttpClientApi httpClientApi = HttpClientApi.getInstance();
		String url = DOMAIN_NAME + SUBJECTITEM_URL + subjectId + getLanguageType();

		System.out.println("  url ----- > " + url);

		String result = null;
		try {
			result = httpClientApi.getContentFromUrl(url);
		} catch (IOException e) {
			System.out.println("getApps error:" + e + ", " + result);
		}
		SubjectInfo subjectInfo = new SubjectInfo();
		if (!TextUtils.isEmpty(result)) {
			JSONObject jsonObject = new JSONObject(result);
			subjectInfo.subjectId = subjectId;
			subjectInfo.contents = jsonObject.getString("contents");
			subjectInfo.subjectIconUrl = jsonObject.getString("pic");
			subjectInfo.title = jsonObject.getString("title");

			JSONArray listJsonArray = jsonObject.getJSONArray("lists");
			if (listJsonArray != null && listJsonArray.length() > 0) {
				subjectInfo.subjectItems = new ArrayList<SubjectItem>();
				SubjectItem subjectItem;
				for (int i = 0; i < listJsonArray.length(); i++) {
					subjectItem = new SubjectItem();
					JSONObject subjectJsonObject = listJsonArray.getJSONObject(i);
					subjectItem.apkSize = subjectJsonObject.getString("apk_size");
					subjectItem.apkVerion = subjectJsonObject.getString("apk_sdkversion");
					subjectItem.appId = subjectJsonObject.getInt("appid");
					subjectItem.catpId = subjectJsonObject.getInt("catcid");
					subjectItem.comment = subjectJsonObject.getString("comment");
					subjectItem.downloadNum = subjectJsonObject.getLong("down_count");
					subjectItem.downUrl = DOMAIN_NAME + subjectJsonObject.getString("down_url");
					subjectItem.iconUrl = DOMAIN_NAME + "d/file/" + subjectJsonObject.getString("apk_ico");
					subjectItem.inputTime = subjectJsonObject.getString("inputtime");
					subjectItem.language = subjectJsonObject.getInt("language");
					subjectItem.title = subjectJsonObject.getString("title");
					subjectItem.packageName = subjectJsonObject.getString("apk_packagename");
					subjectItem.versionCode = subjectJsonObject.getInt("apk_versioncode");
					subjectInfo.subjectItems.add(subjectItem);
				}
			}
			return subjectInfo;
		}
		return null;
	}

	public int subjectCount(int subjectId) throws IOException, JSONException {
		HttpClientApi httpClientApi = HttpClientApi.getInstance();
		String url = DOMAIN_NAME + SUBJECTCOUNT_URL + subjectId;
		String result = httpClientApi.getContentFromUrl(url);
		if (result.equals("1")) {
			return 1;
		} else {
			return 0;
		}
	}

	private String getLanguageType() {
		String language = Locale.getDefault().getLanguage();
		String country = Locale.getDefault().getCountry();
		if (language.equals("zh")) {
			if (country.equals("TW") || country.equals("HK")) {
				return "&lang=1";
			}
		}
		return "&lang=0";
	}

	/**
	 * 获取动机市场详情url
	 * 
	 * @param context
	 * @return
	 */
	public String getDJUrl(Context context) {
		String result = null;
		String cacheSuffix = DJ_ADRESS_URL;
		if (!AndroidUtils.isNetworkAvailable(context)) {
			result = FsCache.getCacheString(cacheSuffix);
		} else {
			String suffixUrl = null;
			suffixUrl = DJ_ADRESS_URL;
			System.out.println("=========suffixUrl=========" + DOMAIN_NAME + suffixUrl);
			HttpClientApi httpClientApi = HttpClientApi.getInstance();
			try {
				result = httpClientApi.getContentFromUrl(DOMAIN_NAME + suffixUrl);
			} catch (IOException e) {
				result = FsCache.getCacheString(cacheSuffix);
				System.out.println("getApps:" + e);
			}
			if (!TextUtils.isEmpty(result)) {
				FsCache.deleteCacheFileByMd5Value(cacheSuffix);
				FsCache.cacheFileByMd5(result, cacheSuffix);
			}
		}
		return DOMAIN_NAME + result;
	}

	/**
	 * 应用详情url
	 * 
	 * @param categoryId
	 * @param appId
	 * @return
	 */
	public String getAppDetailUrl(int categoryId, int appId) {
		return DOMAIN_NAME + "webapp/" + categoryId + "/" + appId + ".html";
	}

	/**
	 * 获取微信详情
	 * 
	 * @param context
	 * @param catid
	 *            分类id
	 * @param id
	 *            软件id
	 * @return
	 */
	public ApkItem getWxApp(Context context) throws JSONException {
		String result = null;
		String cacheSuffix = WX_DATA_URL;
		if (!AndroidUtils.isNetworkAvailable(context)) {
			result = FsCache.getCacheString(cacheSuffix);
		} else {
			String suffixUrl = null;
			suffixUrl = WX_DATA_URL;
			System.out.println("=========suffixUrl=========" + DOMAIN_NAME + suffixUrl);
			HttpClientApi httpClientApi = HttpClientApi.getInstance();
			try {
				result = httpClientApi.getContentFromUrl(DOMAIN_NAME + suffixUrl);
			} catch (IOException e) {
				result = FsCache.getCacheString(cacheSuffix);
				System.out.println("getApps:" + e);
			}
			if (!TextUtils.isEmpty(result)) {
				FsCache.deleteCacheFileByMd5Value(cacheSuffix);
				FsCache.cacheFileByMd5(result, cacheSuffix);
			}
		}
		ApkItem item = new ApkItem();
		if (!TextUtils.isEmpty(result)) {
			JSONObject jsonObject = new JSONObject(result);
			item.appId = jsonObject.getInt("id");
			String category = jsonObject.getString("catcid");
			if (!TextUtils.isEmpty(category)) {
				item.category = Integer.parseInt(category);
			}
			String language = jsonObject.getString("language");
			if (TextUtils.isEmpty(language)) {
				item.language = 1;
			} else {
				item.language = Integer.parseInt(language);
			}
			item.company = jsonObject.getString("developer");
			String apkUrl = jsonObject.getString("down_url");
			if (!TextUtils.isEmpty(apkUrl)) {
				item.apkUrl = ONLINE_STATIC_DOMAIN_NAME + apkUrl;
			}
			item.downloadNum = jsonObject.getLong("down_count");
			String iconUrl = jsonObject.getString("apk_icon");

			if (!TextUtils.isEmpty(iconUrl)) {
				item.appIconUrl = ONLINE_DOMAIN_NAME + iconUrl;
			}
			item.appName = jsonObject.getString("apk_name");
			item.fileSize = jsonObject.getLong("apk_size");
			item.versionCode = jsonObject.getInt("apk_versioncode");
			item.version = jsonObject.getString("apk_versionname");
			item.packageName = jsonObject.getString("apk_packagename");
			if (jsonObject.has("heavy")) {
				item.heavy = jsonObject.getInt("heavy");
			}
		}
		return item;
	}

}
