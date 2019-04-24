# 事件分发机制
## 事件的定义 
     定义:当用户触摸屏幕时，将产生的触摸行为(Touch事件)
## 事件的类型，有四种:
     MotionEvent.ACTION_DOWN 手指刚接触屏幕 
     MotionEvent.ACTION_UP   手指从屏幕松开
     MotionEvent.ACTION_MOVE 手指在屏幕上滑动
     MotionEvent.ACTION_CANCEL 非人为因素取消
## 事件序列
     正常情况下，一次手指触摸屏幕的行为会触发一系列点击事件。
     1 点击屏幕后立即松开，事件序列为Down->up
     2 点击屏幕滑动一会再松开，事件序列为down->move->..move->up
     
     开始-> 用户按下Viwe -> down事件 -> 用户移动手指 -> move事件 -> up事件 -> 结束
                                           非人为原因事件取消 ->  cancel事件
                                            
## 事件分发对象
* Activity:控制生命周期&处理事件
* ViewGroudp:一组View的集合（含多个子View）
* View:   所有ui组件的基类

## 事件分发主要方法
* dispatchTouchEvent(MotionEvent ev):用来进行事件分发
* onInterceptTouchEvent(MotionEvent ev):判断是否拦截事件(只存在于ViewGroup中)
* onTouchEvent(MotionEvent ev):处理点击事件

## 事件分发-Activity
* Activity的事件分发
~~~
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            //空方法，子类可重写
            onUserInteraction();
        }
        //getWindow 是PhoneWindow对象
        //最终调用 ViewGroup.dispatchTouchEvent()
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
~~~
 ## 事件分发-ViewGroup
 * ViewGroup的事件分发，可以用一下伪代码表示
 ~~~
 public boolean dispatchTouchEvent(MotionEvent et){
    boolean concume = false;
    //调用oninterceptTouchEvent 判断是否拦截
    if(onInterceptTouchEvent ev){
        //拦截则调用自身的onToucheEvent
        consume = onTouchEvent(ev);
    }else{
        //不拦截，将事件分发给子View
        consume = child.dispatchTouchEvent(ev);
    }

 }
 ~~~
* ViewGroup的事件分发，dispatchTouchEvent()
~~~
public boolean dispatchTouchEvent(MotionEvent ev){
     final boolean intercepted;
     //down事件或者子view消费了事件，判断是否拦截
     if(actionMasked == MotionEvent.ACTION_DOWN||mFirstTouchTarget!=null){
          final boolean disallowintercept = (mGroupFlags&FLAG_DISALLOW_INTERCEPT)!=0;
          if(!disallowintercept){//允许拦截
               intercepted = onInterceptTouchEvent(ev);
               ev.setAction(action);
          }else{
               intercepted =false;
          }
     
     }else{
          intercepted = true;//没有找到消费事件的子View并且不是down事件
     }

}
~~~
![事件分发](https://raw.githubusercontent.com/zh405557524/AndroidAdvanceLearn/master/1_ui/0_ui_core/3_touch/%E4%BA%8B%E4%BB%B6%E5%88%86%E5%8F%91%E6%A8%A1%E5%9E%8B.png)

## 事件分发结论

* 一个事件序列从手指接触屏幕到手指离开屏幕，在这个过程中产生一系列事件，以down事件开始，中间含有不定数的move事件，以up事件结束
    
*  正常情况下，一个事件序列只能被一个View拦截并且消耗
    
*  某个View一旦决定拦截，那么这个事件序列都将由它的onTouchEvent处理，并且它的onInterceptTouchEvent不会再调用。
    
*  某个View一旦开始处理事件，如果它不消耗ACTION_DOWN事件(onTouchEvent返回false)，那么同一事件序列中其他事件都不会再交给它处理。并且重新郊游它的父元素处理(父元素onTouchEvent被调用)
    
*  事件的传递过程是由外向内的，即事件总是先传递给父元素，然后再由父元素分发给子View，通过requestDisallowInterceptTouchEvent方法可以在子View中干预父元素的事件分发过程，但ACTION_DOWN除外
    
*  ViewGroup默认不拦截任何事件，即onInterceptTouchEvent默认返回false。View没有onInterceptTouchEvent方法，一旦有点击事件传递给它，那么它的onTouchEvent方法就会被调用。
    
*  View的onTouchEvent默认会消耗事件(返回ture)，除非它是不可点击的(clickable和longClickable同时为false)。View的longClickable默认都为false，clickble要分情况，比如button的clickble默认为ture，TextView的clickable默认为false。

*  View的enable属性不影响onTouchEvent的默认返回值。哪怕一个View是disable状态，只要它的clickable或者longClickable有一个为true，那么它的onTouchEvent就返回true。
    
*  onClick会影响的前提是当前View是可点击的，并且收到了ACTION_DOWN和ACTION_UP的事件，并且受长按事件影响，当长按事件返回true时，onClick不会响应。
    
* onLongClick在ACTION_DOWN里判断是否进行响应，要想执行长按事件该View必须是longClickable的并且设置了OnLongClickListener。

























