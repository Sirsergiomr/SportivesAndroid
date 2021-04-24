package com.example.sportivesandroid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class LectorActivity extends AppCompatActivity {
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    boolean seguir = true;
    String value = "0";
    //    private ProgressDialog progress;
    SurfaceView cameraPreview, scanner;
    final int RequestCameraPermissionID = 1001;
    ImageButton bt_cross;
    Dialog dialog;
    Dialog cargando;
    private final static int WEB_VIEW_REQUEST = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lector_qr);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        initializeUi();
        iniciarQrDetector();
        bt_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private AlertDialog cargando() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.loading, null);


        builder.setView(v);
        builder.setCancelable(false);

        return builder.create();

    }

    private void initializeUi() {
        scanner = findViewById(R.id.surfaceView);
        cameraPreview = findViewById(R.id.surfaceView);
        bt_cross = findViewById(R.id.bt_cross);
        cargando = cargando();
    }


    private void iniciarQrDetector() {

        System.out.println("Iniciar Qr");

        final Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        barcodeDetector = new BarcodeDetector.Builder(LectorActivity.this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                if (seguir) {
                    final SparseArray<Barcode> qrcodes = detections.getDetectedItems();

                    try {
                        value = qrcodes.valueAt(0).rawValue;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("No se ha encontrado ningún código.");
                    }

                    if (!value.equals("0")) {
                        seguir = false;
                        System.out.println("Leido, "+value);
//                        checkQr();
                    }
                }
            }
        });
        cameraSource = new CameraSource
                .Builder(LectorActivity.this, barcodeDetector)
                .setRequestedPreviewSize(height, width)
                .setAutoFocusEnabled(true)
                .build();

        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(LectorActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //Request permission
                    ActivityCompat.requestPermissions(LectorActivity.this,
                            new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionID);
                    System.out.println("PERMISOS");
                    return;
                }
                try {
                    System.out.println("INICIAR CAMARA");
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    System.out.println("FALLO AL INICIAR CAMARA");
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });
    }
}