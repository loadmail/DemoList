package com.finals.gpsprovider.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;

/**
 * 这个软键盘控制类 不太懂
 */
// TODO: 2016/6/8 需要研究
public class InputMethodLayout implements OnGlobalLayoutListener {
	/** 初始状态 **/
	public static final byte KEYBOARD_STATE_INIT = -1;
	/** 隐藏状态 **/
	public static final byte KEYBOARD_STATE_HIDE = -2;
	/** 打开状态 **/
	public static final byte KEYBOARD_STATE_SHOW = -3;

	private onKeyboardsChangeListener keyboarddsChangeListener;// 键盘状态监听

	View decodeView;

	Activity mActivity;

	ViewTreeObserver observer;

	Rect mRect;

	public InputMethodLayout(Activity mActivity) {
		this.mActivity = mActivity;
		mRect = new Rect();// TODO: 2016/6/8 需要研究 这个mRect有什么用?
		Window mWindow = mActivity.getWindow();
		if (mWindow != null) {
			//Returns the top-level window decor view.
			decodeView = mWindow.getDecorView(); // TODO: 2016/6/8  DecorView是个什么鬼?
		}
		if (decodeView != null) {
			observer = decodeView.getViewTreeObserver();
		}
		if (observer != null) {
			observer.addOnGlobalLayoutListener(this);
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void OnDestory() {
		if (observer != null) {
			try {
				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
					observer.removeOnGlobalLayoutListener(this);
				}
			} catch (Exception e) {

			}
		}
	}

	int previousKeyboardHeight = -1;

	/**
	 * 视图树的可视性或全局布局状态发生变化时执行的回调函数。
	 */
	@Override
	public void onGlobalLayout() {
		if (decodeView != null) {
			// 先获取显示的高度
			decodeView.getWindowVisibleDisplayFrame(mRect);
			int displayHeight = mRect.bottom;//显示的高度

			// 然后获取窗体的高度
			int height = decodeView.getHeight();//窗体的高度

			// 最后得到键盘的高度
			int keyboardHeight = height - displayHeight;//键盘的高度

			if (previousKeyboardHeight != keyboardHeight) {
				boolean hide = (double) displayHeight / height > 0.8;
				if (previousKeyboardHeight == -1) {
					keyboardSateChange(KEYBOARD_STATE_INIT, keyboardHeight, displayHeight);
				} else if (hide) {
					keyboardSateChange(KEYBOARD_STATE_HIDE, keyboardHeight, displayHeight);
				} else {
					keyboardSateChange(KEYBOARD_STATE_SHOW, keyboardHeight, displayHeight);
				}
			}
			previousKeyboardHeight = height;
		}
	}

	/**
	 * 切换软键盘状态
	 * 
	 * @param state
	 * 
	 */
	public void keyboardSateChange(int state, int keyboardHeight, int displayHeight) {
		if (keyboarddsChangeListener != null) {
			keyboarddsChangeListener.onKeyBoardStateChange(state, keyboardHeight, displayHeight);
		}
	}

	/**
	 * 软键盘状态切换监听
	 * 
	 * @author zihao
	 * 
	 */
	public interface onKeyboardsChangeListener {
		public void onKeyBoardStateChange(int state, int keyboardHeight, int displayHeight);
	}

	/**
	 * 设置软键盘状态监听
	 * 
	 * @param listener
	 */
	public void setOnkeyboarddStateListener(onKeyboardsChangeListener listener) {
		keyboarddsChangeListener = listener;
	}
}
