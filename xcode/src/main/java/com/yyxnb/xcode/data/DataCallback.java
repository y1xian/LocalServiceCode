package com.yyxnb.xcode.data;


import com.yyxnb.xcode.entity.Folder;

import java.util.ArrayList;


/**
 * 数据回调
 */
public interface DataCallback {


    void onData(ArrayList<Folder> list);

}
