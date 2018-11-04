package com.juborajsarker.myfirebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    TextView nameTV, phoneTV, addressTV, emailTV;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        reference = FirebaseDatabase.getInstance().getReference("User/Info");
        init();


    }

    private void init() {

        nameTV = (TextView) findViewById(R.id.name_TV);
        phoneTV = (TextView) findViewById(R.id.phone_TV);
        addressTV = (TextView) findViewById(R.id.address_TV);
        emailTV = (TextView) findViewById(R.id.email_TV);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                    UserModel userModel = snapshot.getValue(UserModel.class);
                    nameTV.setText(userModel.getName());
                    phoneTV.setText(userModel.getPhone());
                    addressTV.setText(userModel.getAddress());
                    emailTV.setText(userModel.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
