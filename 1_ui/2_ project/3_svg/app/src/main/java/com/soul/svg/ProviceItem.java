package com.soul.svg;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019-05-21 16:34
 * UpdateUser:
 * UpdateDate: 2019-05-21 16:34
 * UpdateRemark:
 */
public class ProviceItem {

    /**
     * 绘制路径
     */
    private Path path;

    /**
     * 绘制颜色
     */
    private int drawColor;


    public ProviceItem(Path path) {
        this.path = path;
    }

    public void setDrawColor(int drawColor) {
        this.drawColor = drawColor;
    }

    public void drawItem(Canvas canvas, Paint paint, boolean isSelect) {
        if (isSelect) {
            //绘制内部颜色
            paint.clearShadowLayer();
            paint.setStrokeWidth(1);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(drawColor);
            canvas.drawPath(path, paint);
            //绘制边界
            paint.setStyle(Paint.Style.STROKE);
            int strokeColor = 0xFFD0E8F4;
            paint.setColor(strokeColor);
            canvas.drawPath(path, paint);
        } else {

            paint.setStrokeWidth(2);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            paint.setShadowLayer(8, 0, 0, 0xffffff);
            canvas.drawPath(path, paint);
            //绘制边界
            paint.clearShadowLayer();
            paint.setColor(drawColor);
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(2);
            canvas.drawPath(path, paint);

        }
    }

    public boolean isTouch(float x, float y) {
        final RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        final Region region = new Region();
        region.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));

        return rectF.contains((int) x, (int) y);
    }
}























