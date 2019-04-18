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
        mPaint.setFilterBitmap(true);//设置双线性过滤
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

* setColorFilter(new LightingColorFilter(0x00ffff, 0x000000));//设置颜色过滤器

* setFilterBitmap(true);//设置双线性过滤

* setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));//设置画笔遮罩滤镜，传入度数和样式

* setTextScaleX(2);//设置文本缩放倍数

* setTextSize(38);//设置字体大小

* setTextAlign(Paint.Align.LEFT);//对齐方式

* setUnderlineText(true);//设置下划线

## 3、Paint详解-颜色相关

      setColor(int color)参数具体的颜色值，16进制数值，0xffff0000
      setARGB(int a,int r,int g,int b) 参数分别透明度，红，绿，蓝。0~255数值
      setShader(Shader shader) 参数着色器对象，一般使用shader的几个子类
            LinearGradient:线性渲染
            RadialGradient:环形渲染
            SweepGradient :扫描渲染
            BitmapShader  :位图渲染
            ComposeShader :组合渲染，例如 LinearGradient+BitmapShader 
      setColorFilter(ColorFilter colorFilter) 设置颜色过滤。一般是ColorFilter三个子类
            LightingColorFilter:光照效果
            PorterDuffColorFIlter:指定已颜色和一种PorterDuff.Mode 与绘制对象进行合成
            ColorMatrixColorFilter:使用一个ColorMatrix来对颜色进行处理
1. linearGradient线性渲染
        
       构造方法：
       LinearGradient(float x0,float x1,float y1,int color0,int color1,Float[]{z1,z2},Shader.TileMode tile)
       参数
       x0 y0 x1 y1:渐变的两个端点的位置
       color0 color1 是端点的颜色
       z1,z2 颜色在布局中开始的比例
       tile：端点范围之外的着色规则，类型是TileMode
       
       TileMode.CLAMP:绘制区域超过渲染区的部分，重复排版
       TileMode.CLAMP:绘制区域超过渲染区的部分，会以最后一个像素拉伸排版
       TileMode.MIRROR:绘制区域超过渲染区的部分,镜像翻转排版
       
       使用：
       mShader = new LinearGradient(0,0,500,500,new int[]{Color.RED,Color.BLUe},
       null,Shader.TileMode.CLAMP);
       mPaint.setShader(mShader);
       canvas.drawCircle(250,250,250,mPaint);

2. RadialGradient 环形渲染

        构造方法:
        RadialGradient(float centerX,float cententY,float radius,int centerColor,int edgeColr,ileMode tileMode)
        
        参数:
        centerX centerY:辐射中心的坐标
        radius:辐射半径
        centerColor:辐射中心的颜色
        edgeColor:辐射边缘的颜色
        tileMode:辐射范围之外的着色规则，类型是TIleMode
        
        使用:
        mShader = new RadialGradient(250, 250, 250, new int[]{Color.GREEN, Color.YELLOW, Color.RED},
                null, Shader.TileMode.CLAMP)
        mPaint.setShader(mShader);
        canvas.drawCircle(250,250,250,mPaint);
        
3. SweepGradient扫描渲染
   
         构造方法:
         SweepGradient(float cx,float cy,int color0,int color1)
         
         参数:
         cx cy :扫描中心
         color0:扫描的其实颜色
         color1:扫描的终止颜色
         
         使用:
         mShader  = new SweepGradient(250,250,Color.RED,Color.GREEN);
         mPaint.setShader(mShader);
         canvas.drawCircle(250,250,250,mPaint);
         
4. 位图渲染
 
         构造方法
         BitmapShader(Bitmap bitmap,Shader.TileMode titleX,Shader.TileMode tileY)
         
         参数
         bitmap:用来做模板的bitmap对象
         tileX:横向着色规则，类型是TileMode
         tileY:纵向着色规则，类型是TileMode
         
         使用：
         mShader = new BitmapShader(mBitmap,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
         mPaint.setShader(mShader);
         canvas.drawCircle(250,250,250,mPaint);
5. 组合渲染

         构造方法：
         ComposeShader(Shader shaderA,Shader shaderB,PorterDuff.Mode mode)
         
         参数
         shaderA,shaderB:两个相继使用的Shader
         mode:两个Shader的叠加模式，即ShaderA和ShaderB 应该怎样共同绘制。它的类型是PorterDuff.Mode
         
         使用：
         BitmapShader bitmapShader = new BitmapShader(mBItmap,
         Shader.Tile.REPEAT,Shader.TileMode.REPEAT);
         LinearGradient linearGradient = new LInearGradient(0,0,1000,16000,new 
         int[]{Color.RED,Color.GREEN,Color.BLUE},null,Shader.TileMode.CLAMP);
         mShader = new ComposeShader(bitmapShader,linearGradient,PorterDuff.Mode.MULTIPLY);
          mPaint.setShader(mShader);
         canvas.drawCircle(250,250,250,mPaint);
         
7. PorterDuff.Mode图层混合模式
 
        它将所绘制图形的像素与Canvas中对应位置的像素按照一定规则进行混合，形成新的像素值，从而更新Canvas中
        最终的像素颜色值。
        
        18种模式
        Mode.CLEAR    Mode.SRC      Mode.DST
        Mode.SRC_OVER Mode.DST_OVER Mode.SRC_IN
        Mode.DST_IN   Mode.SRC_OUT  Mode.DST_OUT
        Mode.SRC_ATOP Mode.DST_ATOP Mode.XOR
        Mode.DARKEN   Mode.LIGHTEN  Mode.MULTIPLY
        Mode.SCREEN   Mode.OVERLAY  Mode.ADD
        
        
~~~
            //所有绘制不会提交到画布上
            new PorterDuffXfermode(PorterDuff.Mode.CLEAR),
            //显示上层绘制的图像
            new PorterDuffXfermode(PorterDuff.Mode.SRC),
            //显示下层绘制图像
            new PorterDuffXfermode(PorterDuff.Mode.DST),
            //正常绘制显示，上下层绘制叠盖
            new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
            //上下层都显示，下层居上显示
            new PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
            //取两层绘制交集，显示上层
            new PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
            //取两层绘制交集，显示上层
            new PorterDuffXfermode(PorterDuff.Mode.DST_IN),
            //取上层绘制非交集部分，交集部分变透明
            new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
            //取下层绘制非交集部分，交集部分变透明
            new PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
            //取上层交集部分与下层非交集部分
            new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
            //取下层交集部分与下层非交集部分
            new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
            //去除两图层交集部分
            new PorterDuffXfermode(PorterDuff.Mode.XOR),
            //取两图层全部区域，交集本分颜色加深
            new PorterDuffXfermode(PorterDuff.Mode.DARKEN),
            //取两图层全部区域，交集本分颜色点亮
            new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
            //取两图层交集部分，颜色叠加
            new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
            //取两图层全部区域，交集部分虑色
            new PorterDuffXfermode(PorterDuff.Mode.SCREEN),
            //取两图层全部区域，交集部分饱和度相加
            new PorterDuffXfermode(PorterDuff.Mode.ADD),
            //取两图层全部区域，交集部分叠加
            new PorterDuffXfermode(PorterDuff.Mode.OVERLAY)
~~~
  ![18种图层混合模式](https://github.com/zh405557524/AndroidAdvanceLearn/blob/master/1_ui/0_ui_core/2_paint/1_paint/device-2019-04-16-212639.png)
8. 离屏绘制

        通过使用离屏缓冲，把要绘制的内容单独绘制在缓冲层，保证Xfermode的使用不会出现错误的结果。
        
        使用离屏绘缓冲有两种方式:
        Canvas.saveLayer() 可以做短时的离屏缓冲，在绘制之前保存，绘制之后恢复:
        int saveId = canvas.saveLayer(0,0,width,height,Canvas.ALL_SAVE_FLAG);
        Canvas.drawBItmap(rectBitmap,0,0,paint);画方
        Paint.setXfermode(xfermode);//设置xfermode
        Canvas.drawBitmap(circleBitmap,0,0,paint);//画圆
        Paint.setXfermode(null);//用完及时清除Xfermode
        cavans.restoreToCount(saveId);
        
        
        View.setLayerType() 直接把整个View都绘制在离屏缓冲中。
        setLayerType(LAYER_TYPE_HARDWARE)使用GPU来缓冲，
        setLayerTYpe(LAYER_TYPE_SOFTWARE) 使用一个bitmap来缓冲

## 4、 paint详解-效果相关

1. LightingColorFilter滤镜

       LightingColorFilter
       构造方法：
       LightingColorFilter(int mul,int add)

       参数:
       mul 和 add 都是和颜色值格式相同的int值，其中mul用来和目标像素相乘，add用来和目标像素相加:
       R` = R*mul.R/0xff+add.R
       G` = G*mul.G/0xff+add.G
       B` = B*mul.B/0xff+add.B
       
       使用:
       ColorFilter lighting = new LightingColorFilter(0x00ffff,0x000000);
       paint.setColorFilter(lighting);
       canvas.drawBitmap(bitmap,0,0,paint);

2. PorterDuffColorFilter 滤镜

       PorterDuffColorFilter
       构造方法:
       PorterDuffColorFilter(int color,PorterDuff.Mode mode)
       
       参数:
       color,具体的颜色值，例如Color.RED
       mode ,指定PorterDuff.Mode 混合模式

       使用:
       PorterDuffColorFilter porterDuffColorFilter = new 
       PorterDuffColorFilter(Color.RED,PorterDuff.Mode.DARKEN);
       paint.setColorFilter(porterDuffColorFilter);
       cavans.drawBitmap(mBitmap,100,0,paint);

3. ColorMatrixColorFilter 滤镜

       ColorMatrixColorFilter
       构造方法:
       ColorMatrixColorFilter(float[] colorMatrix);
       
       参数:
       colorMatrix 矩阵数组 
       
       使用:
       float[] colorMatrix = {
            1,0,0,0,0,//red
            0,1,0,0,0,//green
            0,0,1,0,0,//blue
            0,0,0,1,0 //alpha
       }
       mColorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
       mPaint.setColorFilter(mColorMatrixColorFilter);
       canvas.drawBitmap(mBitmap,100,0,mPaint);
    ![色彩矩阵分析](https://raw.githubusercontent.com/zh405557524/AndroidAdvanceLearn/master/1_ui/0_ui_core/2_paint/1_paint/1671555555375.jpg)  
    
    






















