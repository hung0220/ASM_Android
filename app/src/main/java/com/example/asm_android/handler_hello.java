package com.example.asm_android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class handler_hello extends AppCompatActivity {
Handler  handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_hello);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
// Using handler with postDelayed called runnable run method

            @Override

            public void run() {
                Intent intent = new Intent(handler_hello.this, MainActivity.class);

                startActivity(intent);
                // close this activity
                finish();
            }
        }, 1 * 1500); // wait for 5 seconds
    }
}