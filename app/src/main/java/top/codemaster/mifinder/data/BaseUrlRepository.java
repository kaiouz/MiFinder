package top.codemaster.mifinder.data;

import io.reactivex.Flowable;
import io.reactivex.processors.BehaviorProcessor;

public class BaseUrlRepository {

    private BehaviorProcessor<String> mBaseUrlProcessor = BehaviorProcessor.create();

    public Flowable<String> getBaseUrl() {
        return mBaseUrlProcessor.onBackpressureLatest();
    }

    public void updateService(String host) {
        mBaseUrlProcessor.onNext(host);
    }

}
