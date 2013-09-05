package jp.amachang.gpuimagesandbox;

import java.io.FileOutputStream;
import java.io.IOException;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "GPUIMAGE_SANDBOX";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        GPUImage image = new GPUImage(this);
        Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.raw.sample); // LARGE IMAGE!
        image.setImage(srcBitmap);
        image.setFilter(new GPUImageSepiaFilter());
        
        long startMillis = System.currentTimeMillis();
        Log.d(TAG, "Start filter");
        
        Bitmap dstBitmap = image.getBitmapWithFilterApplied(); // HEAVY!
        
        Log.d(TAG, "End filter: " + (System.currentTimeMillis() - startMillis) + "[ms]");
        
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/filtered.png";
        try {
            FileOutputStream out = new FileOutputStream(filePath);
            dstBitmap.compress(CompressFormat.PNG, 80, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        Log.d(TAG, "Saved: filePath = " + filePath);
    }

}
