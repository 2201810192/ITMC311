package com.example.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private ProgressBar myBar;
    private TextView lblTopCaption;
    private EditText txtBox1;
    private Button btnDoSomething;

    private int accum = 0;
    private final long startingMills = System.currentTimeMillis();
    private final String PATIENCE = "Some important data is being collected now.\nPlease be patient.";

    private final Handler myHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        lblTopCaption = findViewById(R.id.lblTopCaption);
        myBar = findViewById(R.id.myBar);
        txtBox1 = findViewById(R.id.txtBox1);
        btnDoSomething = findViewById(R.id.btnDoSomething);

        // Setup progress bar
        myBar.setMax(100);
        lblTopCaption.setText(PATIENCE);

        // Button click listener
        btnDoSomething.setOnClickListener(v -> {
            Editable txt = txtBox1.getText();
            Toast.makeText(this, "You said >> " + txt, Toast.LENGTH_LONG).show();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Create and start background thread
        new Thread(backgroundTask, "backAlias1").start();
        myBar.incrementProgressBy(0);
    }

    // Foreground task for UI updates
    private final Runnable foregroundTask = new Runnable() {
        @Override
        public void run() {
            try {
                int progressStep = 5;
                String status = PATIENCE + "\nTotal sec. so far: " +
                        ((System.currentTimeMillis() - startingMills) / 1000);
                lblTopCaption.setText(status);

                myBar.incrementProgressBy(progressStep);
                accum += progressStep;

                if (accum >= myBar.getMax()) {
                    lblTopCaption.setText("Background work is OVER!");
                    myBar.setVisibility(View.INVISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    // Background task for processing
    private final Runnable backgroundTask = new Runnable() {
        @Override
        public void run() {
            try {
                for (int n = 0; n < 20; n++) {
                    Thread.sleep(1000);
                    myHandler.post(foregroundTask);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}