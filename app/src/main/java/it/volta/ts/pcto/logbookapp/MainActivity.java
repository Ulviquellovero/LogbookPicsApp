package it.volta.ts.pcto.logbookapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.google.zxing.Result;

public class MainActivity extends Activity
{
    private              CodeScanner qrScanner;
    private              int         idQrScannerView;
    private static final int         CAMERA_PERMISSION_REQUEST_CODE = 101;

    //---------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVariables();
        initView();
    }

    //---------------------------------------------------------------------------------------------

    private void initVariables()
    {
                        idQrScannerView  = R.id.scanner_view;
        CodeScannerView scannerView      = findViewById(idQrScannerView);
                        qrScanner        = new CodeScanner(this, scannerView);
    }

    //---------------------------------------------------------------------------------------------

    private void initView()
    {
        CodeScannerView codeScannerView = findViewById(idQrScannerView);
        qrScanner.setDecodeCallback(result -> onQrDecoded(result));
        codeScannerView.setOnClickListener(e -> onScannerClick());
    }

    //---------------------------------------------------------------------------------------------

    private void onQrDecoded(@NonNull final Result result)
    {
        runOnUiThread(() -> showQrContent(result));
    }

    //---------------------------------------------------------------------------------------------

    private void showQrContent(@NonNull final Result result)
    {
        Toast.makeText(MainActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
    }

    //---------------------------------------------------------------------------------------------

    private void onScannerClick()
    {
        qrScanner.startPreview();
    }

    //---------------------------------------------------------------------------------------------

    @Override
    protected void onStart()
    {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}
                                                         , CAMERA_PERMISSION_REQUEST_CODE);
    }

    //---------------------------------------------------------------------------------------------
    @Override
    protected void onResume()
    {
        super.onResume();
        qrScanner.startPreview();
    }

    //---------------------------------------------------------------------------------------------

    @Override
    protected void onPause()
    {
        qrScanner.releaseResources();
        super.onPause();
    }
}