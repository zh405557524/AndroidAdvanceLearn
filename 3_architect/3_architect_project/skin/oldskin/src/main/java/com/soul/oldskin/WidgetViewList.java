package com.soul.oldskin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 控件/布局
 * Author: 祝明
 * CreateDate: 2021/10/15 16:57
 * UpdateUser:
 * UpdateDate: 2021/10/15 16:57
 * UpdateRemark:
 */
public class WidgetViewList {

    private final List<String> attributeList = new ArrayList<>();
    /**
     * 用于缓存Typeface
     */
    private final static Map<WidgetViewList, Typeface> typefaceMap = new HashMap<>();
    private static int[] TYPEFACE_ATTR = {
            R.attr.custom_typeface
    };

    {
        attributeList.add("background");
        attributeList.add("src");
        attributeList.add("textColor");
        attributeList.add("drawableLeft");
        attributeList.add("drawableTop");
        attributeList.add("drawableRight");
        attributeList.add("drawableBottom");
        attributeList.add("tint");
    }

    private Context context;

    public WidgetViewList(Activity context) {
        this.context = context;
        typefaceMap.put(this, initTypeface());
    }

    /**
     * 初始化字体对象相关
     *
     * @return
     */
    private Typeface initTypeface() {
        int[] resIds = new int[TYPEFACE_ATTR.length];
        TypedArray typedArray = context.obtainStyledAttributes(TYPEFACE_ATTR);
        for (int i = 0; i < typedArray.length(); i++) {
            resIds[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();
        return SkinResources.getInstance().getTypeface(resIds[0]);
    }

    /**
     * 保存的属性
     */
    class AttributeNameAndValue {

        /**
         * 属性名称
         */
        String attrName;

        /**
         * 属性资源id
         */
        int attrValueInt;

        public AttributeNameAndValue(String attrName, int attrValueInt) {
            this.attrName = attrName;
            this.attrValueInt = attrValueInt;
        }
    }

    /**
     * View 所对应的属性类
     */
    class WidgetView {
        View mView;
        List<AttributeNameAndValue> attributeNameAndValues;

        public WidgetView(View view, List<AttributeNameAndValue> attributeNameAndValues) {
            mView = view;
            this.attributeNameAndValues = attributeNameAndValues;
        }

        @SuppressLint("RestrictedApi")
        public void skinChange() {
            //符合textView的控件、替换字体
            changeTypeface(mView);

            for (AttributeNameAndValue attributeNameAndValue : attributeNameAndValues) {
                switch (attributeNameAndValue.attrName) {
                    case "background":
                        Object background = SkinResources.getInstance().getBackground(attributeNameAndValue.attrValueInt);

                        break;
                }
            }

        }


        private void changeTypeface(View view) {
            if (view instanceof TextView) {
                ((TextView) view).setTypeface(initTypeface());
            }
        }


    }


}
