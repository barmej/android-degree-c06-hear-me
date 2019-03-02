package com.barmej.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhotoSoundAdapter extends RecyclerView.Adapter<PhotoSoundAdapter.PhotoSoundViewHolder> {
    private List<PhotoSound> mItems;
    private ItemClickListener mItemClickListener;
    private ItemLongClickListener itemLongClickListener;

    public PhotoSoundAdapter(List<PhotoSound> items,
                             @NonNull ItemClickListener itemClickListener,
                             @NonNull ItemLongClickListener itemLongClickListener) {
        this.mItems = items;
        this.mItemClickListener = itemClickListener;
        this.itemLongClickListener = itemLongClickListener;
    }

    @NonNull
    @Override
    public PhotoSoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_sound_item, parent, false);
        return new PhotoSoundViewHolder(view, mItemClickListener, itemLongClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoSoundViewHolder holder, int position) {
        PhotoSound photoSound = mItems.get(position);
        holder.photoIv.setImageURI(photoSound.getImage());
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class PhotoSoundViewHolder extends RecyclerView.ViewHolder {
        private ItemClickListener itemClickListener;
        private ItemLongClickListener itemLongClickListener;
        private ImageView photoIv;
        private int position;

        PhotoSoundViewHolder(@NonNull View itemView,
                             @NonNull ItemClickListener itemClickListener,
                             @NonNull ItemLongClickListener itemLongClickListener) {
            super(itemView);
            photoIv = itemView.findViewById(R.id.image_view_list_item_photo);
            this.itemClickListener = itemClickListener;
            this.itemLongClickListener = itemLongClickListener;
            itemView.setOnClickListener(this::onItemClicked);
            itemView.setOnLongClickListener(this::OnItemLongClicked);
        }

        private boolean OnItemLongClicked(View view) {
            itemLongClickListener.onLongClickItem(position);
            return true;
        }

        private void onItemClicked(View view) {
            itemClickListener.onClickItem(position);
        }
    }
}
