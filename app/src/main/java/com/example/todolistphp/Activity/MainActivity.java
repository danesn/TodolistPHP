package com.example.todolistphp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolistphp.API.ApiClient;
import com.example.todolistphp.API.ApiInterface;
import com.example.todolistphp.Adapter.TodolistAdapter;
import com.example.todolistphp.Model.Todolist;
import com.example.todolistphp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // declare
    private RecyclerView recyclerViewItems;
    private TodolistAdapter toDoListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView tvName, tvCountTask;
    private Button buttonLogout;
    private ImageButton addButton;
    RelativeLayout viewNoTasks;

    // list
    private List<Todolist> todolist;

    // API
    private ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initial
        recyclerViewItems = findViewById(R.id.recyclerViewItems);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewItems.setLayoutManager(layoutManager);
        tvName = findViewById(R.id.tvName);
        tvCountTask = findViewById(R.id.tvCountTask);
        viewNoTasks = findViewById(R.id.noTasksView);
        buttonLogout = findViewById(R.id.buttonLogout);
        addButton = findViewById(R.id.addButton);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");

        tvName.setText("hello "+ name +"!");

        getTodoList(email, name);

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // alert dialog
                final CharSequence[] dialogitem = {"Logout", "Find Your Location"};

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Option");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case 1:
                                Intent intentAlternative = new Intent(MainActivity.this, MapActivity.class);
                                startActivity(intentAlternative);
                                break;
                        }
                    }
                });
                builder.create().show();


            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }

    public boolean getTodoList(String email, String name) {
        Call<List<Todolist>> call = apiInterface.getTodolist(email);
        call.enqueue(new Callback<List<Todolist>>() {
            @Override
            public void onResponse(Call<List<Todolist>> call, Response<List<Todolist>> response) {

                // put json into the list
                todolist = response.body();

                // check amount of todolist
                int amountOfToDoList =  todolist.size();
                if (amountOfToDoList == 1) {
                    tvCountTask.setText("Today you have " +amountOfToDoList+ " task");
                    viewNoTasks.setVisibility(View.GONE);
                    recyclerViewItems.setVisibility(View.VISIBLE);
                }
                else if (amountOfToDoList > 1){
                    tvCountTask.setText("Today you have " +amountOfToDoList+ " tasks");
                    viewNoTasks.setVisibility(View.GONE);
                    recyclerViewItems.setVisibility(View.VISIBLE);
                }
                else {
                    tvCountTask.setText("Today you have no tasks");
                    viewNoTasks.setVisibility(View.VISIBLE);
                    recyclerViewItems.setVisibility(View.GONE);
                }

                // set list into adapter Recyclerview
                toDoListAdapter = new TodolistAdapter(MainActivity.this, todolist);
                recyclerViewItems.setAdapter(toDoListAdapter);

                //onclickitem callback
                toDoListAdapter.setOnItemClickCallback(new TodolistAdapter.OnItemClickCallback() {
                    @Override
                    public void onItemClicked(Todolist todolist) {
                        final CharSequence[] dialogitem = {"Edit", "Delete"};

                        // alert dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Option");
                        builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                switch (item) {
                                    case 0:
                                        String desc = todolist.getDateToDoList();
                                        Toast.makeText(MainActivity.this, "desc: " + desc, Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(MainActivity.this, UpdateTodoActivity.class);
                                        intent.putExtra("id", todolist.getId());
                                        intent.putExtra("title", todolist.getTitleToDoList());
                                        intent.putExtra("desc", todolist.getDescToDoList());
                                        intent.putExtra("date", todolist.getDateToDoList());
                                        intent.putExtra("email", email);
                                        intent.putExtra("name", name);
                                        startActivity(intent);
                                        break;
                                    case 1:

                                        Call<Todolist> call = apiInterface.deleteTodo(Integer.toString(todolist.getId()));
                                        call.enqueue(new Callback<Todolist>() {
                                            @Override
                                            public void onResponse(Call<Todolist> call, Response<Todolist> response) {
                                                String value = response.body().getValue();
                                                String message = response.body().getMassage();

                                                Toast.makeText(
                                                        MainActivity.this,
                                                        "message: " + message,
                                                        Toast.LENGTH_LONG).show();

                                                getTodoList(email, name);
                                                toDoListAdapter.notifyDataSetChanged();

                                            }

                                            @Override
                                            public void onFailure(Call<Todolist> call, Throwable t) {
                                                Toast.makeText(
                                                        MainActivity.this,
                                                        "message: " + t.toString(),
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        });
                                        break;
                                }
                            }
                        });
                        builder.create().show();
                    }
                });

                toDoListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Todolist>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error : "+ t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return true;
    }
}
