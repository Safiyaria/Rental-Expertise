package com.example.rental_expertise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import HomesInFeed.HomesInFeed;
import Model.HomeInFeedModel;

public class myPost extends AppCompatActivity {

    private DatabaseReference HomeRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private FirebaseUser cUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);

        recyclerView =findViewById(R.id.recycler_myPost);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager( this);
        recyclerView.setLayoutManager(layoutManager);

        cUser = FirebaseAuth.getInstance().getCurrentUser();

        HomeRef = FirebaseDatabase.getInstance().getReference().child("Rent_posts");

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<HomeInFeedModel> option = new FirebaseRecyclerOptions.Builder<HomeInFeedModel>().setQuery(HomeRef, HomeInFeedModel.class).build();
        FirebaseRecyclerAdapter<HomeInFeedModel, HomesInFeed> adapter = new FirebaseRecyclerAdapter<HomeInFeedModel, HomesInFeed>(option) {
            @Override
            protected void onBindViewHolder(@NonNull HomesInFeed holder, int position, @NonNull HomeInFeedModel model) {

                holder.HIFapartmentname.setText(model.getHomeName());
                holder.HIFrent.setText(model.getRentCost());
                holder.HIFrooms.setText(model.getRoom());
                holder.HIFlocalAreaName.setText(model.getLocalArea());
                Picasso.get().load(model.getImage()).into(holder.HIFhomePic);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(myPost.this, Home.class);
                        intent.putExtra("pId", model.getpId());
                        startActivity(intent);
                    }
                });
            }



            @NonNull
            @Override
            public HomesInFeed onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homefeeddesign, parent, false);
                HomesInFeed holder = new HomesInFeed(view);
                return holder;
            }

        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}