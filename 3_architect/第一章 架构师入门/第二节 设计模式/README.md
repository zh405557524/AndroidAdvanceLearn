# 设计模式

## 一、MVC

* mvc流程关系:
  * view接收到用户的操作
  * view将用户的操作，交给controller
  * controller完成具体业务逻辑
  * 得到结果封装model，再进行view 更新
* controller 是作为一个媒介，处理model和view之间。model和view之间有紧密的联系，耦合性偏强。

![mvc1](./res/mvc1.jpg)

* 三层模型

![三层模型](./res/mvc2.png)



* 三层模型在Android中的体现

  ![三层模型](./res/mvc3.png)

## 二、Mvp

* 模型

  ![模型](./res/mvp1.png)

* 思想精髓

  <img src="./res/mvp2.png" alt="mvp2.png" style="zoom:33%;" />

* 解耦View与model

  ![mvp3.png](./res/mvp3.png)

* 基础框架搭建

  ![mvp4.png](./res/mvp4.png)

## 三、Mvvm

* 模型

  ![mvvm1](res/mvvm1.png)

* 关系

  ![mvvm2](res/mvvm2.png)

* v层布局绑定 流程

  ![bindview1](res/bindview1.png)

![mvvm2](res/bindview2.png)

![mvvm3](res/bindview3.png)



* databing绑定原理

  * APT预编译方式

    ![apt](res/apt1.png)

    ![xml](res/xml.png)

  * 内部处理布局文件控件

    ![databind1](res/databind1.png)

  * model帮助类

    ![model](res/model1.png)

    























