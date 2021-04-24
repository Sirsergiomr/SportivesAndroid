package com.example.sportivesandroid.ui.qr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportivesandroid.LectorActivity;
import com.example.sportivesandroid.R;

public class qrFragment extends Fragment {
    private static final int QR_REQUEST_CODE = 8888;
    final int RequestCameraPermissionID = 1001;
    private qrviewmodel dashboardViewModel;
    private Button bt_scan;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(qrviewmodel.class);
        View root = inflater.inflate(R.layout.fragment_qr, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        bt_scan = root.findViewById(R.id.bt_qr);

        bt_scan.setOnClickListener(v -> {
            cameraPermisions();
        });

        return root;
    }
    private void cameraPermisions(){
        int estadoDePermiso = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
        if (estadoDePermiso == PackageManager.PERMISSION_GRANTED){
            startActivityForResult(new Intent(getContext(), LectorActivity.class),QR_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionID);
            System.out.println("PERMISOS");

        }
    }
}