package com.soul.customglide.glide.cache.disk;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.soul.customglide.glide.pool.BitmapPool;
import com.soul.customglide.glide.resource.Value;
import com.soul.customglide.utils.Tool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Description：磁盘缓存的封装
 * @Author：祝明
 * @ProjectName:CustomGlide
 * @CreateData：2020/6/5 14:21
 */
public class DiskLruCacheImpl {

    private final String TAG = DiskLruCacheImpl.class.getSimpleName();

    private static final int APP_VERSION = 1;//一旦修改这个版本号，之前的缓存失效
    private static final long MAX_SIZE = 1024 * 1024 * 10;
    private static final int VALUE_COUNT = 1;
    private final String DISKLRU_CACHE_DIR = "disk_lru_cache_dir";//磁盘缓存的目录
    private DiskLruCache mDiskLruCache;


    public DiskLruCacheImpl() {
        //获取diskLruCache对象
        File file = new File("/sdcard" + File.separator + DISKLRU_CACHE_DIR);
        try {
            mDiskLruCache = DiskLruCache.open(file, APP_VERSION, VALUE_COUNT, MAX_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 存放数据
     *
     * @param key
     * @param value
     */
    public void put(String key, Value value) {
        Tool.checkNotEmpty(key);
        OutputStream outputStream = null;
        DiskLruCache.Editor edit = null;
        try {
            edit = mDiskLruCache.edit(key);
            outputStream = edit.newOutputStream(0);
            Bitmap bitmap = value.getBitmap();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);//把bitmap写入到outStream
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                edit.abort();
            } catch (IOException ex) {
                ex.printStackTrace();
                Log.e(TAG, "put: editor.abort() e:" + e.getMessage());
            }
        } finally {
            try {
                edit.commit();//sp 一定要提交
                mDiskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }


    /**
     * 获取数据
     *
     * @param key
     */
    public Value get(String key, BitmapPool bitmapPool) {
        Tool.checkNotEmpty(key);
        InputStream inputStream = null;
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (snapshot != null) {
                Value value = Value.getInstance();
                inputStream = snapshot.getInputStream(0);

                //使用复用池并对图片进行压缩
                int w = 1920;
                int h = 1080;
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap bitmapPoolResult = bitmapPool.get(w, h, Bitmap.Config.RGB_565);
                options.inBitmap = bitmapPoolResult;
                options.inMutable = true;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inJustDecodeBounds = false;
                options.inSampleSize = Tool.sampleBitmapSize(options, w, h);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                value.setBitmap(bitmap);
                value.setKey(key);
                return value;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "get: inputStream.close(); e:" + e.getMessage());
                }
            }
        }

        return null;
    }


}




























