# View是如何被添加到屏幕窗口上
## 一 创建顶层布局容器 DecorView
## 二 在顶层布局中加载基础布局ViewGroup
## 三 将ContentView添加到基础布局中的FragmeLayout中
## View的绘制流程
1. 绘制入口
          
       ActivityThread.handlerResumeActivity
       -->WindowMangerImpl.addView(decorView,layoutParams)
       -->WindowMangerGlobal.addView()
2. 绘制的类及方法
      
       ViewRootImpl.setView(decorView,layoutParmas,parentView)
       -->ViewRootimpl.requestLayout()-->scheduleTraversals()-->doTraversal()
       -->performTraversals()
3. 绘制三大步骤
       
       测量：ViewRootImpl.performMeasure
       布局: ViewRootImpl.performLayout
       绘制: ViewRootImpl.performDraw
