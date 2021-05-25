package com.example.sportivesandroid;

import android.app.Application;
import android.content.Context;

import com.example.sportivesandroid.Utils.Tags;
import com.stripe.android.PaymentConfiguration;


public class Sportives extends Application {

        private static Context context;

        @Override
        public void onCreate() {

            PaymentConfiguration.init(Tags.STRIPE_PUBLIC_KEY);

            super.onCreate();
            context = this;
        }

        public static Context getContext(){
            return context;
        }
    }


