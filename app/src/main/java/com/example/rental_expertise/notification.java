package com.example.rental_expertise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Model.Renters;

public class notification extends AppCompatActivity {

    private RecyclerView rentList;

    RecyclerView.LayoutManager layoutManager;
    DatabaseReference mDatabase;
    private FirebaseUser cUser;
    notoficationAdapter adapter;
    List<Renters> mData;

    DatabaseReference rentRef= FirebaseDatabase.getInstance().getReference().child("ConfirmRent");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        rentList= findViewById(R.id.rent_recyler);

        rentList.setHasFixedSize(true);
        Log.d("checked","i am here");
        mData = new ArrayList<>();
        layoutManager = new LinearLayoutManager( this);
        rentList.setLayoutManager(layoutManager);
        cUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        data();
    }

    void data(){

        rentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds: snapshot.getChildren())
                {
                    Renters data = ds.getValue(Renters.class);
                    Log.d("Checked",data.getAddress());
                    mData.add(data);

                }
                Log.d("Checked", String.valueOf(mData));
                adapter = new notoficationAdapter(mData,rentList.getContext());
                rentList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.d("Checked","Failed to retrieve");
            }
        });

    }
}