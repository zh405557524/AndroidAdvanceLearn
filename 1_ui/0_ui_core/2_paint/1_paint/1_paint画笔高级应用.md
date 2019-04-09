# Paint 详解
## 1、概念
      画笔，保存了绘制几何图形，文本，和位图的样式和颜色
      
## 2、常用api
      常用api主要如颜色，效果和文本相关等。
~~~
mPaint = new Paint();//初始化
        mPaint.setColor(Color.RED);//设置颜色
        mPaint.setARGB(255, 255, 255, 0);//设置paint对象颜色，范围0~255
        mPaint.setAlpha(200);//设置alpha 不透明，范围0~255
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);//描边效果
        mPaint.setStrokeWidth(4);//描边宽度
        mPaint.setStrokeCap(Paint.Cap.ROUND);//圆角效果
        mPaint.setStrokeJoin(Paint.Join.MITER);//拐角风格
        mPaint.setShader(new SweepGradient(200, 200, Color.BLUE, Color.RED));//设置环形渲染器
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));//设置图层混合模式
        mPaint.setColorFilter(new LightingColorFilter(0x00ffff, 0x000000));//设置颜色过滤器
        mPaint.setFilterBitmap(true);//设置颜色过滤器
        mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));//设置画笔遮罩滤镜，传入度数和样式
        mPaint.setTextScaleX(2);//设置文本缩放倍数
        mPaint.setTextSize(38);//设置字体大小
        mPaint.setTextAlign(Paint.Align.LEFT);//对齐方式
        mPaint.setUnderlineText(true);//设置下划线

        String str = "Android 高级工程师";
        final Rect rect = new Rect();
        mPaint.getTextBounds(str, 0, str.length(), rect);//测量文本大小，将文本大小信息存放到rect中
        mPaint.measureText(str);//获取文本的宽
        mPaint.getFontMetrics();//获取字体度量对象
~~~

* setStyle(Paint.Style style) //设置描边效果
   ~~~  
   FILL,//填充
   STROKE,//描边
   FILL_AND_STROKE//填充并表变
   ~~~

* setStrokeWidth(float width ) //设置表变宽度
 
* setStrokeCap(Paint.Cap cap) //设置圆角效果
            
~~~
 BUTT, //默认
 ROUND,//圆角
 SQUARE;//方形
~~~
* setStrokeJoin(Paint.Join join) //设置拐角风格     

~~~
        MITER,//尖角
        ROUND,//切除尖角
        BEVEL;//圆角
~~~
* setShader(Shader shader);//设置渲染器

* setXfermode(Xfermode xfermode);//设置图层混合模式





























