package com.example.adelajda;

public class DataTransfer {
    private DataTransfer(){}

    private static DataTransfer instance;
    public static DataTransfer getInstance() {
        if (instance == null) { instance = new DataTransfer();}
        return instance;
    }

    public String currentListName;
    public boolean newListAdded;
    public String newListName;
}
