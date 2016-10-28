package com.example.li.demo.view.toutiao;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import java.util.List;

/**
 * 使用   mTopView.setData(dataList);
 *
 * @param <T>
 */
public abstract class ScrollTopView<T> extends LinearLayout {

    private Scroller mScroller;  //滚动实例

    private List<T> dataList;  //存放数据集合
    private final int DURING_TIME = 3000;  //滚动延迟
    protected OnAdapterClickListener<T> click;
    private Context context;
    private final int distance = 50; // TODO: 2016/10/19 调节高度

    public ScrollTopView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ScrollTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
    }

    /**
     * 设置数据
     *
     * @param articleList
     */
    public void setData(List<T> articleList) {
        this.dataList = articleList;
        if (articleList != null) {
            removeAllViews();
            int size = articleList.size(); //> 1 ? 4 : dataList.size();
            for (int i = 0; i < size; i++) {
                addContentView(i);
            }
            if (articleList.size() > 1) {
                getLayoutParams().height = dip2px(context, distance);  //调节滚动数据的高度
                // 滚动
                cancelAuto();
                mHandler.sendEmptyMessageDelayed(0, DURING_TIME);
                // TODO: 2016/10/19 加入一个时间插值器,平滑滚动
                if (mScroller.computeScrollOffset()) {

                }

                smoothScrollBy(0, dip2px(context, distance));
            }
        }
    }

    /**
     * 设置列表点击事件
     *
     * @param click
     */
    public void setClickListener(OnAdapterClickListener<T> click) {
        this.click = click;
    }

    /**
     * 重置数据
     */
    private void resetView() {
        T article = dataList.get(0);
        dataList.remove(0);
        dataList.add(article);

        for (int i = 0; i < dataList.size(); i++) {
            addContentView(i);
        }
    }

    /**
     * 取消滚动
     */
    public void cancelAuto() {
        mHandler.removeMessages(0);
    }

    private void addContentView(int position) {
        ViewHolder mHolder;
        if (position >= getChildCount()) {
            mHolder = new ViewHolder();
            // TODO: 2016/10/19 设置布局 以及 viewId
            View v = View.inflate(getContext(), android.R.layout.simple_expandable_list_item_1, null);
            mHolder.nameTv = (TextView) v.findViewById(android.R.id.text1);
            v.setTag(mHolder);
            addView(v, LayoutParams.MATCH_PARENT, dip2px(context, distance));
        } else {
            mHolder = (ViewHolder) getChildAt(position).getTag();
        }
        final T t = dataList.get(position);
        setNext(mHolder, t);
    }

    protected abstract void setNext(ViewHolder vh, T t);

    // TODO: 2016/10/20 无法抽象ViewHolder
    public class ViewHolder {
        public TextView nameTv;
    }

    Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mHandler.removeMessages(0);
            mHandler.sendEmptyMessageDelayed(0, DURING_TIME);
            smoothScrollBy(0, dip2px(context, distance));
            resetView();
        }
    };

    // 调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy) {
        // 设置mScroller的滚动偏移量
        // TODO: 2016/10/19 这里有bug y轴的偏移量是多少,才能是从底部平滑滚动到顶部?
        mScroller.startScroll(mScroller.getFinalX(), -dy, dx, 2 * dy, DURING_TIME);
        invalidate();// 这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void computeScroll() {

        // 先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {

            // 这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            // 必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();

        }
        super.computeScroll();
    }

    @Override
    protected void onDetachedFromWindow() {
        mHandler.removeCallbacksAndMessages(this);
        super.onDetachedFromWindow();
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        //return (int) (pxValue / scale + 0.5f);//todo px2dip
        return (int) (dpValue * scale + 0.5f);
    }


    public interface OnAdapterClickListener<T> {
        void onAdapterClick(View v, T t);
    }

}
