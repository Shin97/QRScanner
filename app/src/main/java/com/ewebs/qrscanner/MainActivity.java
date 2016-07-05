package com.ewebs.qrscanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        scanner();
    }


    @OnClick(R.id.scannerButton)
    public void onClick(View v) {
        scanner();
    }

    private void scanner(){
        Intent intent = new Intent(this, BarcodeScanner.class);
        startActivity(intent);
    }
}