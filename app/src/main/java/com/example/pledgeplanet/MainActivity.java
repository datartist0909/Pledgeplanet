package com.example.pledgeplanet;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;


public class MainActivity extends AppCompatActivity {

    // Firebase Authentication instance
    private FirebaseAuth mAuth;

    // UI components
    private EditText etUsername, etEmail, etPassword, etConfirmPassword;
    private Button btnSignup, btnAlreadyUser;
    private CheckBox cbShowPassword;
    private SharedPreferences sharedPreferences;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("login_credentials", MODE_PRIVATE);
        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        if (sharedPreferences.contains("user_id") && sharedPreferences.contains("user_token")) {
            String userId = sharedPreferences.getString("user_id", "");
            String userToken = sharedPreferences.getString("user_token", "");

            // Use the stored credentials to log in the user
            mAuth.signInWithCustomToken(userToken)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // User is logged in, navigate to dashboard activity
                                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Login failed, show error message
                                Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        // Initialize UI components
        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password); 
        btnSignup = findViewById(R.id.btn_signup);
        btnAlreadyUser = findViewById(R.id.btn_already_user);
        cbShowPassword = findViewById(R.id.cb_show_password);




        cbShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        // Set up button click listener for "Sign up" button
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signupUser();
            }
        });

        // Set up button click listener for "Already a user? Login" button
        btnAlreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginDialog();
            }
        });
    }

    private void signupUser() {
        // Get input data
        String username = etUsername.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        // Input validation
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Email validation using regular expression
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(emailRegex)) {
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new user with Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.getIdToken(true)
                                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                                            if (task.isSuccessful()) {
                                                String userId = user.getUid();
                                                String userToken = task.getResult().getToken();

                                                // Store user credentials in SharedPreferences
                                                sharedPreferences.edit()
                                                        .putString("user_id", userId)
                                                        .putString("user_token", userToken)
                                                        .apply();

                                                // Sign up successful, navigate to the next activity
                                                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                                                startActivity(intent);
                                            } else {
                                                // Handle error
                                                Log.e("FirebaseAuth", "getIdToken failed", task.getException());
                                                Toast.makeText(MainActivity.this, "Sign up failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            // Sign up failed, display an error message
                            Toast.makeText(MainActivity.this, "Sign up failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void showLoginDialog() {
        // Create a new dialog for login
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Login");

        // Set up the login form
        View loginFormView = getLayoutInflater().inflate(R.layout.login_form, null);
        final EditText etLoginUsername = loginFormView.findViewById(R.id.et_login_username);
        final EditText etLoginPassword = loginFormView.findViewById(R.id.et_login_password);
        Button btnLogin = loginFormView.findViewById(R.id.btn_login);
        Button btnForgotPassword = loginFormView.findViewById(R.id.btn_forgot_password);

        builder.setView(loginFormView);

        // Set up the login button click listener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etLoginUsername.getText().toString();
                String password = etLoginPassword.getText().toString();

                String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
                if (!username.matches(emailRegex)) {
                    Toast.makeText(MainActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                    return;
                }


                // Login with Firebase Authentication
                mAuth.signInWithEmailAndPassword(username, password)
                        .addOnSuccessListener(MainActivity.this, new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                // Login successful, navigate to the next activity
                                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(MainActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Login failed, display an error message
                                Log.e("FirebaseAuth", "signInWithEmailAndPassword failed", e);
                                Toast.makeText(MainActivity.this, "Login failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        // Set up the "Forgot Password" button click listener
        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // Get the email address from the user
                    final EditText etForgotPasswordEmail = new EditText(MainActivity.this);
                    etForgotPasswordEmail.setHint("Enter your email address");
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Forgot Password")
                            .setMessage("Enter your email address to reset your password")
                            .setView(etForgotPasswordEmail)
                            .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String email = etForgotPasswordEmail.getText().toString();
                                    if (!email.isEmpty()) {
                                        // Send a password reset email to the user
                                        mAuth.sendPasswordResetEmail(email)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(MainActivity.this, "Password reset email sent successfully", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(MainActivity.this, "Error sending password reset email: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(MainActivity.this, "Please enter your email address", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                }
        });

        // Create and show the login dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}


