# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)


# 一、定义工程名
project(cbasics-lib)

# 二、设置头文件搜索路径，本地工程与第三方库
include_directories(${CMAKE_SOURCE_DIR}/./src)
include_directories(${CMAKE_SOURCE_DIR}/./src/basics)
include_directories(${CMAKE_SOURCE_DIR}/./src/utils)


# 三、设置日志输出
message(STATUS " CMAKE_ANDROID_ARCH_ABI : ${CMAKE_ANDROID_ARCH_ABI}")
MESSAGE("----NATIVE_CLIENT_VERSION_INFO=" ${NATIVE_CLIENT_VERSION_INFO})
add_compile_options(-DNATIVE_CLIENT_VERSION_INFO="${NATIVE_CLIENT_VERSION_INFO}")

# 四、递归的方式，添加所有的源文件到变量中
file(GLOB_RECURSE glob_source_files "${CMAKE_CURRENT_SOURCE_DIR}/./src/*.c" "${CMAKE_CURRENT_SOURCE_DIR}/./src/*.cpp" "${CMAKE_CURRENT_SOURCE_DIR}/./src/*.cc")


# 五、设置lib的生成路径
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_SOURCE_DIR}/libs/${CMAKE_ANDROID_ARCH_ABI})


# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

# 六、添加需要链接的第三方搜索路径  静态库链接
#LINK_DIRECTORIES(${CMAKE_SOURCE_DIR}/./thirdparty/ffmpeg/lib/)

# 七、设置编译的动态库
add_library(cbasics-lib SHARED ${glob_source_files})

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
        cbasics-lib
        android
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})