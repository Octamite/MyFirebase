package com.juborajsarker.myfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText emailEt, passwordEt;
    Button loginBTN;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        init();
    }

    private void init() {

        emailEt = (EditText) findViewById(R.id.email_ET);
        passwordEt = (EditText) findViewById(R.id.password_ET);
        loginBTN = (Button) findViewById(R.id.login_BTN);

        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkValidity()){

                    String email = emailEt.getText().toString();
                    String password = passwordEt.getText().toString();

                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()){

                                        Toast.makeText(LoginActivity.this, "Logged in !!!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intent);

                                    }else {

                                        Toast.makeText(LoginActivity.this, ""+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });
    }

    private boolean checkValidity(){

        if (emailEt.getText().toString().equals("")){

            emailEt.setError("This filed is required !!!");
            return false;

        }else if (passwordEt.getText().toString().equals("")){

            passwordEt.setError("This field is required !!!");
            return false;

        }else {

            return true;
        }
    }
}
