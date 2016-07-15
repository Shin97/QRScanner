package com.ewebs.qrscanner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrowserActivity extends AppCompatActivity {

    @BindView(R.id.browser_view) WebView webview;
    @BindView(R.id.BrowserCoordinatorLayout) CoordinatorLayout coordinatorLayout;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_browser);
        ButterKnife.bind(this);

        Intent intent = this.getIntent();
        String url = intent.getStringExtra("URL");

        progressBar = ProgressDialog.show(BrowserActivity.this, "網頁載入中...", "請稍後");

        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                new AlertDialog.Builder(view.getContext()).setMessage(message).setCancelable(true).show();
                result.confirm();
                return true;
            }

        });
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

        });
        Snackbar.make(coordinatorLayout, "開啟網頁 " + url, Snackbar.LENGTH_LONG).show();
        webview.loadUrl(url);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            boolean back;
            if (webview.canGoBack()) {
                back = false;
                webview.goBack();
            } else {
                back = true;
                Intent intent = new Intent(BrowserActivity.this,BarcodeScanner.class);
                startActivity(intent);
                finish();
            }
            event.startTracking();
            return back;
        }
        return super.onKeyDown(keyCode, event);
    }


}
