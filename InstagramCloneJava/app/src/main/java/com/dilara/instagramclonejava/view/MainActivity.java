package com.dilara.instagramclonejava.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dilara.instagramclonejava.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        auth=FirebaseAuth.getInstance();

        FirebaseUser user=auth.getCurrentUser();//daha önceden giriş yapmış kullanıcı varsa bana bunu verir

        if(user != null){//kullanıcı varsa bunu yap
            Intent intent=new Intent(MainActivity.this, FeedActivity.class);
            startActivity(intent);
            finish();
        }

    }
    public void signInClicked(View view){ //giriş işlemleri
        String email=binding.emailText.getText().toString();
        String password=binding.passwordText.getText().toString();

        if(email.equals("") || password.equals("")){
            Toast.makeText(this,"Enter email and password",Toast.LENGTH_LONG).show();
        }else{
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent=new Intent(MainActivity.this,FeedActivity.class);
                    startActivity(intent);
                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull  Exception e) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();

                }
            });
        }

    }
    public void signUpClicked(View view){//kayıt işlmeleri
        String email=binding.emailText.getText().toString();
        String password=binding.passwordText.getText().toString();

        if(email.equals("") || password.equals("")){
            Toast.makeText(this,"Enter email and password",Toast.LENGTH_LONG).show();

        }else{
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() { //addonsuccesslistener başarılı olursa ne olacağı
                @Override
                public void onSuccess(AuthResult authResult) {//bu metot bi serverdan cevabı aldığımızda çalıştırılır
                    Intent intent=new Intent(MainActivity.this,FeedActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {//başarısız olursa ne olacağı
                @Override
                public void onFailure(@NonNull  Exception e) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    //getLocalizedmessage kullanıcının anlayabileceği gibi mesaj yazma

                }
            });
        }


    }
}