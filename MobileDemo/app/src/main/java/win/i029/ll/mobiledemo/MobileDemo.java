package win.i029.ll.mobiledemo;

import android.app.Activity;
import android.util.Log;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.io.IOException;

/**
 * Created by lvh on 12/26/17.
 */

public class MobileDemo {
    private final static String TAG = "MobileDemo";
    private static final String MODEL_PATH = "file:///android_asset/mobile-model.pb";

    private TensorFlowInferenceInterface inferenceInterface;
    public MobileDemo(Activity activity) throws IOException {
        inferenceInterface = new TensorFlowInferenceInterface(activity.getAssets(),MODEL_PATH);

        Log.d(TAG, "Created a Tensorflow MobileDemo");
    }
    public String run() {
        // Tensor("Placeholder:0", shape=(1, 2), dtype=float32)
        // always 1 dim, feed assign Rank
        float[] output = new float[1 * 2];
        float[] input = new float[1 * 2];
        // init input

        for(int i = 0; i < 1 * 2 ; i++) {
            input[i] = i ;
        }

        inferenceInterface.feed("in_tensor", input, 1, 2);


        String[] outputNames = new String[] {"out_tensor"};
        inferenceInterface.run(outputNames);

        inferenceInterface.fetch("out_tensor", output);

        StringBuffer sb = new StringBuffer("");
        for(int i = 0; i < 1 * 2 ; i++) {
            sb.append(output[i]);

        }
        return sb.toString();
    }
    public void close() {
        inferenceInterface.close();
        inferenceInterface = null;
    }
}
