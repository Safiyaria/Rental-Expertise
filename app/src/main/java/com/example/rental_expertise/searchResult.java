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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import HomesInFeed.HomesInFeed;
import Model.HomeInFeedModel;

public class searchResult extends AppCompatActivity {

    private DatabaseReference HomeRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    String point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);


        Bundle bundle = getIntent().getExtras();
        point = bundle.getString("message");


        recyclerView =findViewById(R.id.recycler_menu2);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager( this);
        recyclerView.setLayoutManager(layoutManager);
        HomeRef = FirebaseDatabase.getInstance().getReference().child("Rent_posts");

    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Rent_posts");
        FirebaseRecyclerOptions<HomeInFeedModel> option =
                new FirebaseRecyclerOptions.Builder<HomeInFeedModel>().setQuery(reference.orderByChild("localArea").equalTo(point), HomeInFeedModel.class).build();



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
                        Intent intent=new Intent(searchResult.this, Home.class);
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