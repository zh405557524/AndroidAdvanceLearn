package com.soul.animator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019/4/29 下午3:53
 * UpdateUser:
 * UpdateDate: 2019/4/29 下午3:53
 * UpdateRemark:
 */
public class ParallaxLayoutInflater extends LayoutInflater {

    private ParallaxFragment mParallaxFragment;

    int[] attrIds = new int[]{
            R.attr.a_in,
            R.attr.a_out,
            R.attr.x_in,
            R.attr.x_out,
            R.attr.y_in,
            R.attr.y_out

    };

    protected ParallaxLayoutInflater(LayoutInflater original, Context newContext, ParallaxFragment fragment) {
        super(original, newContext);
        mParallaxFragment = fragment;
        setFactory2(new ParallaxFactory(this));

    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new ParallaxLayoutInflater(this, newContext, mParallaxFragment);
    }


    class ParallaxFactory implements Factory2 {

        private final String[] sClassPrefix = {
                "android.widget.",
                "android.view"
        };

        private LayoutInflater inflater;


        public ParallaxFactory(LayoutInflater inflater) {
            this.inflater = inflater;
        }


        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            Log.i("Tag", "onCreateView:");

            View view = null;
            view = createMyView(name, context, attrs);
            if (view != null) {
                final TypedArray array = context.obtainStyledAttributes(attrs, attrIds);
                if (array != null && array.length() > 0) {
                    //获取系统控件的自定义属性
                    final ParallaxViewTag tag = new ParallaxViewTag();
                    tag.alphaIn = array.getFloat(0, 0f);
                    tag.alphaOut = array.getFloat(1, 0f);
                    tag.xIn = array.getFloat(2, 0f);
                    tag.xOut = array.getFloat(3, 0f);
                    tag.yIn = array.getFloat(4, 0f);
                    tag.yOut = array.getFloat(5, 0f);
                    view.setTag(R.id.parallax_view_tag, tag);
                }
                array.recycle();
                mParallaxFragment.getParallaxViews().add(view);
            }
            return view;
        }

        private View createMyView(String name, Context context, AttributeSet attrs) {
            if (name.contains(".")) {
                reflectView(name, null, context, attrs);
            } else {
                for (String prefix : sClassPrefix) {
                    final View view = reflectView(name, prefix, context, attrs);
                    if (view != null) {
                        return view;
                    }
                }
            }

            return null;
        }

        private View reflectView(String name, String prefix, Context context, AttributeSet attributeSet) {
            try {
                return inflater.createView(name, prefix, attributeSet);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }


        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {

            return null;
        }
    }


}
