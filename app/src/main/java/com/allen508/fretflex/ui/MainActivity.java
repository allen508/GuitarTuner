package com.allen508.fretflex.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.allen508.fretflex.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setDevicePermissions();

        setContentView(R.layout.activity_main);
    }

    private void setDevicePermissions(){

        int recordAudioRequestCode = 1;
        String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO};

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, recordAudioRequestCode);
        }
    }

}