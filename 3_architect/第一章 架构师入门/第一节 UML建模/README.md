# 第一节 UML建模

## 一、PowerDesigner 安装

* 下载安装包并解压

* 安装

## 二、正向工程与逆向工程

* 逆向工程

  > 第一步 (语言-Reverse Engineer Java)
  >
  > 第二步 (添加java)

* 正向工程

  > 第一步 (语言-Generate Java Code)
  >
  > 第二步：选择生成文件和路径
  >
  > 第三部：查看生成的java文件

## 三、关系(依赖、泛化)

* 依赖(Dependence)

  > A类持有B类的引用，这种关系称为依赖关系,例如房子里面有车，车依赖房子

  ![依赖](https://github.com/zh405557524/AndroidAdvanceLearn/blob/master/3_%E6%9E%B6%E6%9E%84%E5%B8%88/%E7%AC%AC%E4%B8%80%E7%AB%A0%20%E6%9E%B6%E6%9E%84%E5%B8%88%E5%85%A5%E9%97%A8/%E7%AC%AC%E4%B8%80%E8%8A%82%20UML%E5%BB%BA%E6%A8%A1/res/%E4%BE%9D%E8%B5%96.jpg)

* 泛化(Generalization)

  > 泛化关系 类似 java 的继承关系 。男人与女人都是人类
  >
  > B 类 具有A类 所有的属性和方法，并具有自己特有的属性和方法。

  ![泛化](https://github.com/zh405557524/AndroidAdvanceLearn/blob/master/3_%E6%9E%B6%E6%9E%84%E5%B8%88/%E7%AC%AC%E4%B8%80%E7%AB%A0%20%E6%9E%B6%E6%9E%84%E5%B8%88%E5%85%A5%E9%97%A8/%E7%AC%AC%E4%B8%80%E8%8A%82%20UML%E5%BB%BA%E6%A8%A1/res/%E6%B3%9B%E5%8C%96.jpg)

* 关联(Association)

  > 1 单向关联  地址 对应  城市
  >
  > 2 双向关联   男孩有女孩，女孩有男孩
  >
  > 3 自关联   

![关联](https://github.com/zh405557524/AndroidAdvanceLearn/blob/master/3_%E6%9E%B6%E6%9E%84%E5%B8%88/%E7%AC%AC%E4%B8%80%E7%AB%A0%20%E6%9E%B6%E6%9E%84%E5%B8%88%E5%85%A5%E9%97%A8/%E7%AC%AC%E4%B8%80%E8%8A%82%20UML%E5%BB%BA%E6%A8%A1/res/%E5%85%B3%E8%81%94.jpg)

* 实现(Realization)

  > 类似java中的接口实现  usb（断开，连接） ->u盘、键盘、鼠标 

  ![实现](https://github.com/zh405557524/AndroidAdvanceLearn/blob/master/3_%E6%9E%B6%E6%9E%84%E5%B8%88/%E7%AC%AC%E4%B8%80%E7%AB%A0%20%E6%9E%B6%E6%9E%84%E5%B8%88%E5%85%A5%E9%97%A8/%E7%AC%AC%E4%B8%80%E8%8A%82%20UML%E5%BB%BA%E6%A8%A1/res/%E5%AE%9E%E7%8E%B0.jpg)

* 聚合(Aggregation)  

  > 表示整体和部分的关系，可以分开。单独存在  ; 学生 既可以是学校，也可以在网吧打游戏

![聚合](https://github.com/zh405557524/AndroidAdvanceLearn/blob/master/3_%E6%9E%B6%E6%9E%84%E5%B8%88/%E7%AC%AC%E4%B8%80%E7%AB%A0%20%E6%9E%B6%E6%9E%84%E5%B8%88%E5%85%A5%E9%97%A8/%E7%AC%AC%E4%B8%80%E8%8A%82%20UML%E5%BB%BA%E6%A8%A1/res/%E8%81%9A%E5%90%88.jpg)

* 组合(Composition)

  > 与聚合类似，但是整体跟部分不能分开。 例如ViewGroup 与View 的关系

![组合](https://github.com/zh405557524/AndroidAdvanceLearn/blob/master/3_%E6%9E%B6%E6%9E%84%E5%B8%88/%E7%AC%AC%E4%B8%80%E7%AB%A0%20%E6%9E%B6%E6%9E%84%E5%B8%88%E5%85%A5%E9%97%A8/%E7%AC%AC%E4%B8%80%E8%8A%82%20UML%E5%BB%BA%E6%A8%A1/res/%E7%BB%84%E5%90%88.jpg)



## 四、AOP面向切面架构设计

* 两种方式

    aop 通过动态代理和预编译的方式,可以对业务逻辑各个部分进行分离,各个业务之间的耦合度降低,提高程序的可重用性.

  * 第一种方式

    >  	view 				 view
    >
    > ​        Proxy not null
    >
    > ​		mVIew.method();

  * 第二种方式

    > ​       	  activity						activity
    >
    > ​			 Activity All Callback LIstener
    >
    > ​			 做Activity生命周期业务处理

* 理解切面

  ![切面](/Users/hillliao/github/AndroidAdvanceLearn/3_架构师/第一章 架构师入门/第一节 UML建模/res/切面.jpg)



* 动态代码切面需求

![切面](/Users/hillliao/github/AndroidAdvanceLearn/3_架构师/第一章 架构师入门/第一节 UML建模/res/切面动态代理.jpg)



## 五、面向切面思想之集中式登录架构

![切面](/Users/hillliao/github/AndroidAdvanceLearn/3_架构师/第一章 架构师入门/第一节 UML建模/res/登录.jpg)

* AspectJ























