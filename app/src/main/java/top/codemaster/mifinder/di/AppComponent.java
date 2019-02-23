package top.codemaster.mifinder.di;

import dagger.Component;
import okhttp3.OkHttpClient;

@AppScope
@Component(modules = {AppModule.class})
public interface AppComponent {

    OkHttpClient okHttpClient();
}
