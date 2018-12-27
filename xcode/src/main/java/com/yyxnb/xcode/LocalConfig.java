package com.yyxnb.xcode;

import java.io.Serializable;

public class LocalConfig implements Serializable {

    /**
     * 最大图片选择次数，int类型，默认9
     */
    public static final String MAX_SELECT_COUNT = "max_select_count";

    public static final int DEFAULT_SELECTED_MAX_COUNT = 9;

    /**
     * 最大文件大小，int类型，默认180m
     */
    public static final String MAX_SELECT_SIZE = "max_select_size";

    public static final long DEFAULT_SELECTED_MAX_SIZE = 188743680L;

    /**
     * 图片选择模式，默认选视频和图片
     */
    public static final String SELECT_MODE = "select_mode";

    /**
     * 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合
     */
    public static final String EXTRA_RESULT = "select_result";
    /**
     * 默认选择集
     */
    public static final String DEFAULT_SELECTED_LIST = "default_list";
    /**
     * 预览集
     */
    public static final String PRE_RAW_LIST = "pre_raw_List";
    public static final int RESULT_CODE = 0x201;
    public static final int RESULT_UPDATE_CODE = 0x200;
    public static final int IMAGE = 0x100;
    public static final int VIDEO = 0x102;
    public static final int AUDIO = 0x103;
    public static final int IMAGE_VIDEO = 0x101;
    public static int GridSpanCount = 3;
    public static int GridSpace = 4;


    public static class Builder implements Serializable {

    }

}
