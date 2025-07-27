package com.example.asynctask;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = findViewById(R.id.textView);

        new MyTask().execute();
    }

    private class MyTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            txt.setText("Loading...");
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                Thread.sleep(3000); // simulate long running task
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Task Completed!";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            txt.setText(s);
        }
    }
}
