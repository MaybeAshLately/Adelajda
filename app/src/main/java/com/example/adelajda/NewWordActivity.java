package com.example.adelajda;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import java.io.FileOutputStream;
import java.io.IOException;

public class NewWordActivity extends AppCompatActivity {

    private TextView name;
    private TextView languageOneName;
    private TextView languageTwoName;
    private EditText languageOneWordEditText;
    private EditText languageTwoWordEditText;
    private EditText commentEditText;
    private Button cancel;
    private Button addWord;
    private RadioGroup radioGroup;

    private String languageOneWord;
    private String languageTwoWord;
    private String selectedColor;
    private String comment;


    private DataTransfer dataTransfer;

    private void setUpComponents()
    {
        dataTransfer=DataTransfer.getInstance();
        name=findViewById(R.id.list_name);
        languageOneName=findViewById(R.id.language1);
        languageTwoName=findViewById(R.id.language2);
        languageOneWordEditText=findViewById(R.id.enter_language_1_word_edit_text);
        languageTwoWordEditText=findViewById(R.id.enter_language_2_word_edit_text);
        commentEditText=findViewById(R.id.enter_comment_edit_text);
        cancel=findViewById(R.id.cancel);
        addWord=findViewById(R.id.add_new_word_button);
        radioGroup=findViewById(R.id.radioGroup);

        languageOneName.setText(dataTransfer.currentListLanguageOneName);
        languageTwoName.setText(dataTransfer.currentListLanguageTwoName);


        name.setText(dataTransfer.currentListName);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.add_new_word);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        setUpComponents();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                languageOneWord=languageOneWordEditText.getText().toString();
                languageTwoWord=languageTwoWordEditText.getText().toString();
                comment= commentEditText.getText().toString();

                if(valuesCorrect()==true) addWord();
                else displayInfo();

            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId==R.id.radio_beige) selectedColor="beige";
            else if(checkedId==R.id.radio_red) selectedColor="red";
            else if(checkedId==R.id.radio_green) selectedColor="green";
            else if(checkedId==R.id.radio_yellow) selectedColor="yellow";
            else if(checkedId==R.id.radio_orange) selectedColor="orange";
            else if(checkedId==R.id.radio_blue) selectedColor="blue";
            else if(checkedId==R.id.radio_white) selectedColor="white";
            else if(checkedId==R.id.radio_purple) selectedColor="purple";
            else if(checkedId==R.id.radio_pink) selectedColor="pink";
            else if(checkedId==R.id.radio_gray) selectedColor="gray";
            }
        });

    }

    private boolean valuesCorrect()
    {
        if(languageOneWord.isEmpty()) return false;
        if(languageTwoWord.isEmpty()) return false;
        if(languageOneWord.contains(";")) return false;
        if(languageTwoWord.contains(";")) return false;
        if(comment.contains(";")) return false;

        return true;
    }

    private void displayInfo()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wrong names");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.text, null);
        builder.setView(dialogView);

        TextView txt = dialogView.findViewById(R.id.text);
        txt.setText("Word fields cannot be empty. None of fields can contain \";\" character.");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void addWord()
    {
        String newLine;
        if(comment.isEmpty()) comment=" ";
        if(selectedColor==null) selectedColor="beige";
        newLine=languageOneWord+";"+languageTwoWord+";"+comment+";"+selectedColor;

        try (FileOutputStream fileOutputStream = openFileOutput(dataTransfer.currentListName, this.MODE_APPEND)) {
            fileOutputStream.write(newLine.getBytes());
            fileOutputStream.write("\n".getBytes());
        }
        catch (IOException e) {e.printStackTrace();}

        Toast toast = Toast.makeText(getApplicationContext(), "New word added.", Toast.LENGTH_LONG);
        toast.show();

        dataTransfer.newWordAdded=true;
        dataTransfer.newWordDisplay=languageOneWord+" - "+languageTwoWord;

        dataTransfer.newWordLanguageOne=languageOneWord;
        dataTransfer.newWordLanguageTwo=languageTwoWord;
        dataTransfer.newComment=comment;
        dataTransfer.newColor=selectedColor;

        finish();
    }
}
