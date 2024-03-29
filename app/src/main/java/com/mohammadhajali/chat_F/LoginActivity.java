package com.mohammadhajali.chat_F;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mohammadhajali.lost_thing1.R;


public class LoginActivity extends AppCompatActivity {

    //private FirebaseUser CurrentUser;  // 12

    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    private Button LoginButton,PhoneLoginButton;
    private EditText UserEmail,UserPassword;
    private TextView NeedNewAccountLink,ForgetPasswordLink;

    private DatabaseReference UserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        //CurrentUser = mAuth.getCurrentUser();

        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");

        InitializeFields();


        NeedNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SendUserToRegisterActivity();
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AllowUserToLogin();
            }
        });

        PhoneLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent phoneLoginIntent = new Intent(LoginActivity.this,PhoneLoginActivity.class);
                //startActivity(phoneLoginIntent);
            }
        });

    }

    private void AllowUserToLogin() {

        String email = UserEmail.getText().toString();
        String password = UserPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Pleas Enter Email", Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Pleas Enter Password", Toast.LENGTH_LONG).show();
        }
        else{
            loadingBar.setTitle("Sign in");
            loadingBar.setMessage("Please Wait...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

//                            if (task.isSuccessful()){
//
//                                String currentUserId = mAuth.getCurrentUser().getUid();
//                                String deviceToken = FirebaseInstanceId.getInstance().getToken();
//
//                                UserRef.child(currentUserId).child("device_token")
//                                        .setValue(deviceToken)
//                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()){

                                                    SendUserToMainActivity();   // put here from down in "52"
                                                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                                                    loadingBar.dismiss();
                                                }
//                                            }
//                                        });
//
//
//                            }
                            else {
                                String message = task.getException().toString();
                                Toast.makeText(LoginActivity.this, "Error :"+message, Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();
                            }

                        }
                    });

        }
    }

    private void InitializeFields() {

        LoginButton = findViewById(R.id.login_button);
        PhoneLoginButton = findViewById(R.id.phone_login_button);
        UserEmail = findViewById(R.id.login_email);
        UserPassword = findViewById(R.id.login_password);
        NeedNewAccountLink = findViewById(R.id.need_new_account_link);
        ForgetPasswordLink = findViewById(R.id.forget_password_link);

        loadingBar = new ProgressDialog(this);
    }

//    @Override
//    protected void onStart() {              create" 6 "  remove   "12"
//        super.onStart();
//
//        if (CurrentUser != null){
//            SendUserToMainActivity();
//        }
//
//    }

    private void SendUserToMainActivity() {
        Intent MainIntent = new Intent(LoginActivity.this, MainActivity.class);
        MainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(MainIntent);
        finish();
    }
    private void SendUserToRegisterActivity() {
        Intent RegisterIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(RegisterIntent);
    }
}
