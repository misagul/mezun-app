package com.example.mainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mainactivity.databinding.ActivityPaylasimBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Nonnull;

public class PaylasimActivity  extends AppCompatActivity {
    ActivityPaylasimBinding binding;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FirestoreRecyclerAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaylasimBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Paylaşımlar");

        recyclerView = findViewById(R.id.paylasimRecView);
        linearLayoutManager = new LinearLayoutManager(this, recyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);


        Calendar c = Calendar.getInstance();
        Query query = db.collection("paylasim");

        binding.btnPaylasimEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaylasimActivity.this, PaylasimEkleActivity.class));
            }
        });

        FirestoreRecyclerOptions<PaylasimModel> options = new FirestoreRecyclerOptions.Builder<PaylasimModel>()
                .setQuery(query, PaylasimModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<PaylasimModel, ViewHolder>(options) {
            @Override
            public void onBindViewHolder(ViewHolder holder, int position, PaylasimModel model) {
                holder.bind(model);
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.paylasim_layout, group, false);

                return new ViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);

    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView yazar;
        TextView baslik;
        TextView icerik;
        ImageView gorsel;
        public ViewHolder(@Nonnull View itemView){
            super(itemView);
            yazar = itemView.findViewById(R.id.yazar);
            icerik = itemView.findViewById(R.id.icerik);
            baslik = itemView.findViewById(R.id.baslik);
            gorsel = itemView.findViewById(R.id.gorsel);

        }
        public void bind(PaylasimModel paylasimModel){
            yazar.setText(paylasimModel.getUid());
            icerik.setText(paylasimModel.getIcerik());
            baslik.setText(paylasimModel.getBaslik().toString());
            Glide.with(PaylasimActivity.this).asBitmap().load(paylasimModel.getUrl()).into(gorsel);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }
    private Date parseDate(String date, String format) throws ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(date);
    }
}