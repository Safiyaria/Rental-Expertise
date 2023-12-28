package com.example.rental_expertise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import HomesInFeed.HomesInFeed;
import Model.HomeInFeedModel;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomePage extends AppCompatActivity {

    NavigationView sidenav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    FirebaseAuth mAuth;

    private DatabaseReference HomeRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;


    CircleImageView SNpropic;

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        recyclerView =findViewById(R.id.recycler_menu1);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager( this);
        recyclerView.setLayoutManager(layoutManager);

        mAuth = FirebaseAuth.getInstance();

        HomeRef = FirebaseDatabase.getInstance().getReference().child("Rent_posts");
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.homePage);

        auth= FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");

        retrivePicture();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.homePage:
                        return true;

                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), searchHome.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.postAHome:
                        startActivity(new Intent(getApplicationContext(), postHome.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });

        Toolbar toolbar2;
        toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        sidenav = (NavigationView) findViewById(R.id.sidenavmenu);
        SNpropic=(CircleImageView)sidenav.getHeaderView(0).findViewById(R.id.profile_pic_SN);

        drawerLayout = (DrawerLayout) findViewById(R.id.draw);
        toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar2,
                R.string.open,
                R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        sidenav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.profileSN:

                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent = new Intent(HomePage.this, profile.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                        break;
                    case R.id.mypostsSN:

                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent1 = new Intent(HomePage.this, myPost.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);

                        break;
                    case R.id.notificationSN:

                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent2 = new Intent(HomePage.this, notification.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);

                        break;
                    case R.id.settingsSN:

                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent3 = new Intent(HomePage.this, settings.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent3);

                        break;
                    case R.id.exitSN:
                        Toast.makeText(getApplicationContext(), "Exit", Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        FirebaseAuth.getInstance().signOut();

                        Intent intent7 = new Intent(HomePage.this, MainActivity.class);
                        startActivity(intent7);
                        break;

                    case R.id.logoutSN:
                        Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        FirebaseAuth.getInstance().signOut();

                        Intent intent5 = new Intent(HomePage.this, Login.class);
                        startActivity(intent5);
                        break;

                    case R.id.aboutusSN:

                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent4 = new Intent(HomePage.this, AboutUs.class);
                        intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent4);

                        break;

                }
                return true;
            }
        });
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
                        Intent intent=new Intent(HomePage.this, Home.class);
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
    private void retrivePicture() {
        databaseReference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChild("image")) {
                    String image = snapshot.child("image").getValue().toString();
                    Picasso.get().load(image).into(SNpropic);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}