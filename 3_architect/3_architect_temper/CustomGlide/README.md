# Glide图片加载框架设计
## 一、Glide手写之资源封装
    * 资源封装
        * key   -对Value的唯一性进行描述
        * Value -Bitmap的封装(+1，-1,释放)

## 二、Glide手写实现之活动缓存
    * 2.1 回收机制:GC扫描的时候回收，移除容器(GC被动移除) (弱引用)
    * 2.2 容器管理方式: 资源的封装 key ----  (弱引用<Value>)
    * 2.3 手动移除的区分
    * 2.4 关闭线程
        ~~~java
            if (null != mThread) {
                       mThread.interrupt();
                       try {
                           //线程稳定安全 停下来
                           mThread.join(TimeUnit.SECONDS.toMillis(5));
                           if (mThread.isAlive()) {
                               throw new IllegalStateException("活动缓存中 关闭线程 线程没有停下来");
                           }
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
        ~~~
     * 2.5 Value 监听加入
## 三、手写实现之内存缓存(LRU算法)
   * LRU算法：最近没有使用的元素，会自动被移除掉
   * 职责:
        * 活动缓存：给正在使用的资源存储的，弱引用
        * 内存缓存：为第二次缓存服务，LRU算法
   * LruCache v4:
        * 利用LinkedHashMap<K,V>
        * linkedHashMap:true==拥有访问排序的功能(最少使用元素算法-lru算法)
    put：
        1.如果是重复的key，会被移除掉一个
        key=15151511551
        previous = key=15151511551
        entryRemoved
        2.trimToSize 移除哪些最近没有使用的元素 ---》 entryRemoved



## 四、手写之磁盘缓存
       * 保存时长比较长：保存在本地磁盘 文件的形式存储 （不再是保存在运行内存中，而是磁盘中）
       * LRU算法: ---> 最近没有使用的元素，会自动被移除掉
       * LruCahce -- Android中提供了 V4
       * DiskLruCache --- Android中没有提供了 --> DiskLruCache
       * DiskLruCache：回收方式：LRU算法， 访问排序
       * DiskLruCache: 面向磁盘文件保存
       * sp == Editor edit

## 五、手写之生命周期
## 六、手写实现之加载图片

## 七、概述
    简单来说，glide 框架分为4个部分，1、内存缓存,图片加载数据时依次从 活动缓存，内存缓存，磁盘缓存，中获取数据 2、外部资源加载，当内存缓存中，
    无法获取到数据时，会去外部加载资源，网络数据跟本地数据。 3、bitmap复用池，当一个bitmap 被回收后，bitmap的资源会被释放，但占用的内存会存放在
    内存中，当磁盘缓存需要创建内存时，会优先在复用池中获取数据。4、生命周期的管理,application的context 是无法做管理的，但是activity的context 是可以的，创建一个fragment 添加到activity中，
    fragment的生命跟activity的生命周期进行绑定了。添加监听，会glide的内存进行相应的处理。
    具体流程，就是Glide.with 传入context，通过GlideBuild创建glide对象，和资源请求的管理类也就是(RequestManger),requestManger类，在静态代码库创建了具体的资源加载类
    并与context的生命周期进行监听,最后将requestManger的对象返回给外部，接着调用load()传入具体的图片加载地址，将地址传递给里面，
    init()的时候，开始从内存加载数据，如果没有会去加载外部资源，加载完成后，将图片显示出来。

