#  阿里云andfix热修复

* 编译dex 文件
    * build 文件里面找到class 文件 javac-debug-...-com.xxx.xx
    * sdk文件里面，找到dx.bat(win)
    * 打包命令 : dx --dex --output=fix.dex  打包文件路径名