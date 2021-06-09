package com.example.sportivesandroid.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.sportivesandroid.R;

/**
 * @deprecated
 * */
public class CustomWebViewClient extends WebViewClient {
    Context context;
    Activity activity;

    public CustomWebViewClient(Context context, Activity activity){
        this.context = context;
        this.activity = activity;

    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

        System.out.println("URL: " + request.getUrl().toString());
        //if (request.getUrl().toString().startsWith("http:")) return dialogSslError();

        return super.shouldOverrideUrlLoading(view, request);
    }

 }
