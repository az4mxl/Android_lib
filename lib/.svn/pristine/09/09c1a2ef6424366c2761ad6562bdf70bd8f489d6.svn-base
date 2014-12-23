package az.mxl.lib.base.view;

import android.view.View.OnClickListener;

public interface IActionBar {

	/**
	 * 设置ActionBar的基本属型
	 */
	void inItBaseActionBar();

	/**
	 * 具有左右俩按钮ActionBar(左侧按钮-->自定义; 右侧按钮-->自定义)
	 * 
	 * @param text
	 *            Actionbar的Title
	 * @param text4LeftBtn
	 *            左侧按钮文字(如果此项为null，则隐藏左侧按钮;如果此项为空字符串，则左侧按钮是默认文字)
	 * @param drawable4LeftBtn
	 *            左侧按钮效果图(如果此项为-1，则不用此值)
	 * @param listener4LeftBtn
	 *            左侧按钮监听
	 * @param text4RightBtn
	 *            右侧按钮文字(如果此项为null，则隐藏右侧按钮;如果此项为空字符串，则右侧按钮是默认文字)
	 * @param drawable4RightBtn
	 *            右侧按钮效果图(如果此项为-1，则不用此值)
	 * @param listener4RightBtn
	 *            右侧按钮监听
	 */
	public void inItActionBar4TwoBtn(String text, String text4LeftBtn, int drawable4LeftBtn, OnClickListener listener4LeftBtn, String text4RightBtn, int drawable4RightBtn, OnClickListener listener4RightBtn);

	/**
	 * 添加具有左侧按钮的ActionBar(左边按钮监听默认为finish)
	 * 
	 * @param text
	 *            页面的title
	 */
	void inItActionBar(String text);

	/**
	 * 添加具有左侧按钮的ActionBar(左边按钮监听默认为finish)
	 * 
	 * @param res
	 *            页面的title
	 */
	void inItActionBar(int res);

	/**
	 * 添加具有左侧按钮的ActionBar(左边按钮监听->自己设置)
	 * 
	 * @param text
	 *            页面的title
	 * @param leftBtnListener
	 *            actionBar左边按钮监听
	 */
	void inItActionBar(String text, OnClickListener leftBtnListener);

	/**
	 * 添加具有左侧按钮的ActionBar(左边按钮监听->自己设置)
	 * 
	 * @param res
	 *            页面的title
	 * @param leftBtnListener
	 *            actionBar左边按钮监听
	 */
	void inItActionBar(int res, OnClickListener leftBtnListener);

}
