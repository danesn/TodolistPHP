package com.example.todolistphp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolistphp.API.ApiClient;
import com.example.todolistphp.API.ApiInterface;
import com.example.todolistphp.Model.Todolist;
import com.example.todolistphp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    // declare
    private EditText edtEmail, edtPassword;
    private Button buttonLogin, buttonRegister;

    // API
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initial
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSameUser();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void checkSameUser() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();


        // check if input is empty
        if (email.length() > 0 && password.length() > 0) {

            Call<Todolist> call = apiInterface.checkUser(email, password);
            call.enqueue(new Callback<Todolist>() {
                @Override
                public void onResponse(Call<Todolist> call, Response<Todolist> response) {

                    // take json value into variables
                    String value = response.body().getValue();
                    String message = response.body().getMassage();

                    // parse string value into integer
                    int hasil = Integer.parseInt(value);

                    // check value/hasil
                    if (hasil == 1) {
                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("name", message);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        finish();
                    }
                    else if (hasil == 0) {
                        Toast.makeText(LoginActivity.this, "Wrong email or password", Toast.LENGTH_LONG).show();
                    }
//
                }

                @Override
                public void onFailure(Call<Todolist> call, Throwable t) {
                    Toast.makeText(
                            LoginActivity.this,
                            "message: " + t,
                            Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            Toast.makeText(LoginActivity.this, "Text cannot be empty", Toast.LENGTH_LONG).show();
        }

    }
}
