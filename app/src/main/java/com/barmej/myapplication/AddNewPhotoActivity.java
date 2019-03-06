package com.barmej.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class AddNewPhotoActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 47;
    private static final int PICK_SOUND = 48;
    private static final int READ_PHOTO_FROM_GALLERY_PERMISSION = 13;
    private static final int READ_SOUND_FROM_GALLERY_PERMISSION = 14;
    public static final String RES_EXTRA_PHOTO_URI = "RES_EXTRA_PHOTO_URI";
    public static final String RES_EXTRA_SOUND_URI = "RES_EXTRA_SOUND_URI";


    private Button mSelectPhotoBt;
    private Button mSelectSoundBt;
    private ImageView mNewPhotoIv;
    private ImageView mNewSoundIv;
    private Uri mSelectedPhotoUri;
    private Uri mSelectedSoundUri;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_photo);
        mSelectPhotoBt = findViewById(R.id.button_select_photo);
        mSelectSoundBt = findViewById(R.id.button_select_sound);
        mNewPhotoIv = findViewById(R.id.image_view_new_photo);
        mNewSoundIv = findViewById(R.id.image_view_sound_added);
        findViewById(R.id.text_view_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        mSelectSoundBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSound();
            }
        });
        mSelectPhotoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == READ_PHOTO_FROM_GALLERY_PERMISSION || requestCode == READ_SOUND_FROM_GALLERY_PERMISSION) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (requestCode == READ_PHOTO_FROM_GALLERY_PERMISSION) {
                    firePickPhotoIntent();
                } else {
                    firePickSoundIntent();
                }
            } else {
                Toast.makeText(this, R.string.read_permission_needed_to_access_files, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE || requestCode == PICK_SOUND) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                if (requestCode == PICK_IMAGE) {
                    setSelectedPhoto(data.getData());
                } else {
                    setSelectedSound(data.getData());
                }
                getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION);

            } else {
                Toast.makeText(this, R.string.failed_to_get_image, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void submit() {
        if (mSelectedSoundUri == null || mSelectedPhotoUri == null) {
            Toast.makeText(this, R.string.select_picture_and_sound, Toast.LENGTH_SHORT).show();
        } else {
            Intent res = new Intent();
            res.putExtra(RES_EXTRA_PHOTO_URI, mSelectedPhotoUri);
            res.putExtra(RES_EXTRA_SOUND_URI, mSelectedSoundUri);
            setResult(RESULT_OK, res);
            finish();
        }
    }

    private void firePickPhotoIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), PICK_IMAGE);
    }

    private void firePickSoundIntent() {
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), PICK_SOUND);
    }

    private void selectSound() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_SOUND_FROM_GALLERY_PERMISSION);
        } else {
            firePickSoundIntent();
        }
    }

    private void selectPhoto() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_PHOTO_FROM_GALLERY_PERMISSION);
        } else {
            firePickPhotoIntent();
        }
    }

    private void setSelectedPhoto(@NonNull Uri data) {
        mNewPhotoIv.setImageURI(data);
        mNewPhotoIv.setVisibility(View.VISIBLE);
        mSelectedPhotoUri = data;
    }

    private void setSelectedSound(@NonNull Uri data) {
        mNewSoundIv.setVisibility(View.VISIBLE);
        mSelectedSoundUri = data;
    }

}
