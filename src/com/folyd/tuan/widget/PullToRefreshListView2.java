package com.folyd.tuan.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 自定义的下拉刷新ListView
 * @author Folyd
 *
 */
public class PullToRefreshListView2 extends ListView implements OnScrollListener {
    private TextView state;
    private TextView updateTime;
    private ImageView arrowImage;
    
    
    private final String TAG = "PullToRefreshListView";
	public PullToRefreshListView2(Context context) {
		super(context);
	}

	public PullToRefreshListView2(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

	}

}
