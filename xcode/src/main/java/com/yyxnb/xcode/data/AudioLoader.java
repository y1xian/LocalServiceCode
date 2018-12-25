package com.yyxnb.xcode.data;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.yyxnb.xcode.R;
import com.yyxnb.xcode.entity.Folder;
import com.yyxnb.xcode.entity.Media;

import java.util.ArrayList;

/**
 * 音频 音乐
 */
public class AudioLoader extends LoaderM implements LoaderManager.LoaderCallbacks<Cursor> {
    String[] MEDIA_PROJECTION = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.MIME_TYPE};

    Context mContext;
    DataCallback mLoader;

    public AudioLoader(Context context, DataCallback loader) {
        this.mContext = context;
        this.mLoader = loader;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int picker_type, Bundle bundle) {

        Uri queryUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        CursorLoader cursorLoader = new CursorLoader(
                mContext,
                queryUri,
                MEDIA_PROJECTION,
                null,
                null, // Selection args (none).
                MediaStore.Audio.Media.DATE_ADDED + " DESC" // Sort order.
        );
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor cursor) {
        try {
            ArrayList<Folder> folders = new ArrayList<>();
            Folder allFolder = new Folder(mContext.getResources().getString(R.string.all_audio));
            folders.add(allFolder);
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                long dateTime = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED));
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                int mediaType = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.MIME_TYPE));
                String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.MIME_TYPE));
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

                if (size < 1 || size > 10000000L) {
                    continue;
                }
                if(path == null || path.equals("")) {
                    continue;
                }

//                Log.d("AudioLoader", name + "," + mediaType + "。"+ mimeType);

                String dirName = getParent(path);
                Media media = new Media();
                media.setTitle(title);
                media.setPath(path);
                media.setName(name);
                media.setTime(dateTime);
                media.setSize(size);
                media.setId(id);
                media.setMediaType(mediaType);
                media.setParentDir(dirName);
                media.setArtist(artist);
                media.setMimeType(mimeType);
                media.setDuration(duration);
                allFolder.addMedias(media);

                int index = hasDir(folders, dirName);
                if (index != -1) {
                    folders.get(index).addMedias(media);
                } else {
                    Folder folder = new Folder(dirName);
                    folder.addMedias(media);
                    folders.add(folder);
                }
            }
            mLoader.onData(folders);
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onLoaderReset(Loader loader) {

    }
}
