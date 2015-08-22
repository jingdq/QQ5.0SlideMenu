package com.example.myapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.example.myapp.R;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by jingdongqi on 8/22/15.
 */
public class SlideMenu extends HorizontalScrollView {

    private LinearLayout mWapper;

    private ViewGroup mMenu;

    private ViewGroup mContent;

    private int mScreenWidth;

    private int mMenuRightPadding = 50;


    private int mMenuWidth;

    private boolean isOpen;


    public SlideMenu(Context context) {
        this(context, null);
    }

    public SlideMenu(Context context, AttributeSet attrs) {
        this(context, attrs, -1);


    }

    public SlideMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        //获取自定义属性

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlideMenu, defStyle, 0);

        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {

            int attr = a.getIndex(i);

            switch (attr) {

                case R.styleable.SlideMenu_rigthPadding:

                    mMenuRightPadding = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics()));

                    break;

            }

        }

        a.recycle();

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        mScreenWidth = outMetrics.widthPixels;

        //把dp转化为px

//        mMenuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());


    }

    private boolean once = false;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (!once) {
            mWapper = (LinearLayout) getChildAt(0);

            mMenu = (ViewGroup) mWapper.getChildAt(0);

            mContent = (ViewGroup) mWapper.getChildAt(1);

            mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
            mMenuWidth = mScreenWidth - mMenuRightPadding;

            mContent.getLayoutParams().width = mScreenWidth;
            once = true;

        }


        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }

    //通过设置偏移量将MEnu隐藏。

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {


        super.onLayout(changed, l, t, r, b);

        if (changed) {

            this.scrollTo(mMenuWidth, 0);
        }


    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {


        int action = ev.getAction();

        switch (action) {

            case MotionEvent.ACTION_UP:
//getScrollX() 隐藏在屏幕左边的宽度
                if (getScrollX() >= mMenuWidth / 2) {

                    this.smoothScrollTo(mMenuWidth, 0);
                    isOpen = false;
                } else {

                    this.smoothScrollTo(0, 0);
                    isOpen = true;
                }


                return true;

        }

        return super.onTouchEvent(ev);
    }

    /**
     *
     */
    public void openMenu() {
        if (isOpen) return;

        this.smoothScrollTo(0, 0);

        isOpen = true;

    }

    public void closeMenu() {
        if (!isOpen) return;

        this.smoothScrollTo(mMenuWidth, 0);
        isOpen = false;


    }


    public void toggle() {

        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }


    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        float scale = (float) (l * 1.0 / mMenuWidth);

        ViewHelper.setTranslationX(mMenu, scale * mMenuWidth*0.7f);

        float rightScale = 0.7f + 0.3f * scale;
        float leftScale = 1.0f - scale * 0.3f;
        float leftAlpha = 0.6f + 0.4f * (1 - scale);


        ViewHelper.setPivotX(mContent, 0);
        ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);

        ViewHelper.setScaleY(mContent, rightScale);
        ViewHelper.setScaleY(mContent, rightScale);

        ViewHelper.setScaleX(mMenu, leftScale);
        ViewHelper.setScaleY(mMenu, leftScale);

        ViewHelper.setAlpha(mMenu, leftAlpha);


    }
}

