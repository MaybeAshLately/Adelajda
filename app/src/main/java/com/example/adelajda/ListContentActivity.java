package com.example.adelajda;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Vector;

public class ListContentActivity extends AppCompatActivity {

    private TextView nameTextView;
    private DataTransfer dataTransfer;
    private Button addNewWordButton;
    private Button settingsButton;

    private ListView listOfWords;
    private ArrayAdapter<String> adapter;
    private Vector<String> contentToDisplayOnListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.list_content);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        getListContentFromFile();

        addNewWordButton=findViewById(R.id.add_new_word_list_content);
        settingsButton=findViewById(R.id.settings_button_list_content);
        nameTextView=findViewById(R.id.text_name_list_content);
        nameTextView.setText(dataTransfer.currentListName);

        listOfWords=findViewById(R.id.list_of_words);
        adapter = new ArrayAdapter<>(this, R.layout.list_row, contentToDisplayOnListView);
        listOfWords.setAdapter(adapter);




        addNewWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        listOfWords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem =  parent.getItemAtPosition(position).toString();

            }
        });
    }


    private void getListContentFromFile()
    {
        contentToDisplayOnListView=new Vector<>();
    }
}
