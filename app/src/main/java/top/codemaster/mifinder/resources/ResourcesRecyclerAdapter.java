package top.codemaster.mifinder.resources;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import top.codemaster.mifinder.data.Resource;
import top.codemaster.mifinder.databinding.ResourcesRecycleviewItemBinding;
import top.codemaster.mifinder.video.VideoActivity;

public class ResourcesRecyclerAdapter extends ListAdapter<Resource, ResourcesRecyclerAdapter.ViewHolder> {

    private String mBaseUrl;

    protected ResourcesRecyclerAdapter(String baseUrl) {
        super(new ResourceDiffCallback());
        this.mBaseUrl = baseUrl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ResourcesRecycleviewItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Resource resource = getItem(position);
        holder.bind(v -> {
            Intent intent = new Intent(v.getContext(), VideoActivity.class);
            intent.putExtra(VideoActivity.EXTRA_KEY_URL, mBaseUrl + "/resources/" + resource.getId());
            v.getContext().startActivity(intent);
        }, resource);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ResourcesRecycleviewItemBinding binding;

        public ViewHolder(ResourcesRecycleviewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(View.OnClickListener clickListener, Resource res) {
            binding.setClickListener(clickListener);
            binding.setResource(res);
            binding.executePendingBindings();
        }
    }

    static class ResourceDiffCallback extends DiffUtil.ItemCallback<Resource> {

        @Override
        public boolean areItemsTheSame(@NonNull Resource oldItem, @NonNull Resource newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Resource oldItem, @NonNull Resource newItem) {
            return oldItem.equals(newItem);
        }
    }
}
