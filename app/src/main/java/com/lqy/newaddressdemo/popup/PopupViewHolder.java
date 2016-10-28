package com.lqy.newaddressdemo.popup;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lqy.newaddressdemo.MyApplication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * 操作PopupWindow视图的实现类
 * Created by LQY on 2016/10/19.
 */
public class PopupViewHolder {
	/**
	 * PopupWindow View
	 */
	View mPopupView;

	public PopupViewHolder(View view){
		mPopupView = view;
	}

	/**
	 * 通过泛型获取findViewById资源文件
	 */
	public final <T extends View> T getView(int viewId){
		return (T) mPopupView.findViewById(viewId);
	}

	public void setText(int viewId , int stringId){
		TextView textView = getView(viewId);
		textView.setText(stringId);
	}

	public void setText(int viewId , String text){
		TextView textView = getView(viewId);
		textView.setText(text);
	}

	public void setBackgroundColor(int viewId , int color){
		View target = getView(viewId);
		target.setBackgroundColor(color);
	}

	public void setBackgroundResource(int viewId , int resId){
		View target = getView(viewId);
		target.setBackgroundResource(resId);
	}

	public void setBackgroundDrawable(int viewId , Drawable drawable){
		View target = getView(viewId);
		target.setBackgroundDrawable(drawable);
	}

	@TargetApi(16)
	public void setBackground(int viewId, Drawable drawable){
		View target = getView(viewId);
		target.setBackground(drawable);
	}

	public void setImageBitmap(int viewId, Bitmap bitmap){
		ImageView target = getView(viewId);
		target.setImageBitmap(bitmap);
	}

	public void setImageResource(int viewId, int resId){
		ImageView target = getView(viewId);
		target.setImageResource(resId);
	}

	public void setImageDrawable(int viewId, Drawable drawable){
		ImageView target = getView(viewId);
		target.setImageDrawable(drawable);
	}

	public void setImageURI(int viewId, Uri uri){
		ImageView target = getView(viewId);
		target.setImageURI(uri);
	}

	public void setImageDrawableAsyncWithOption(int viewId, String uri, Context context, DisplayImageOptions options){
		ImageView target = getView(viewId);
		MyApplication.imageLoader.displayImage(uri, target, options);
	}

	public void setImageDrawableAsync(int viewId,String uri,Context context){
		ImageView target = getView(viewId);
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheOnDisk(true)
				.cacheInMemory(true)
				.build();
		MyApplication.imageLoader.displayImage(uri,target,defaultOptions);
	}

	@TargetApi(16)
	public void setImageAlpha(int viewId, int alpha){
		ImageView target = getView(viewId);
		target.setImageAlpha(alpha);
	}

	public void setChecked(int viewId, boolean checked){
		Checkable checkable = getView(viewId);
		checkable.setChecked(checked);
	}

	public void setProgress(int viewId, int progress){
		ProgressBar view = getView(viewId);
		view.setProgress(progress);
	}

	public void setProgress(int viewId, int progress, int max){
		ProgressBar view = getView(viewId);
		view.setMax(max);
		view.setProgress(progress);
	}

	public void setMax(int viewId, int max){
		ProgressBar view = getView(viewId);
		view.setMax(max);
	}

	public void setRating(int viewId,float rating){
		RatingBar view = getView(viewId);
		view.setRating(rating);
	}

	public void setRating(int viewId, float rating, int max){
		RatingBar view = getView(viewId);
		view.setMax(max);
		view.setRating(rating);
	}

	public void getText(int viewId){
		EditText view = getView(viewId);
		view.getText().toString();
	}

	public void setOnClickListener(int viewId , View.OnClickListener listener){
		View view = getView(viewId);
		view.setOnClickListener(listener);
	}

	public void setOnTouchListener(int viewId, View.OnTouchListener listener){
		View view = getView(viewId);
		view.setOnTouchListener(listener);
	}

	public void setOnLongClickListener(int viewId, View.OnLongClickListener listener){
		View view = getView(viewId);
		view.setOnLongClickListener(listener);
	}

	public void setOnItemClickListener(int viewId, AdapterView.OnItemClickListener listener){
		AdapterView view = getView(viewId);
		view.setOnItemClickListener(listener);
	}

	public void setArrayAdapter(int viewId, ArrayAdapter adapter){
		ListView view = getView(viewId);
		view.setAdapter(adapter);
	}

	public void setBaseAdapter(int viewId, BaseAdapter adapter){
		ListView view = getView(viewId);
		view.setAdapter(adapter);
	}
}
