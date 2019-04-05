
#绘制流程
## 一、measure
1. android源码的测量流程
* ViewRootImpl 
```
  private void performMeasure(int childWidthMeasureSpec, int childHeightMeasureSpec) {
        Trace.traceBegin(Trace.TRACE_TAG_VIEW, "measure");
        try {
            mView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        } finally {
            Trace.traceEnd(Trace.TRACE_TAG_VIEW);
        }
    }
 ```
 * View
 ```
 public final void measure(int widthMeasureSpec, int heightMeasureSpec) {
        
        ....
        
        final boolean specChanged = widthMeasureSpec != mOldWidthMeasureSpec
                || heightMeasureSpec != mOldHeightMeasureSpec;
        ....
       
       final boolean needsLayout = specChanged
                && (sAlwaysRemeasureExactly || !isSpecExactly || !matchesSpecSize);

        if (forceLayout || needsLayout) {
          ....
            if (cacheIndex < 0 || sIgnoreMeasureCache) {
                // measure ourselves, this should set the measured dimension flag back
                onMeasure(widthMeasureSpec, heightMeasureSpec);
                ...
            } else {
               ....
            }

          ...
        }

      .....
    }
 ```
 
 ```
 protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }
```
  
  
```
protected final void setMeasuredDimension(int measuredWidth, int measuredHeight) {
        ...
        setMeasuredDimensionRaw(measuredWidth, measuredHeight);
    }

```

```
private void setMeasuredDimensionRaw(int measuredWidth, int measuredHeight) {
        mMeasuredWidth = measuredWidth;
        mMeasuredHeight = measuredHeight;

        mPrivateFlags |= PFLAG_MEASURED_DIMENSION_SET;
    }

```
2. View 模式，尺寸
   
        MeasureSpec 32 位 int值。
        0000000000000000000000000000
        SpecMode(前2位) + SpecSize（后30）
        
        //父容器不对VIew做任何限制，系统内部使用
        public static final int UNSPECIFIED = 0 << MODE_SHIFT; 0000000000000000000000000000
        //父容器检测出VIew的大小，VIew的大小就是SpecSize LayoutPamras match_parent 固定大小
        public static final int EXACTLY     = 1 << MODE_SHIFT; 0100000000000000000000000000
        //父容器指定一个可用大小，View的大小不能超过这个值。layoutPamras wrap_content
        public static final int AT_MOST     = 2 << MODE_SHIFT; 1000000000000000000000000000
        
   ```
    public static int makeMeasureSpec(@IntRange(from = 0, to = (1 << MeasureSpec.MODE_SHIFT) - 1) int size,
                                          @MeasureSpecMode int mode) {
            if (sUseBrokenMakeMeasureSpec) {
                return size + mode;
            } else {
                return (size & ~MODE_MASK) | (mode & MODE_MASK);
            }
        }
   ```
   
3. View的测量-确定DecorView的MeasureSpec
      
      * DecorView的MeasureSpec由窗口大小和自身LayoutParams决定，遵守如下规则：
      
            1 LayoutParams.MATCH_PARENT:精确模式，窗口大小。
            2 LayoutParams.WRAR_CONTENT:最大模式，最大为窗口大小
            3 固定大小：精确模式，大小为LayoutParams的大小
            
 4. 测量流程
 
    * ViewGroup measure -->onMeasure(测量子控件的宽高)-->setMeasuredDimension-->setMeasureDimensionRaw(保存自己的宽高)
    * View      measure -->onMeasure-->setMeasureDimension -->setMeasureDimensionRaw
    
    
## 二、View的布局

  * 调用view.layout确定自身的位置，即确定mLeft,mTop,mRight,mBottom的值。
  
  
  * 如果是ViewGroup类型，需要调用onLayout确定子View的位置
  
## 三、 View的绘制
  
  * 绘制背景 drawBackground(canvas)
  * 绘制自己 onDraw(canvas)
  * 绘制子View dispatchDraw(canvas)
  * 绘制前景，滚动条等装饰onDrawForeground(canvas)


实现过程 onMeasure --> onLayout(容器) --> onDraw(可选)



 
          
