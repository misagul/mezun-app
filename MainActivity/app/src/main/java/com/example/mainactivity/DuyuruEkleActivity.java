package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mainactivity.databinding.ActivityDuyuruBinding;
import com.example.mainactivity.databinding.ActivityDuyuruEkleBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class DuyuruEkleActivity extends AppCompatActivity {

    ActivityDuyuruEkleBinding binding;
    ProgressDialog progressDialog;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDuyuruEkleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Duyuru Ekle");
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        binding.duyuruEkleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String yazar = binding.duyuruYazar.getText().toString();
                String icerik = binding.duyuruIcerik.getText().toString();
                String uid = FirebaseAuth.getInstance().getUid();

                SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                Date tarih = null;
                try {
                    tarih = f.parse(binding.duyuruTarih.getText().toString());
                } catch (ParseException e) {
                    Toast.makeText(DuyuruEkleActivity.this, "Duyuru eklenemedi", Toast.LENGTH_SHORT).show();
                }


                firebaseFirestore.collection("duyuru")
                                .document(UUID.randomUUID().toString())
                                .set(new DuyuruModel(uid, yazar,icerik,tarih));

                startActivity(new Intent(DuyuruEkleActivity.this, DuyuruActivity.class));
            }
        });
    }
}