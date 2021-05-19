package com.sedatepace.foregraound1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn1, btn2;
    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);

        btn1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startService();
                Toast.makeText(getApplicationContext(),"start",Toast.LENGTH_SHORT).show();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"end",Toast.LENGTH_SHORT).show();
                stopService();
            }
        });
    }

    public void startService(){
        serviceIntent = new Intent(this, MyService.class);
        startService(serviceIntent);
    }

    public void stopService(){
        serviceIntent = new Intent(this, MyService.class);
        stopService(serviceIntent);
    }
}