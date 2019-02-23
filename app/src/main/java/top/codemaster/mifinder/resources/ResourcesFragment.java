package top.codemaster.mifinder.resources;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.android.schedulers.AndroidSchedulers;
import top.codemaster.mifinder.MiFinderApplication;
import top.codemaster.mifinder.data.Resource;
import top.codemaster.mifinder.databinding.ResourcesFragmentBinding;
import top.codemaster.mifinder.di.DaggerServiceComponent;
import top.codemaster.mifinder.di.ServiceModule;

public class ResourcesFragment extends Fragment {
    private static final String TAG = "ResourcesFragment";

    public static final String ARG_RES_TYPE = ResourcesFragment.class.getName() + ".ARG_RES_TYPE";

    public static final String ARG_BASE_URL = ResourcesFragment.class.getName() + ".ARG_BASE_URL";

    private ResourcesViewModel mViewModel;

    /**
     * 展示的资源类型, null展示所有
     */
    private Resource.ResType mResType;

    private String mBaseUrl;

    private ResourcesRecyclerAdapter mAdapter;

    public static ResourcesFragment newInstance(Resource.ResType resType, String baseUrl) {
        ResourcesFragment fragment = new ResourcesFragment();
        Bundle data = new Bundle();
        data.putString(ARG_BASE_URL, baseUrl);
        if (resType != null) {
            data.putSerializable(ARG_RES_TYPE, resType);
        }
        fragment.setArguments(data);
        return fragment;
    }

    private void getArgs() {
        Bundle data = this.getArguments();
        if (data != null) {
            mBaseUrl = data.getString(ARG_BASE_URL);
            mResType = (Resource.ResType) data.getSerializable(ARG_RES_TYPE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getArgs();

        ResourcesFragmentBinding binding = ResourcesFragmentBinding.inflate(inflater, container, false);
        mAdapter = new ResourcesRecyclerAdapter(mBaseUrl);
        binding.resList.setAdapter(mAdapter);

        mViewModel = ViewModelProviders.of(this).get(ResourcesViewModel.class);
        DaggerServiceComponent.builder()
                .appComponent(((MiFinderApplication) getActivity().getApplicationContext()).getAppComponent())
                .serviceModule(new ServiceModule(mBaseUrl))
                .build().inject(mViewModel);
        mViewModel.init(mResType);
        subscribeUI();

        return binding.getRoot();
    }

    private void subscribeUI() {
        mViewModel.getResources()
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(res -> mAdapter.submitList(res), err -> Log.e(TAG, err.getMessage()));
    }
}
