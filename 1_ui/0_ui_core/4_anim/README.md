
# 属性动画源码分析
## 属性动画种类
    平移动画，透明度动画，缩放动画，旋转动画
## 设计动画需要考虑的问题
* 生成动画的api越简单越好
* 一个View上可以有东哥动画，但同时只能有一个在运行。
* 动画的执行不能依赖自身的for循环代码
* 如何让动画动起来。

## 属性动画使用
~~~
Button btn = findViewById(R.id.btn);
ObjectAnimator anim = ObjectAnimator.ofFloat(btn,"alpha",0f,1f);
anim.setDuration(1000);
anim.start();
~~~
## 动画本质
    本质：动画实际上是改变View在某一时间点的样式属性
         比如在0.1s的时候View的x坐标是100px 在0.2s的时候变成了200px。 用户感觉View在向右移动
    本质：实际上通过一个线程每隔一段时间 通过调用view。setX(index++)值也能产生动画效果，
         这就是属性动画的原理。

## 动画架构初始化

    动画任务：控件、时长、起始值，结束值、差值器。
    关键帧动画->关键帧动画->关键帧动画->关键帧动画





























