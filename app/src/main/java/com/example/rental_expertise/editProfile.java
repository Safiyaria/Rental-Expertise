package com.example.rental_expertise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.core.utils.TextUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class editProfile extends AppCompatActivity {

    private ImageView close;
    private TextView save,changePic;
    private CircleImageView pro_pic1;
    private MaterialEditText name,username,email,address,contactNo;

    private FirebaseUser cur_user;


    private String check="";


    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        auth= FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");
        retrivePicture();

        close = findViewById(R.id.close);
        save = findViewById(R.id.save);
        pro_pic1 = findViewById(R.id.pro_pic);
        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address1);
        changePic = findViewById(R.id.cngPic);
        contactNo=findViewById(R.id.cntNo);


        cur_user = FirebaseAuth.getInstance().getCurrentUser();


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        changePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check.equals("clicked"))
                {
                    userInfoSaved();
                }
                else
                {
                    updateProfile();
                }
            }
        });
    }


    private void updateProfile() {

        HashMap<String,Object> map= new HashMap<>();
        map.put("Name",name.getText().toString());
        map.put("Username",username.getText().toString());
        map.put("Email",email.getText().toString());
        map.put("Address",address.getText().toString());
        map.put("ContactNo",contactNo.getText().toString());


        FirebaseDatabase.getInstance().getReference().child("Users").child(cur_user.getUid()).updateChildren(map);

        startActivity(new Intent(editProfile.this, profile.class));
        Toast.makeText(editProfile.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
        finish();

    }


    private void userInfoSaved()
    {
        if (TextUtils.isEmpty(contactNo.getText().toString()))
        {
            Toast.makeText(this, "Please provide your Contact No", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(address.getText().toString()))
        {
            Toast.makeText(this, "Please provide your Address", Toast.LENGTH_SHORT).show();
        }

        else if(check.equals("clicked"))
        {
            uploadImage();
        }
    }



    private void uploadImage() {


    }


    private void retrivePicture() {
        databaseReference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}