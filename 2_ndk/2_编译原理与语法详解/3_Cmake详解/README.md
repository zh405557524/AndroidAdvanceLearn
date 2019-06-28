# Cmake语法详解
## 一、CMake语法
* 什么是CMake
> 构建原生库的默认工具。

* CMake源文件
    * CMake的源码文件可以包含命令、注释、空格和换行
    * 以CMake编译的源文件以CMakeLists.txt命名以.cmake为扩展名
    * 可以通过add_subdirectory()命令把子目录的CMake源文件添加进来。
    * CMake源文件中所有有效的语句都是命令，可以是内置命令或自定义的函数/宏命令。

* CMake注释
    * 单行注释：#注释内容（注释从#开始到行尾结束）
    * 多行注释：可以使用括号来实现多行注释（# [] []）
    ~~~cmake
    # 单行注释
    #[[多行注释
    多行注释
    多行注释]]
    ~~~
    
* CMake变量
    * CMake中所有变量都是string类型。可以使用set()和unset()命令来声明或移除一个变量。
    * 变量的引用：$[变量名]
    ~~~cmake
    # 声明变量： set(变量名 变量值)
    set(var 123)
    # 引用变量 message 命令来打印
    message("var = ${var}")
    ~~~

* CMake列表(lists)
    * 列表也是字符串，可以把列表看作一个特殊的变量，这个变量有多个值。
    * 语法格式 ：set(列表名 值2 值2 ...值n) 或set(列表名 "值1；值2；值n")
    * 列表的引用：${列表名}
    ~~~cmake
    #声明列表 set(列表名 值1 值2 。。。值n)
    #或set(列表名 "值1；值2；。。；值n")
    set(list_var 1 2 3 4 5)
    #或者
    set(list_var1 "1;2;3;4;5")
    message("list_var = ${list_var}")
    ~~~
    
* Cmake流程控制-操作符

> 类型                名称
  一元            EXIST<COMMAND<DEFINED
  二元         EQUAL<LESS<LESS_EQUAL<GREATER<GREATER_EQUAL,
 STREQUAL,STRLESS,STRLESS_EQUAL,STRGREATER,
 STRGREATER_EQUAL,VERSION_EQUAL,VERSION_LESS,
 VERSION_LESS_EQUAL,VERSION_GREATER,
 VERSION_GREATER_EQUAL,MATCHES
 逻辑                 NOT,AND,OR

 * CMake流程控制-布尔常量值
 > 类型                   值
    true               1,ON,YES,TRUE,Y,非0的值
    false              0,OFF,NO,FALSE,N,IGNORE,NOTFOUND,空字符串，以-NOTFOUND结尾的字符串

* CMake流程控制-条件命令
    * 语法格式
     ~~~cmake
     if(表达式)
            COMMAND(ARGS...)
      elseif(表达式)
            COMMAND(ARGS...)
      endif(表达式)
            COMMAND(ARGS...)
     ~~~
    * elseif和else部分是可选的，也可以有多个elseif部分，缩进和空格对语句解析没有影响。
    ~~~cmake
    set(if_tap OFF)
    set(elseif_tap ON)
    
    if(#{if_tap})
        message("if")
    elseif(${elseif_tap})
        message("elseif")
    else(${if_tap})
        message("else")
    endif(${if_tap})
    ~~~

* CMake流程控制-循环命令
    * 语法格式：
       ~~~cmake
       while(表示)
            COMMAND(ARGS...)
       endwhile(表达式)
            COMMAND(ARGS...)
       ~~~
     * break命令可以跳出整个循环，continue()可以跳出当前的循环。
    
     ~~~cmake
     set(a "")
     while(NOT a STREQUAL "xxx")
        set(a "${a}x")
        meeage("a = ${a}")
     endwhile
     ~~~

* CMake流程控制-循环遍历一
   * 语法格式：
   ~~~cmake
   foreach(循环变量 参数1 参数2 ...参数N)
         COMMAND(ARGS...)
   endforeach(循环变量)
   ~~~
   * 每次迭代设置循环变量为参数
   * foreach也支持break()和continue()命令跳出循环
   
   ~~~cmake
   foreach(item 1 2 3)
         message("item = ${item}")
    endforeach(item)
   ~~~
   
 * Cmake流程控制-循环遍历二
   * 语法格式
   ~~~cmake
   foreach(循环变量 RANGE total)
         COMMAND(ARGS...)
   endforeach(循环变量)
   ~~~
   * 循环范围从0到total
    ~~~cmake
   foreach(item RANGE 3)
         message("item = ${item}")
    endforeach(item)
    ~~~
   
* CMake流程控制-循环遍历三
   * 语法格式：
   ~~~cmake
    foreach(循环变量 RANGE start stop stop)
         COMMAND(ARGS...)
   endforeach(循环变量)
   ~~~
   * 循环范围从start到stop，循环增量为step
   
   ~~~cmake
   foreach(item RANGE 1 5 2)
          message("item = ${item}")
    endforeach(item)
   ~~~
   
* CMake流程控制-循环遍历四
   * foreach还支持对列表的循环
   * 语法格式
   ~~~cmake
   foreach(循环遍历 IN LISTS 列表)
         COMMAND(ARGS...)
   endforeach(循环变量)
   ~~~
   
   ~~~cmake
   set(list_var 1 2 3)
   foreach(item IN LISTS list_var)
      message("item = ${item}")
    endforeach(item)
   ~~~
   
* CMake自定义函数命令
   * 自定义函数命令格式：
   ~~~cmake
   function(<name>[arg1[arg2[grg3...]]]
         COMMAND()
   endfuntion(<name>)
   ~~~
   * 函数命令调用格式:name(实参列表)
   ~~~cmake
   function(func x y z)
      message("call function func")
      message("x = ${x}")
      message("y = ${y)")
      message("z = ${z}")
      message("ARGC = ${ARGC}")
      message("arg1 = ${ARGV0} arg2 = ${ARGV1}
      arg3 = ${ARGV2}")
      message("all args = ${ARGV}")
    endfunction(func)
    func(1 2 3)
   ~~~

* CMake自定义洪命令
   * 自定义宏命令格式：
   ~~~cmake
  macro(<name>[arg1[arg2[grg3...]]]
         COMMAND()
   endfuntion(<name>)
  ~~~
   * 宏命令调用格式化: name(实参列表)
   ~~~cmake
   macro(ma x y z)
      message("call macro ma")
      message("x = ${x}")
      message("y = ${y}")
      message("z = ${z}")
   endmacro(ma)
   
   ma(1 2 3)
   ~~~

* CMake 变量的作用域
   * 全局层：cache变量，在整个项目范围可见，一般在set定义变量时，指定CACHE参数就能定义为cache变量
   * 目录层：在当前目录CMakeLists.txt中定义，以及在该文件包含的其他cmake源文件中定义的变量
   * 函数层：在命令函数中定义的变量，属于函数作用域内的变量。

## 二、CMakeLists.txt详解

* CMakeLists.txt简析
   * 使用androidstudio 3.4创建一个C/C++Support的项目，默认在app/src/main目录下会生成cpp目录，里面包含CMakeList.txt和native-lib.cpp。
   ~~~cmake
   cmake_minimum_required(VERSION 3.4.1)
   
   add_library(
            native-lib
            SHARED
            native-lib.cpp)
   find_library(
            log-lib
            log)
   
   target_link_libraries(
            native-lib
            #{log-lib})
   ~~~

* 常用命令
   * cmake_minimum_required
   ~~~cmake
     cmake_minimum_required(VERSION 3.4.1)
   ~~~
    指定cmake最低支持的版本
   
    * aux_source_directory
      * 查找当前目录所有源文件 并将源文件名称保存到 DIR_SRCS变量
      * 不能查找子目录
    ~~~cmake
    aux_sourec_directory(.DIR_SRCS)
    ~~~
   
* 常用命令-add_library
   * 添加一个库
      * 添加一个库文件，名为 name
      * 指定STATIC,SHARED,MODULE参数来指定库的类型。STATIC:静态库;SHARED:动态库;MODULE:在使用dyl的系统有效，若不支持dyld，等同于SHARED.
      * EXCLUDE_FROM_ALL：表示该库不会被默认构建
      * source1 source2.....sourceN:用来指定库的源文件
      ~~~cmake
      add_library(<name> [STATIC|SHARED|MODULE] 
      [EXCLUDE_FROM_ALL] source1 source2...sourceN)
      ~~~
      
    * 导入预编译库
      * 添加一个已存在的预编译库，名为 name
      * 一般配合set_target_properties使用
     ~~~cmake
     add_library(<name>)
     <SHARED|STATIC|MODULE|UNKNOWN> IMPORTED)
     
     #比如
     add_library(test SHARED IMPORTED)
     set_target_properties(
               test #指明目标库名
               PROPERTIES IMPORTED_LOCATION #指明要设置的参数
               库路径/${ANDROID_ABI}/libtest.so #导入库的路径
     ~~~

* 常用命令-set
   * 设置CMake变量
   ~~~cmake
   #设置可执行文件的输出路径(EXCUTABLE_OUTPUT_PATH是全局变量)
   set(EXECUTABLE_OUTPUT_PATH [output_path])
   
   #设置库文件的输出路径(LIBRARY_OUTPUT_PATH是全局变量)
   set(LIBRARY_OUTPUT_PATH [output_path])
   
   #设置C++编译参数(CMAKE_CXX_FLAGS是全局变量)
   set(CMAKE_CXX_FLAGS "-Wall std=c++11")
   
   # 设置源文件集合(SOURCE_FILES是本地变量即自定义变量)
   set(SOURCE_FILES main.cpp test.cpp....)
   ~~~

* 常用命令-include_directories
   * 设置头文件目录
   * 相当于g++选项中的-l参数
   ~~~cmake
   # 可以用相对或绝对路径，也可以用自定义的变量值
   include_directories(./include ${$MY_INCLUDE})
   ~~~

* 常用命令-target_link_libraries
   * 将若干库连接到目标文件
   * 连接的顺序应该符合gcc链接顺序规则，被链接的库放在依赖它的库的后面，即如果上面的命令中，lib1依赖lib2，lib2又依赖于1ib3，则在上面命令中必须严格按照lib1 lib2 lib3 的顺序排列，否则会报错
   ~~~cmake
   target_link_libraries(<name> lib1 lib2 lib3)
   
   #如果出现相互依赖的静态库，CMake会允许依赖图中包含循环依赖，如：
   add_library(A STATIC a.c)
   add_library(B STATIC b.c)
   target_link_libraries(A B)
   target_link_libraries(B A)
   add_executable(main main.c)
   target_link_libraries(main A)
   ~~~

* 常用命令-add_definitions
   * 为当前路径以及子目录的源文件加入由-D引入的define flag
   ~~~cmake
   add_definitions(-DFOO -DDBUG ...)
   ~~~

* 常用命令-add_subdirectory
   * 如果当前目录下还有子目录时可以使用add_subdirectory,子目录也需要包含有CMakeList.txt
   ~~~cmake
   #sub_dir指定包含CMakeLists.txt和源码文件的子目录位置
   #binary_dir是输出路径，一般可以不指定
   add_subdirecroty(sub_dir [binary_dir])
   ~~~

* 常用命令-file

   * 文件操作命令

     ~~~cmake
     # 将message写入filename文件中，会覆盖文件原有内容
     file(WRITE filename "message")
     
     #	将message写入filename文件中，会追加在文件末尾
     file(APPEND filename "message")
     
     #从filename文件中读取内容并存储到var变量中，如果指定了numBytes和offset，
     #则从offset处开始最多读numBytes个字节，另外如果指定了HEX参数，则内容会以十六进制形式存储在var变量中
     file(READ filename var [LIMIT numBytes] [OFFSET offset] [HEX])
     
     #重命名文件
     file(RENAME <oldname> <newname>)
     
     #删除文件，等于rm命令
     file(REMOVE [file1...])
     
     #递归的执行删除文件命令，等于rm -r
     file(REMOVE_RECURSE [file1...])
     
     #根据指定的url下载文件
     #timeout超时时间；下载的状态会保存到status中；下载日志会被保存到log；sum指定所有下载文件预期的MD5值，如果指定会自动进行比较，
     #如果不一致，则返回 一个错误，SHOW_PROGRESS，进度信息会以状态信息的形式被打印出来
     file(DOWNLOAD url file [TIMEOUT timeout] [STATUS status] [LOG log] [EXPECTED_MD5 sum] [SHOW_PROGRESS])
     
     #创建目录
     file(MAKE_DIRECTORY [dir1 dir2...])
     
     #会把path转换为unix的开头的cmake风格路径，保存在result中
     file(TO_CMAKE_PATH path result)
     
     #它会把cmake风格的路径转换为本地路径风格:windows 下用 "\"，而unix下用 "/"
     file(TO_CMAKE_PATH path result)
     
     #将会为所有匹配查询表达式的文件生成一个文件list，并将该list存储进变量variable里，如果一个表达式指定了RELATIVE，返回的结果
     # 将会是相对于给定路径的相对路径，查询表达式例子：*.cxx,*.vt?
     #NOTE：按照官方文件的说法，不建议使用file的GLOB指令来手机工程的源文件
     file(GLOB variable [RELATIVE path] [globbing expressions]...)
     
     ~~~

     

* 常用命令-set_directory_properites

  * 设置路径的一种属性

  * prop1,prop2代表属性，取值为：INCLUDE_DIRECTORIES；LINK_DIRECTORIES；INCLUDE_REGULAR_EXPRESSION;ADDITIONAL_MAKE_CLEAN_FILES

    ~~~cmake
    set_directory_properties(PROPERTIES prop1 value1 prop2 value2)
    ~~~

    

* 常用命令-set_property

  * 在给定的作用域内设置一个命名的属性

  * PROPERTY参数是必现的

  * 第一个参数决定了属性可以影响的作用域：

    >GLOBAL: 全局作用域
    >
    >DIRECTORY：默认当前路径，也可以用[dir]指定路径
    >
    >TARGET：目标作用域，可以是0个或多个源文件
    >
    >CACHE：必现指定0个或多个cache中已有的条目

   ~~~cmake
  set_property(<GLOBAL|
  						DIRECTORY [dir]|
  						TARGET [target ...]|
  						SOURCE [src1 ...]|
  						TEST [test1 ...]|
  						CACHE [entry1 ...]>
  						[APPEND]
  						PROPERTY <name> [value ...])
   ~~~

  

* 多个源文件处理

  * 如果源文件很多，把所有文件一个个加入很麻烦，可以使用aux_source_directory命令或file命令，会查找指定目录下的所有源文件，然后将结果存进指定变量名

    ~~~cmake
    cmake_minimum_required(VERSION 3.4.1)
    # 查找当前目录所有源文件 并将名称保存到 DIR_SRCS 变量不能查找子目录
    aux_sourece_directory(.DIR_SRCS)
    #也可以使用
    #file(GLOB DIR_SRCS *.c *.cpp)
    
    add_library(
    					native-lib
    					SHARED
    					${DIR_SRCS})
    ~~~

    

* 多目录多源文件处理

  * 主目录中的CMakeLists.txt中添加add_subdirectory(child)命令，指明本项目包含一个子项目child。并在target_link_libraries指明本项目需要链接一个名为child的库

  * 子目录child中创建CMakeLists.txt，这里child编译为共享库

    ~~~cmake
    cmake_minimum_required(VERSION 3.4.1)
    aux_source_directory(.DIR_SRCS)
    #添加 child子目录下的cmakeList
    add_subdirectory(child)
    
    add_library(
    					native-lib
    					SHARED
    					${DIR_SRCS})
    target_link_libraries(tative-lib child)
    -----------------------------------------
    #child目录下的CMakeList.txt:
    cmake_minimum_required(VERSION 3.4.1)
    aux_source_directory(.DIR_LIB_SRCS)
    add_library(
    					child
    					SHARED
    					${DIR_LIB_SRCS})
    ~~~

    

* 添加预编译库（android6.0版本以前）

  * 假设我们本地项目引用了libimported-lib.so.

  * 添加add_library命令，第一个参数是模块名，第二个参数SHARED表示动态库，STATIC表示静态库，第三个参数IMPORTED表示以导入的形式添加。

  * 添加set_target_properties命令设置导入路径属性

  * 将import-lib添加到target_link_libraries命令参数中，表示native-lib需要链接imported-lib模块

    ~~~cmake
    cmake_minimum_required(VERSION 3.4.1)
    #使用 IMPORTED 标志告知 CMake 只希望将库导入项目中
    #如果静态库将shared改为static
    add_library(imported-lib
    						SHARED
    						IMPRORTED)
    #参数分别为：库、属性、导入地址、库所在地址
    set_target_properties(
    											imported-lib
    											PROPERTIES
    											IMPORTED_LOCATION
    											<路径> /libimported-lib.so)
    aux_source_directory(.DIR_SRCS)
    add_library(
    						native-lib
    						SHARED
    						${DIR_SRCS})
    target_link_libraries(native-lib imported-lib)
    			
    ~~~

    

* 添加预编译库(Android6.0版本以后)

  * 在android6.0及以上版本，如果使用上节的方法添加预编译动态库的话，会有问题。我们可以使用另外一种方式来配置

    ~~~cmake
    #set命令定义一个变量
    #CMAKE_C_FLAGS:c的参数，会传递给编译器
    #如果是c++文件，需要用CMAKE_CXX_FLAGS
    #-L:库的查找路径
    set(CMAKE_C_FLAGS "${CMAKE_CFLAGS}" -L[SO所在目录])
    ~~~

    

* 添加头文件目录

  * 为了确保CMake可以在编译时定位头文件，使用include_directories，相当于g++选项中的-|参数。这样就可以使用#include<xx.h>,否则需要是用 #include "path/xx.h"

    ~~~cmake
    cmake_minimum_required(VERSION 3.4.1)
    #设置头文件目录
    include_directories(<文件目录>)
    set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -L[SO所在目录]")
    aux_source_directory(.DIR_SRCS)
    add_library(
    						native-lib
    						SHARED
    						${DIR_SRCS})
    target_link_libraries(native-lib imported-lib)
    ~~~

    

* build.gradle配置

  * 还可以在gradle中使用arguments设置一些配置

    ~~~java
    android{
      defaultConfig{
        externalNativeBuild{
          cmake{
            //使用的编译器 clang/gcc
            //cmake默认就是 gnustl_static
            arguments "-DANDROID_TOOLCHAIN=clang",
            "-DANDROID_STL=gnustl_static"
              //指定cflags和cppflags，效果和cmakelist使用一样
              cFlags ""
              cppFLags ""
             //指定需要编译的cpu架构
              abiFIlters "armeabi-v7a"
          }
        }
      }
      
      externalNativeBuild{
        cmake{
          //指定CMakeLists.txt文件相对应前Build.gradle的路径
          path "xx/CMakeLists.txt"
        }
      }
       
    }
    ~~~

    













