package com.yyxnb.localservicecode;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yyxnb.xcode.data.AudioLoader;
import com.yyxnb.xcode.data.DataCallback;
import com.yyxnb.xcode.entity.Folder;

import java.util.ArrayList;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements DataCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getMediaData();
    }

    @AfterPermissionGranted(119)
    void getMediaData() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//            int type = argsIntent.getIntExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_IMAGE_VIDEO);
//            if (type == PickerConfig.PICKER_IMAGE_VIDEO) {
//                getLoaderManager().initLoader(type, null, new MediaLoader(this, this));
//            } else if (type == PickerConfig.PICKER_IMAGE) {
//                getLoaderManager().initLoader(type, null, new ImageLoader(this, this));
//            } else if (type == PickerConfig.PICKER_VIDEO) {
//                getLoaderManager().initLoader(type, null, new VideoLoader(this, this));
//            }

            getLoaderManager().initLoader(0x1, null, new AudioLoader(this, this));

        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.READ_EXTERNAL_STORAGE), 119, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onData(ArrayList<Folder> list) {

    }
}
