package top.codemaster.mifinder.sd;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import top.codemaster.mifinder.databinding.DialogServiceDiscoveryBinding;

public class ServiceDialog extends DialogFragment {

    private static final String TAG = "ServiceDialog";

    private ServiceChangeListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (ServiceChangeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ServiceChangeListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        DialogServiceDiscoveryBinding binding =
                DialogServiceDiscoveryBinding.inflate(inflater, null, false);
        ServiceDiscoveryAdapter adapter = new ServiceDiscoveryAdapter(mListener);
        binding.serviceList.setAdapter(adapter);
        builder.setView(binding.getRoot());

        NsdHelper.discover(getContext())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(serviceHosts -> {
                            if (serviceHosts.isEmpty()) {
                                binding.setDiscovered(false);
                            } else {
                                binding.setDiscovered(true);
                            }
                            binding.executePendingBindings();
                            adapter.submitList(serviceHosts);
                        },
                        e -> Log.e(TAG, e.getMessage(), e));
        return builder.create();
    }

    public interface ServiceChangeListener {
        void onServiceChange(String url);
    }

}
