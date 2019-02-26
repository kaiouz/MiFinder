package top.codemaster.mifinder.sd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import top.codemaster.mifinder.data.ServiceHost;
import top.codemaster.mifinder.databinding.ContentDiscoveryListItemBinding;

public class ServiceDiscoveryAdapter extends ListAdapter<ServiceHost, ServiceDiscoveryAdapter.VH> {

    private ServiceDialog.ServiceChangeListener mListener;

    protected ServiceDiscoveryAdapter(ServiceDialog.ServiceChangeListener listener) {
        super(new ServiceHostDiffCallback());
        this.mListener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(ContentDiscoveryListItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ServiceHost serviceHost = getItem(position);
        holder.bind(v -> {
            mListener.onServiceChange(serviceHost.getUrl());
        }, serviceHost);
    }

    static class VH extends RecyclerView.ViewHolder {

        private ContentDiscoveryListItemBinding binding;

        public VH(ContentDiscoveryListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(View.OnClickListener clickListener, ServiceHost serviceHost) {
            binding.setClickListener(clickListener);
            binding.setService(serviceHost);
            binding.executePendingBindings();
        }
    }

    static class ServiceHostDiffCallback extends DiffUtil.ItemCallback<ServiceHost> {

        @Override
        public boolean areItemsTheSame(@NonNull ServiceHost oldItem, @NonNull ServiceHost newItem) {
            return oldItem.getName().equals(newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ServiceHost oldItem, @NonNull ServiceHost newItem) {
            return oldItem.equals(newItem);
        }
    }

}
