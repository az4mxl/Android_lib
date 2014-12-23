package az.mxl.lib.base;

import android.app.Application;
import az.mxl.lib.utils.ActivityManager;

public class AppController extends Application {

	private static AppController theInstance;
	private ActivityManager activityManager = null;

	private boolean isDebug;

	/**
	 * 获取Application Controller
	 * 
	 * @return
	 */
	public static AppController getInstance() {
		return theInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		theInstance = this;
		// 初始化自定义Activity管理器
		activityManager = ActivityManager.getScreenManager();

	}

	/**
	 * 设置是否开启AZ_lib的log,发布时请关闭
	 * 
	 * @param debug
	 */
	public void setDebug4azlib(boolean debug) {
		this.isDebug = debug;
	}

	public boolean isDebug() {
		return isDebug;
	}

	/**
	 * 获取Acitivity管理器
	 * 
	 * @return
	 */
	public ActivityManager getActivityManager() {
		if (activityManager == null) {
			activityManager = ActivityManager.getScreenManager();
		}
		return activityManager;
	}

}
