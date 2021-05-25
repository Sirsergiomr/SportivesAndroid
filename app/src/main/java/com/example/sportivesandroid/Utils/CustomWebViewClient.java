package com.example.sportivesandroid.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.sportivesandroid.R;


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


    private boolean dialogSslError() {
        final DialogX dialog = new DialogX(activity, R.layout.dialog_message_title);
        dialog.dialog_confirmacion("", activity.getResources().getString(R.string.seguir_navegando), "¡Cuidado!", activity.getResources().getString(R.string.no_seguro_message),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        activity.finish();
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
        dialog.show();
        return true;
    }
 }
