package com.example.mongodb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Index extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        Thread time = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                } finally {
                    Intent i = new Intent(Index.this, Login.class);
                    startActivity(i);
                }
            }
        };
        time.start();
    }
    protected void onPause() {
        super.onPause();
        finish();
    }
}
