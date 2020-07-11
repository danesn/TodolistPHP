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

public class RegisterActivity extends AppCompatActivity {

    // declare
    private EditText edtName, edtEmail, edtPassword;
    private Button buttonRegister, buttonLogin;

    // API
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // initial
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonLogin = findViewById(R.id.buttonLogin);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void checkUser() {
        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        Call<Todolist> call = apiInterface.checkUserRegister(name, email, password);
        call.enqueue(new Callback<Todolist>() {
            @Override
            public void onResponse(Call<Todolist> call, Response<Todolist> response) {
                String value = response.body().getValue();
                String message = response.body().getMassage();

                int hasil = Integer.parseInt(value);

                if (hasil == 1) {
                    Toast.makeText(
                            RegisterActivity.this,
                            "message: " + message,
                            Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    finish();
                }
                else if (hasil == 0) {
                    Toast.makeText(
                            RegisterActivity.this,
                            message,
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Todolist> call, Throwable t) {
                Toast.makeText(
                        RegisterActivity.this,
                        "message: " + t,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
