package com.example.rental_expertise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class searchHome extends AppCompatActivity {

    ImageButton imageButtonSearch;

    String[] DivisionsStringVariable;
    String[] SylhetVariable;
    String[] DhakaVariable;
    String[] BarishalVariable;
    String[] MymensinghVariable;
    String[] KhulnaVariable;
    String[] RangpurVariable;
    String[] RajshahiVariable;
    String[] ChittagongVariable;

    String[] DhakaDistrictAreaStringVariable;
    String[] GazipurDistrictAreaStringVariable;

    String[] RentRangeStringVariable;
    String[] RoomsStringVariable;

    private Spinner DivisionSpinnerVariable;
    private Spinner DistrictSpinnerVariable;
    private Spinner AreaSpinnerVariable;
    private Spinner RentRangeSpinnerVariable;
    private Spinner RoomsSpinnerVariable;


    NavigationView sidenav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    FirebaseAuth mAuth;
    String searchPoint;
    CircleImageView SNpropic;

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_home);



        mAuth=FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.search);
        auth= FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.homePage:
                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.search:
                        return true;

                    case R.id.postAHome:
                        startActivity(new Intent(getApplicationContext(), postHome.class));
                        overridePendingTransition(0,0);
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

        retrivePicture();

        sidenav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.profileSN:

                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent= new Intent(searchHome.this,profile.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                        break;
                    case R.id.mypostsSN:

                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent1= new Intent(searchHome.this,myPost.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);

                        break;
                    case R.id.notificationSN:

                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent2= new Intent(searchHome.this,notification.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);

                        break;
                    case R.id.settingsSN:

                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent3= new Intent(searchHome.this,settings.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent3);

                        break;
                    case R.id.exitSN:
                        Toast.makeText(getApplicationContext(), "Exit", Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        FirebaseAuth.getInstance().signOut();

                        Intent intent7 = new Intent(searchHome.this, MainActivity.class);
                        startActivity(intent7);
                        break;
                    case R.id.logoutSN:
                        Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        FirebaseAuth.getInstance().signOut();

                        Intent intent5 = new Intent(searchHome.this, Login.class);
                        startActivity(intent5);
                        break;
                    case R.id.aboutusSN:

                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent4= new Intent(searchHome.this,AboutUs.class);
                        intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent4);

                        break;
                }
                return true;
            }
        });


        DivisionsStringVariable=getResources().getStringArray(R.array.DivisionsString);// ei variable e values er declare kora string recieve korbe
        DhakaVariable=getResources().getStringArray(R.array.DhakaDistrictsString);
        SylhetVariable=getResources().getStringArray(R.array.SylhetDistrictString);
        BarishalVariable=getResources().getStringArray(R.array.BarishalDistrictsString);
        MymensinghVariable=getResources().getStringArray(R.array.MymensinghDistrictsString);
        RajshahiVariable=getResources().getStringArray(R.array.RajshahiDistrictsString);
        KhulnaVariable=getResources().getStringArray(R.array.KhulnaDistrictsString);
        RangpurVariable=getResources().getStringArray(R.array.RangpurDistrictsString);
        ChittagongVariable=getResources().getStringArray(R.array.ChittagongDistrictsString);

        RentRangeStringVariable=getResources().getStringArray(R.array.Rent);
        RoomsStringVariable=getResources().getStringArray(R.array.Room);

        DhakaDistrictAreaStringVariable=getResources().getStringArray(R.array.dhakaDisArea);
        GazipurDistrictAreaStringVariable=getResources().getStringArray(R.array.gazipurDisArea);

        DivisionSpinnerVariable=(Spinner) findViewById(R.id.spinnerDivison); // divison spinner jeta activity_search.xml e ase oita ke variable e set korbe
        DistrictSpinnerVariable =(Spinner) findViewById(R.id.spinnerDistrict);
        AreaSpinnerVariable=(Spinner)findViewById(R.id.spinnerArea);
        RentRangeSpinnerVariable=(Spinner)findViewById(R.id.spinnerRentRange);
        RoomsSpinnerVariable=(Spinner)findViewById(R.id.spinnerRooms);

        ArrayAdapter<String> DivisionAdapter = new ArrayAdapter<String>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay, DivisionsStringVariable);// ei adapter division er nam gula ke spinner display layout er maddome adapter e set korbe

        ArrayAdapter<String> SylhetDivisionAdapter = new ArrayAdapter<String>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay, SylhetVariable);
        ArrayAdapter<String> DhakaDivisionAdapter = new ArrayAdapter<String>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay, DhakaVariable);
        ArrayAdapter<String> BarishalDivisionAdapter = new ArrayAdapter<String>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay, BarishalVariable);
        ArrayAdapter<String> MymensinghDivisionAdapter = new ArrayAdapter<String>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay, MymensinghVariable);
        ArrayAdapter<String> KhulnaDivisionAdapter = new ArrayAdapter<String>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay, KhulnaVariable);
        ArrayAdapter<String> RajshahiDivisionAdapter = new ArrayAdapter<String>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay, RajshahiVariable);
        ArrayAdapter<String> RangpurDivisionAdapter = new ArrayAdapter<String>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay, RangpurVariable);
        ArrayAdapter<String> ChittagongDivisionAdapter = new ArrayAdapter<String>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay, ChittagongVariable);
        ArrayAdapter<String> RentRangeAdapter = new ArrayAdapter<String>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay, RentRangeStringVariable);
        ArrayAdapter<String> RoomsAdapter = new ArrayAdapter<String>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay, RoomsStringVariable);

        ArrayAdapter<String> DhakaDistrictAreaAdapter = new ArrayAdapter<String>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay, DhakaDistrictAreaStringVariable);
        ArrayAdapter<String> GazipurDistrictAreaAdapter = new ArrayAdapter<String>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay, GazipurDistrictAreaStringVariable);

        RentRangeSpinnerVariable.setAdapter(RentRangeAdapter);
        RoomsSpinnerVariable.setAdapter(RoomsAdapter);
        DivisionSpinnerVariable.setAdapter(DivisionAdapter);// set kora divison gula spinner e show korbe

        DivisionSpinnerVariable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==2)
                {
                    DistrictSpinnerVariable.setAdapter(DhakaDivisionAdapter);
                    DistrictSpinnerVariable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position==0) {
                                AreaSpinnerVariable.setAdapter(DhakaDistrictAreaAdapter);
                                searchPoint="";
                                searchPoint= AreaSpinnerVariable.getSelectedItem().toString();

                            }
                            if(position==1) {
                                AreaSpinnerVariable.setAdapter(GazipurDistrictAreaAdapter);
                                searchPoint="";
                                searchPoint= AreaSpinnerVariable.getSelectedItem().toString();

                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                if(position==7)
                {
                    DistrictSpinnerVariable.setAdapter(SylhetDivisionAdapter);
                }
                if(position==0)
                {
                    DistrictSpinnerVariable.setAdapter(BarishalDivisionAdapter);
                }
                if(position==1)
                {
                    DistrictSpinnerVariable.setAdapter(ChittagongDivisionAdapter);
                }
                if(position==3)
                {
                    DistrictSpinnerVariable.setAdapter(KhulnaDivisionAdapter);
                }
                if(position==4)
                {
                    DistrictSpinnerVariable.setAdapter(MymensinghDivisionAdapter);
                }
                if(position==5)
                {
                    DistrictSpinnerVariable.setAdapter(RajshahiDivisionAdapter);
                }
                if(position==6)
                {
                    DistrictSpinnerVariable.setAdapter(RangpurDivisionAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imageButtonSearch=(ImageButton)findViewById(R.id.imageButtonSearch);
        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passData();

            }
        });
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
    private void passData() {

        Intent intent = new Intent(searchHome.this, searchResult.class);
        intent.putExtra("message", searchPoint);
        startActivity(intent);
    }

}