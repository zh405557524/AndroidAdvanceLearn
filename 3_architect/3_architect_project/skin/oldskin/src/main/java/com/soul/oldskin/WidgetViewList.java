package com.soul.oldskin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.core.view.ViewCompat;

/**
 * Description: 控件/布局
 * Author: 祝明
 * CreateDate: 2021/10/15 16:57
 * UpdateUser:
 * UpdateDate: 2021/10/15 16:57
 * UpdateRemark:
 */
public class WidgetViewList {

    public static final String TAG = WidgetViewList.class.getSimpleName();

    private final List<String> attributeList = new ArrayList<>();


    /**
     * 既然 一个WidgetViewList 对应 多个个控件
     * 那么这个WIDGET_VIEWS 就是对应 多个控件的
     * 注意⚠️：最终的结果多个WidgetView，是保存到这里的
     */
    private static final List<WidgetView> WIDGET_VIEWS = new ArrayList<>();

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
     * 点击换肤第四步：遍历所有保存的 控件(WidgetView)
     * 然后告诉每一个控件(WidgetView) 去换肤
     */
    public void skinChange() {
        //todo 每一个控件去换肤
        Log.i(TAG, "skinChange");
        for (WidgetView widgetView : WIDGET_VIEWS) {
            widgetView.skinChange();
        }
    }

    /**
     * 此行为目的就是保存 WidgetView 到 静态集合中，因为所有的WidgetView都存放在静态集合中
     *
     * @param attrs
     * @param resultView
     */
    public void saveWidgetView(AttributeSet attrs, View resultView) {
        Log.i(TAG, "saveWidgetView");
        // 由于一个控件 有多个 属性名=属性值，所以需要把多个 属性名=属性值(对象) 保存到集合
        List<AttributeNameAndValue> attributeNameAndValues = new ArrayList<>();

        /**
         *      <TextView
         *         android:layout_width="wrap_content"
         *         android:layout_height="wrap_content"
         *         android:text="测试"
         *         android:textSize="30sp"
         *         android:textColor="@color/skin_my_textColor"
         *         />
         *
         *       遍历，就相当于要遍历上面的这种属性=属性值
         */
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);

            Log.d(TAG, attrName + " == " + attrValue);

            // 这种情况 不换肤
            if (attrValue.startsWith("#") || attrValue.startsWith("?")) {
                continue;
            }

            // 满足以上定义的标记，那就换呗
            if (attributeList.contains(attrName)) {
                // 符合就那获取Value
                // attributeSet.getAttributeValue(i);

                Log.d(TAG, "=============attrValue:" + attrValue);

                // 现在拿到的attrValue可能是 @46464345，所以需要把@给去掉
                attrValue = attrValue.substring(1);
                int attrValueInt = Integer.parseInt(attrValue);
                // 拿到🆔，就可以通过🆔去加载资源
                Log.d(TAG, "resId==============attrValueInt:" + attrValueInt);

                if (attrValueInt != 0) {
                    // 需要被替换的属性+属性值 【注意⚠️】
                    AttributeNameAndValue nameAndValue = new AttributeNameAndValue(attrName, attrValueInt);
                    attributeNameAndValues.add(nameAndValue); // 保存起来
                }
            }
        }

        /**
         * 最终要理解 保存好一个控件 WidgetView
         */
        WidgetView widgetView = new WidgetView(resultView, attributeNameAndValues);
        WIDGET_VIEWS.add(widgetView);
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
                        if (background instanceof Integer) {
                            mView.setBackgroundColor((Integer) background);
                        } else {
                            // mView.setBackground((Drawable) background);
                            // 用兼容包的
                            ViewCompat.setBackground(mView, (Drawable) background);
                        }
                        break;

                    case "textColor":
                        TextView textView = (TextView) mView;
                        textView.setTextColor(SkinResources.getInstance().getColorStateList(attributeNameAndValue.attrValueInt));
                        break;

                    case "src":
                        Object src = SkinResources.getInstance().getBackground(attributeNameAndValue.attrValueInt);
                        if (src instanceof Integer) {
                            ((ImageView) mView).setImageDrawable(new ColorDrawable((Integer) src));
                        } else {
                            ((ImageView) mView).setImageDrawable((Drawable) src);
                        }
                    case "tint":
                        SkinResources skinRes = SkinResources.getInstance();
                        Log.d(TAG, "tint>>>>>>>>>>>" + attributeNameAndValue.attrValueInt + "  -- " + skinRes.getDefaultSkin());
                        break;
                }
            }
        }

    }


    private void changeTypeface(View view) {
        if (view instanceof TextView) {
            ((TextView) view).setTypeface(initTypeface());
        }
    }

}
