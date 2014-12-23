package az.mxl.lib.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import az.mxl.lib.R;
import az.mxl.lib.base.AppController;

public class ViewUtils4AZ {

	/**
	 * 获取DialogBuilder，点击屏幕其他位置dialog不会消失
	 * 
	 * @param activity
	 * @param msg
	 *            Message
	 * @return
	 */
	public static Builder getDialogBuilder(Activity activity, String msg) {
		return new AlertDialog.Builder(activity).setCancelable(false).setMessage(msg);
	}

	private static Toast toast = null;

	/**
	 * 显示Toast--重复执行此方法，不会一直显示Toast
	 * 
	 * @param context
	 * @param msg
	 * @see showTextToast(context,msg);
	 */
	public static void showTextToast(CharSequence msg) {
		if (toast == null) {
			toast = Toast.makeText(AppController.getInstance().getApplicationContext(), msg, Toast.LENGTH_SHORT);
		} else {
			toast.setText(msg);
		}
		toast.show();
	}

	public static void showLongToast(String msg) {
		Toast.makeText(AppController.getInstance().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}

	public static void showShortToast(String msg) {
		Toast.makeText(AppController.getInstance().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 从view 得到图片
	 * 
	 * @param view
	 * @return
	 */
	public static Bitmap getBitmapFromView(View view) { // baiduMap Utils
		view.destroyDrawingCache();
		view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
				View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = view.getDrawingCache(true);
		return bitmap;
	}
	
	public static Dialog createLoadingDialog2(Context context, String msg){
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog2, null);// 得到加载view
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		if (TextUtils.isEmpty(msg)) {
			tipTextView.setVisibility(View.GONE);
		} else {
			tipTextView.setText(msg);// 设置加载信息
			tipTextView.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.loading_dialog_text_size));
		}

		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
		loadingDialog.setCancelable(true);// 可用“返回键”取消
		loadingDialog.setCanceledOnTouchOutside(false);// 不可点击dialog外部取消
		loadingDialog.setContentView(v);
		return loadingDialog;
	}
	
	
	/**
	 * 得到自定义的progressDialog
	 * 
	 * @param context
	 * @param msg
	 * @return
	 */
	public static Dialog createLoadingDialog(Context context, String msg) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
		RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.dialog_view);// 加载布局
		final ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		if (TextUtils.isEmpty(msg)) {
			tipTextView.setVisibility(View.GONE);
		} else {
			tipTextView.setText(msg);// 设置加载信息
		}

		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
		loadingDialog.setCancelable(true);// 可用“返回键”取消
		loadingDialog.setCanceledOnTouchOutside(false);// 不可点击dialog外部取消
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		loadingDialog.setContentView(layout, params);// 设置布局
		
		// 加载动画
		final Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
		// dialog显示时，ImageView显示动画
		loadingDialog.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				spaceshipImage.startAnimation(hyperspaceJumpAnimation);
			}
		});
		// dialog消失时，ImageView停止动画
		loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				spaceshipImage.clearAnimation();
			}
		});
		return loadingDialog;
	}

}
