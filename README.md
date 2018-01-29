# tensorflowmobile
TensorFlow moblie and TensorFlow Lite on Android Phone
# Build From Source
   Must Use NDK R14b(http://www.oreilly.com/data/free/building-mobile-applications-with-tensorflow.csp)
   1. git clone --recurse-submodules https://github.com/tensorflow/tensorflow newSrc
   2. 修改 WORKSPACE文件,加入sdk/ndk路径
   3. bazel build -c opt //tensorflow/examples/android:tensorflow_demo
   4. adb install -r bazel-bin/tensorflow/examples/android/tensorflow_demo.apk
   
   PR1: Executor failed to create kernel. Not found: No registered 'ListDiff' OpKernel for CPU devices
        tensorflow/core/kernels/BUILD android_extended_ops_group1 增加(ADD)
	@@ -4808,6 +4808,7 @@ filegroup(
         "population_count_op.cc",
         "population_count_op.h",
         "winograd_transform.h",
+        "listdiff_op.cc", ## ListDiff
         ":android_extended_ops_headers",

# Resource ：
	https://developer.android.google.cn/ndk/guides/neuralnetworks/index.html
	https://developer.android.google.cn/about/versions/oreo/android-8.1.html
	https://tensorflow.google.cn/
	https://github.com/googlecodelabs/tensorflow-for-poets-2


# 实现的功能 :
	1. 单个输入单个输出，将一个二维数据（1x2 matrix） 自加一次（每个元素乘2） [Mobile]
		out = in + in
		MobileDemo/python/mobile_android.py
	2. 多个输入多个输出， in1 = 1x2 matrix , in2 = 1x2 matrix [lite]
		out1 = in1 + in2
		out2 = in1 + in2 + in2
		TFLiteDemo/python/lite_android.py


# Android NN HAL (只有Lite使用):
	1. 接口： aosp/hardware/interfaces/neuralnetworks
	2. 框架： aosp/frameworks/ml 生成libneuralnetworks.so ， TensorFlow Lite会使用， 也包含一个Demo 的NN HAL
	3. 流程 ：
		0). Android boot， 启动NN HAL Service （Android 8的HAL定义与之前版本不一样，HAL层使用了HIDL语言）
        1). APK 调用Java
        2). Java通过JNI调用 TensorFlow Lite
        3). TensorFlow Lite 加载   libneuralnetworks.so , 如果成功执行4，  否则执行5
        4). libneuralnetworks.so 查找全部 nn hal service ， 如果存在nn hal service
        5). 执行nn代码， 如果优化不存在执行google默认代码，否则执行厂商优化代码

# TensorFlow Lite： 
	1. 必须将数据设置为二维数组(python 模型是1x2的矩阵)，并与模型一致。 部分对象需要使用lite版本（如Session）
	2. 单个输入、单个输出，使用run， 多个输入或多个输出使用runForMultipleInputsOutputs
	3. build.gradle :
		必须不能压缩
		aaptOptions {
		    noCompress "tflite"
		    noCompress "lite"
    	}
		dependencies {
			... ...
			compile 'org.tensorflow:tensorflow-lite:+'
		}
	4. 流程
		1). 使用tflite文件初始化 org.tensorflow.lite.Interpreter
        2). 使用run 或 runForMultipleInputsOutputs


# TensorFlow Mobile：
	 1. 数据永远为一维，但是feed时候，必须指定维数，需要使用tensorflow中的（如Session）
	 2. build.gradle :
		aaptOptions {
		    noCompress "pb"
    	}
		dependencies {
			... ...
			compile 'org.tensorflow:tensorflow-android:+'
		}
	 3. 流程
		1). 使用pb文件初始化org.tensorflow.contrib.android.TensorFlowInferenceInterface
        2). 使用feed， 提供输入数据， 如需要识别的图片。 如果有多个参数需要输入， 就调用多次, 通过python之前定义的name来关联
        3). 使用run, 运行推理
        4). 使用fetch，获取推理结果

# Linux
	1. 获取最新代码 https://github.com/tensorflow/tensorflow
	2. 安装sudo apt-get install eigen3
	3. protobuf版本必须与TensorFlow中一致
		curl -SsL -O https://github.com/google/protobuf/archive/v${PROTOBUF_VERSION}.tar.gz
		tar xzf v${PROTOBUF_VERSION}.tar.gz
		cd $DIR/protobuf-${PROTOBUF_VERSION}
		./autogen.sh
		./configure --prefix=/usr
		make -j8
		sudo make install
	4. 编译Tensorflow
		./configure
		bazel build :libtensorflow_cc.so
		tensorflow/contrib/makefile$ ./build_all_linux.sh
	5. 拷贝库
		libtensorflow_cc.so libtensorflow_framework.so => Linux/lib
	6. 编译运行
		Linux/build$ cmake ..
		Linux/build$ make
		Linux/build$ ./tf_test



