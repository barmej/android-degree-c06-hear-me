package com.barmej.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.barmej.myapplication.adapter.PhotoSoundAdapter;
import com.barmej.myapplication.entity.PhotoSound;
import com.barmej.myapplication.listener.ItemClickListener;
import com.barmej.myapplication.listener.ItemLongClickListener;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

public class PhotosListActivity extends AppCompatActivity {


    private static final int ADD_PHOTO = 145;
    private ArrayList<PhotoSound> mItems;
    private PhotoSoundAdapter mAdapter;
    private RecyclerView.LayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridLayoutManger;
    private MediaPlayer mMediaPlayer;
    private Menu mMenu;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_list);
        findViewById(R.id.floating_button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAddNewPhotoActivity();
            }
        });
        mRecyclerView = findViewById(R.id.recycler_view_photos);
        mItems = PhotoSound.getDefaultList();
        mAdapter = new PhotoSoundAdapter(mItems, new ItemClickListener() {
            @Override
            public void onClickItem(int position) {
                playSound(position);
            }
        }, new ItemLongClickListener() {
            @Override
            public void onLongClickItem(int position) {
                deleteItem(position);
            }
        });
        mLinearLayoutManager = new LinearLayoutManager(this);
        mGridLayoutManger = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.mMenu = menu;
        getMenuInflater().inflate(R.menu.menu_photo_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_grid:
                mRecyclerView.setLayoutManager(mGridLayoutManger);
                item.setVisible(false);
                mMenu.findItem(R.id.action_list).setVisible(true);
                return true;
            case R.id.action_list:
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                item.setVisible(false);
                mMenu.findItem(R.id.action_grid).setVisible(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMediaPlayer = new MediaPlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMediaPlayer.release();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == ADD_PHOTO){
            if (resultCode == RESULT_OK && data != null) {
                Uri soundUri = data.getParcelableExtra(AddNewPhotoActivity.RES_EXTRA_SOUND_URI);
                Uri photoUri = data.getParcelableExtra(AddNewPhotoActivity.RES_EXTRA_PHOTO_URI);
                PhotoSound photoSound = new PhotoSound(photoUri, soundUri);
                addItem(photoSound);
            } else {
                Toast.makeText(this, R.string.didnt_add_photo, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startAddNewPhotoActivity() {
        Intent addNewPhotoIntent = new Intent(this, AddNewPhotoActivity.class);
        startActivityForResult(addNewPhotoIntent, ADD_PHOTO);
    }

    private void deleteItem(int position) {
        mItems.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    private void addItem(PhotoSound photoSound) {
        mItems.add(photoSound);
        mAdapter.notifyItemInserted(mItems.size() - 1);
    }

    private void playSound(int position) {
        PhotoSound item = mItems.get(position);
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(this, item.getSound());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.couldnt_play_sound, Toast.LENGTH_SHORT).show();
        }
    }
}
