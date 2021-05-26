package com.example.niposttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.niposttracker.model.LoginResponse;
import com.example.niposttracker.service.NIPostApi;
import com.example.niposttracker.service.NIPostClient;
import com.example.niposttracker.utils.Constants;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextInputLayout inputEmail, inputPassword;
    TextInputEditText edtEmail, edtPassword;
    MaterialButton btnLogin;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("My Preference",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();

//        Binding
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing in");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tryLogin();
//
            }
        });

    }


    public void closeDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void tryLogin() {

        if (edtEmail.getText().toString().isEmpty()) {
            inputEmail.setError("Enter Your Email");
        } else if (edtPassword.getText().toString().isEmpty()) {
            inputPassword.setError("Emter Your Password");
        } else {

            login();

        }


    }

    public void login() {
        progressDialog.show();
        NIPostApi niPostApi = NIPostClient.getInstance().create(NIPostApi.class);
        Call<LoginResponse> loginResponseCall = niPostApi.getLoginResponse(edtEmail.getText().toString(), edtPassword.getText().toString());
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: " + response.body().getResult().getUser());
                    if (response.body().getStatus().equals("success")) {
                        closeDialog();
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        editor.putString("token",response.body().getResult().getToken());
                        Constants.TOKEN=response.body().getResult().getToken();
                        editor.apply();
                        Toast.makeText(MainActivity.this, response.body().getResult().getToken(), Toast.LENGTH_SHORT).show();

                    } else {
                        closeDialog();
                        Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    closeDialog();
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onFailure: " + t.getMessage());
                closeDialog();

            }
        });
//
    }
}