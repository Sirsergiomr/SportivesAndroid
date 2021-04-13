package com.example.sportivesandroid;

import android.app.Application;
import android.content.Context;

//import com.example.sportivesandroid.Utils.Tags;
//import com.stripe.android.PaymentConfiguration;


public class Sportives extends Application {

        private static Context context;

        @Override
        public void onCreate() {
//todo Hacer esto mas tarde para los pagos con tarjeta mediante stripe
//            PaymentConfiguration.init(Tags.STRIPE_PUBLIC_KEY);

            super.onCreate();
            context = this;
        }

        public static Context getContext(){
            return context;
        }
    }


