package com.yyxnb.xcode.entity;

import java.util.ArrayList;


public class LocalFolder {

    public String name;

    public int count;

    ArrayList<LocalMedia> localMedia = new ArrayList<>();

    public void addMedias(LocalMedia localMedia) {
        this.localMedia.add(localMedia);
    }

    public LocalFolder(String name) {
        this.name = name;
    }

    public ArrayList<LocalMedia> getLocalMedia() {
        return this.localMedia;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setLocalMedia(ArrayList<LocalMedia> localMedia) {
        this.localMedia = localMedia;
    }
}
