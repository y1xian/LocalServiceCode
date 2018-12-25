package com.yyxnb.xcode.entity;

import java.util.ArrayList;


public class Folder {

    public String name;

    public int count;

    ArrayList<Media> medias = new ArrayList<>();

    public void addMedias(Media media) {
        medias.add(media);
    }

    public Folder(String name) {
        this.name = name;
    }

    public ArrayList<Media> getMedias() {
        return this.medias;
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

    public void setMedias(ArrayList<Media> medias) {
        this.medias = medias;
    }
}
