# OK源码分析

## 一、OSI 七层模型介绍

* 七层模式

  * 应用层 -> 可见的终端应用

    > 应用终端，例如浏览器

  * 表示层 -> 计算机识别的信息转变人可以的信息

    > 将数据转成人能看懂的信息，例如 视频，图片，文字

  * 会话层 -> 传输端口和接受端口建立会话

    > 一个设备与另外一个设备建立链接

  * 传输层 -> 传输数据的协议与端口

  * 网络层 -> IP地址

  * 数据链路层 -> 交换机传输

  * 物理层 -> 具体物理设备

* TCP/IP参考模型

  * 应用层

    > http,https

  * 传输层

    > upd/tcp

  * 网络层

  * 主机至网络层

* http 1.0/1.1

  * 1.0 请求 响应 断开
  * 1.1 建立长连接，请求 响应 请求 响应  断开

* htt get request 

  * 1 请求头之 请求行
  * 2 请求头之请求属性集

* http post 

  * 1 请求头之 请求行
  * 2 请求头之请求属性集
  * 3 请求体长度
  * 4 请求的类型



## 二、OKHttp 主线程的源码阅读

* 相关类

  * okHttpClient
  * Request
  * Response
  * Call
  * Callback

* 主流程 

  * 1 创建 okHttpClient 对象

    ~~~java
     OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
    ~~~

    * builder 的构造方法

      ~~~java
      dispatcher = new Dispatcher();
            protocols = DEFAULT_PROTOCOLS;
            connectionSpecs = DEFAULT_CONNECTION_SPECS;
            eventListenerFactory = EventListener.factory(EventListener.NONE);
            proxySelector = ProxySelector.getDefault();
            cookieJar = CookieJar.NO_COOKIES;
            socketFactory = SocketFactory.getDefault();
            hostnameVerifier = OkHostnameVerifier.INSTANCE;
            certificatePinner = CertificatePinner.DEFAULT;
            proxyAuthenticator = Authenticator.NONE;
            authenticator = Authenticator.NONE;
            connectionPool = new ConnectionPool();
            dns = Dns.SYSTEM;
            followSslRedirects = true;
            followRedirects = true;
            retryOnConnectionFailure = true;
            connectTimeout = 10_000;
            readTimeout = 10_000;
            writeTimeout = 10_000;
            pingInterval = 0;
      ~~~

      * 可在build 之前设置参数。

  

  * 2 创建 requset类，通过httpClient .newCall（），获取Call 类(RealCall)

  * 3 发起网络请求

    * 同步网络请求

      * 源码

      ~~~java
      @Override public Response execute() throws IOException {
        synchronized (this) {
          if (executed) throw new IllegalStateException("Already Executed");  // (1)
          executed = true;
        }
        try {
          client.dispatcher().executed(this);                                 // (2)
          Response result = getResponseWithInterceptorChain();                // (3)
          if (result == null) throw new IOException("Canceled");
          return result;
        } finally {
          client.dispatcher().finished(this);                                 // (4)
        }
      }
      ~~~

      * 这里做了4件事

        > 1.检查这个call 释放已经执行了，每个call 只能被执行一次，如果想要一个完全一样的call，可以利用call#clone方法进行克隆
        >
        > 2.利用client.dispatcher().executed(this) 来进行实际执行dispatcher
        >
        > 3.调用getResponseWithInterceptorChain()函数获取HTTP返回结果，从函数可以看出，这一步还会进行一系列拦截操作。
        >
        > 4.最后还要通知dispatcher 自己已经执行完毕。

      * 真正做网络请求的是  `getResponseWithInterceptorChain()` 这个方法。`Interceptor` 是OkHttp 最核心的一个东西，它不是只负责拦截请求进行一些额外的处理，实际上它把实际的网络请求、缓存、透明压缩等功能都统一了起来，每一个功能都是一个`Interceptor` ,它们再连接成一个`Interceptor.Chain`  ,环环相扣，最终圆满完成一个网络请求。

      * `Interceptor.Chain` 的分布依次是:

           1.在配置`OkHttpClient` 时设置的 `interceptors`

        > log日志拦截，自定义的拦截器，添加公告参数参数。

           2.负责失败重试以及重定向的`RetryAndFollowUpInterceptor` 

        3. 负责把用户构造的请求转换为发送服务器的请求、把服务器返回的响应转换为用户友好的响应的`BridgeInterceptor`;
        4. 负责读取缓存直接返回、更新缓存的`CacheInterceptor`;
        5. 负责和服务器建立连接的`connetInterceptor`
        6. 配置`OkHttpClient` 时设置的`networkInterceptors`;
        7. 负责向服务器发送请求数据、从服务器读取响应数据`CallServerInterceptor`;

      * 位置决定了功能，最后一个`Interceptor` 一定负责和服务器实际通讯的，重定向、缓存等一定时实际通讯之前的。

      * 责任链模式在这个`Interceptor` 链条中得到了很好的实践

        > 它包含了一些命令对象和一系列的处理对象，每个处理对象决定它额能处理哪些命令对象，它也知道如何将它不能处理的命令对象传递过改链中的下一个处理对象。该模式还描述了该处理链的末尾添加新的处理对象的方法。

      * 对于把`Request` 变成 `Response` 这件事来说，每个`Interceptor` 都可能完成这件事，所以我们循着链条让每个 `Interceptor` 自行决定能否完成任务以及怎么完成任务。这样一来，完成网络请求这件事就彻底从`RealCall` 类中剥离出来，简化了各自的责任和逻辑。

      * 建立连接:`ConnectInterceptor`

           ~~~java
           @Override public Response intercept(Chain chain) throws IOException {
             RealInterceptorChain realChain = (RealInterceptorChain) chain;
             Request request = realChain.request();
             StreamAllocation streamAllocation = realChain.streamAllocation();
           
             // We need the network to satisfy this request. Possibly for validating a conditional GET.
             boolean doExtensiveHealthChecks = !request.method().equals("GET");
             HttpCodec httpCodec = streamAllocation.newStream(client, doExtensiveHealthChecks);
             RealConnection connection = streamAllocation.connection();
           
             return realChain.proceed(request, streamAllocation, httpCodec, connection);
           }
           ~~~

           实际是创建了一个`HttpCodec`对象 ，它里面是用`OKio` 对`Socket` 的读写操作进行封装。

      * 发送和接受数据:`CallServerInterceptor`

           ~~~java
           @Override public Response intercept(Chain chain) throws IOException {
             HttpCodec httpCodec = ((RealInterceptorChain) chain).httpStream();
             StreamAllocation streamAllocation = ((RealInterceptorChain) chain).streamAllocation();
             Request request = chain.request();
           
             long sentRequestMillis = System.currentTimeMillis();
             httpCodec.writeRequestHeaders(request);
           
             if (HttpMethod.permitsRequestBody(request.method()) && request.body() != null) {
               Sink requestBodyOut = httpCodec.createRequestBody(request, request.body().contentLength());
               BufferedSink bufferedRequestBody = Okio.buffer(requestBodyOut);
               request.body().writeTo(bufferedRequestBody);
               bufferedRequestBody.close();
             }
           
             httpCodec.finishRequest();
           
             Response response = httpCodec.readResponseHeaders()
                 .request(request)
                 .handshake(streamAllocation.connection().handshake())
                 .sentRequestAtMillis(sentRequestMillis)
                 .receivedResponseAtMillis(System.currentTimeMillis())
                 .build();
           
             if (!forWebSocket || response.code() != 101) {
               response = response.newBuilder()
                   .body(httpCodec.openResponseBody(response))
                   .build();
             }
           
             if ("close".equalsIgnoreCase(response.request().header("Connection"))
                 || "close".equalsIgnoreCase(response.header("Connection"))) {
               streamAllocation.noNewStreams();
             }
             // 省略部分检查代码
           
             return response;
           }
           ~~~

           > 主干部分是:
           >
           > 1.向服务器发送request header
           >
           > 2.如果有request body,就向服务器发送；
           >
           > 3.读取response header,先构造一个Response 对象
           >
           > 4.如果有 response body，就在上面的基础加上body构造一个新的response 对象

    * 异步网络请求

      >
      >
      >

    * ### 返回数据的获取

    * ### HTTP 缓存

    * 总结

  

  

  

  

























