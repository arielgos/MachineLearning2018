package com.agos.ml;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.agos.ml.util.CameraSource;
import com.agos.ml.util.CameraSourcePreview;
import com.agos.ml.util.FaceDetectionProcessor;
import com.agos.ml.util.GraphicOverlay;

import java.io.IOException;

public class Main extends AppCompatActivity {

    private CameraSource cameraSource = null;
    private CameraSourcePreview preview;
    private GraphicOverlay graphicOverlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        preview = findViewById(R.id.firePreview);
        graphicOverlay = findViewById(R.id.fireFaceOverlay);


        createCameraSource();

        findViewById(R.id.buttonML).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Configuration.getInstance().withText = !Configuration.getInstance().withText;
            }
        });


    }

    private void createCameraSource() {
        if (cameraSource == null) {
            cameraSource = new CameraSource(this, graphicOverlay);
        }
        cameraSource.setMachineLearningFrameProcessor(new FaceDetectionProcessor());
        cameraSource.setFacing(CameraSource.CAMERA_FACING_BACK);
    }

    private void startCameraSource() {
        if (cameraSource != null) {
            try {
                preview.start(cameraSource, graphicOverlay);
            } catch (IOException e) {
                cameraSource.release();
                cameraSource = null;
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        startCameraSource();

    }

    @Override
    protected void onPause() {
        super.onPause();
        preview.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cameraSource != null) {
            cameraSource.release();
        }
    }


}
