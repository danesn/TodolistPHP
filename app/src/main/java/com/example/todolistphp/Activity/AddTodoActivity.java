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

public class AddTodoActivity extends AppCompatActivity {

    // Declare
    private EditText edtTitle, edtDesc, edtDate;
    private Button buttonAddTask;

    // API
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_to_do_list);

        // Initial
        edtTitle = findViewById(R.id.edtTitle);
        edtDesc = findViewById(R.id.edtDesc);
        edtDate = findViewById(R.id.edtDate);
        buttonAddTask = findViewById(R.id.buttonAddTask);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser(email, name);
            }
        });

    }

    public boolean checkUser(String emailUser, String nameUser) {
        String title = edtTitle.getText().toString();
        String desc = edtDesc.getText().toString();
        String date = edtDate.getText().toString();

        // check if input is empty
        if (title.length() > 0 && desc.length() > 0 && date.length() > 0) {

            Call<Todolist> call = apiInterface.addTodo(emailUser, title, desc, date);
            call.enqueue(new Callback<Todolist>() {
                @Override
                public void onResponse(Call<Todolist> call, Response<Todolist> response) {
                    String value = response.body().getValue();
                    String message = response.body().getMassage();

                    int hasil = Integer.parseInt(value);

                    if (hasil == 1) {
                        Toast.makeText(
                                AddTodoActivity.this,
                                "message: " + message,
                                Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(AddTodoActivity.this, MainActivity.class);
                        intent.putExtra("name", nameUser);
                        intent.putExtra("email", emailUser);
                        startActivity(intent);
                        finish();
                    }
                    else if (hasil == 0) {
                        Toast.makeText(
                                AddTodoActivity.this,
                                message,
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Todolist> call, Throwable t) {
                    Toast.makeText(
                            AddTodoActivity.this,
                            "message: " + t.toString(),
                            Toast.LENGTH_LONG).show();
                }
            });

        }
        else {
            Toast.makeText(AddTodoActivity.this, "Text cannot be empty", Toast.LENGTH_LONG).show();
        }

        return true;
    }
}
