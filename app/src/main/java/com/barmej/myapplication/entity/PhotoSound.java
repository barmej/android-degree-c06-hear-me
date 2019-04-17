package com.barmej.myapplication.entity;

import android.net.Uri;

import com.barmej.myapplication.R;

import java.util.ArrayList;


public class PhotoSound {
    private Uri Image;
    private Uri sound;

    public PhotoSound(Uri image, Uri sound) {
        this.Image = image;
        this.sound = sound;
    }

    public Uri getImage() {
        return Image;
    }

    public Uri getSound() {
        return sound;
    }

    public static ArrayList<PhotoSound> getDefaultList() {
        ArrayList<PhotoSound> defaultList = new ArrayList<>();

        defaultList.add(new PhotoSound(resourceToUri(R.drawable.eat), resourceToUri(R.raw.eat_audio)));
        defaultList.add(new PhotoSound(resourceToUri(R.drawable.drink), resourceToUri(R.raw.drink_audio)));

        return defaultList;
    }

    private static Uri resourceToUri(int res) {
        return Uri.parse("android.resource://com.barmej.myapplication" + "/" + res);
    }
}
