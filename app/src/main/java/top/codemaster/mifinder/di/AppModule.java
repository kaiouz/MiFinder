package top.codemaster.mifinder.di;


import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class AppModule {

    @AppScope
    @Provides
    public static OkHttpClient provideOkttpClient() {
       return new OkHttpClient.Builder().build();
    }


}
