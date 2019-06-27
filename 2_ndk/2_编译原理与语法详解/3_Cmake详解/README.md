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

































