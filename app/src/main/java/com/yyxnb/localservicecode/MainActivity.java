package com.yyxnb.localservicecode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yyxnb.xcode.entity.LocalMedia;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvShow = findViewById(R.id.tvShow);
        tvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaSelectDialog dialog = new MediaSelectDialog();
                dialog.show(getSupportFragmentManager(),dialog.getTag());
                dialog.setOnItemClickListener(new MediaSelectDialog.OnItemClickListener() {
                    @Override
                    public void onItemData(List<LocalMedia> mediaList) {
                        StringBuffer buffer = new StringBuffer();
                        for (LocalMedia media : mediaList){
                            buffer.append(media.getPath()).append("\n");
                        }
                        tvShow.setText(buffer.toString());
                    }
                });
            }
        });

    }

}
