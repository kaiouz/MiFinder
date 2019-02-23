package top.codemaster.mifinder.data;

import android.util.Log;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.processors.BehaviorProcessor;
import top.codemaster.mifinder.service.ResourceService;

public class ResourceRepository {

    private static final String TAG = "ResourceRepository";

    private ResourceService resourceService;

    private BehaviorProcessor<List<Resource>> behaviorProcessor = BehaviorProcessor.create();

    public ResourceRepository(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    public Flowable<List<Resource>> getResources(Resource.ResType resType) {

        refresh(resType)
                .subscribe(behaviorProcessor::onNext, e -> Log.e(TAG, e.getMessage(), e));

        return behaviorProcessor;
    }

    public Single<List<Resource>> refresh(Resource.ResType resType) {
        if (resType != null) {
            return resourceService.getResources(resType);
        } else {
            return resourceService.getResources();
        }
    }
}
