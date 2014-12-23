package az.mxl.lib.base.view;

import android.os.Bundle;
import android.view.View;
import az.mxl.lib.base.BaseActivity;

/**
 * 定义Activity中View操作的一些方法，让{@link BaseActivity} 继承.
 * 
 * @author mxl
 * 
 */
public interface IActivityView {

	/**
	 * Activity执行onCreate()方法前
	 * 
	 * @param savedInstanceState
	 */
	void onBeforeCreate(Bundle savedInstanceState);
	/** 
	 * 初始化Log(BaseActivity中已经在onCreate()中执行了)
	 */
	abstract void initLog();
	/**
	 * Activity执行onCreate()方法后,请在此操作UI控件
	 * 
	 * @param savedInstanceState
	 */
	abstract void onAfterCreate(Bundle savedInstanceState);

	/**
	 * 设置content,conCreate()方法中执行
	 * 
	 * @param savedInstanceState
	 */
	void onCreateContent(Bundle savedInstanceState);

	/**
	 * 创建contentView(使用代码生成view),不要在此操作UI控件
	 * 
	 * @return
	 */
	View createContentView();

	/**
	 * 创建contentView(使用xml布局的ID),不要在此操作UI控件
	 * 
	 * @return
	 */
	int createContentViewFromID();

	/**
	 * 获取view--->findViewById(int id)
	 * @param id viewId
	 */
	<E extends View> E getView(int id);
	
	/**
	 * 显示progress
	 */
	void showProgress();

	/**
	 * 隐藏progress
	 */
	void hideProgress();

	/**
	 * 是否执行原始返回操作
	 * 
	 * @return false执行自定义back()方法，true执行原始返回操作---默认为false
	 */
	boolean doNativeBack();

	/** 自定义返回操作 */
	void back();

	/** 
	 * 启动Acitivity执行动画,可自己修改此方法
	 * 
	 * @see 默认动画：本Activity不动，启动的Activity从右边进入
	 */
	void anim4open();
	/** 
	 * 关闭Acitivity执行动画,可自己修改此方法
	 * 
	 *  @see 默认动画：本Activity向右边移除，底部Activity直接呈现
	 */
	void anim4close();
	
}