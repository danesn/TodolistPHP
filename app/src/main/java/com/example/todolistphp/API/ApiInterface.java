package com.example.todolistphp.API;

import com.example.todolistphp.Model.Todolist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("retrieve.php")
    Call<List<Todolist>> getTodolist(
            @Query("email") String email);

    @GET("delete.php")
    Call<Todolist> deleteTodo(
            @Query("id") String id);

    @FormUrlEncoded
    @POST("update.php")
    public Call<Todolist> updateTodo(
            @Field("id") String id,
            @Field("email") String email,
            @Field("title") String title,
            @Field("desc") String desc,
            @Field("date") String date);

    @FormUrlEncoded
    @POST("add.php")
    public Call<Todolist> addTodo(
            @Field("email") String email,
            @Field("title") String title,
            @Field("desc") String desc,
            @Field("date") String date);

    @FormUrlEncoded
    @POST("login.php")
    public Call<Todolist> checkUser(
            @Field("email") String email,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("register.php")
    public Call<Todolist> checkUserRegister(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password);






}
