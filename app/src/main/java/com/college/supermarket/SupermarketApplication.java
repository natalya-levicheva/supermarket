package com.college.supermarket;

import com.college.supermarket.domain.HelperFactory;
import android.app.Application;

public class SupermarketApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        HelperFactory.setHelper(getApplicationContext());
    }
    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }
}
