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
     ~~~
     if(表达式)
            COMMAND(ARGS...)
      elseif(表达式)
            COMMAND(ARGS...)
      endif(表达式)
            COMMAND(ARGS...)
     ~~~
    * elseif和else部分是可选的，也可以有多个elseif部分，缩进和空格对语句解析没有影响。
    ~~~
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
       ~~~
       while(表示)
            COMMAND(ARGS...)
       endwhile(表达式)
            COMMAND(ARGS...)
       ~~~
     * break命令可以跳出整个循环，continue()可以跳出当前的循环。
     
     ~~~
     set(a "")
     while(NOT a STREQUAL "xxx")
        set(a "${a}x")
        meeage("a = ${a}")
     endwhile
     ~~~

* CMake流程控制-循环遍历一
   * 语法格式：
   ~~~
   foreach(循环变量 参数1 参数2 ...参数N)
         COMMAND(ARGS...)
   endforeach(循环变量)
   ~~~
   * 每次迭代设置循环变量为参数
   * foreach也支持break()和continue()命令跳出循环
   
   ~~~
   foreach(item 1 2 3)
         message("item = ${item}")
    endforeach(item)
   ~~~
   
 * Cmake流程控制-循环遍历二
   * 语法格式
   ~~~
   foreach(循环变量 RANGE total)
         COMMAND(ARGS...)
   endforeach(循环变量)
   ~~~
   * 循环范围从0到total
    ~~~
   foreach(item RANGE 3)
         message("item = ${item}")
    endforeach(item)
   ~~~
   
* CMake流程控制-循环遍历三
   * 语法格式：
   ~~~
    foreach(循环变量 RANGE start stop stop)
         COMMAND(ARGS...)
   endforeach(循环变量)
   ~~~
   * 循环范围从start到stop，循环增量为step
   
   ~~~
   foreach(item RANGE 1 5 2)
          message("item = ${item}")
    endforeach(item)
   ~~~
   
* CMake流程控制-循环遍历四
   * foreach还支持对列表的循环
   * 语法格式
   ~~~
   foreach(循环遍历 IN LISTS 列表)
         COMMAND(ARGS...)
   endforeach(循环变量)
   ~~~
   
   ~~~
   set(list_var 1 2 3)
   foreach(item IN LISTS list_var)
      message("item = ${item}")
    endforeach(item)
   ~~~
   
* CMake自定义函数命令
   * 自定义函数命令格式：
   ~~~
   function(<name>[arg1[arg2[grg3...]]]
         COMMAND()
   endfuntion(<name>)
   ~~~
   * 函数命令调用格式:name(实参列表)
   ~~~
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
   ~~~
  macro(<name>[arg1[arg2[grg3...]]]
         COMMAND()
   endfuntion(<name>)
   ~~~
   * 宏命令调用格式化: name(实参列表)
   ~~~
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
   ~~~
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
   ~~~
     cmake_minimum_required(VERSION 3.4.1)
   ~~~
    指定cmake最低支持的版本
    
    * aux_source_directory
      * 查找当前目录所有源文件 并将源文件名称保存到 DIR_SRCS变量
      * 不能查找子目录
    ~~~
    aux_sourec_directory(.DIR_SRCS)
    ~~~
   
* 常用命令-add_library
   * 添加一个库
      * 添加一个库文件，名为 name
      * 指定STATIC,SHARED,MODULE参数来指定库的类型。STATIC:静态库;SHARED:动态库;MODULE:在使用dyl的系统有效，若不支持dyld，等同于SHARED.
      * EXCLUDE_FROM_ALL：表示该库不会被默认构建
      * source1 source2.....sourceN:用来指定库的源文件
      ~~~
      add_library(<name> [STATIC|SHARED|MODULE] 
      [EXCLUDE_FROM_ALL] source1 source2...sourceN)
      ~~~
      
    * 导入预编译库
      * 添加一个已存在的预编译库，名为 name
      * 一般配合set_target_properties使用
     ~~~
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
   ~~~
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
   ~~~
   # 可以用相对或绝对路径，也可以用自定义的变量值
   include_directories(./include ${$MY_INCLUDE})
   ~~~

* 常用命令-target_link_libraries
   * 将若干库连接到目标文件
   * 连接的顺序应该符合gcc链接顺序规则，被链接的库放在依赖它的库的后面，即如果上面的命令中，lib1依赖lib2，lib2又依赖于1ib3，则在上面命令中必须严格按照lib1 lib2 lib3 的顺序排列，否则会报错
   ~~~
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
   ~~~
   add_definitions(-DFOO -DDBUG ...)
   ~~~

* 常用命令-add_subdirectory
   * 如果当前目录下还有子目录时可以使用add_subdirectory,子目录也需要包含有CMakeList.txt
   ~~~
   #sub_dir指定包含CMakeLists.txt和源码文件的子目录位置
   #binary_dir是输出路径，一般可以不指定
   add_subdirecroty(sub_dir [binary_dir])
   ~~~

* 常用命令-file















