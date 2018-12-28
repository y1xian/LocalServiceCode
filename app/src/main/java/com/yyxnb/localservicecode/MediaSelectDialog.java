package com.yyxnb.localservicecode;

import android.Manifest;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yyxnb.xcode.LocalConfig;
import com.yyxnb.xcode.adapter.MediaSelectAdapter;
import com.yyxnb.xcode.data.DataCallback;
import com.yyxnb.xcode.data.VideoLoaderManager;
import com.yyxnb.xcode.entity.LocalFolder;
import com.yyxnb.xcode.entity.LocalMedia;
import com.yyxnb.yyxarch.utils.ScreenUtils;
import com.yyxnb.yyxarch.utils.dialog.BaseSheetDialog;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MediaSelectDialog extends BaseSheetDialog<MediaSelectDialog> implements DataCallback {

    private MediaSelectAdapter mAdapter;
    private List<LocalMedia> mData = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int initLayoutId() {
        return R.layout.dialog_media_select_layout;
    }

    @Override
    public void initViews(View view) {
        setDimAmount(0f).setHeight(ScreenUtils.getScreenHeight(getActivity()));

        TextView tvCancel = view.findViewById(R.id.tvCancel);
        TextView tvComplete = view.findViewById(R.id.tvComplete);
        RecyclerView mRecyclerView = view.findViewById(R.id.mRecyclerView);

        tvCancel.setOnClickListener(v -> dismiss());
        tvComplete.setOnClickListener(v -> {
            onItemClickListener.onItemData(mAdapter.getSelectlist());
            dismiss();
        });

        LocalConfig localConfig = new LocalConfig();
        localConfig.setMaxCount(1);
//        localConfig.setIsSelectSingle(true);

        mAdapter = new MediaSelectAdapter(getContext(),localConfig, mData);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerView.setHasFixedSize(true);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator())
                .setSupportsChangeAnimations(false);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MediaSelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                tvComplete.setText("完成(" + mAdapter.getSelectlist().size() + ")");
            }
        });



        getMediaData();

    }

    @AfterPermissionGranted(119)
    void getMediaData() {
        if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {

            getLoaderManager().initLoader(LocalConfig.VIDEO, null, new VideoLoaderManager(getActivity(), this));

        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.READ_EXTERNAL_STORAGE), 119, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onData(ArrayList<LocalFolder> list) {

        Log.d("---", "" + list.get(0).getLocalMedia().size());
        mData.addAll(list.get(0).getLocalMedia());
        mAdapter.notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemData(List<LocalMedia> mediaList);
    }
}
