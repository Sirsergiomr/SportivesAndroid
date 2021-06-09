package com.example.sportivesandroid;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.example.sportivesandroid.Utils.Tags;
import com.stripe.android.PaymentConfiguration;

/**
 * Activity to catch the context or activity at any time.
 *
 * @author Sergio Muñoz Ruiz
 * @version 2021.0606
 * @since 30.0
 */
public class Sportives extends Application {

    private static Context context;
    private static Activity activity;

    @Override
    public void onCreate() {

        PaymentConfiguration.init(Tags.STRIPE_PUBLIC_KEY);

        super.onCreate();
        context = this;
    }

    public static Activity getCurrentActivity() {
        return activity;
    }

    public static void setCurrentActivity(Activity act) {
        activity = act;
    }

    public static Context getContext() {
        return context;
    }
}


