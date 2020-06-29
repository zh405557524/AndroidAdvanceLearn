# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)


# 一、 定义工程名
project(player-lib)

# 二、设置头文件搜索路径，本地工程与第三方库
include_directories(${CMAKE_SOURCE_DIR}/./src)
include_directories(${CMAKE_SOURCE_DIR}/./src/player)
include_directories(${CMAKE_SOURCE_DIR}/./src/player/channel)
include_directories(${CMAKE_SOURCE_DIR}/./src/module)
include_directories(${CMAKE_SOURCE_DIR}/./src/utils)
include_directories(${CMAKE_SOURCE_DIR}/./thirdparty/ffmpeg/include/)

# 三、设置日志输出
message(STATUS " CMAKE_ANDROID_ARCH_ABI : ${CMAKE_ANDROID_ARCH_ABI}")
MESSAGE("----NATIVE_CLIENT_VERSION_INFO=" ${NATIVE_CLIENT_VERSION_INFO})
add_compile_options(-DNATIVE_CLIENT_VERSION_INFO="${NATIVE_CLIENT_VERSION_INFO}")

# 四、递归的方式，添加所有的源文件到变量中
file(GLOB_RECURSE glob_source_files "${CMAKE_CURRENT_SOURCE_DIR}/./src/*.c" "${CMAKE_CURRENT_SOURCE_DIR}/./src/*.cpp" "${CMAKE_CURRENT_SOURCE_DIR}/./src/*.cc")


# 五、设置lib的生成路径
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_SOURCE_DIR}/libs/${CMAKE_ANDROID_ARCH_ABI})

# 六、添加需要链接的第三方搜索路径  静态库链接

LINK_DIRECTORIES(${CMAKE_SOURCE_DIR}/./thirdparty/ffmpeg/lib/)

# 七、设置编译的动态库
add_library(player-lib SHARED ${glob_source_files})

# 八、链接第三方库以及系统库
target_link_libraries(player-lib
        avfilter avformat avcodec avutil
        swresample swscale
        z
        OpenSLES
        android
        -llog -lz)








