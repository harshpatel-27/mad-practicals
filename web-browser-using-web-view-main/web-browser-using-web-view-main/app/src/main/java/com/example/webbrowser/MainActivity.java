package com.example.webbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private EditText etUrl;
    private Button btnGo, btnBack, btnForward, btnReload, btnClearHistory;
    private ProgressBar progress;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        etUrl = findViewById(R.id.etUrl);
        btnGo = findViewById(R.id.btnGo);
        btnBack = findViewById(R.id.btnBack);
        btnForward = findViewById(R.id.btnForward);
        btnReload = findViewById(R.id.btnReload);
        btnClearHistory = findViewById(R.id.btnClearHistory);
        progress = findViewById(R.id.progress);

        // WebView settings
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setLoadWithOverviewMode(true);
        ws.setUseWideViewPort(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);

        webView.setWebViewClient(new WebViewClient() {
            @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progress.setVisibility(View.VISIBLE);
                etUrl.setText(url);
                updateNavButtons();
            }
            @Override public void onPageFinished(WebView view, String url) {
                progress.setVisibility(View.GONE);
                updateNavButtons();
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override public void onProgressChanged(WebView view, int newProgress) {
                progress.setProgress(newProgress);
                if (newProgress >= 100) progress.setVisibility(View.GONE);
            }
        });

        // Actions
        btnGo.setOnClickListener(v -> loadFromBar());
        etUrl.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_DONE) {
                loadFromBar();
                return true;
            }
            return false;
        });

        btnBack.setOnClickListener(v -> {
            if (webView.canGoBack()) webView.goBack();
            updateNavButtons();
        });

        btnForward.setOnClickListener(v -> {
            if (webView.canGoForward()) webView.goForward();
            updateNavButtons();
        });

        btnReload.setOnClickListener(v -> webView.reload());

        btnClearHistory.setOnClickListener(v -> {
            webView.clearHistory();
            webView.clearCache(true);
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
            updateNavButtons();
            Toast.makeText(this, "History & cache cleared", Toast.LENGTH_SHORT).show();
        });

        // Load a default page
        String home = "https://www.google.com";
        etUrl.setText(home);
        webView.loadUrl(home);
    }

    private void loadFromBar() {
        String url = etUrl.getText().toString().trim();
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(this, "Enter a URL", Toast.LENGTH_SHORT).show();
            return;
        }
        // Normalize: add scheme if missing
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }
        webView.loadUrl(url);
    }

    private void updateNavButtons() {
        btnBack.setEnabled(webView.canGoBack());
        btnForward.setEnabled(webView.canGoForward());
    }

    /**
     *
     */
    // Handle device Back button to navigate WebView history first
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            updateNavButtons();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        // Clean up WebView to avoid leaks
        if (webView != null) {
            webView.loadUrl("about:blank");
            webView.clearHistory();
            ((android.view.ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
        }
        super.onDestroy();
    }
}