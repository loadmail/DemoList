package com.example.li.demo.view.threeDView;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ly on 2016/10/17 11:03.
 */

public class ThreeDView extends View {

    private int mCenterX;
    private int mCenterY;
    private float mCanvasRotateX = 0;
    private float mCanvasRotateY = 0;
    private float mCanvasMaxRotateDegree = 20;
    private Matrix mMatrix = new Matrix();
    private Camera mCamera = new Camera();
    private Paint mPaint;


    public ThreeDView(Context context) {
        this(context, null);
    }

    public ThreeDView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mPaint = new Paint();
        mCanvasMaxRotateDegree = 20; // TODO: 2016/10/17 改变值可以出现不同效果
    }

    public ThreeDView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCenterX = getWidth() / 2;
        mCenterY = getHeight() / 2;
        // TODO: 2016/10/17 关键方法
        rotateCanvas(canvas);
         canvas.drawCircle(mCenterX, mCenterY, 200, mPaint); // TODO: 2016/10/17 画圆
    }

    /**
     修改矩阵值
     * @param canvas
     */
    private void rotateCanvas(Canvas canvas) {
        mMatrix.reset();
        mCamera.save(); // TODO: 2016/10/17 Camera是用矩阵变换3d的类
        mCamera.rotateX(mCanvasRotateX);
        mCamera.rotateY(mCanvasRotateY);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();
        //改变矩阵作用点
        mMatrix.preTranslate(-mCenterX, -mCenterY);
        mMatrix.postTranslate(mCenterX, mCenterY);

        canvas.concat(mMatrix);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                rotateCanvasWhenMove(x, y);  // TODO: 2016/10/17 百分比思想
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                rotateCanvasWhenMove(x, y);
                invalidate();
                return true;
            }
            case MotionEvent.ACTION_UP: {
                mCanvasRotateY = 0;
                mCanvasRotateX = 0;
                invalidate();

                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     百分比思想修改值
     * @param x
     * @param y
     */
    private void rotateCanvasWhenMove(float x, float y) {
        float dx = x - mCenterX;
        float dy = y - mCenterY;

        float percentX = dx / mCenterX;
        float percentY = dy / mCenterY;

        if (percentX > 1f) {
            percentX = 1f;
        } else if (percentX < -1f) {
            percentX = -1f;
        }
        if (percentY > 1f) {
            percentY = 1f;
        } else if (percentY < -1f) {
            percentY = -1f;
        }

        mCanvasRotateY = mCanvasMaxRotateDegree * percentX;
        mCanvasRotateX = -(mCanvasMaxRotateDegree * percentY);
    }


}
