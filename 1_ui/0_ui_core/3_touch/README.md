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





















