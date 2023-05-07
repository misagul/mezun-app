package com.example.mainactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mainactivity.databinding.ActivityPaylasimEkleBinding;
import com.example.mainactivity.databinding.ActivityRegisterBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class PaylasimEkleActivity extends AppCompatActivity {

    ActivityPaylasimEkleBinding binding;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    FirebaseFirestore firebaseFirestore;
    PaylasimModel paylasim;
    Uri uri;
    boolean payimg;
    ImageView imageView;
    String payUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaylasimEkleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore = FirebaseFirestore.getInstance();
        getSupportActionBar().setTitle("Paylaşım Ekle");
        progressDialog = new ProgressDialog(this);
        imageView = findViewById(R.id.paylasimVideo);
        binding.payEkle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String yazar = binding.payYazar.getText().toString();
                String baslik = binding.payBaslik.getText().toString();
                String icerik = binding.payIcerik.getText().toString();

                firebaseFirestore.collection("paylasim")
                        .document(UUID.randomUUID().toString())
                        .set(new PaylasimModel(yazar,baslik,icerik,payUri,payimg));
                Toast.makeText(PaylasimEkleActivity.this, "Paylaşım Yapıldı", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PaylasimEkleActivity.this, PaylasimActivity.class));
            }
        });

        binding.paylasimVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        uri = data.getData();
        if(uri.toString().contains("image")){
            payimg = true;
            binding.paylasimVideo.setImageURI(uri);
        }
        else{
            payimg = false;

            RequestOptions options = new RequestOptions().frame(1000);

        }
        progressDialog.show();
        FirebaseStorage.getInstance().getReference("paylasim/"+ UUID.randomUUID().toString()).putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    Toast.makeText(PaylasimEkleActivity.this, "Görsel/Video Yüklendi", Toast.LENGTH_SHORT).show();
                    task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()){
                                payUri = task.getResult().toString();
                                Glide.with(PaylasimEkleActivity.this).asBitmap().load(uri).into(imageView);

                            }
                        }
                    });
                }else{
                    Toast.makeText(PaylasimEkleActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.cancel();
            }
        });

    }



}