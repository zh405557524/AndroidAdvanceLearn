# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.14

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /Applications/CLion.app/Contents/bin/cmake/mac/bin/cmake

# The command to remove a file.
RM = /Applications/CLion.app/Contents/bin/cmake/mac/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /Users/hillliao/github/AndroidAdvanceLearn/2_ndk/1_c:c++基础/1_基础知识/3_pointer

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /Users/hillliao/github/AndroidAdvanceLearn/2_ndk/1_c:c++基础/1_基础知识/3_pointer/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/3_pointer.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/3_pointer.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/3_pointer.dir/flags.make

CMakeFiles/3_pointer.dir/main.c.o: CMakeFiles/3_pointer.dir/flags.make
CMakeFiles/3_pointer.dir/main.c.o: ../main.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/hillliao/github/AndroidAdvanceLearn/2_ndk/1_c:c++基础/1_基础知识/3_pointer/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building C object CMakeFiles/3_pointer.dir/main.c.o"
	/Library/Developer/CommandLineTools/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/3_pointer.dir/main.c.o   -c /Users/hillliao/github/AndroidAdvanceLearn/2_ndk/1_c:c++基础/1_基础知识/3_pointer/main.c

CMakeFiles/3_pointer.dir/main.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/3_pointer.dir/main.c.i"
	/Library/Developer/CommandLineTools/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /Users/hillliao/github/AndroidAdvanceLearn/2_ndk/1_c:c++基础/1_基础知识/3_pointer/main.c > CMakeFiles/3_pointer.dir/main.c.i

CMakeFiles/3_pointer.dir/main.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/3_pointer.dir/main.c.s"
	/Library/Developer/CommandLineTools/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /Users/hillliao/github/AndroidAdvanceLearn/2_ndk/1_c:c++基础/1_基础知识/3_pointer/main.c -o CMakeFiles/3_pointer.dir/main.c.s

# Object files for target 3_pointer
3_pointer_OBJECTS = \
"CMakeFiles/3_pointer.dir/main.c.o"

# External object files for target 3_pointer
3_pointer_EXTERNAL_OBJECTS =

3_pointer: CMakeFiles/3_pointer.dir/main.c.o
3_pointer: CMakeFiles/3_pointer.dir/build.make
3_pointer: CMakeFiles/3_pointer.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/Users/hillliao/github/AndroidAdvanceLearn/2_ndk/1_c:c++基础/1_基础知识/3_pointer/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking C executable 3_pointer"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/3_pointer.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/3_pointer.dir/build: 3_pointer

.PHONY : CMakeFiles/3_pointer.dir/build

CMakeFiles/3_pointer.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/3_pointer.dir/cmake_clean.cmake
.PHONY : CMakeFiles/3_pointer.dir/clean

CMakeFiles/3_pointer.dir/depend:
	cd /Users/hillliao/github/AndroidAdvanceLearn/2_ndk/1_c:c++基础/1_基础知识/3_pointer/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/hillliao/github/AndroidAdvanceLearn/2_ndk/1_c:c++基础/1_基础知识/3_pointer /Users/hillliao/github/AndroidAdvanceLearn/2_ndk/1_c:c++基础/1_基础知识/3_pointer /Users/hillliao/github/AndroidAdvanceLearn/2_ndk/1_c:c++基础/1_基础知识/3_pointer/cmake-build-debug /Users/hillliao/github/AndroidAdvanceLearn/2_ndk/1_c:c++基础/1_基础知识/3_pointer/cmake-build-debug /Users/hillliao/github/AndroidAdvanceLearn/2_ndk/1_c:c++基础/1_基础知识/3_pointer/cmake-build-debug/CMakeFiles/3_pointer.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/3_pointer.dir/depend

