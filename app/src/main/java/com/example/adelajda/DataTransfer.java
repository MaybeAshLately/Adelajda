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
    public boolean listRemoved;

    public boolean newWordAdded;
    public String newWordDisplay;

    public String currentListLanguageOneName;
    public String currentListLanguageTwoName;

    public String currentWordLanguageOne;
    public String currentWordLanguageTwo;
    public String currentWordComment;
    public String currentWordColor;


    public String newWordLanguageOne;
    public String newWordLanguageTwo;
    public String newComment;
    public String newColor;

    public boolean wordDeleted;
    public boolean wordEdited;

    public LearningMode chosenMode;
    public MisspellMode chosenMisspellMode;


}
