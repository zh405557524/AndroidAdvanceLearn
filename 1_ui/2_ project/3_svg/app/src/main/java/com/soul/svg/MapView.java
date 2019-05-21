package com.soul.svg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.graphics.PathParser;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019-05-21 16:28
 * UpdateUser:
 * UpdateDate: 2019-05-21 16:28
 * UpdateRemark:
 */
public class MapView extends View {

    private int[] colorArray = new int[]{0xFF239BD7, 0xFF30A9E5, 0xFF80CBF1, 0xFFFFFFFF};
    private Context mContext;
    private List<ProviceItem> itemList;
    private ProviceItem select;
    private Paint paint;
    private RectF totalRect;
    private float scale = 1.0f;

    public MapView(Context context) {
        this(context, null);
    }

    public MapView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        paint = new Paint();
        paint.setAntiAlias(true);
        itemList = new ArrayList<>();
        //加载地图文件
        loadThread.start();
    }

    private Thread loadThread = new Thread(new Runnable() {
        @Override
        public void run() {
            final InputStream inputStream = mContext.getResources().openRawResource(R.raw.china);
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            try {
                builder = factory.newDocumentBuilder();
                final Document doc = builder.parse(inputStream);
                final Element rootElement = doc.getDocumentElement();
                NodeList items = rootElement.getElementsByTagName("path");
                float left = -1;
                float right = -1;
                float top = -1;
                float bottom = -1;

                List<ProviceItem> list = new ArrayList<>();
                for (int i = 0; i < items.getLength(); i++) {
                    Element element = (Element) items.item(i);
                    final String pathData = element.getAttribute("android:pathData");
                    @SuppressLint("RestrictedApi") Path path = PathParser.createPathFromPathData(pathData);
                    final ProviceItem proviceItem = new ProviceItem(path);
                    proviceItem.setDrawColor(colorArray[i % 4]);


                    RectF rect = new RectF();
                    path.computeBounds(rect, true);
                    left = left == -1 ? rect.left : Math.min(left, rect.left);
                    right = right == -1 ? rect.right : Math.max(right, rect.right);
                    top = top == -1 ? rect.top : Math.min(top, rect.top);
                    bottom = bottom == -1 ? rect.bottom : Math.max(bottom, rect.bottom);

                    list.add(proviceItem);
                }
                itemList = list;

                totalRect = new RectF(left, top, right, bottom);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        requestLayout();
                        invalidate();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    });

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取当前控件宽高值
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (totalRect != null) {
            final double mapWidth = totalRect.width();
            final double mapHeight = totalRect.height();

            final float widthScale = (float) (width / mapWidth);
            float heightScale = (float) (height / mapHeight);
            scale = Math.min(widthScale, heightScale);
        }

        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        handleTouch(event.getX() / scale, event.getY() / scale);
        return super.onTouchEvent(event);
    }

    private void handleTouch(float x, float y) {
        if (itemList == null) {
            return;
        }
        ProviceItem selectItem = null;
        for (ProviceItem proviceItem : itemList) {
            if (proviceItem.isTouch(x, y)) {
                selectItem = proviceItem;
            }
        }
        if (selectItem != null) {
            select = selectItem;
            postInvalidate();
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (itemList != null) {
            canvas.save();
            canvas.scale(scale, scale);
            for (ProviceItem proviceItem : itemList) {
                if (proviceItem != select) {
                    proviceItem.drawItem(canvas, paint, false);
                } else {
                    select.drawItem(canvas, paint, true);
                }
            }
        }
    }
}
