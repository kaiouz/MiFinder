package top.codemaster.mifinder;

import android.app.Application;

import top.codemaster.mifinder.di.AppComponent;
import top.codemaster.mifinder.di.DaggerAppComponent;

public class MiFinderApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.create();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
