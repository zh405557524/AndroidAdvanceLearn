# SVG

## svg 概念

​			svg是一种图像文件格式，类似 与png jpg。只不过png这种图片需要图像引擎加载，svg 则是由画布来加载，它的英文全称为 Scalable Vector Graphics，意思为可缩放的矢量图形。可燃给你设计无损失、高分辨率的web图形页面。用户可以直接用代码来描绘图像。



## svg特性

* svg可被非常多的工具读取和修改(比如记事本)
* svg 与jpeg 和gif 图像比起来 ，尺寸更小，且可压缩性更强。
* svg 是可伸缩的
* svg图像可在任何分辨率下被高质量地打印
* svg可在图像质量不下降的情况下被放大
* svg图像中的文本是可自定义的，可以融入代码片段
* svg文件是纯粹的xml



## svg 在android中能做什么

* app图标： 能sdk23后，app的图标都是由svg来表示
* 自定义控件：不规则的控件，复杂的交互，子控件重叠判断，图表等都可以用svg来做
* 复杂动画：如根据用户滑动动态显示动画，路径动画。



## android例子

*  不规则自定义控件(如遥控界面)

* 复杂的交互动画
* 图标

## 标准svg预览

~~~xml
<svg width = "580" height = "400" xmlns = "http://www.w3.org/2000/svg">
<title>Layer 1</title>
  <line stroke-linecap = "undefind" stroke-linejoin = "undefined"
        id= "svg_1" y2= "119.4375" x2 = "412.537311" y1 = "119.4375" x1 = "77.5" stroke-width = "1.5" stroke = "#000" fill = "none"/>
</svg>
~~~

## svg 语法

* m = moveto(m x,y):将画笔移动到指定的坐标位置 
* L= lineto(L X，y) ： 画直线到指定的坐标位置
* H= horizontal lineto(H X):画水平线到指定的x坐标位置
* V = vertical lineto(V Y):画垂直线到指定的y坐标位置
* C = curveto(C x1,y1,x2,y2,ENDX,ENDY)：三次贝塞曲线
* S = smooth curveto(S x2,y2,endx,endy)
* q = quadratic belzier curve(Q x,y,endx,endy):二次贝塞曲线
* z = closepath():关闭路径

## 完成效果

* 课程效果 ：完成中国地图的绘制，并且能正常点击省份。

* 中国地图svg下载
  * svg下载地址1：https://www.amcharts.com/download/































