package win.i029.ll.tflitedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tv = (TextView)findViewById(R.id.textView);

        Button btn = (Button)findViewById(R.id.perform);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = "";
                try {
                    LiteDemo demo = new LiteDemo(MainActivity.this);
                    result = demo.run();
                    demo.close();
                } catch (Exception e) {
                    result = e.toString();
                }
                tv.setText(result);
            }
        });
    }
}
