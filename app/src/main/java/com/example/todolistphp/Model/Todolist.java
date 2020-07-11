package com.example.todolistphp.Model;

import com.google.gson.annotations.SerializedName;

public class Todolist {

    @SerializedName("id")
    private int id;
    @SerializedName("titleToDoList")
    private String titleToDoList;
    @SerializedName("descToDoList")
    private String descToDoList;
    @SerializedName("dateToDoList")
    private String dateToDoList;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleToDoList() {
        return titleToDoList;
    }

    public void setTitleToDoList(String titleToDoList) {
        this.titleToDoList = titleToDoList;
    }

    public String getDescToDoList() {
        return descToDoList;
    }

    public void setDescToDoList(String descToDoList) {
        this.descToDoList = descToDoList;
    }

    public String getDateToDoList() {
        return dateToDoList;
    }

    public void setDateToDoList(String dateToDoList) {
        this.dateToDoList = dateToDoList;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
