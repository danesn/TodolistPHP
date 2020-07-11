package com.example.todolistphp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistphp.Model.Todolist;
import com.example.todolistphp.R;

import java.util.List;

public class TodolistAdapter extends RecyclerView.Adapter<TodolistAdapter.MyViewHolder>{

    Context context;
    List<Todolist> todolist;

    public TodolistAdapter(Context context, List<Todolist> todolist) {
        this.context = context;
        this.todolist = todolist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.titleToDoList.setText(todolist.get(position).getTitleToDoList());
        holder.descToDoList.setText(todolist.get(position).getDescToDoList());
        holder.dateToDoList.setText(todolist.get(position).getDateToDoList());

        // onclick item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(todolist.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return todolist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titleToDoList, descToDoList, dateToDoList;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titleToDoList = (TextView)itemView.findViewById(R.id.titleToDoList);
            descToDoList = (TextView)itemView.findViewById(R.id.descToDoList);
            dateToDoList = (TextView)itemView.findViewById(R.id.dateToDoList);
        }
    }

    // interface onitemclick
    public interface OnItemClickCallback {
        void onItemClicked(Todolist todolist);
    }

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }
}
