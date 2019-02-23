package top.codemaster.mifinder.resources;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;
import io.reactivex.Flowable;
import top.codemaster.mifinder.data.Resource;
import top.codemaster.mifinder.data.ResourceRepository;

public class ResourcesViewModel extends ViewModel {

    @Inject ResourceRepository mResourceRepository;

    private Resource.ResType mResType;


    public void init(Resource.ResType resType) {
        this.mResType = resType;
    }

    public Flowable<List<Resource>> getResources() {
        return mResourceRepository.getResources(mResType);
    }

}
