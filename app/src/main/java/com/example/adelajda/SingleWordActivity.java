package com.example.adelajda;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SingleWordActivity extends AppCompatActivity {

    private TextView listName;
    private TextView languageOneName;
    private TextView languageTwoName;
    private TextView languageOneWord;
    private TextView languageTwoWord;
    private TextView comment;

    private Button editWordButton;
    private Button removeWordButton;
    private Button goBackButton;

    private TableLayout tableLayout;

    private DataTransfer dataTransfer;

    private void setUpComponent()
    {
        tableLayout=findViewById(R.id.main);

        listName=findViewById(R.id.list_name);
        languageOneName=findViewById(R.id.language1);
        languageTwoName=findViewById(R.id.language2);
        languageOneWord=findViewById(R.id.language_one_word);
        languageTwoWord=findViewById(R.id.language_two_word);
        comment=findViewById(R.id.comment);
        editWordButton=findViewById(R.id.edit_word);
        removeWordButton=findViewById(R.id.remove_word);
        goBackButton=findViewById(R.id.go_back);

        dataTransfer=DataTransfer.getInstance();
    }

    private void loadData()
    {
        listName.setText(dataTransfer.currentListName);
        languageOneName.setText(dataTransfer.currentListLanguageOneName);
        languageTwoName.setText(dataTransfer.currentListLanguageTwoName);
        languageOneWord.setText(dataTransfer.currentWordLanguageOne);
        languageTwoWord.setText(dataTransfer.currentWordLanguageTwo);
        comment.setText(dataTransfer.currentWordComment);

        String color=dataTransfer.currentWordColor;

        if(color.equals("red")) tableLayout.setBackgroundColor(getResources().getColor(R.color.red));
        else if(color.equals("green")) tableLayout.setBackgroundColor(getResources().getColor(R.color.green));
        else if(color.equals("yellow")) tableLayout.setBackgroundColor(getResources().getColor(R.color.yellow));
        else if(color.equals("orange")) tableLayout.setBackgroundColor(getResources().getColor(R.color.orange));
        else if(color.equals("blue")) tableLayout.setBackgroundColor(getResources().getColor(R.color.blue));
        else if(color.equals("white")) tableLayout.setBackgroundColor(getResources().getColor(R.color.white));
        else if(color.equals("purple")) tableLayout.setBackgroundColor(getResources().getColor(R.color.purple));
        else if(color.equals("pink")) tableLayout.setBackgroundColor(getResources().getColor(R.color.pink));
        else if(color.equals("gray")) tableLayout.setBackgroundColor(getResources().getColor(R.color.gray));
        else tableLayout.setBackgroundColor(getResources().getColor(R.color.beige));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.single_word_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setUpComponent();
        loadData();


        editWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        removeWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


}
