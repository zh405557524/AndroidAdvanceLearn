# makefie语法详解

## 一、静态库与动态库原理详解

* 编译流程

  > 编译分为四大过程：
  >
  > 1.预处理
  >
  > 2.编译
  >
  > 3.汇编
  >
  > 4.链接

* 编译流程-预处理(Preprocessing)

  > 预处理：
  >
  > 1 完成宏替换、文件引入、以及去除空行、注释等，为下一步的编译做准备
  >
  > 2 也就是对各种预处理命令进行处理，包括文件的包含、宏定义的扩展、条件编译的选择等。* 

~~~c++
# test.c文件内容
#include <stdio.h>
int main(){
  printf("hello world!\n");
  return 0;
}
----------------------------
  $ gcc -E test.c -o test.i
  
  * 选项 -e 让gcc 在预处理结束后停止编译。
  * "test.i" 文件为预处理后输出的文件。
  * -o ：指定输出文件
~~~



* 编译流程-编译(Compliation)

  >编译：
  >
  >1.将预处理后的代码编译成汇编代码。在这个阶段中，首先要检查代码的规范性、是否有语法错误等，以确定代码实际要做的工作，在检查无误后，再把代码翻译成汇编语言
  >
  >2.编译程序执行时，先分析，后综合。分析，就是指词法分析、语法分析、语义分析和中间代码生成。综合，就是指代码优化和代码生成。
  >
  >3.大多数的编译程序直接产生机器语言的目标代码，形成可执行的目标文件，也有的是先产生汇编语言一级的符号代码文件，再调用汇编程序进行翻译和加工处理，最后产生可执行的机器语言目标文件。

~~~c++

extern int ftrylockfile (FILE *_stream) _attribute_ ((_nothrow_),_leaf_);
...
...
# 3 "test.c"
int main(){
  printf("hello world!");
  return 0;
}

#上面是预处理后test.i文件部分内容
-----------------------------
 $ gcc -S test.i -o test.s
 
   * 选项 -S 让gcc 在编译结束后停止编译。
  * "test.s" 文件为编译后生成的汇编代码

~~~



* 编译流程-汇编(Assemble)

  >汇编：
  >
  >1.汇编就是把编译阶段生成的".s"文件转成二进制目标代码，也就是机器代码(01序列)。

~~~c++
 $ gcc -c test.s -o test.o
 
   * 选项 -c 让gcc 在汇编结束后停止编译。
  * "test.o" 文件为汇编后生成的机器码目标文件。
~~~



* 编译流程-链接(Linking)

  > 链接：
  >
  > 链接就是将多个目标文件以及所需的库文件链接生成可执行目标文件的过程。

~~~C++
$ gcc test.o -o test
$ ./test
hello world~

-------------------
* -o 本质上是一个重命名选项。不适用-o选项时，默认生成的是a。out文件。这里生成的是可执行文件test
* ./test 执行后输出hello world！
~~~



* 静态库

  > 静态库 ：
  >
  > 1.静态库实际就是一些目标文件(一般以.o结尾)的集合，静态库一般以.a结尾，只用于生成可执行文件阶段
  >
  > 2.在连接步骤中，链接器将从库文件取得所需代码，复制到生成的可执行文件中。这种库称为静态库。期特点是可执行文件中包含了库代码的一份完整拷贝，在编译过程中被载入程序中。缺点就是多次使用就会有多份冗余拷贝，并且对程序的更新、部署和发布会带来麻烦，如果静态库有更新，那么所有使用它的程序都需要重新编译、发布。

* 生成静态库

  ~~~c++
  
  #首先生成目标文件
  $ gcc -c test.c -o test.o
  #使用ar 命令将目标文件打包成静态库
  ar rcs libtest.a test.o
  ar:creating libtest.a
  #使用ar t libtest.a 查看静态库内容
  $ ar t libtest.a
  test.o
  
  ---------------------
  * 首先生成test.o目标文件
  *	使用ar 命令将test.o打包成libtest.a静态库
  
  ~~~

  

* 动态库

  > 动态库：
  >
  > 1.动态库在链接阶段没有复制到程序中，而是在程序运行时由系统动态加载到内存中供程序调用。
  >
  > 2.系统只需要载入一次动态库，不同的程序可以得到内存中相同动态库的副本，因此节省了很多内存。

* 生成动态库

  ~~~c++
  
  # 首先生成目标文件
  $ gcc -c test.c
  # 使用-fPIC 和 -shared 生成动态库
  ￥ gcc -shared -fPIC -o libtest.so test.o
  --------------------
  * 首先生成test.o目标文件。
  * 使用-shared和-fPIC参数生成动态库。
  ~~~



* 静态库与动态库区别总结

  > 载入时刻不同:
  >
  > 1.静态库在程序编译时会链接到目标代码中，程序运行时不再需要静态库，因此体积较大。而且每次编译都需要载入静态代码，因此内存开销大。
  >
  > 2.动态库在编译时不会被链接到目标代码中，而是在程序运行时才被载入，程序运行时需要动态库存在，因此体积较小。而且系统只需载入一次动态库，不同程序可以得到内存中相同的动态副本，因此内存开销小。



## 二、MakeFile走读与语法基础

* 什么是Makefile

  > makefile
  >
  > 1 makefile 定义了一系列的规则来指定，哪些文件需要先编译，哪些文件需要重新编译，如何进行链接等操作。
  >
  > 2 makefile 就是"自动化编译"，告诉make命令如何编译和链接。

* makefile里有什么

  > makefile包含以下五个：
  >
  > 1.显示规则
  >
  > 2.隐晦规则
  >
  > 3.变量定义
  >
  > 4.文件指示
  >
  > 5.注释

* makefile的规则
  * target:目标文件。可以是Object File，也可以是执行文件，还可以使标签(Label)。
  * prerequisites:依赖文件，即要生成那个target所需要的文件或其他target
  * command:make需要执行的命令

~~~ C++
target ...: prerequisites ... 
  	command
或者：
target ...: prerequisites ...;command
~~~

* makefile 示例
  * -o 指定可执行文件的名称
  * clean:标签，不会生成"clean"文件，这样的target称之为"伪目标"，伪目标的名字不能喝文件名重复。clean一般放在文件最后
  * .PHONY:显示地指明clean是一个"伪目标"

~~~C++

#当前目录存在main.c、tool.c、tool.h三个文件
#下面是makefile文件内容
main:main.o tool.o
	gcc main.o too.o -o main
.PHONY:clean
clean :
	-rm main *.o
	-------------------------
	//执行 make 后输出如下
	cc   -c -o main.o main.c
	cc   -c -o tool.o tool.c
	gcc  main.o tool.o -o main 
	//并且生成了一个可执行文件main
~~~

* Makefile是如何工作的

  > 默认方式下，输入make命令后：
  >
  > make会在当前目录下找名字叫"Makefile"或"makefile"的文件
  >
  > 如果找到，它会找到文件中第一目标(target)，并把这个target作为最终的目标文件，如前面示例中的"main"。
  >
  > 如果main文件不存在，或main所以依赖的.o文件的修改时间要比main文件要新，那么它会执行后面所定义的命令来生成mian文件。
  >
  > 如果main所以依赖的.o文件也存在，那么make会在当前文件中找目标为.o文件的依赖性，若找到则根据规则生成.o文件
  >
  > make再用.o文件声明make的终极任务，也就是执行文件 "main".

* Makefile中使用便利
  * 为了makefile的易维护，在makefile中我们可以使用变量。makefile的变量也就是一个字符串，理解成C语言中的宏可能会更好。
  * 比如，我们声明一个变量，叫objects，于是，我们就可以很方便地在我们的makefile中以"￥(objects)"的方式来使用这个变量了

~~~C++
objects = main.o tool.o

main:$(objects)
  gcc $(objects) -o main
.PHONY:clean 
clean:
	   rm main $(objects)
------------------------------       
//执行 make 后输出如下
cc     -c -o main.o main.c
cc 	   -c -o tool.o tool.c
gcc    main.o tool.o -o main
~~~

* 引用其他的Makefile
  * 使用include 关键字可以把其他Makefile包含进来，include语法格式：include<filename>

~~~C++
#语法格式
include <filename>
#举个例子，有这样几个 Makefile:a.mk,b.mk,c.mk,还有一个文件叫# foo.make,以及一个变量$(bar),其包含了 e.mk 和f.mk

include foo.make *.mk $(bar)
# 等价于：
include foo.make a.mk b.mk c.mk e.mk f.mk

#如果文件找不到，而你希望make时不理会那些无法读取的文件二继续执行
# 可以在include前加一个减号 "-" ，如：
-include <filename>

~~~

* 环境变量 MAKEFILES

  > MAKEFILES
  >
  > 如果当前环境中定义了环境变量MAKEFILES，那么，make会把这个变量中的值做一个类似于 include的动作。这个变量中的值是其他的makefile，用空格分隔。只是，它和include 不同的是，从这个环境中引入的makefile的"目标"不会起作用，如果环境变量中定义的文件发现错误，make 也不会理。但是建议不要使用这个环境变量，因为只要这个变量一被定义，那么当你使用make时，所有的makefile都会受到它的影像。
  >
  > 也许有时候makefile出现了奇怪的事，那么可以查看当前环境中有没有定义这个变量。

* Makefile预定义变量

  > 变量名								描述								默认值
  >
  > CC							C语言编译器的名称					CC
  >
  > CPP						C语言预处理器的名称			   $(CC)-E
  >
  > CXX					     C++语言编译器的名称				g++
  >
  > RM							删除文件程序的名称				 rm -f
  >
  > CFLAGS				 C语言编译器的编译选项			    无
  >
  > CPPFLAGS    		C语言预处理器的编译选项			 无
  >
  > CXXFLAGS			C++语言编译器的编译选项 			无

* Makefile自动变量

  > 自动变量					     描述
  >
  > ​	$*						目标文件的名称，不包含扩展名
  >
  > ​	$@					   目标文件的名称，包含扩展名
  >
  > ​	$+						 所有的依赖文件，以空格隔开，可能含有重复的文件
  >
  > ​	$^						 所有的依赖文件，以空格隔开，不重复
  >
  > ​	$<						 依赖项中第一个依赖文件的名称
  >
  > ​	$?						 依赖项中所有比目标文件新的依赖文件

* Makefile 函数

  * 不带参数

    ~~~C++
    defile FUNC
    $(info echo "hello")
    endef 
    
    $(call FUNC)
    ---------------------
     输出:hello
    ~~~

  * 带参数

  ~~~C++
  define FUNC1
  $(info echo $(1) $(2))
  endef 
  
  $(call FUNC1,hello,wolrd)
   -----------------
    输出：hello world
  ~~~


* make的工作流程

  > GNU的make 工作时的执行步骤如下：
  >
  > 1.读入所有的makefile。
  >
  > 2.读入被include的其他makefile。
  >
  > 3.初始化文件中的变量。
  >
  > 4.推导隐晦规则，并分析所有规则。
  >
  > 5.为所有的目标文件创建依赖关系链。
  >
  > 6.根据依赖关系，决定哪些目标要重新生成。
  >
  > 7.执行生成命令。



## 三、Android.mk基础

* 简介

  > Android.mk是一个像Android NDK构建系统描述NDK项目的GNU makefile片段。主要用来编译生成以下几种：
  >
  > * APK程序：一般的Android应用程序，系统级别的直接push即可。
  > * JAVA库：JAVA类库，编译打包生成JAR文件。
  > * C\C++应用程序：可执行的C\C++应用程序。
  > * C\C++静态库：编译生成C\C++静态库，并打包成.a文件。
  > * C\C++共享库：编译生成共享库，并打包.so文件。

* 基本格式

  * 这是一个简单的android.mk文件的内容

    ~~~C++
    # 定义模块当前路径(必须定义在文件开头，只需定义一次)
    LOCAL_PATH := $(call my-dir)
    # 清空当前环境变量(LOCAL_PATH除外)
    include $(CLEAR_VARS)
    # 当前模块名(这里会生成libhello-jni.so)
     LOCAL_MODULE := hellp-jni
    # 当前模块包含的源代码文件
    LOCAL_SRC_FILES : = hello-jni.c
    # 表示当前模块将编译成一个共享库
    include $(BUILE_SHARED_LIBRARY)
    ~~~

  * 一个Android.mk可能编译产生多个共享库模块。这里会产生libmodule1.so和libmodule2.so两个动态库。

    ~~~C++
    LOCAL_PATH := $(call my-dir)
    # 模块1
    include $(CLEAR_VARS)
    LOCAL_MODULE := module1
    LOCAL_SRC_FILES := module1.c
    include $(BUILD_SHARED_LIBRARY)
      
    # 模块2
    include $(CLEAR_VARS)
    LOCAL_MODULE := module2
    LOCAL_SRC_FILES := module2.c
    include $(BUILD_SHARED_LIBRARY)
    ~~~

    

* 编译静态库
  * 虽然Android应用程序不能直接使用静态库，静态库可以用来编译动态库。比如在将第三方代码添加到原生项目中，可以不用直接将第三方源码包括在原生项目中，而是将第三方源码编译成静态库，然后并入共享库。

~~~C++
LOCAL_PATH:=$(call my-dir)
# 第三方AVI库
include $(CLEAR_VARS)
LOCAL_MODULE := avilib
LOCAL_SRC_FILES := avilib.c platform_posix.c
include $(BUILD_STATIC_LIBRARY)
# 原生模块
include $(CLERA_VARS)
LOCAL_MODULE := module
LOCAL_SRC_FILES := module.c
# 将静态库模块名添加到LOCAL_STATIC_LIBRARIES变量
LOCAL_STATIC_LIBRARIES := avilib
include $(BUILD_SHARED_LIBRARY) 
~~~

* 使用共享库共享通用模块

  * 静态库可以保证源代码模块化，但是当静态库与共享库相连时，它就变成了共享库的一部分。在多个共享库的情况下，多个共享库与静态库连接时，需要将通用模块的多个副本与不同的共享库重复相连，这样就增加了app的大小。这种情况，可以将通用模块作为共享库。

    ~~~C++
    LOCAL_PATH := $(call my-dir)
    # 第三方AVI库
    include $(CLEAR_VARS)
    LOCAL_MODULE := avilib
    LOCAL_SRC_FILES := avilib.c platform_posix.c
    include $(BUILD_SHARED_LIBRARY)
    
    # 原生模块1
    include $(CLEAR_VARS)
    LOCAL_MODULE := module1
    LOCAL_SRC_FILES := module1.c
    LOCAL_SHARED_LIBRARIES := avilib
    include $(BUILD_SHARED_LIBRARY)
      
    # 原生模块2
      include $(CLEAR_VARS
      LOCAL_MODULE : = module2
      LOCAL_SRC_FILES := module2.c
      LOCAL_SHARED_LIBRARIES := avilib
      include $(BUILD_SHARED_LIBRARY)
    ~~~

    

* 在多个NDK项目间共享模块

  * 首先将aviib源代码移动到ndk项目以外的位置，比如：C:\android\shared-modules\transcode\avilib.

  * 作为共享模块，avilib需要有自己的Android.mk文件。

  * 以transcode/avilib为参数调用函数宏 import-module添加到NDK项目的Android.mk文档末尾。

    ~~~C++
    
    #avilib模块自己的android.mk文件
    LOCAL_PATH := $(call my-dir)
    include $(CLEAR_VARS)
    LOCAL_MODULE := avilib
    LOCAL_SRC_FILES := avilib.c platform_posix.c
    include $(BUILD_SHARED_LIBRARY)
     ---------------------------------
    #使用共享模块的NDK项目1的Android.mk文件
      LOCAL_PATH := $(call my-dir)
      include $(CLEAR_VARS)
      LOCAL_MODULE := module1
      LOCAL_SRC_FILES := module1.c
      LOCAL_SHARED_LIBRARIES := avilib
      include $(BUILD_SHRAED_LIBRARY)
      $(call import-module,transcode/avilib)
      ---------------------------------------
      #使用共享模块的NDK项目2的Android.mk文件
       LOCAL_PATH := (call my-dir)
       include $(CLEAR_VARS)
       LOCAL_MODULE := module2
       LOCAL_SRC_FILES := module2.c
       include $(BUILD_SHARED_LIBRARY)
       $(call import-module,transcode/avilib)
    ~~~

    

* 使用预编译库

  * 想在不发布源代码的情况下将模块发布给他人。

  * 想使用共享模块的预编译版来加速编译过程

    ~~~C++
    #预编译共享模块的Android.mk文件
    LOCAL_PATH := $(call my-dir)
    #第三方预编译的库
    include $(CLEAR_VARS)
    LOCAL_MODULE := avilib
    LOCAL_SRC_FILES := libavilib.so
    include $(PREBUILE_SHARED_LIBRARY)
    ~~~

    

* 编译独立的可执行文件

  * 为了方便测试和进行快速开发，可以编译成可执行文件。不用打包成apk就可以复制到Android设备上直接执行。

    ~~~C++
    # 独立可执行模块Android.mk文件
    LOCAL_PATH := $(call my-dir)
    include $(CLEAR_VARS)
    LOCAL_MODULE := module
    LOCAL_SRC_FILES := module.c
    LOCAL_STATIC_LIBRARIES := avilib
    include $(BUILD_EXECUABLE)
    ~~~

    

* 注意事项

  * 假如我们本地库libhello-jni.so依赖于libTest.so(可以使用NDK下的ndk-depends查看so的依赖关系)

  * 在Android6.0版本之前，需要在加载本地库前先加载被依赖的so

  * 在android6.0版本之后，不能再使用 预编译的动态库。

    ~~~C++
    # android 6.0 版本之前：
    System.loadLibrary("Test");
    System.loadLibrary("hello-jni");
    
    #Android 6.0版本之后：
    System.loadLibrary("hello-jni")
    ~~~

    



















