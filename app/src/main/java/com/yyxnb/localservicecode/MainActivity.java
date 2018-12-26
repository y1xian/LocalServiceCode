package com.yyxnb.localservicecode;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yyxnb.xcode.LocalConfig;
import com.yyxnb.xcode.data.AudioLoader;
import com.yyxnb.xcode.data.DataCallback;
import com.yyxnb.xcode.entity.LocalFolder;

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

            getLoaderManager().initLoader(LocalConfig.AUDIO, null, new AudioLoader(this, this));

        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.READ_EXTERNAL_STORAGE), 119, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onData(ArrayList<LocalFolder> list) {

        Log.d("---",""+list.get(0).getLocalMedia().size());
    }
}
