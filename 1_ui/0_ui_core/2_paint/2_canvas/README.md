
# Canvas 详解

## 1、概念
  
     画布，通过画笔绘制几何图形、文本、路径和位图等
     
## 2、常用api类型

     常用api分为绘制，变换，状态保存和恢复

1. 绘制几何图形，文本，位图等 

       view drawBitmap(Bitmap bitmap,float left,float top,Paint paint);在指定坐标绘制位图
     
       void drawLine(float startX,float startY,float stopX,float stopY,Paint paint);根据给定的起始点和结束点之间绘制连线
       
       void drawPath(Path path,Paint paint);根据给定的path，绘制连线。
       
       void drawPoint(float X,float y,Paint paint);根据给定的坐标，绘制点。
       
       void drawText(String text,int start,int end,Paint paint);根据给定的坐标，绘制文字
       ...
2. 位置，形状变换等

       void translate(float dx,float dy);平移操作
       
       void scale(float sx,float sy);缩放操作
       
       void rotate(float degrees);旋转操作
       
       void skew(float sx,float sy);倾斜操作
       
       void clipXXX(...);//切割操作，参数指定区域内可以继续绘制
       
       void clipOutXXX(...);反向切割操作，参数指定区域内部不可以绘制
       
       void setMatrix(Matrix matrix);可通过matrix实现平移，缩放，旋转等操作。


































