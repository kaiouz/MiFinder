package top.codemaster.mifinder.di;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import top.codemaster.mifinder.data.ResourceRepository;
import top.codemaster.mifinder.service.ResourceService;
import top.codemaster.mifinder.service.RetrofitFactory;

@Module
public class ServiceModule {

    private String baseUrl;

    public ServiceModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @ServiceScope
    @Provides
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new RetrofitFactory(baseUrl, okHttpClient).getInstance();
    }

    @ServiceScope
    @Provides
    public ResourceService provideResourceService(Retrofit retrofit) {
        return retrofit.create(ResourceService.class);
    }

    @ServiceScope
    @Provides
    public ResourceRepository provideResourceRepository(ResourceService resourceService) {
        return new ResourceRepository(resourceService);
    }

}
