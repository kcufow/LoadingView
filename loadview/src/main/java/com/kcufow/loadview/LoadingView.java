package com.kcufow.loadview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by ldw on 2018/2/27.
 */

public abstract class LoadingView extends FrameLayout{

    public static final int STATE_UNKNOWN = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_ERROR = 2;
    public static final int STATE_EMPTY = 3;
    public static final int STATE_SUCCESS = 4;
    private int status = STATE_LOADING;

    private View loadingView;                 // 加载中的界面
    private View errorView;                   // 错误界面
    private View emptyView;                   // 空界面
    private View contentView;                 // 加载成功的界面
    private Context mContext;
    private GifImageView gifImageView;

    public LoadingView(Context context) {
        this(context,null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        init();

    }

    private void init() {

        this.setBackgroundResource(R.color.colorPageBg);

        if (loadingView == null) {
            loadingView = creatLoadingView();
            this.addView(loadingView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        }
        if (errorView == null) {
            errorView=creatErrorView();
            this.addView(errorView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        }
        if (emptyView == null) {
            emptyView = creatEmptyView();
            this.addView(emptyView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        }


        if (contentView==null){

            if (status == STATE_SUCCESS){
                contentView = creatContentView();
                this.addView(contentView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
                initView(contentView);
            }

        }
        showStateView();

    }

    protected abstract void initView(View contentView);

    private View creatContentView() {
        Object  o= getLayoutRes();
        View view;
        if (o instanceof Integer){
            view= LayoutInflater.from(mContext).inflate((int)o,null);
        }else if (o instanceof View){
            view = (View) o;
        }else {
            throw new RuntimeException("the type of viewRes error!!!");
        }


        return view;
    }

    protected abstract Object getLayoutRes();

    private View creatEmptyView() {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_empty,null);
        return view;
    }

    private View creatErrorView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_error,null);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.ll_error_refresh);
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                status = STATE_LOADING;
                showStateView();
                loadData();
            }
        });

        return view;
    }

    public void showStateView() {
        switch (status){
            case STATE_EMPTY:
                showView(emptyView);
                break;
            case STATE_ERROR:
                showView(errorView);
                break;
            case STATE_LOADING:
                showView(loadingView);
                break;
            case STATE_SUCCESS:
                showView(contentView);
                break;
            default:
                showView(errorView);
                break;

        }

    }

    private void showView(View target) {
        int count = this.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = this.getChildAt(i);
         int visibility = view==target?VISIBLE:GONE;
            view.setVisibility(visibility);
        }
    }

    protected abstract void loadData();

    private View creatLoadingView() {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_loading, null);
        gifImageView = (GifImageView) view.findViewById(R.id.gif_view);
        gifImageView.setImageResource(R.drawable.num1);
        return view;
    }

    public void setLoadingImageResource(int resId){
        if (gifImageView != null) {
            gifImageView.setImageResource(resId);
        }
    }

    public void setStatus(int status) {
        this.status = status;
        showStateView();
    }
}
