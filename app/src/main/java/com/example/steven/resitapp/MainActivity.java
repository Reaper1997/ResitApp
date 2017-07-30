package com.example.steven.resitapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button signUp;
    private EditText editusername;
    private EditText editpassword;
    private FirebaseAuth authenthication;
    private ProgressDialog progressBar;
    //private ProgressDialog progressLogin;
    private Button Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar=new ProgressDialog(this);
        //progressLogin=new ProgressDialog(this);

        authenthication=FirebaseAuth.getInstance();

//        if(authenthication.getCurrentUser()!=null){
//            Intent login=new Intent(MainActivity.this,LoginProfile.class);
//            startActivity(login);
//        }


        Login=(Button)findViewById(R.id.Login);
        signUp=(Button)findViewById(R.id.signUpButton);
        editusername=(EditText)findViewById(R.id.username);
        editpassword=(EditText)findViewById(R.id.password);

        signUp.setOnClickListener(this);
        Login.setOnClickListener(this);

    }

    private void UserRegistration() {
        String email=editusername.getText().toString().trim();
        String password=editpassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter a username ", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return ;
        }
        progressBar.setMessage("Registering User..");
        progressBar.show();

        authenthication.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            progressBar.dismiss();
                            Intent second=new Intent(MainActivity.this,ClothesCategory.class);
                            startActivity(second);

                        }else{
                            Toast.makeText(MainActivity.this, "Not Registered", Toast.LENGTH_SHORT).show();
                            progressBar.dismiss();
                        }
                    }
                });
    }

    private void UserLogin(){
        String email=editusername.getText().toString().trim();
        String password=editpassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter a username ", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password )){
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return ;

        }
             progressBar.setMessage("Logging in...");
             progressBar.show();
        authenthication.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           Toast.makeText(MainActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                           progressBar.dismiss();
                           Intent login=new Intent(MainActivity.this,LoginProfile.class);
                           startActivity(login);
                       }else{
                           Toast.makeText(MainActivity.this, "Login unsuccessful", Toast.LENGTH_SHORT).show();
                           progressBar.dismiss();
                       }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v==signUp){
            UserRegistration();


        }
         if(v==Login){
             UserLogin();
         }
    }


}
