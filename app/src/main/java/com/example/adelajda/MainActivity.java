package com.example.adelajda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private Button addList;
    private ListView listOfLists;
    private ArrayAdapter<String> adapter;
    private Vector<String> listNames;

    private DataTransfer dataTransfer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dataTransfer=DataTransfer.getInstance();
        restoreDataFromFile();

        addList=findViewById(R.id.add_new_list);

        listOfLists=findViewById(R.id.list_of_lists);
        adapter = new ArrayAdapter<>(this, R.layout.list_row, listNames);
        listOfLists.setAdapter(adapter);

        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //activity new list
            }
        });

        listOfLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem =  parent.getItemAtPosition(position).toString();
                dataTransfer.currentListName=selectedItem;
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivityForResult(intent,1);
            }
        });

    }


    private void restoreDataFromFile()
    {
        String fileName="ListNamesData.txt";
        listNames=new Vector<>();
        listNames.clear();

        File file = getFileStreamPath(fileName);
        if(file.exists()==false)
        {
            try (FileOutputStream fileOutputStream = openFileOutput(fileName, this.MODE_PRIVATE)) {}
            catch (IOException e) {e.printStackTrace();}
        }
        else
        {
            try(FileInputStream fileInputStream = this.openFileInput(fileName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream))) {
                String bufferLine;
                while ((bufferLine = reader.readLine()) != null) listNames.add(bufferLine);
            } catch (IOException e) { e.printStackTrace(); }
        }

    }
}