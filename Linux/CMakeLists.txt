
PROJECT (tf_test)

CMAKE_MINIMUM_REQUIRED(VERSION 2.8.8)



SET(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -g -std=c++11 -W")

AUX_SOURCE_DIRECTORY(./src DIR_SRCS)

link_directories(./lib)

INCLUDE_DIRECTORIES(
	/home/lvh/tensorflow/src
	/home/lvh/tensorflow/src/tensorflow/contrib/makefile/downloads/eigen
	/home/lvh/tensorflow/src/bazel-genfiles
	/home/lvh/tensorflow/src/tensorflow/contrib/makefile/downloads/nsync/public/
	/home/lvh/tensorflow/src/contrib/makefile/gen/protobuf/include
)

ADD_EXECUTABLE(tf_test  ${DIR_SRCS}) 

TARGET_LINK_LIBRARIES(tf_test tensorflow_cc tensorflow_framework)
