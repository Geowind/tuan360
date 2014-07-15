package com.folyd.tuan.ui;

import com.folyd.tuan.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.activity_webview);

		mWebView = (WebView) findViewById(R.id.webview);
		mWebView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				setTitle("Loading...");
				setProgress(newProgress * 100);
				if (newProgress == 100) {
					setTitle(mWebView.getTitle());
				}
			}

		});
		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				System.out.println("url:   "+url);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}

		});
		WebSettings settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setDomStorageEnabled(true);
		settings.setBuiltInZoomControls(true);
		// settings.setSupportZoom(true);
		mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
		settings.setUserAgentString("Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.2.149.27 Safari/525.13");
		String detail_url = getIntent().getStringExtra("detail_url");
		mWebView.loadUrl("http://www.baidu.com");
	}

	@Override
	public void onBackPressed() {
		if (mWebView != null && mWebView.canGoBack()) {
			mWebView.goBack();
		} else {
			super.onBackPressed();
		}
	}
}
