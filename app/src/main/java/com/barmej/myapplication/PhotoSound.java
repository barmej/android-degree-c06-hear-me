package com.barmej.myapplication;

import android.net.Uri;

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

        defaultList.add(new PhotoSound(resourceToUri("drawable/eat"), resourceToUri("raw/eat_audio")));
        defaultList.add(new PhotoSound(resourceToUri("drawable/drink"), resourceToUri("raw/drink_audio")));

        return defaultList;
    }

    private static Uri resourceToUri(String res) {
        return Uri.parse("android.resource://com.barmej.myapplication" + "/" + res);
    }
}
