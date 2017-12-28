package win.i029.ll.tflitedemo;

/**
 * Created by lvh on 12/26/17.
 */

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.support.annotation.NonNull;
import android.util.Log;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static android.content.ContentValues.TAG;

public class LiteDemo {

    private static final String MODEL_PATH = "lite-model.tflite";

    private Interpreter tflite;

    LiteDemo(Activity activity) throws IOException {
        tflite = new Interpreter(loadModelFile(activity));

        Log.d(TAG, "Created a Tensorflow LiteDemo");
    }

    public String run() {
        // Tensor("Placeholder:0", shape=(1, 2), dtype=float32)
        // inputs : float[1][2] , float[1][2]
        // outputs: float[1][2] , float[1][2]
        float[][] output = new float[1][2];
        float[][] input = new float[1][2];
        // init input
        for(int i = 0; i < 1 ; i++) {
            for(int j = 0; j < 2; j++) {
                input[i][j] = i * 1000.0f + j;
            }
        }



        Object[] inputList = new Object[2];
        float[][] input2 = new float[1][2];
        for(int i = 0; i < 1 ; i++) {
            for(int j = 0; j < 2; j++) {
                input2[i][j] = input[i][j] * 2;
            }
        }
        inputList[0] = input;
        inputList[1] = input2;

        Map<Integer,Object>  outputMap = new HashMap<Integer, Object>();
        float[][] output2 = new float[1][2];
        outputMap.put(0,output);
        outputMap.put(1,output2);

        tflite.runForMultipleInputsOutputs(inputList,outputMap);

        StringBuffer sb = new StringBuffer("");
        for(int i = 0; i < 1 ; i++) {
            for(int j = 0; j < 2; j++) {
                sb.append(output[i][j]);
                sb.append(",");
            }
        }
        sb.append("  :   ");
        for(int i = 0; i < 1 ; i++) {
            for(int j = 0; j < 2; j++) {
                sb.append(output2[i][j]);
                sb.append(",");
            }
        }

        return sb.toString();
    }

    private String run_in1_out1() {
        // Tensor("Placeholder:0", shape=(1, 2), dtype=float32)
        float[][] output = new float[1][2];
        float[][] input = new float[1][2];
        // init input

        for(int i = 0; i < 1 ; i++) {
            for(int j = 0; j < 2; j++) {
                input[i][j] = i * 1000.0f + j;
            }
        }

        tflite.run(input, output);
        tflite.close();
        StringBuffer sb = new StringBuffer("");
        for(int i = 0; i < 1 ; i++) {
            for(int j = 0; j < 2; j++) {
                sb.append(output[i][j]);
            }
        }
        return sb.toString();
    }

    public void close() {
        tflite.close();
        tflite = null;
    }

    private MappedByteBuffer loadModelFile(Activity activity) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd(MODEL_PATH);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

}
