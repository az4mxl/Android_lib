package az.mxl.lib.base;

import java.util.List;

import android.app.Dialog;
import android.app.NativeActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import az.mxl.lib.R;
import az.mxl.lib.base.view.IActivityView;
import az.mxl.lib.log.LogUtils;
import az.mxl.lib.utils.ViewUtils4AZ;

import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * TODO: 继承IViewActivity,实现其中的方法（Create an implementation of IViewActivity and
 * delegate all calls to the impl）.
 * <p>
 * Advantage: Can reuse(重复使用) the implementation working with {@link NativeActivity} etc.
 * </p>
 */
public abstract class BaseActivity extends SherlockFragmentActivity implements IActivityView {

	public LogUtils log;
	
	// activity 的 contentView
	private View contentView;
	
	private FragmentManager fm;
	private FragmentTransaction ft;
	public final static String TAG_UN_HIDI = "unHide";
	
	// 加载数据显示的dialog
	private Dialog progressDialog;

	//重新加载layout
	private View reLoadLayout;
			
	public boolean isPause, isDestroy;

	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		getController().getActivityManager().pushActivity(this);

		initLog();
		
		onBeforeCreate(savedInstanceState);
		
		super.onCreate(savedInstanceState);
		onCreateContent(savedInstanceState);

		onAfterCreate(savedInstanceState);
	}

	public AppController getController() {
		return (AppController) getApplication();
	}

	@Override
	public void onBeforeCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	}
	
	public void onCreateContent(Bundle savedInstanceState) {
		LayoutInflater inflator = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

		int rid = createContentViewFromID();
		if (rid != -1) {
			contentView = inflator.inflate(createContentViewFromID(), null, false);
		} else {
			contentView = createContentView();
		}
		setContentView(contentView);
	}

	public View createContentView() {
		return null;
	}

	public int createContentViewFromID() {
		return -1;
	}

	/**
	 * 获取主View
	 * @return
	 */
	protected final View getContentView() {
		if (contentView == null)
			throw new IllegalStateException("Not yet initialized");
		return contentView;
	}

	@SuppressWarnings("unchecked")
	@Override
	public final <E extends View> E getView(int id) {
		try {
			return (E) findViewById(id);
		} catch (ClassCastException ex) {
			LogUtils.d4defualtTag("Could not cast View to concrete class." + ex);
			throw ex;
		}
	}
	
	/**
	 * 启动Activity
	 * 
	 * @param intent
	 */
	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		anim4open();
	}
	
	/**
	 * 启动Activity,使用默认切换动画(当前Activity不动，启动的Activity从右边进来)
	 * 
	 * @param intent
	 * @param requestCode
	 */
	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		anim4open();
	}
	
	@Override
	public void anim4open() {
		// TODO Auto-generated method stub
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_null);	
	}
	
	@Override
	public void anim4close() {
		// TODO Auto-generated method stub
		overridePendingTransition(R.anim.slide_null, R.anim.slide_out_right);
	}
	
	@Override
	public void finish() {
		popActivity();
		anim4close();
	}
	
	/**
	 * finish本Activity，并从ActivityManager移除
	 */
	public void popActivity(){
		getController().getActivityManager().popActivity(this);
	}
	
	/**
	 * Activity 原始finish()方法
	 */
	public final void finish4Native(){
		super.finish();
	}
	

	@Override
	public boolean doNativeBack() {
		return false;
	}

	@Override
	public void back() {
		finish();
	}
	
	@Override
	public void onBackPressed() {
		if (doNativeBack()) {
			super.onBackPressed();
		} else {
			back();
		}
	}
	
	//----------------------------------------------操作Fragment，add，remove---------------------------------------------------------//
	/**
	 * 向Activity中添加Fragment,且隐藏其他Fragment
	 * 
	 * @param contentId
	 * @param fragment
	 * @param tag
	 */
	public void addFragment(int contentId, Fragment fragment, String tag) {
		if (fragment != null) {
			if (fragment.isVisible()) {
				return;
			}
		}
		if (fm == null) {
			fm = getSupportFragmentManager();
		}
		ft = fm.beginTransaction();
		if (!fragment.isAdded()) {
			if (tag == null) {
				ft.add(contentId, fragment);
			} else {
				ft.add(contentId, fragment, tag);
			}
		}
		List<Fragment> fs = fm.getFragments();
		ft.show(fragment);
		if (fs != null) {
			for (int i = 0; i < fs.size(); i++) {
				Fragment f = fs.get(i);
				if (f != fragment && !TAG_UN_HIDI.equals(f.getTag())) {
					ft.hide(f);
				}
			}
		}
		ft.commitAllowingStateLoss();
	}

	/**
	 * 移除fragment
	 * 
	 * @param fragment
	 */
	public void removeFragment(Fragment fragment) {
		if (fragment == null) {
			return;
		}
		if (fm == null) {
			fm = getSupportFragmentManager();
		}
		ft = fm.beginTransaction();
		ft.remove(fragment);
		ft.commitAllowingStateLoss();
	}
	
	//----------------------------------------------数据加载View---------------------------------------------------------//
	
	@Override
	public void showProgress() {
		setProgressBar(View.VISIBLE);
	}

	@Override
	public void hideProgress() {
		setProgressBar(View.GONE);
	}
	
	/** 
	 * 生成数据加载view
	 * 
	 * @param msg 
	 */
	public void superProBar(String msg) {
		progressDialog = ViewUtils4AZ.createLoadingDialog2(this, msg);
	}
	
	/** 
	 * 生成数据加载view--默认msg:正在加载…
	 * 
	 */
	public void superProBar() {
		superProBar("正在加载…");
	}
	
	/**
	 * 设置数据加载view显示msg
	 * @param msg
	 */
	public void setProBarMsg(String msg){
		if (progressDialog == null) {
			superProBar(msg);
		} else {
			((TextView)progressDialog.findViewById(R.id.tipTextView)).setText(msg);
		}
	}
	
	/** 
	 * 设置数据加载view的显示或者隐藏
	 * 
	 * @param show View.VISIBLE | View.GONE
	 */
	private void setProgressBar(int show) {
		if (progressDialog == null) {
			LogUtils.e4defualtTag("progressDialog 没有初始化 ，请先执行superProBar()方法！");
			return;
		}
		if(show != View.GONE && show != View.VISIBLE){
			LogUtils.e4defualtTag("setProgressBar() 方法的参数不合法！");
			return;
		}
		
		try {
			if (show == View.VISIBLE && !progressDialog.isShowing()) {
				progressDialog.show();
				progressDialog.getWindow().setLayout(getResources().getDimensionPixelSize(R.dimen.loading_dialog_width), getResources().getDimensionPixelSize(R.dimen.loading_dialog_height));    
			} else if (show == View.GONE && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//----------------------------------------------重新加载数据View---------------------------------------------------------//
	/**
	 * show重新加载按钮（按钮文字默认：点击重新加载）
	 * 
	 * @param onClickListener
	 */
	public void showReLoadLayout(OnClickListener onClickListener, View view){
		showReLoadLayout(null, onClickListener, view);
	}
	
	/**
	 * show重新加载按钮
	 * @param reLoadText 重新加载按钮文字
	 * @param onClickListener
	 */
	public void showReLoadLayout(String reLoadText, OnClickListener onClickListener, View view){
		if (reLoadLayout == null) 
			superReLoadLayout(reLoadText, onClickListener);
		try {
			reLoadLayout.setTag(view);
			view.setVisibility(View.GONE);
			reLoadLayout.setVisibility(View.VISIBLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * hide重新加载按钮
	 */
	public void hideReLoadLayout(){
		if (reLoadLayout != null) {
			((View)reLoadLayout.getTag()).setVisibility(View.VISIBLE);
			reLoadLayout.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 向布局中add重新加载layout
	 * 
	 * @param onClickListener 重新加载按钮的监听事件
	 */
	private void superReLoadLayout(String reLoadText, OnClickListener onClickListener){		
		RelativeLayout layout = new RelativeLayout(this);
		reLoadLayout = LayoutInflater.from(this).inflate(R.layout.item_reload, null);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2, -2);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		layout.addView(reLoadLayout, params);
		
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
		addContentView(layout, layoutParams);
		reLoadLayout.setVisibility(View.GONE);
		Button btn = (Button) reLoadLayout.findViewById(R.id.btn_reload);
		if (!TextUtils.isEmpty(reLoadText)) 
			btn.setText(reLoadText);
		btn.setOnClickListener(onClickListener);
	}
	
}
