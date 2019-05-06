# 屏幕适配

​			原因:Android 设备碎片化，导致app的界面元素在不同屏幕尺寸上显示不一致

​			目的:让布局，布局组件，资源，用户界面流畅，匹配不同屏幕尺寸。

## 屏幕适配常见方式

* 布局适配



​		-避免写死控件尺寸，使用wrap_content,match_parent

​        -LinearLayout xxx:layout_weight = "0.5"

​	    -RelativeLayout xxx:layout_centerInParent = "true"

​		-ContraintLayout xxxx:layout_constraintLeft_toLeftOf = "parent"

​		-Percent-support-lib xxx:layout_widthPercent = "30%"



* 图片资源适配



​		-.9图或者svg图实现缩放

​		-备用位图匹配不同分辨率		



* 用户流程适配



​		-根据业务逻辑执行不同的跳转逻辑

​		-根据别名展示不同的界面



* 限定符适配



​		-分辨率限定符 drawable-hdpi,drawable-xdpi,..

​		-尺寸限定符 layout-small，layout-large,...

​		-最小宽度限定符values-sw360dp,vlues-sw384dp,...

​		-屏幕方向限定符layout-land,layout-port



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

  































