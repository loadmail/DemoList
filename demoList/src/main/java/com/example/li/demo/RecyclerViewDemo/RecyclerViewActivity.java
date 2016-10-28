package com.example.li.demo.RecyclerViewDemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.li.demo.R;

public class RecyclerViewActivity extends AppCompatActivity {
    //顶部删除按钮
    private Button mDeleteBar;
    //添加item按钮，这里也是使用Design库里面的一个新控件，就当做一个按钮使用。只是样式很好看
    private FloatingActionButton mAddBtn;
    //recyclerView
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mDeleteBar = (Button) findViewById(R.id.delete_btn);
        mAddBtn = (FloatingActionButton) findViewById(R.id.add_item);


        //不能简单创建内部类,因为onClick还要用到
        //创建LinearLayoutManager
      //  final GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //为RecyclerView指定布局管理器对象
        mRecyclerView.setLayoutManager(layoutManager);
        //创建列表项分割线对象
        final RecyclerView.ItemDecoration itemDecoration = new SampleDivider(this);
        //RecyclerView控件指定分割线对象
        mRecyclerView.addItemDecoration(itemDecoration);
        //创建SampleRecyclerAdapter
        final SampleRecycleAdapter sampleRecycleAdapter = new SampleRecycleAdapter();
        //为RecyclerView控件指定Adapter
        mRecyclerView.setAdapter(sampleRecycleAdapter);
        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        //为右下角按钮添加点击事件
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取第一个可视的列表项的位置，再加上2个位置，为了演示方便
                int positionToAdd = layoutManager.findFirstCompletelyVisibleItemPosition() + 2;
                //在该位置的后面插入新的列表项
                sampleRecycleAdapter.addItem(positionToAdd);
            }
        });

        //为顶部的删除面板添加点击事件
        mDeleteBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("执行", "--onclick--:"+"执行");
                //获取第一个可视的列表项的位置，再加上2个位置，为了演示方便
                int positionToRemove = layoutManager.findFirstCompletelyVisibleItemPosition() + 2;
                //删除第一个可视的列表项
                sampleRecycleAdapter.removeData(positionToRemove);
                //删除完成后隐藏删除面板
                hideDeleteBar();
            }
        });

        //为RecyclerView控件设置滚动事件
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //滚动状态变化事件的方法
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            //滚动事件方法（判断上下或者左右滚动）
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //如果是垂直显示的列表， dy>0 表示向上滚动，否则表示向下滚动
                //如果是水平显示的列表，dx > 0 表示向右滚动，否则表示向左滚动
                if (dy > 0) {
                    //向上滚动时，隐藏删除面板，
                    if (mDeleteBar.getVisibility() == View.VISIBLE) {
                        hideDeleteBar();
                    }
                } else {
                    //向下滚动时，显示显示面板
                    if (mDeleteBar.getVisibility() == View.GONE) {
                        showDeleteBar();
                    }
                }
            }
        });
    }




    /**
     * 显示删除栏，使用动画效果
     */
    private void showDeleteBar() {
        mDeleteBar.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
        mDeleteBar.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏删除栏，使用动画效果
     */
    private void hideDeleteBar() {
        mDeleteBar.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
        mDeleteBar.setVisibility(View.GONE);
    }
}
