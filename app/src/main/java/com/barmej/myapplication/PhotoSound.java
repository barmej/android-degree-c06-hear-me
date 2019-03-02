package com.barmej.myapplication;

import android.net.Uri;


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
}
