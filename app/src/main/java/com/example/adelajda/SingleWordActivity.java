package com.example.adelajda;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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
                Intent intent = new Intent(SingleWordActivity.this, EditWord.class);
                startActivityForResult(intent,4);}
        });

        removeWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayRemoveAlert();}
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    private void displayRemoveAlert()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(SingleWordActivity.this);
        builder.setTitle("Delete Word");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.text, null);
        builder.setView(dialogView);

        TextView txt = dialogView.findViewById(R.id.text);
        txt.setText("Are you sure you want to delete this word? Action cannot be undone.");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dataTransfer.wordDeleted=true;
                finish();}
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void loadData()
    {
        listName.setText(dataTransfer.currentListName);
        languageOneName.setText(dataTransfer.currentListLanguageOneName);
        languageTwoName.setText(dataTransfer.currentListLanguageTwoName);
        languageOneWord.setText(dataTransfer.currentWordLanguageOne);
        languageTwoWord.setText(dataTransfer.currentWordLanguageTwo);
        comment.setText(dataTransfer.currentWordComment);

        setColor(dataTransfer.currentWordColor);
    }


    private void setColor(String color)
    {
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


    @Override
    protected void onResume() {
        super.onResume();
        if(dataTransfer.wordEdited==true)
        {
            languageOneWord.setText(dataTransfer.newWordLanguageOne);
            languageTwoWord.setText(dataTransfer.newWordLanguageTwo);
            comment.setText(dataTransfer.newComment);
            setColor(dataTransfer.newColor);
        }
    }
}
