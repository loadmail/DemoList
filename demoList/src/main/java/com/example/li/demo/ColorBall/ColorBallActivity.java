package com.example.li.demo.ColorBall;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.li.demo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ColorBallActivity extends AppCompatActivity implements View.OnClickListener{

    private Context context;

    private Button randomRed;
    private Button randomBlue;
    private TextView change;
    // 选号容器
    private PopGridView redContainer;
    private GridView blueContainer;

    private PoolAdapter redAdapter;
    private PoolAdapter blueAdapter;

    private List<Integer> redNums;
    private List<Integer> blueNums;

    private SensorManager manager;
    private ShakeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_ball);

        context = this;

        redContainer = (PopGridView) findViewById(R.id.ii_ssq_red_number_container);
        blueContainer = (GridView) findViewById(R.id.ii_ssq_blue_number_container);
        randomRed = (Button) findViewById(R.id.ii_ssq_random_red);
        randomBlue = (Button) findViewById(R.id.ii_ssq_random_blue);
        change = (TextView) findViewById(R.id.change);

        redNums = new ArrayList<Integer>();
        blueNums = new ArrayList<Integer>();

        redAdapter = new PoolAdapter(context, 33, redNums, R.drawable.id_redball);
        blueAdapter = new PoolAdapter(context, 16, blueNums, R.drawable.id_blueball);

        redContainer.setAdapter(redAdapter);
        blueContainer.setAdapter(blueAdapter);

        manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        setListener();
    }

    public void setListener() {
        randomRed.setOnClickListener(this);
        randomBlue.setOnClickListener(this);
        redContainer.setOnActionUpListener(new PopGridView.OnActionUpListener() {

            @Override
            public void onActionUp(View view, int position) {
                // 判断当前点击的item是否被选中了
                if (!redNums.contains(position + 1)) {
                    // 如果没有被选中
                    // 背景图片切换到红色
                    view.setBackgroundResource(R.drawable.id_redball);
                    redNums.add(position + 1);
                } else {
                    // 被选中
                    // 还原背景图片
                    view.setBackgroundResource(R.drawable.id_defalut_ball);
                    redNums.remove((Object) (position + 1));
                }

                changeNotice();
            }
        });

		/*
		 * redContainer.setOnItemClickListener(new OnItemClickListener() {
		 *
		 * @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // 判断当前点击的item是否被选中了 if (!redNums.contains(position + 1)) { // 如果没有被选中 //
		 * 背景图片切换到红色 view.setBackgroundResource(R.drawable.id_redball); // 摇晃的动画 view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.ia_ball_shake));
		 * redNums.add(position + 1); } else { // 被选中 // 还原背景图片 view.setBackgroundResource(R.drawable.id_defalut_ball); redNums.remove((Object) (position + 1)); }
		 *
		 * } });
		 */

        blueContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 判断当前点击的item是否被选中了
                if (!blueNums.contains(position + 1)) {
                    // 如果没有被选中
                    // 背景图片切换到红色
                    view.setBackgroundResource(R.drawable.id_blueball);
                    // 摇晃的动画
                    view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.ia_ball_shake));
                    blueNums.add(position + 1);
                } else {
                    // 被选中
                    // 还原背景图片
                    view.setBackgroundResource(R.drawable.id_defalut_ball);
                    blueNums.remove((Object) (position + 1));
                }
                changeNotice();
            }
        });

    }

    @Override
    public void onClick(View v) {
        Random random = new Random();
        switch (v.getId()) {
            case R.id.ii_ssq_random_red:
                // 机选红球
                redNums.clear();
                while (redNums.size() < 6) {
                    int num = random.nextInt(33) + 1;

                    if (redNums.contains(num)) {
                        continue;
                    }
                    redNums.add(num);
                }

                // 处理展示
                redAdapter.notifyDataSetChanged();
                changeNotice();
                break;
            case R.id.ii_ssq_random_blue:
                blueNums.clear();
                int num = random.nextInt(16) + 1;
                blueNums.add(num);

                blueAdapter.notifyDataSetChanged();
                changeNotice();
                break;
        }

    }

    /**
     * 改变底部导航的提示信息
     */
    private void changeNotice() {
        String notice = "";
        // 以一注为分割
        if (redNums.size() < 6) {
            notice = "您还需要选择" + (6 - redNums.size()) + "个红球";
        } else if (blueNums.size() == 0) {
            notice = "您还需要选择" + 1 + "个蓝球";
        } else {
            notice = "共 " + calc() + " 注 " + calc() * 2 + " 元";
        }
        change.setText(notice);
        Log.e("执行", "notice: "+notice);
    }

    /**
     * 计算注数
     *
     * @return
     */
    private int calc() {
        int redC = (int) (factorial(redNums.size()) / (factorial(6) * factorial(redNums.size() - 6)));
        int blueC = blueNums.size();
        return redC * blueC;
    }

    /**
     * 计算一个数的阶乘
     *
     * @param num
     * @return
     */
    private long factorial(int num) {
        // num=7 7*6*5...*1

        if (num > 1) {
            return num * factorial(num - 1);
        } else if (num == 1 || num == 0) {
            return 1;
        } else {
            throw new IllegalArgumentException("num >= 0");
        }
    }

}

