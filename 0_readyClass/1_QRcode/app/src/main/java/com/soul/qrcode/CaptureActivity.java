package com.soul.qrcode;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.zxing.Result;
import com.soul.qr.library.CaptureCallback;
import com.soul.qr.library.camera.CameraManager;
import com.soul.qr.library.decode.DecodeThread;
import com.soul.qr.library.utils.CaptureActivityHandler;
import com.soul.qr.library.utils.InactivityTimer;
import com.soul.qrcode.utils.BeepManager;
import com.soul.qrcode.utils.Utils;

import java.io.IOException;

public class CaptureActivity extends AppCompatActivity implements SurfaceHolder.Callback, CaptureCallback {


    private Context context;//上下文
    private Activity activity;
    private TextView tvLight;//开灯/关灯
    private ToggleButton tbLight;//开灯/关灯
    private SurfaceView scanPreview;//SurfaceView控件
    private RelativeLayout scanContainer;//布局容器
    private RelativeLayout scanCropView;//布局中的扫描框

    private boolean isPause;//是否暂停
    private CaptureActivityHandler handler;
    private Rect mCropRect;//矩形
    private CameraManager cameraManager;//相机管理类
    private InactivityTimer inactivityTimer;//计时器
    private BeepManager beepManger; //蜂鸣器
    private ObjectAnimator objectAnimator;//属性动画
    private boolean isHasSurface;//surfaceView 是否存在


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //保持屏幕常量
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_capture);
        context = this;
        activity = this;
        init();
        initScan();

    }


    private void init() {
        tvLight = findViewById(R.id.tv_light);
        tbLight = findViewById(R.id.tb_light);
        // 闪光灯控制
        tbLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvLight.setText("关灯");
                    Utils.openFlashlight(cameraManager);
                } else {
                    tvLight.setText("开灯");
                    Utils.closeFlashlight();
                }
            }
        });

        //打开相册

        findViewById(R.id.ll_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开相册，做权限判断
                Utils.openAlbum(activity);
            }
        });
    }

    private void initScan() {
        scanPreview = findViewById(R.id.capture_preview);
        scanContainer = findViewById(R.id.capture_container);
        scanCropView = findViewById(R.id.capture_crop_view);
        ImageView scanLine = findViewById(R.id.scan_line);

        // 扫描线性动画(属性动画可暂停)
        float curTranslationY = scanLine.getTranslationY();
        objectAnimator = ObjectAnimator.ofFloat(scanLine, "translationY",
                curTranslationY, Utils.dp2px(this, 170));
        // 动画持续的时间
        objectAnimator.setDuration(4000);
        // 线性动画 Interpolator 匀速
        objectAnimator.setInterpolator(new LinearInterpolator());
        // 动画重复次数
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        // 动画如何重复，从下到上，还是重新开始从上到下
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);

    }


    @Override
    protected void onResume() {
        super.onResume();
        startScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 相册返回
        if (requestCode == Utils.SELECT_PIC_KITKAT && resultCode == Activity.RESULT_OK) { // 4.4及以上图库
            Uri uri = data.getData();
            String path = Utils.getPath(context, uri);
            Result result = Utils.scanningImage(path);
            if (result == null) {
                Toast.makeText(context, "未发现二维码/条形码", Toast.LENGTH_LONG).show();
            } else {
                // 数据返回
                String recode = Utils.recode(result.toString());
                Toast.makeText(context, recode, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onPause() {
        pauseScan();
        super.onPause();
    }


    /**
     * 开始扫描
     */
    private void startScan() {
        inactivityTimer = new InactivityTimer(this);
        beepManger = new BeepManager(this);

        if (isPause) {
            objectAnimator.resume();
            isPause = false;
        } else {
            //扫描动画开始
            objectAnimator.start();
        }
        //初始化相机管理
        cameraManager = new CameraManager(context);
        handler = null;//重置handler
        if (isHasSurface) {
            initCamera(scanPreview.getHolder());
        } else {
            //安装好等待 初始化相机
            scanPreview.getHolder().addCallback(this);
        }
        inactivityTimer.onResume();
    }


    private void pauseScan() {
        if (handler != null) {
            //handler 退出，同步 置空
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManger.close();
        cameraManager.closeDriver();
        if (!isHasSurface) {
            //remove
            scanPreview.getHolder().removeCallback(this);
        }
        //动画暂停
        objectAnimator.pause();
        isPause = true;
    }

    /**
     * 初始化相机
     *
     * @param holder
     */
    private void initCamera(SurfaceHolder holder) {
        if (holder == null) {
            throw new IllegalStateException(" SurfaceHolder is null");
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            cameraManager.openDriver(holder);
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager, DecodeThread.ALL_MODE);
            }

            initCrop();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 初始化矩形
     */
    private void initCrop() {
        // 获取相机的宽高
        int cameraWidth = cameraManager.getCameraResolution().y;
        int cameraHeight = cameraManager.getCameraResolution().x;

        // 获取布局中扫描框的位置信息
        int[] location = new int[2];
        scanCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - Utils.getStatusBarHeight(context);

        // 获取截取的宽高
        int cropWidth = scanCropView.getWidth();
        int cropHeight = scanCropView.getHeight();

        // 获取布局容器的宽高
        int containerWidth = scanContainer.getWidth();
        int containerHeight = scanContainer.getHeight();

        // 计算最终截取的矩形的左上角顶点x坐标
        int x = cropLeft * cameraWidth / containerWidth;
        // 计算最终截取的矩形的左上角顶点y坐标
        int y = cropTop * cameraHeight / containerHeight;

        // 计算最终截取的矩形的宽度
        int width = cropWidth * cameraWidth / containerWidth;
        // 计算最终截取的矩形的高度
        int height = cropHeight * cameraHeight / containerHeight;

        // 生成最终的截取的矩形
        mCropRect = new Rect(x, y, width + x, height + y);

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (!isHasSurface) {
            isHasSurface = true;
            initCamera(surfaceHolder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        isHasSurface = false;
    }

    @Override
    public Rect getCropRect() {
        return mCropRect;
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    @Override
    public CameraManager getCameraManager() {
        return cameraManager;
    }

    @Override
    public void handleDecode(Result result, Bundle bundle) {
        //解码 扫码成功之后回调的方法
        inactivityTimer.onActivity();
        beepManger.playBeepSoundAndVibrate();

        //将扫码的结果返回mainacitivty
        final Intent intent = new Intent();
        intent.putExtra(Utils.BAR_CODE, result.getText());
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        if (objectAnimator != null) {
            objectAnimator.end();
        }
        super.onDestroy();
    }
}
