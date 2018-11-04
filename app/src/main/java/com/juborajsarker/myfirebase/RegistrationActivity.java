package com.juborajsarker.myfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    EditText nameEt, phoneEt, addressET, emailET, passwordET, retypePasswordET;
    Button registerBTN;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regitration);

        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("User/Info");
        init();
    }

    private void init() {

        nameEt = (EditText) findViewById(R.id.name_ET);
        phoneEt = (EditText) findViewById(R.id.phone_ET);
        addressET = (EditText) findViewById(R.id.address_ET);
        emailET = (EditText) findViewById(R.id.email_ET);
        passwordET = (EditText) findViewById(R.id.password_ET);
        retypePasswordET = (EditText) findViewById(R.id.retype_password_ET);

        registerBTN = (Button) findViewById(R.id.register_BTN);

        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkValidity()){

                    final String name = nameEt.getText().toString();
                    final String phone = phoneEt.getText().toString();
                    final String address = addressET.getText().toString();
                    final String email = emailET.getText().toString();
                    final String password = retypePasswordET.getText().toString();

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()){

                                        UserModel userModel = new UserModel();
                                        userModel.setName(name);
                                        userModel.setAddress(address);
                                        userModel.setPhone(phone);
                                        userModel.setEmail(email);
                                        userModel.setPassword(password);

                                        reference.child(firebaseAuth.getUid()).setValue(userModel);

                                        Toast.makeText(RegistrationActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                                        startActivity(intent);


                                    }else {

                                        Toast.makeText(RegistrationActivity.this,
                                                ""+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }
            }
        });
    }


    private boolean checkValidity(){

        if (nameEt.getText().toString().equals("")){

            nameEt.setError("This field is required !!!");
            return false;

        }else if (phoneEt.getText().toString().equals("")){

            phoneEt.setError("This field is required !!!");
            return false;

        }else if (addressET.getText().toString().equals("")){

            addressET.setError("This field is required !!!");
            return false;

        }else if (emailET.getText().toString().equals("")){

            emailET.setError("This field is required !!!");
            return false;

        }else if (passwordET.getText().toString().equals("")){

            passwordET.setError("This field is required !!!");
            return false;

        }else if (retypePasswordET.getText().toString().equals("")){

            retypePasswordET.setError("This field is required !!!");
            return false;

        }else if (! passwordET.getText().toString().equals(retypePasswordET.getText().toString())){

            retypePasswordET.setError("Password don't match !!!");
            return false;

        }else {

            return true;
        }
    }
}
