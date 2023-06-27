package com.example.keylogger;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button shallwebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shallwebutton = findViewById(R.id.shallwebutton);

        shallwebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSecondActivity();
            }
        });

    }

    private void openSecondActivity(){
        Intent i = new Intent(MainActivity.this,SecondActivity.class);
        startActivity(i);
    }

}