package cn.dkm.gamehelper.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2018/1/18.
 */

public class MyScrollView extends ScrollView {


    private int downX;
    private int downY;

    private int mTouchSlop;
    private int d;


    private  OnScrollListener onScrollListener;

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public MyScrollView(Context context) {
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attributes) {
        this(context, attributes, 0);
    }

    public MyScrollView(Context context, AttributeSet attributes, int defStyle) {
        super(context, attributes, defStyle);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

        int action = e.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    if (d == 0){
                        return super.onInterceptTouchEvent(e);
                    }else {
                        return true;
                    }
                }
        }


        return super.onInterceptTouchEvent(e);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        View view = (View) getChildAt(getChildCount() - 1);
//        d = view.getBottom();//成功解决
        d -= (getHeight() + getScrollY());
       /* if (d == 0) {
            //you are at the end of the list in scrollview
            //do what you wanna do here
        } else{

        }*/
        super.onScrollChanged(l, t, oldl, oldt);

        if (onScrollListener != null) {
            onScrollListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    public interface OnScrollListener{
        void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy);
    }
}
