package com.example.android.carros_material;

import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private EditText email, password;
    private Resources res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        res=this.getResources();
        email=(EditText)findViewById(R.id.txtEmail);
        password=(EditText)findViewById(R.id.txtPassword);

        firebaseAuth=FirebaseAuth.getInstance();
        authStateListener=new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
            }
        };

    }

    public void signIn(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent i = new Intent(Login.this, Principal.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(Login.this,task.getException().getMessage(), Toast.LENGTH_LONG);

                }
            }
        });
    }

    public void crearUsuario(String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, R.string.msj_exito_crearUsuario, Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    public void ingresar(View v){
        signIn(email.getText().toString(),password.getText().toString());
    }


    public void crearUsuario(View v){
        crearUsuario(email.getText().toString(),password.getText().toString());
    }
}
