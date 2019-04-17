package com.barmej.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.barmej.myapplication.listener.ItemClickListener;
import com.barmej.myapplication.listener.ItemLongClickListener;
import com.barmej.myapplication.entity.PhotoSound;
import com.barmej.myapplication.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PhotoSoundAdapter extends RecyclerView.Adapter<PhotoSoundAdapter.PhotoSoundViewHolder> {
    private ArrayList<PhotoSound> mItems;
    private ItemClickListener mItemClickListener;
    private ItemLongClickListener itemLongClickListener;

    public PhotoSoundAdapter(ArrayList<PhotoSound> items,
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

        private ImageView photoIv;
        private int position;

        PhotoSoundViewHolder(@NonNull View itemView,
                             @NonNull final ItemClickListener itemClickListener,
                             @NonNull final ItemLongClickListener itemLongClickListener) {
            super(itemView);
            photoIv = itemView.findViewById(R.id.image_view_list_item_photo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClickItem(position);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemLongClickListener.onLongClickItem(position);
                    return true ;
                }
            });
        }

    }
}
