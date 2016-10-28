package com.lqy.newaddressdemo.popup;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * PopupWindow弹出对话框抽象类
 * Created by LQY on 2016/10/19.
 */
public abstract class CommonPopupWindow extends PopupWindow {

	private Activity mActivity;
	/**
	 * popup Layout
	 */
	private View mPopupView;

	private int mPopupLayoutId;
	/**
	 * PopupView实现类
	 */
	PopupViewHolder popupView;

	public CommonPopupWindow(Activity activity, int LayoutId){
		mActivity = activity;
		mPopupLayoutId = LayoutId;
	}

	/**
	 * 绑定布局资源
	 */
	private void getPopupView(){
		mPopupView = LayoutInflater.from(mActivity).inflate(mPopupLayoutId,null);
		popupView = new PopupViewHolder(mPopupView);
		showWindow(popupView);
		setContentView(mPopupView);
	}

	/**
	 *弹出对话框
	 */
	public void showPopup(){
		getPopupView();
		setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
		//设置背景半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		setBackgroundDrawable(dw);
		setFocusable(true);
		setOutsideTouchable(true);
		showAtLocation(mPopupView, Gravity.CENTER_VERTICAL, 0, 0);
	}

	/**
	 * 弹出下拉列表
	 */
	public void listPopup(View view){
		getPopupView();
		setWidth(view.getWidth() + 5);
		setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(Color.WHITE);
        setBackgroundDrawable(dw);
		setOutsideTouchable(true);
		setFocusable(true);
		showAsDropDown(view);
	}

	/**
	 * 弹出对话框以后
	 */
	protected abstract void showWindow(PopupViewHolder view);

}
