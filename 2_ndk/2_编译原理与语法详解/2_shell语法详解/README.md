# shell语法详解

## 一、变量的定义到适用

* 创建一个 shell 文件 vim demo.sh

  ~~~shell
  #!/bin/bash
  #File by //注释
  echo "wangyi"//输出
  A = 10  //变量
  echo #A 
  echo $PWD //环境变量，当前路径
  
  echo "######"
  echo $0
  echo $1  //输出第一个参数
  echo $2	 //输出第二个参数
  
  echo "this \$? is $?"
  echo "this \*? is $*"
  echo "this \#? is $#"
  ~~~

* 输入参数

  > 形式                                 说明
  >
  > $0 								当前程序的名称
  >
  > $n 								程序的输入参数 n=1 第一个参数 n2 第二个参数 1..n
  >
  > $*								所有的输入参数
  >
  > $#								输入参数的个数
  >
  > $?								命令执行的状态一般返回0 代表成功

  

## 二、语法(循环语句 for)

> for 变量 in 字符串
>
> do 
>
> ​		语句1
>
> done 

* seq 1  15 //遍历语句。
* 遍历

~~~shell
#!/bin/bash
for i in `seq 1 15`
do 
		echo "数字 $i"
~~~

* 遍历求和

  ~~~shell
  #!/bin/bash
  j=0
  for((i=0;i<=100;i++))
  do 
  		j= `expr $i + $j`
  done 
  	echo $j
  ~~~

* 遍历压缩文件

  ~~~shell
  #!/bin/bash
  for i in `find /root -name "*.sh"`
  do
  		tar -czf wanggi.tgz $i
  done 
  
  ~~~

  

* 循环语句 while

  > while 条件语句
  >
  > do
  >
  > ​		语句
  >
  > done 

~~~shell
#!/bin/bash
i=0
# while((i<100))
while[[ $i -lt 100]]
do 
		echo "数字 $i"
		i = `expr $i + 1`
done
~~~

* while实现读文件

  > while read line 
  >
  > do 
  >
  > ​		echo $line 
  >
  > done </root/text.txt

~~~shell
#!/bin/bash
while read line 
do
	echo $line 
done </root/text.txt
~~~

## 三、if语句

> 第一种
>
> if(表达式)
>
> fi
>
> 第二种
>
> if(表达式);then
>
> 语句
>
> else
>
> 语句
>
> fi
>
> 注意if()后面不需要{} 只需要；
>
> ​		语句一定要有tab键输入，不能是空格。空格在shell语法中不能随便写。是一种特殊语义代表分割。

~~~shell
#!/bin/bash
NUM1=100
MUM2=200
if(($NUM1!>$MUM2));then
		echo "num1>num2"
else 
		echo "num1<num2"
fi
~~~



## 四、逻辑运算符

* -f 判断文件是否存在

  ~~~shell
  #!/bin/sh
  NDK=/root/text.txt
  #if [ ! -d /root/wangyi ]; then
  if[ ! -f $NDK ]; then
  		mkdir -p /root/wangyi
  else 
  			echo "目录已经存在"
  			cat $NDK
  fi
  ~~~

* -d 判断目录是否存在

* -eg 等于(整形比较)

* -ne 不等于比较

* -le 小于比较

* -ge 大于或等于

* -a 双方都成立

* -o 单方成立

## 五、算数运算符

* 算数运算符

  ~~~
  运算符			说明				举例
  +					加法				`expr $a + $b` 结果为30
  -					减法				`expr $a - $b` 结果为-10
  * 				乘法				`expr $a \* $b` 结果为200
  /					除法				`expr $b/$b` 结果为2
  %					取余				`expr $b%$a` 结果为0
  =					赋值				a=$b 将把变量b的值赋给a
  ==				相等用于比较两个数字，相同则返回true。       [$a == $b]返回false
  !=				不相等。用于比较两个数字，不相同返回true 			[$a !=$ b]返回true
  注意：条件表达式要放在方括号之间，并且要有空格，例如[$a==$b]是错误的，必须写成[$a == $b]
  ~~~

  * 运算方式

    * 使用$(())  

      ~~~
      r = $((4 + 5))
      ~~~

    * 使用$[]

      ~~~
      r = $[ 4 + 5 ]
      ~~~

    * 使用 let 命令

      ~~~
      let n= 4+5
      ~~~

    * 使用 expr 外部程式

      ~~~
      r=`expr 4 + 5`
      ~~~

* 关系运算符

  ~~~
  运算符			说明																		 	  举例
  -eq				检测两个数是否相等，相等返回true。							[$a -eq $b]返回false
  -ne				检测两个数是否不相等，不相等返回true。				 	 [$a -ne $b]返回true
  -gt 			检测左边的数是否大于右边的，如果是，则返回true。  [$a -gt $b]返回false
  -lt			  检测左边的数是否小于右边的，如果是，则返回true。	[$a -lt $b]返回true
  -ge 			检测左边的数是否大于等于右边的，如果是，则返回true[$a -ge $b]返回false
  -le 			检测左边的数是否小于等于右边的，如果是，则返回true[$a -le $b]返回true
  ~~~

* 布尔运算符

  ~~~
  运算符 		说明																				举例
  !				  非运算，表达式为true则返回false，否则返回true		[!false]返回true
  -o				或运算，有个表达式为true则返回true。					  [$a -lt 20 -o $b -gt 100]返回true
  -a				与运算，两个表达式都为true才返回true。					[$a -lt 20 -a $b -gt 100]返回false
  ~~~

* 逻辑运算符

  ~~~
  一下介绍shell 的逻辑运算符，假定变量a为10，变量b为20；
  运算符			说明							举例
  &&				逻辑的AND				[[$a -lt 100 && $b -gt 100]] 返回false
  ||				逻辑的OR					[[$a -lt 100 || $b -gt 100]] 返回true
  ~~~

* 字符串运算符

  ~~~
  运算符			说明													     				举例
  =					检测两个字符串是否相等，相等返回true					[$a = $b]返回false
  !=				检测两个字符串是否相等，不相等返回true					[$a != $b]返回true
  -z				检测字符串长度是否为0，为0返回true						[-z$a]返回false
  -n				检测字符串长度是否为0，不为0返回true						[-z "$a"] 返回true
  $					检测字符串是否为空，不为空返回true						[$a]返回true
  ~~~

* 文件测试运算符

  ~~~
  操作符			说明																															举例
  -b file 	检测文件是否是块设备，如果是，则返回true													 [-b $file]返回false
  -c file 	检测文件是否是字符设备文件，如果是返回true		  										[-c $file]返回false
  -d file   检测是否是目录，如果是，则返回true。															[-d $file]返回false
  -f file   检测文件是否是普通文件(既不是目录，也不是设备文件)，如果是，则返回true [-f $file]返回true
  -g file		检测文件是否设置了SGID位，如果是，则返回true											[-g $file]返回false
  -k file 	检测文件是否设置了沾着位(Sticky Bit)，如果是，则返回true					 [-k $file]返回false
  -p file 	检测文件是否是有名管道，如果是，则返回true													[-p $file]返回false
  -u file 	检测文件时是否设置了SUID位，如果是，则返回true											[-u $file]返回false
  -r file 	检测文件是否可读，如果是，则返回true															[-r $file]返回true
  -w file 	检测文件时可写，如果是，则返回true																 [-w $file]返回true
  -x file 	检测文件是否可执行，如果是，则返回true															[-x $file]返回true
  -s file 	检测文件是否为空(文件大小是否大于0)，不为空返回true									 [-s $file]返回true
  -e file 	检测文件(包括目录)是否存在，如果是，则返回true											[-e $file]返回true
  ~~~

  

## 六、重定向

* 描述符
  * 标准输入 standard input 0 (默认设备键盘)
  * 标准输出 standard output1(默认设备显示器)
  * 错误输出: error output2 (默认设备显示器)

* 重定向

  > 你可以从显示器获取输入 也可以输出到显示器 我们把来源称之为重定向。

  * 输入重定向  >

  * 输出重定向 <

    

## 七、方法参数传递机制详解

* 介绍

  > 1.Linux shell 中的函数和大多数编程语言中的函数一样
  >
  > 2.将相似的任务或代码封装到函数中，供其他地方调用

* 定义

  * 第一种格式

    ~~~ shell
    name(){
    command;
    command2;
    }
    ~~~

  * 第二种格式

    ~~~shell
    [ function ] funname [()]
    {
    action;
    [return int;]
    }
    ~~~

    

## 七、shell脚本编写与执行编译ffmpeg

* ffmpeg组成部分

  * ffmpeg工具
    * ffmpeg
    * ffplay
    * ffprobe

  * ffmpeg开发库
    * libavcodec
    * libvutil
    * libvformat
    * libavdevice
    * libavfilter
    * libswscale
    * libswresample

* ffmpeg编译流程

  * 1下载 

  * 2 解压

  * 3 配置ndk环境变量

  * 4 编译ffmpeg库

    ~~~
    # !/bin/bash
    
    NDK_ROOT=/root/ndk/android-ndk-r17c
    
    # TOOLCHAIN 变量指向ndk中的交叉编译gcc所在的目录
    
    TOOLCHAIN=$NDK_ROOT/toolchains/arm-linux-androideabi-4.9/prebuilt/linux-x86_64/
    
    # FLAGS与INCLUDES变量 可以从AS ndk工程的.externativeBuild/cmake/debug/armeabi-v7a/build.ninja中拷贝，需要注意的是**地址**
    
    FLAGS="-isystem $NDK_ROOT/sysroot/usr/include/arm-linux-androideabi -D__ANDROID_API__=21 -g -DANDROID -ffunction-sections -funwind-tables -fstack-protector-strong -no-canonical-prefixes -march=armv7-a -mfloat-abi=softfp -mfpu=vfpv3-d16 -mthumb -Wa,--noexecstack -Wformat -Werror=format-security -std=c++11  -O0 -fPIC"
    INCLUDES="-isystem $NDK_ROOT/sources/cxx-stl/llvm-libc++/include -isystem $NDK_ROOT/sources/android/support/include -isystem $NDK_ROOT/sources/cxx-stl/llvm-libc++abi/include"
    
    # 执行configure脚本，用于生成makefile
    
    # --prefix : 安装目录
    
    # --enable-small : 优化大小
    
    # --disable-programs : 不编译ffmpeg程序(命令行工具)，我们是需要获得静态(动态)库。
    
    # --disable-avdevice : 关闭avdevice模块，此模块在android中无用
    
    # --disable-encoders : 关闭所有编码器 (播放不需要编码)
    
    # --disable-muxers :  关闭所有复用器(封装器)，不需要生成mp4这样的文件，所以关闭
    
    # --disable-filters :关闭视频滤镜
    
    # --enable-cross-compile : 开启交叉编译（ffmpeg比较**跨平台**,并不是所有库都有这么happy的选项 ）
    
    # --cross-prefix: 看右边的值应该就知道是干嘛的，gcc的前缀 xxx/xxx/xxx-gcc 则给xxx/xxx/xxx-
    
    # disable-shared enable-static 不写也可以，默认就是这样的。
    
    # --sysroot: 
    
    # --extra-cflags: 会传给gcc的参数
    
    # --arch --target-os :
    
    PREFIX=./android/armeabi-v7a2
    ./configure \
    --prefix=$PREFIX \
    --prefix=$PREFIX \
    --enable-small \
    --disable-programs \
    --disable-avdevice \
    --disable-encoders \
    --disable-muxers \
    --disable-filters \
    --enable-cross-compile \
    --cross-prefix=$TOOLCHAIN/bin/arm-linux-androideabi- \
    --disable-shared \
    --enable-static \
    --sysroot=$NDK_ROOT/platforms/android-21/arch-arm \
    --extra-cflags="$FLAGS $INCLUDES" \
    --extra-cflags="-isysroot $NDK_ROOT/sysroot" \
    --arch=arm \
    --target-os=android 
    
    make clean
    make install
    ~~~

    





































