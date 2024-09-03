package com.example.adelajda;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class ListSettingsActivity extends AppCompatActivity {

    private Button deleteButton;
    private Button editButton;
    private Button goBackButton;

    private DataTransfer dataTransfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.settings_of_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setUpComponents();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteListPopUp();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListSettingsActivity.this, EditListInfoActivity.class);
                startActivityForResult(intent,1);}
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void setUpComponents()
    {
        deleteButton=findViewById(R.id.delete_list);
        editButton=findViewById(R.id.edit_list_name);
        goBackButton=findViewById(R.id.go_back_button);
        dataTransfer=DataTransfer.getInstance();
    }


    private void showDeleteListPopUp()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete list");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.text, null);
        builder.setView(dialogView);

        TextView txt = dialogView.findViewById(R.id.text);
        txt.setText("Are you sure you want to delete this list? Action cannot be undone.");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteList();
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void deleteList()
    {
        deleteInfoFromMainFile();
        deleteListFile();
        dataTransfer.listRemoved=true;
    }


    private void deleteInfoFromMainFile()
    {
        String fileName="ListNamesData.txt";
        Vector<String> listNames=new Vector<>();

        try(FileInputStream fileInputStream = this.openFileInput(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream))) {
            String bufferLine;
            while ((bufferLine = reader.readLine()) != null) listNames.add(bufferLine);
        } catch (IOException e) { e.printStackTrace(); }

        if(listNames.contains(dataTransfer.currentListName)) listNames.remove(dataTransfer.currentListName);

        try (FileOutputStream fileOutputStream = openFileOutput(fileName, this.MODE_PRIVATE)) {
            for(int i=0;i<listNames.size();i++)
            {
                fileOutputStream.write(listNames.elementAt(i).getBytes());
                fileOutputStream.write("\n".getBytes());
            }
        }
        catch (IOException e) {e.printStackTrace();}
    }

    private void deleteListFile()
    {
        deleteFile(dataTransfer.currentListName);
    }
}
