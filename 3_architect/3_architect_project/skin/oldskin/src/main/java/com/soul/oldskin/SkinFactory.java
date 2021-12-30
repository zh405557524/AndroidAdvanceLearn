package com.soul.oldskin;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * Description: xml 解析工厂
 * Author: 祝明
 * CreateDate: 2021/10/15 11:54
 * UpdateUser:
 * UpdateDate: 2021/10/15 11:54
 * UpdateRemark:
 */
public class SkinFactory implements LayoutInflater.Factory2, Observer {


    public static final String TAG = SkinFactory.class.getSimpleName();

    /**
     * 例如：new TextView(context, attr)  或  new Button(context, attr)  或 new Button(context, attr) ...
     * 所以需要建立 获取控件的构造方法 参数类型，好去创建构造对象
     */
    static final Class<?>[] mConstructorSignature = new Class[]{Context.class, AttributeSet.class};

    /**
     * 进行缓存起来，因为 ClassLoader getConstructor 是耗费性能的
     */
    private static final Map<String, Constructor<? extends View>> sConstructorMap = new HashMap<>();


    private Activity activity;
    /**
     * android 包名前缀
     */
    private static final String[] sClassPrefixList = {"android.widget.", "android.webkit.", "android.app."};
    private final WidgetViewList mWidgetViewList;


    public SkinFactory(Activity activity) {
        this.activity = activity;
        mWidgetViewList = new WidgetViewList(activity);

    }

    /**
     * 拦截View的创建，此View指的是 布局文件中 某个控件，例如：TextView，Button，自定义控件，...
     *
     * @param parent  父控件View
     * @param name    控件的名字，例如：TextView
     * @param context 上下文环境
     * @param attrs   控件的属性，例如：TextView(定义了很多的属性 宽 高 text textColor ...)
     * @return 返回创建好的View，如果返回null，系统内部就会创建View
     */
    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name
            , @NonNull Context context, @NonNull AttributeSet attrs) {
        // 此createViewFromTag，目的是去创建系统的控件 例如：TextView，Button，ImageView
        View resultView = createViewFromTag(parent, name, context, attrs);
        switch (name) {
            case "ImageView":
                resultView = new AppCompatImageView(context, attrs);
                break;
        }

        // 如果为null，可认为是自定义View，所以需要传入 name + "" ---> 自定义控件包名和控件名 + ""
        if (null == resultView) {
            resultView = createView(name, "", context, attrs);
        }

        /**
         * WidgetViewList: 可以理解成布局文件 布局文件里面有多个控件TextView，某个控件就是WidgetView
         *
         * saveWidgetView: 这里保存的resultView attrs 就是为了 构建个WidgetVeiw
         */
        mWidgetViewList.saveWidgetView(attrs, resultView);

        return resultView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return activity.onCreateView(name, context, attrs); // null
    }

    /**
     * 点击换肤第二步：收到 被观察者 发出的---> 改变通知
     * 当被被观察者发生改变时回调
     */
    @Override
    public void update(Observable o, Object arg) {
        Log.i(TAG, "update");
        mWidgetViewList.skinChange();
    }

    /**
     * （源码498 -> 863行）
     * 创建系统控件 例如：TextView，ImageView，Button ...
     */
    private View createViewFromTag(View parent, String name, Context context, AttributeSet attrs) {
        View view = null;
        for (String s : sClassPrefixList) {
            view = createView(name, s, context, attrs);
            if (view != null) {
                break; // 结束循环♻️
            }
        }
        return view;
    }

    /**
     * （源码615行）
     * 真正进行反射的方式创建View
     * 1.当传入 name + s  -->  控件名 + 控件包名， 需要创建系统的控件。
     * 2.当传入 name + "" -->  控件名 + ""        这个控件名就是完整的 自定义 包名+自定义控件名
     */
    private View createView(String name, String prefix, Context context, AttributeSet attrs) {
        Constructor<? extends View> constructors = sConstructorMap.get(name);

        if (null == constructors) {

            Log.d(TAG, "需要反射找的>>>>>>>>>> prefix + name：" + prefix + name);
            // 反射找
            try {
                Class<? extends View> classz = context.getClassLoader().loadClass(prefix + name).asSubclass(View.class);
                Constructor<? extends View> constructor = classz.getConstructor(mConstructorSignature);
                constructor.setAccessible(true);
                sConstructorMap.put(name, constructor); // 缓存一份
                return constructor.newInstance(context, attrs);
            } catch (Exception e) {
            }

        } else {
            try {
                constructors.setAccessible(true);
                return constructors.newInstance(context, attrs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}





