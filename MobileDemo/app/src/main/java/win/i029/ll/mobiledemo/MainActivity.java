package win.i029.ll.mobiledemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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
                    MobileDemo demo = new MobileDemo(MainActivity.this);
                    result = demo.run();
                } catch (Exception e) {
                    result = e.toString();
                }
                tv.setText(result);
            }
        });
    }
}
