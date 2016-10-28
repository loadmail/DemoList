package com.example.li.demo.RecyclerViewDemo.CardLayoutManager;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**地址:http://blog.csdn.net/qibin0506/article/details/52676670
 * Created by ly on 2016/10/19 10:06.
 */

public class CardLayoutManager extends RecyclerView.LayoutManager {
    public static final int DEFAULT_GROUP_SIZE = 5;

    // TODO: 2016/10/19 构造的三个参数
    private int mGroupSize;
    private boolean isGravityCenter;
    private Pool<Rect> mItemFrames;

    private int mTotalWidth;
    private int mTotalHeight;

    private int mHorizontalOffset;
    private int mVerticalOffset;

    private int mGravityOffset;

    public CardLayoutManager(boolean center) {
        this(DEFAULT_GROUP_SIZE, center);
    }


    public CardLayoutManager(int groupSize, boolean center) {
        // TODO: 2016/10/19  2  在开始布局之前, 还有几个参数需要我们从构造传递,
        //TODO  一个是每组需要显示几个, 一个当每组的总宽度小于RecyclerView总宽度的时候是否要居中显示

        mGroupSize = groupSize;
        isGravityCenter = center;
        // TODO: 2016/10/19 这块代码怎么用的,不懂
        mItemFrames = new Pool<>(new Pool.New<Rect>() {
            @Override
            public Rect get() {
                return new Rect();
            }
        });
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        // TODO: 2016/10/19  1 实现抽象方法
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        // TODO: 2016/10/19   3  着手准备进行item的布局操作了,
        // todo 在RecyclerView.LayoutManager中布局的入口是一个叫onLayoutChildren的方法.重写
        if (getItemCount() <= 0 || state.isPreLayout()) {
            return;
        }
// TODO: 2016/10/19  detachAndScrapAttachedViews 作用是将界面上的所有item都detach掉,
        // TODO:    并缓存在scrap中,以便下次直接拿出来显示.
/*首先一个detachAndScrapAttachedViews方法,
这个方法是RecyclerView.LayoutManager的,
它的作用是将界面上的所有item都detach掉,
并缓存在scrap中,以便下次直接拿出来显示. */
        detachAndScrapAttachedViews(recycler);

        // TODO: 2016/10/19 代码来获取第一个item view并测量它.

        /*为什么只测量第一个view呢?
        这里是因为在我们的这个效果中所有的item大小都是一样的,
         所以我们只要获取第一个的大小, 就知道所有的item的大小了.

          Decorated   decoration 装饰
         方法getDecoratedMeasuredWidth, 这个方法是什么意思?
         其实类似的还有很多, 例如getDecoratedMeasuredHeight, getDecoratedLeft…
         这个getDecoratedXXX的作用就是获取该view以及他的decoration的值,
         大家都知道RecyclerView是可以设置decoration的.
         */
        View first = recycler.getViewForPosition(0);
        measureChildWithMargins(first, 0, 0);
        int itemWidth = getDecoratedMeasuredWidth(first);
        int itemHeight = getDecoratedMeasuredHeight(first);

        // TODO: 2016/10/19 获取每一组中第一行和第二行中item的个数. 
        int firstLineSize = mGroupSize / 2 + 1;
        int secondLineSize = firstLineSize + mGroupSize / 2;

        // TODO: 2016/10/19 当设置了isGravityCenter为true, 并且每组的宽度小于recyclerView的宽度时居中显示.  
        if (isGravityCenter && firstLineSize * itemWidth < getHorizontalSpace()) {
            mGravityOffset = (getHorizontalSpace() - firstLineSize * itemWidth) / 2;
        } else {
            mGravityOffset = 0;
        }

/*
接下来的一个if...else...
在if中的是判断当前item是否在它所在组的第一行.
 为什么要加这个判断?
 大家看效果就知道了, 因为第二行的view的起始会有一个二分之一的item宽度的偏移,
 而且相对于第一行, 第二行的高度是偏移了二分之一的item高度.
 至于这里面具体的逻辑大家可以对照着效果图去看代码,
  这里就不一一解释了. */
        for (int i = 0; i < getItemCount(); i++) {
            Rect item = mItemFrames.get(i);
            float coefficient = isFirstGroup(i) ? 1.5f : 1f;  // TODO: 2016/10/19  coefficient 系数 组之间的间隔默认是1
            int offsetHeight = (int) ((i / mGroupSize) * itemHeight * coefficient); // TODO: 2016/10/19 组之间距离

            // 每一组的第一行
            if (isItemInFirstLine(i)) {
                int offsetInLine = i < firstLineSize ? i : i % mGroupSize;
                // TODO: 2016/10/19  item.set(l,t,r,b)  记录item的位置
                item.set(mGravityOffset + offsetInLine * itemWidth, offsetHeight, mGravityOffset + offsetInLine * itemWidth + itemWidth,
                        itemHeight + offsetHeight);
            } else {
                int lineOffset = itemHeight / 2;
                int offsetInLine = (i < secondLineSize ? i : i % mGroupSize) - firstLineSize;
                item.set(mGravityOffset + offsetInLine * itemWidth + itemWidth / 2,
                        offsetHeight + lineOffset, mGravityOffset + offsetInLine * itemWidth + itemWidth + itemWidth / 2,
                        itemHeight + offsetHeight + lineOffset);
            }
        }
// TODO: 2016/10/19 记录了item的总宽度和总高度 
        mTotalWidth = Math.max(firstLineSize * itemWidth, getHorizontalSpace());
        int totalHeight = getGroupSize() * itemHeight;
        if (!isItemInFirstLine(getItemCount() - 1)) {
            totalHeight += itemHeight / 2;
        }
        mTotalHeight = Math.max(totalHeight, getVerticalSpace());

        // TODO: 2016/10/19 onLayoutChildren方法中我们仅仅记录了所有的item view所在的位置, 并没有真正的去layout它
        // todo 真正的layout肯定是在这个fill方法中了
        fill(recycler, state);
    }

    private boolean isFirstGroup(int index) {
        return index < mGroupSize;
    }

    private boolean isItemInFirstLine(int index) {
        int firstLineSize = mGroupSize / 2 + 1;
        return index < firstLineSize || (index >= mGroupSize && index % mGroupSize < firstLineSize);
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    private int getGroupSize() {
        return (int) Math.ceil(getItemCount() / (float) mGroupSize);
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0 || state.isPreLayout()) {
            return;
        }
        // TODO: 2016/10/19 首先定义了一个displayRect, 他的作用就是标记当前显示的区域 
        // TODO: 2016/10/19 因为RecyclerView是可滑动的, 所以这个区域不能简单的是0~高度/宽度这么一个值, 我们还要加上当前滑动的偏移量.  
        Rect displayRect = new Rect(mHorizontalOffset, mVerticalOffset,
                getHorizontalSpace() + mHorizontalOffset,
                getVerticalSpace() + mVerticalOffset);

// TODO: 2016/10/19  我们通过getChildCount获取RecyclerView中的所有子view,
// todo: 并且依次判断这些view是否在当前显示范围内, 如果不再, 我们就通过removeAndRecycleView将它移除并回收掉

      /* recycle的作用是回收一个view, 并等待下次使用, 这里可能会被重新绑定新的数据.
       而scrap的作用是缓存一个view, 并等待下次显示, 这里的view会被直接显示出来. */
        for (int i = 0; i < getItemCount(); i++) {

   /*循环getItemCount, 也就是所有的item个数,
    判断它是不是在显示区域,
    如果在, 则我们通过recycler.getViewForPosition(i)拿到这个view,
     并且通过addView添加到RecyclerView中,
     添加进去了还没完,
   我们还需要调用measureChildWithMargins方法对这个view进行测量.
    最后的最后我们调用layoutDecorated对item view进行layout操作.*/
            Rect frame = mItemFrames.get(i);
            if (Rect.intersects(displayRect, frame)) {  //判断区域
                View scrap = recycler.getViewForPosition(i);  //拿到view
                addView(scrap);     //添加
                measureChildWithMargins(scrap, 0, 0);  //测量此view
                //对item view放置
                layoutDecorated(scrap, frame.left - mHorizontalOffset, frame.top - mVerticalOffset,
                        frame.right - mHorizontalOffset, frame.bottom - mVerticalOffset);
            }
        }
    }

    // TODO: 2016/10/19 让RecyclerView能滑动, 我们需要重写几个方法
    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
// TODO: 2016/10/19
        //1 首先我们还是先调用detachAndScrapAttachedViews将所有的子view缓存起来
        detachAndScrapAttachedViews(recycler);
        //2 判断是做边界检测, 接着
        if (mVerticalOffset + dy < 0) {
            dy = -mVerticalOffset; //取反  dy在我们手指往左滑动的时候是正值
        } else if (mVerticalOffset + dy > mTotalHeight - getVerticalSpace()) {
            dy = mTotalHeight - getVerticalSpace() - mVerticalOffset;
        }
//3 我们调用offsetChildrenVertical来做偏移
        offsetChildrenVertical(-dy);
        // 4 调用fill方法来做新的子view的布局, 最后我们记录偏移量并返回.
        fill(recycler, state);
        mVerticalOffset += dy;
        return dy;
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        if (mHorizontalOffset + dx < 0) {
            dx = -mHorizontalOffset;
        } else if (mHorizontalOffset + dx > mTotalWidth - getHorizontalSpace()) {
            dx = mTotalWidth - getHorizontalSpace() - mHorizontalOffset;
        }

        offsetChildrenHorizontal(-dx);
        fill(recycler, state);
        mHorizontalOffset += dx;
        return dx;
    }

}
