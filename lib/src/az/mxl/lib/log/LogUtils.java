package az.mxl.lib.log;

import android.util.Log;
import az.mxl.lib.base.AppController;

public class LogUtils implements ILog{
	
	private static final String TAG = "az-lib";
	
	private final String tag;

	private LogUtils(Class<?> clazz) {
		tag = String.format("%s: %s", "app", clazz.getSimpleName());
	}

	public static final LogUtils getLog(Class<?> clazz) {
		return new LogUtils(clazz);
	}

	@Override
	public void d(String msg) {
		if (AppController.getInstance().isDebug()) {
			Log.d(tag, msg);
		}
	}

	@Override
	public void w(String msg) {
		if (AppController.getInstance().isDebug()) {
			Log.w(tag, msg);
		}
	}

	@Override
	public void e(String msg) {
		if (AppController.getInstance().isDebug()) {
			Log.e(tag, msg);
		}
	}

	public static void d4defualtTag(String msg) {
		if (AppController.getInstance().isDebug()) {
			Log.d(TAG, msg);
		}
	}

	public static void w4defualtTag(String msg) {
		if (AppController.getInstance().isDebug()) {
			Log.w(TAG, msg);
		}
	}

	public static void e4defualtTag(String msg) {
		if (AppController.getInstance().isDebug()) {
			Log.e(TAG, msg);
		}
	}
}
