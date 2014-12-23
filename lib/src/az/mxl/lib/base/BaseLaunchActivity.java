package az.mxl.lib.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import az.mxl.lib.R;

public abstract class BaseLaunchActivity extends BaseActivity{

	private TextView tv;

	@Override
	public void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setLaunchBG(getView(R.id.layout_launch));
		tv = getView(R.id.tv_loading);
	}
	
	@Override
	public int createContentViewFromID() {
		// TODO Auto-generated method stub
		return R.layout.activity_launch;
	}
	
	/**
	 * 设置页面背景
	 * @param view 设置bg
	 */
	public abstract void setLaunchBG(View view);
	
	/**
	 * 
	 * @param msg
	 * @param color
	 * @param paddingTop
	 */
	public void showLaunchPageLoading(String msg, int color, double paddingTop){
		
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		int height = wm.getDefaultDisplay().getHeight();
		
		tv.setText(msg);
		tv.setTextColor(color);
		tv.setPadding(0, (int)(paddingTop * height), 0, 0);
		tv.setVisibility(View.VISIBLE);
	}

}
