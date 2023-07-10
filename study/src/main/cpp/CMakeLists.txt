
# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.22.1)

# Declares and names the project.

project("study_kotlin")

include_directories(${CMAKE_CURRENT_SOURCE_DIR})

file(GLOB utils_file utils/**.cpp utils/**.h)
add_library(slin-utils STATIC ${utils_file})

add_library(shared-memory STATIC shm/SharedMemory.cpp)
target_link_libraries(shared-memory slin-utils ${log-lib})

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

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
        native-mmap-server

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        server/native-mmap-server.cpp
        )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
        native-mmap-server

        # Links the target library to the log library
        # included in the NDK.
        ${log-lib}
        slin-utils
        shared-memory
        )

add_library(
        native-mmap-test
        SHARED
        test/native-mmap-test.cpp
)
target_link_libraries(
        native-mmap-test
        ${log-lib}
        slin-utils
        shared-memory
)
