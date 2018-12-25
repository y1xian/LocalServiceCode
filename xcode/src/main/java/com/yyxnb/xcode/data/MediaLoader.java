package com.yyxnb.xcode.data;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.yyxnb.xcode.LocalConfig;
import com.yyxnb.xcode.R;
import com.yyxnb.xcode.entity.Folder;
import com.yyxnb.xcode.entity.Media;

import java.util.ArrayList;

/**
 * 图片 + 视频
 */
public class MediaLoader extends LoaderM implements LoaderManager.LoaderCallbacks<Cursor> {
    String[] MEDIA_PROJECTION = {
            MediaStore.Files.FileColumns.TITLE,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Files.FileColumns._ID,
            MediaStore.Video.VideoColumns.DURATION,
            MediaStore.Video.VideoColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.PARENT};

    Context mContext;
    DataCallback mLoader;

    public MediaLoader(Context context, DataCallback loader) {
        this.mContext = context;
        this.mLoader = loader;
    }

    @Override
    public Loader onCreateLoader(int picker_type, Bundle bundle) {
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

        Uri queryUri = MediaStore.Files.getContentUri("external");
        CursorLoader cursorLoader = new CursorLoader(
                mContext,
                queryUri,
                MEDIA_PROJECTION,
                selection,
                null, // Selection args (none).
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC" // Sort order.
        );
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor cursor) {
        try {
            ArrayList<Folder> folders = new ArrayList<>();
            Folder allFolder = new Folder(mContext.getResources().getString(R.string.all_dir_name));
            folders.add(allFolder);
            Folder allVideoDir = new Folder(mContext.getResources().getString(R.string.video_dir_name));
            folders.add(allVideoDir);
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.TITLE));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME));
                long dateTime = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_ADDED));
                int mediaType = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MEDIA_TYPE));
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE));
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID));
                String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.MIME_TYPE));
                long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DURATION));

                if (size < 1 || size > LocalConfig.DEFAULT_SELECTED_MAX_SIZE) {
                    continue;
                }
                if (path == null || path.equals("")) {
                    continue;
                }
                if (mimeType.contains("video") && duration < 2000) {
                    continue;
                }

//                Log.d("VideoLoader", name + "," +  mimeType);

                String dirName = getParent(path);
                Media media = new Media();
                media.setTitle(title);
                media.setPath(path);
                media.setName(name);
                media.setTime(dateTime);
                media.setMediaType(mediaType);
                media.setSize(size);
                media.setId(id);
                media.setParentDir(dirName);
                media.setDuration(duration);
                media.setMimeType(mimeType);
                allFolder.addMedias(media);
                if (mediaType == 3) {
                    allVideoDir.addMedias(media);
                }

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
