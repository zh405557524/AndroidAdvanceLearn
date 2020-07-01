package com.soul.customglide.glide.load_data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import com.soul.customglide.glide.resource.Value;
import com.soul.customglide.utils.Tool;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description：加载外部资源管理类
 * @Author：祝明
 * @ProjectName:CustomGlide
 * @CreateData：2020/6/5 23:31
 */
public class LoadDataManger implements ILoadData, Runnable {

    private final String TAG = LoadDataManger.class.getSimpleName();

    private String path;

    private ResponseListener mResponseListener;

    private Context mContext;

    @Override
    public Value loadResource(String path, ResponseListener responseListener, Context context) {
        this.path = path;
        mResponseListener = responseListener;
        mContext = context;
        Uri uri = Uri.parse(path);

        if ("HTTP".equalsIgnoreCase(uri.getScheme()) || "HTTPS".equalsIgnoreCase(uri.getScheme())) {
            new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>()).execute(this);
        }


        return null;
    }

    @Override
    public void run() {

        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;

        try {
            URL url = new URL(path);
            URLConnection urlConnection = url.openConnection();
            httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setConnectTimeout(50000);
            final int responseCode = httpURLConnection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode) {
                inputStream = httpURLConnection.getInputStream();
                int w = 1920;
                int h = 1080;

                // 不需要使用复用池，拿去图片内存
                BitmapFactory.Options options2 = new BitmapFactory.Options();
                //   既然是外部网络加载图片，就不需要用复用池 Bitmap bitmapPoolResult = bitmapPool.get(w, h, Bitmap.Config.RGB_565);
                //   options2.inBitmap = bitmapPoolResult; // 如果我们这里拿到的是null，就不复用
                options2.inMutable = true;
                options2.inPreferredConfig = Bitmap.Config.RGB_565;
                options2.inJustDecodeBounds = false;
                // inSampleSize:是采样率，当inSampleSize为2时，一个2000 1000的图片，将被缩小为1000 500， 采样率为1 代表和原图宽高最接近
                options2.inSampleSize = Tool.sampleBitmapSize(options2, w, h);
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options2); // 真正的加载
                //成功
                //切换主线程
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Value value = Value.getInstance();
                        value.setBitmap(bitmap);
                        mResponseListener.responseSuccess(value);
                    }
                });

            } else {
                //失败
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {

                        mResponseListener.responseException(new IllegalStateException("请求失败 请求码：" + responseCode));
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }


}
