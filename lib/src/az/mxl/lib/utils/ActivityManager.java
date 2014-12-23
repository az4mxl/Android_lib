package az.mxl.lib.utils;

import java.util.Stack;

import android.app.Activity;
import az.mxl.lib.base.BaseActivity;

public class ActivityManager {

	private static Stack<BaseActivity> activityStack;
	
//	private static Stack<BaseActivity> activityStack4Show = new Stack<BaseActivity>();
	
	private static ActivityManager instance;

	private ActivityManager() {
	}

	
//	public void pushActivity4ShowStack(BaseActivity activity) {
//		if (activityStack4Show == null) {
//			activityStack4Show = new Stack<BaseActivity>();
//		}
//		activityStack4Show.add(activity);
//	}
//	
//	public void popActivity4ShowStack(BaseActivity activity) {
//		if (activity != null) {
//			// 在从自定义集合中取出当前Activity时，也进行了Activity的关闭操作
//			if (activityStack4Show != null)
//				activityStack4Show.remove(activity);
//		}
//	}
//	
//	public boolean isHaveActivity(){
//		if (activityStack4Show == null || activityStack4Show.isEmpty()) {
//			return false;
//		} else{
//			return true;
//		}
//	}
	
	public static ActivityManager getScreenManager() {
		if (instance == null) {
			instance = new ActivityManager();
		}
		return instance;
	}

	/**
	 * 退出栈中的Activity
	 * 
	 * @param activity
	 */
	public void popActivity(BaseActivity activity) {
		if (activity != null) {
			// 在从自定义集合中取出当前Activity时，也进行了Activity的关闭操作
			activity.finish4Native();
			if (activityStack != null)
				activityStack.remove(activity);
			activity = null;
		}
	}

	/**
	 * 退出栈中的Activity
	 * 
	 * @param cls
	 */
	public void popActivity(Class<?> cls) {
		if (activityStack == null)
			return;
		for (BaseActivity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				popActivity(activity);
				return;
			}
		}
	}

	/**
	 * activityStack是否存在该cls,如果存在则返回改activity
	 * 
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getActivity(Class<T> cls) {
		if (activityStack == null)
			return null;
		for (BaseActivity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				return (T) activity;
			}
		}
		return null;
	}

	/**
	 * activityStack是否存在该cls
	 * 
	 * @param cls
	 * @return
	 */
	public boolean isHaveActivity(Class<?> cls) {
		if (activityStack == null)
			return false;
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获得当前栈顶Activity
	 * 
	 * @return activity
	 */
	public BaseActivity currentActivity() {
		BaseActivity activity = null;
		if (activityStack != null && !activityStack.empty())
			activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 将当前Activity推入栈中
	 * 
	 * @param activity
	 */
	public void pushActivity(BaseActivity activity) {
		if (activityStack == null) {
			activityStack = new Stack<BaseActivity>();
		}
		activityStack.add(activity);
	}

	/**
	 * 退出栈中所有Activity
	 */
	public void popAllActivity() {
		while (true) {
			BaseActivity activity = currentActivity();
			if (activity == null) {
				break;
			}
			popActivity(activity);
		}
	}

	/**
	 * 退出栈中所有Activity
	 * 
	 * @param cls
	 *            此cls不退出
	 */
	public void popAllActivityExceptOne(Class<?> cls) {
		while (true) {
			BaseActivity activity = currentActivity();
			if (activity == null) {
				break;
			}
			if (activity.getClass().equals(cls)) {
				break;
			}
			popActivity(activity);
		}
	}
}
