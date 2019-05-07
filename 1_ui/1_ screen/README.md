# 屏幕适配

​			原因:Android 设备碎片化，导致app的界面元素在不同屏幕尺寸上显示不一致

​			目的:让布局，布局组件，资源，用户界面流畅，匹配不同屏幕尺寸。

## 屏幕适配常见方式

* 布局适配
  - 避免写死控件尺寸，使用wrap_content,match_parent
  - LinearLayout xxx:layout_weight = "0.5"
  - RelativeLayout xxx:layout_centerInParent = "true"
  - ContraintLayout xxxx:layout_constraintLeft_toLeftOf = "parent"
  - Percent-support-lib xxx:layout_widthPercent = "30%"

* 图片资源适配

  * .9图或者svg图实现缩放
  * 备用位图匹配不同分辨率		

  

* 用户流程适配
  * 根据业务逻辑执行不同的跳转逻辑
  * 根据别名展示不同的界面

* 限定符适配

  - 分辨率限定符 drawable-hdpi,drawable-xdpi,..
  - 尺寸限定符 layout-small，layout-large,...
  - 最小宽度限定符values-sw360dp,vlues-sw384dp,...
  - 屏幕方向限定符layout-land,layout-port

## 屏幕适配-自定义View

* 以一个特定宽度尺寸的设备为参考，在view的加载过程中，根据当前设备的实际像素换算出目标像素，再作用在控件上。

  ~~~java
   @Override
      protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
          super.onMeasure(widthMeasureSpec, heightMeasureSpec);
          if (flag) {
              flag = false;
              final float scaleX = UIUtils.getInstance(getContext()).getHorizontalScaleValue();//获取横向缩放比
              final float scaleY = UIUtils.getInstance(getContext()).getVerticalScaleValue();//获取竖向缩放比
              final int childCount = this.getChildCount();
              for (int i = 0; i < childCount; i++) {
                  final View child = this.getChildAt(i);//重新设置子view的布局属性，再进行view的测量
                  final LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                  layoutParams.width = (int) (layoutParams.width * scaleX);//换算宽度目标值
                  layoutParams.height = (int) (layoutParams.height * scaleY);//换算高度目标值
                  layoutParams.leftMargin = (int) (layoutParams.leftMargin * scaleX);
                  layoutParams.rightMargin = (int) (layoutParams.rightMargin * scaleX);
                  layoutParams.topMargin = (int) (layoutParams.topMargin * scaleY);
                  layoutParams.bottomMargin = (int) (layoutParams.bottomMargin * scaleY);
              }
          }
      }
  ~~~

  

  ## 屏幕适配-百分比布局

* 以父容器尺寸做为参考，在view的加载过程，根据当前父容器实际尺寸换算出目标尺寸，再作用在view上。

  ~~~java
  protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
    //获取父容器的宽高
    int width = MeasureSpec.getSize(widthMeasureSpec);
    int height = MeasureSpec.getSize(heightMeasureSpec);
    for(int i = 0;i<getChildCount();i++){
      View child = getChildAt(i);//重新设置子view的布局属性，再进行View的测量
      LayoutParams lp = (LayoutParams)child.getLayoutParams();
      float widthPercent = ((LayoutParams)lp).widthPercent;//自定百分比属性
      if(widthPercent>0){
        lp.width = (int)(width*widthPercent);//设置当前view在父容器中尺寸占比
      }
      
    }
  	super.onMeasure(widthMeasureSpec,heightMeasureSpec);
  }
  ~~~

  

## 屏幕适配-像素密度

* 修改density,scaleDensity,densityDpi值-直接更改系统内部对于目标尺寸而言的像素密度。

~~~java
    private static float mAppDensity;
    private static float mAppScaledDensity;

    private static final float WIDTH = 480;//参考像素密度

    /**
     * 设置屏幕像素密度适配
     */
    protected static void setDensity(final Application application, Activity activity) {
        //获取当前屏幕像素，
        final DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        if (mAppDensity == 0) {
            mAppDensity = displayMetrics.density;
            mAppScaledDensity = displayMetrics.scaledDensity;//字体缩放比例
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    //字体缩放比例发生改变，修改字体比例
                    if (newConfig != null && newConfig.fontScale > 0) {
                        mAppScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });

        }
        //根据标准的像素，计算出新的比例
        float targetDensity = displayMetrics.widthPixels / WIDTH;
        final float targetScaleDensity = targetDensity * (mAppScaledDensity / mAppDensity);
        int targetDensityDpi = (int) (targetDensity * 160);
        //重新给activity的DisplayMetrics设值
        final DisplayMetrics displayMetrics1 = activity.getResources().getDisplayMetrics();
        displayMetrics1.density = targetDensity;
        displayMetrics1.scaledDensity = targetScaleDensity;
        displayMetrics1.densityDpi = targetDensityDpi;
    }

~~~



## 屏幕适配-刘海屏

* Android官方9.0刘海屏适配策略

   - 如果非全屏模式(有状态栏)，则app不受刘海屏的影响，刘海屏的高就是状态栏的高。

   - 如果全屏模式，app未适配刘海屏，系统会对界面做特殊处理，竖屏向下移动，横屏向右移动。

     

* Android9.0刘海屏适配-全屏模式下刘海区黑边(内容区域下挫)问题。

  ~~~java
  //1 全屏设置
  requestWindowFeature(Window.FEATURE_NO_TITLE);
  window.setFLags(LayoutParams.FLAG_FULLSCREEN,WindowManager.LayutParams.FLAG_FULLSCREEN);
  
  //2 让内容区延伸需要先判断是否有刘海
  WindowManger.LayoutParams params = window.getAttributes();
   /**
               *  * @see #LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT 全屏模式，内容下移，非全屏不受影响
               *  * @see #LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES 允许内容去延伸进刘海区
               *  * @see #LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER 不允许内容延伸进刘海区
               */
  params.layoutInDisplayCutoutMode = WindowManger.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
  window.setAttributes(params);
  
  //3 沉浸式
  int flags = View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
  int visibility = window.getDecorView().getSystemUiVisibility();
  visibility |= flags;
  vindow.getDecorView().setSystemUiVisibility(visibolity);
  
  
  
  
   private boolean hasDisplayCutout(Window window) {
  
          DisplayCutout displayCutout;
          View rootView = window.getDecorView();
          WindowInsets insets = rootView.getRootWindowInsets();
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && insets != null){
              displayCutout = insets.getDisplayCutout();
              if (displayCutout != null){
                  if (displayCutout.getBoundingRects() != null && displayCutout.getBoundingRects().size() > 0 && displayCutout.getSafeInsetTop() > 0){
                      return true;
                  }
              }
          }
          return false; //因为模拟器原因，这里设置成true
      }
  
  ~~~



* Android9.0刘海屏适配-避开刘海区域

  ~~~java
  //获取刘海高度，通常情况下刘海高度不会超过状态栏高度。
  int height = getStateusBarHeight();//状态栏高度
  TextView tv = findViewById(R.id.text);
  RelativeLayout.LayputParams rlp = (RelativeLayout.LayoutParams)tv.getLayoutParams();
  //设置控件的margin
  rlp.topMargin = height;
  tv.setLayoutParams(rlp);
  
  //设置父容器的padding
  RelativeLayout layout = findViewById(R.id.container);
  layout.setPadding(layout.getPadingLeft(),height,layout.getPaddingRight(),layout.getPaddingBottom());
  
  
  
  //通常情况下，刘海的高就是状态栏的高
      public int getStateusBarHeight(){
          int resID = getResources().getIdentifier("status_bar_height", "dimen", "android");
          if (resID > 0){
              return getResources().getDimensionPixelSize(resID);
          }
          return 96;
      }
  ~~~



* 其他手机厂商(华为，小米，oppo，vivo) 适配
  - 华为:https://devcenter-test.huawei.com/consumer/cn/devservice/doc/50114
  - 小米:https://dev.mi.com/console/doc/detail?pld=1293
  - Oppo:https://open.oppomobile.com/service/message/detail?id=61876
  - Vivo:https://dev.vico.com.cn/documentCenter/doc/103





















