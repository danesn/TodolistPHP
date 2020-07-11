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

public class UpdateTodoActivity extends AppCompatActivity {

    // Declare
    private EditText edtUpdateTitle, edtUpdateDesc, edtUpdateDate;
    private Button buttonUpdateToDo;

    // API
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_to_do_list);

        // Initial
        edtUpdateTitle = findViewById(R.id.edtUpdateTitle);
        edtUpdateDesc = findViewById(R.id.edtUpdateDesc);
        edtUpdateDate = findViewById(R.id.edtUpdateDate);
        buttonUpdateToDo = findViewById(R.id.buttonUpdateToDo);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        int id = getIntent().getIntExtra("id", 0);
        String title = getIntent().getStringExtra("title");
        String desc = getIntent().getStringExtra("desc");
        String date = getIntent().getStringExtra("date");
        String email = getIntent().getStringExtra("email");
        String name = getIntent().getStringExtra("name");

        edtUpdateTitle.setText(title);
        edtUpdateDesc.setText(desc);
        edtUpdateDate.setText(date);

        buttonUpdateToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = edtUpdateTitle.getText().toString();
                String desc = edtUpdateDesc.getText().toString();
                String date = edtUpdateDate.getText().toString();

                // check if input is empty
                if (title.length() > 0 && desc.length() > 0 && date.length() > 0) {

                    Call<Todolist> call = apiInterface.updateTodo(String.valueOf(id), email, title, desc, date);
                    call.enqueue(new Callback<Todolist>() {
                        @Override
                        public void onResponse(Call<Todolist> call, Response<Todolist> response) {
                            String value = response.body().getValue();
                            String message = response.body().getMassage();

                            Toast.makeText(UpdateTodoActivity.this, message, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(UpdateTodoActivity.this, MainActivity.class);
                            intent.putExtra("name", name);
                            intent.putExtra("email", email);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Todolist> call, Throwable t) {
                            Toast.makeText(UpdateTodoActivity.this, "message: " + t.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
                else {
                    Toast.makeText(UpdateTodoActivity.this, "Text cannot be empty", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
