package com.example.sportivesandroid;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportivesandroid.Utils.CustomWebViewClient;
import com.example.sportivesandroid.Utils.Functions;
import com.example.sportivesandroid.Utils.Preferences;
import com.example.sportivesandroid.Utils.Tags;

import java.util.HashMap;


public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    CustomWebViewClient customWebViewClient;
    ImageButton btn_back;
    String url;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        System.out.println("DENTRO DEL WEBVIEW");
        setTitle("");
        initUi();
        cargarWebView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Functions.setDecorView(com.example.sportivesandroid.WebViewActivity.this);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }


    private void cargarWebView() {

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {

                if (!customWebViewClient.shouldOverrideUrlLoading(view, view.getUrl())) {
                    //Make the bar disappear after URL is loaded, and changes string to Loading...
                    setTitle("Cargando...");
                    // Return the app name after finish loading
                    if (progress == 100) {
                        url = webView.getUrl();
                        setTitle(R.string.app_name);
                        NotificationManager notificationManager = ((NotificationManager) getSystemService(com.example.sportivesandroid.WebViewActivity.this.NOTIFICATION_SERVICE));
                        notificationManager.cancelAll();
                    }
                } else {
                    webView.loadUrl(url);
                }
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.addJavascriptInterface(this, "Android");

        webView.setWebViewClient(customWebViewClient);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
//        webView.loadUrl("https://" + url + "/");
        HashMap<String, String> headers = new HashMap<>();
        headers.put("TOKEN", Preferences.getToken());
        webView.loadUrl(url , headers);
    }

    private void initUi() {
        webView = findViewById(R.id.webview_use);
        customWebViewClient = new CustomWebViewClient(com.example.sportivesandroid.WebViewActivity.this, com.example.sportivesandroid.WebViewActivity.this);
        intent = getIntent();
        url = "https://www.google.com/";

        try {
            url = Tags.SERVIDOR;
            url = url.replace(url.substring(url.length()-1), "");
            url += intent.getStringExtra(Tags.URL);
            System.out.println("URL: "+url);
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @JavascriptInterface
    public void finalizarPago() {
        setResult(0);
        finish();
    }

    @JavascriptInterface
    public void pagoCancelado() {

        setResult(-1);
        finish();
    }
    @JavascriptInterface
    public void logOut() {
        System.out.println("Comprobar LLamada a logOut");
        setResult(3333);
        finish();
    }
}
