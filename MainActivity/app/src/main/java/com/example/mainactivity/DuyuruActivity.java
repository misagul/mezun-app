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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainactivity.databinding.ActivityDuyuruBinding;
import com.example.mainactivity.databinding.ActivityRegisterBinding;
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

public class DuyuruActivity extends AppCompatActivity {
    ActivityDuyuruBinding binding;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDuyuruBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Duyurular");

        recyclerView = findViewById(R.id.recylerView);
        linearLayoutManager = new LinearLayoutManager(this, recyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Calendar c = Calendar.getInstance();
        Date curDate = c.getTime();
        Query query = db.collection("duyuru").whereGreaterThan("tarih",curDate);

        binding.btnDuyuruEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DuyuruActivity.this, DuyuruEkleActivity.class));
            }
        });



        FirestoreRecyclerOptions<DuyuruModel> options = new FirestoreRecyclerOptions.Builder<DuyuruModel>()
                .setQuery(query, DuyuruModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<DuyuruModel, ViewHolder>(options) {
            @Override
            public void onBindViewHolder(ViewHolder holder, int position, DuyuruModel model) {
                holder.bind(model);
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup group, int i) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.duyuru_layout, group, false);

                return new ViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);

    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView yazar;
        TextView icerik;
        TextView tarih;
        public ViewHolder(@Nonnull View itemView){
            super(itemView);
            yazar = itemView.findViewById(R.id.yazar);
            icerik = itemView.findViewById(R.id.icerik);
            tarih = itemView.findViewById(R.id.tarih);

        }
        public void bind(DuyuruModel duyuruModel){
            yazar.setText(duyuruModel.getYazar());
            icerik.setText(duyuruModel.getIcerik());
            tarih.setText(duyuruModel.getTarih().toString());
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