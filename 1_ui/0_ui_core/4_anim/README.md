
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
ObjectAnimator anim = ObjectAnimator.ofFloat(btn,"alpha",0f,1f);//透明度
ObjectAnimator anim = ObjectAnimator.ofFloat(btn,"translationY",0,100f);//移动
ObjectAnimator anim = ObjectAnimator.ofFloat(btn,"scaleX",0,1.0f);//缩放
anim.setRepeatCount(ObjectAnimator.INFINITE);//无限重复
anim.setRepeatMode(ObjectAnimator.RESTART);
anim.setDuration(1000);
ObjectAnimator anim = ObjectAnimator.ofFloat(btn,"alpha",0f,1f);//透明度
anim.start();
~~~
## AnimatorSet的使用
~~~
    AnimatorSet animSet = new AnimatorSet();
    animSet.playTogether(translationY, scaleX, scaleY);
    animSet.setDuration(2000);
    animSet.start();
~~~
## 动画本质
    本质：动画实际上是改变View在某一时间点的样式属性
         比如在0.1s的时候View的x坐标是100px 在0.2s的时候变成了200px。 用户感觉View在向右移动
    本质：实际上通过一个线程每隔一段时间 通过调用view。setX(index++)值也能产生动画效果，
         这就是属性动画的原理。

## 动画架构初始化

    动画任务：控件、时长、起始值，结束值、差值器。
    关键帧动画->关键帧动画->关键帧动画->关键帧动画


# 平行动画
## 思考
    控件开发思路：拿到自定义控件需要冷静下来仔细思考。观察有哪些动画，哪些元素比较特别，后面有没有可能对需求进行变更。根据效果来看有没有事件交互。怎样设置自定控件才是合理的。


























