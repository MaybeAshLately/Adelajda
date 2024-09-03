package com.example.adelajda;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
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

public class EditListInfoActivity extends AppCompatActivity {

    private Button cancel;
    private Button edit;

    private EditText listNameEditText;
    private EditText languageOneEditText;
    private EditText languageTwoEditText;

    private DataTransfer dataTransfer;

    private String listName;
    private String languageOneName;
    private String languageTwoName;

    private String mainFileName="ListNamesData.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.create_new_list);
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

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listName=listNameEditText.getText().toString();
                languageOneName=languageOneEditText.getText().toString();
                languageTwoName=languageTwoEditText.getText().toString();

                if((checkIfNamesCorrect()==true)&&(checkIfNameUnique()==true)) editData();
                else displayInfo();
            }
        });
    }

    private void setUpComponents()
    {
        dataTransfer=DataTransfer.getInstance();
        cancel=findViewById(R.id.cancel_adding_list_button);
        edit=findViewById(R.id.add_new_list_button);
        edit.setText("Edit");

        listNameEditText=findViewById(R.id.enter_list_name_edit_text);
        listNameEditText.setText(dataTransfer.currentListName);

        languageOneEditText=findViewById(R.id.enter_language_1_name_edit_text);
        languageOneEditText.setText(dataTransfer.currentListLanguageOneName);

        languageTwoEditText=findViewById(R.id.enter_language_2_name_edit_text);
        languageTwoEditText.setText(dataTransfer.currentListLanguageTwoName);
    }


    private boolean checkIfNamesCorrect()
    {
        return (checkIfNameCorrect(listName))&&(checkIfNameCorrect(languageOneName))&&(checkIfNameCorrect(languageTwoName));
    }


    private boolean checkIfNameCorrect(String string)
    {
        for(int i=0;i<string.length();i++)
        {
            char buffer=string.charAt(i);
            if((Character.isLetterOrDigit(buffer)==false)&&(buffer!=' ')&&(buffer!='_')) return false;
        }
        return true;
    }


    private boolean checkIfNameUnique()
    {
        if(listName.equals(dataTransfer.currentListName)) return true;

        Vector<String> listNamesBuffer=new Vector<>();

        try(FileInputStream fileInputStream = this.openFileInput(mainFileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream))) {
            String bufferLine;
            while ((bufferLine = reader.readLine()) != null) listNamesBuffer.add(bufferLine);
        } catch (IOException e) { e.printStackTrace(); }

        if(listNamesBuffer.contains(listName)) return false;
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
        txt.setText("All names can consist only of alphanumeric signs, spaces and underscores. They cannot be empty. Name of list must be unique.");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void editData()
    {
        if(listName.equals(dataTransfer.currentListName)==false)
        {
            changeNameInMainFile();
            changeNameOfListFile();
        }
        if((languageOneName.equals(dataTransfer.currentListLanguageOneName)==false)||(languageOneName.equals(dataTransfer.currentListLanguageOneName)==false))
        {
            changeLanguageNames();
        }

        dataTransfer.currentListName=listName;
        dataTransfer.currentListLanguageOneName=languageOneName;
        dataTransfer.currentListLanguageTwoName=languageTwoName;

        Toast toast = Toast.makeText(getApplicationContext(), "Data changed.", Toast.LENGTH_LONG);
        toast.show();

        finish();
    }


    private void changeNameInMainFile()
    {
        Vector<String> buffer=new Vector<>();

        try(FileInputStream fileInputStream = this.openFileInput(mainFileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream))) {
            String bufferLine;
            while ((bufferLine = reader.readLine()) != null) buffer.add(bufferLine);
        } catch (IOException e) { e.printStackTrace(); }

        int idx=buffer.indexOf(dataTransfer.currentListName);
        if(idx<buffer.size()) buffer.set(idx,listName);

        try (FileOutputStream fileOutputStream = openFileOutput(mainFileName, this.MODE_PRIVATE)) {
            for(int i=0;i<buffer.size();i++)
            {
                fileOutputStream.write(buffer.elementAt(i).getBytes());
                fileOutputStream.write("\n".getBytes());
            }
        }
        catch (IOException e) {e.printStackTrace();}
    }


    private void changeNameOfListFile()
    {
        Vector<String> buffer=new Vector<>();

        try(FileInputStream fileInputStream = this.openFileInput(dataTransfer.currentListName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream))) {
            String bufferLine;
            while ((bufferLine = reader.readLine()) != null) buffer.add(bufferLine);
        } catch (IOException e) { e.printStackTrace(); }

        deleteFile(dataTransfer.currentListName);

        try (FileOutputStream fileOutputStream = openFileOutput(listName, this.MODE_APPEND)) {
            for(int i=0;i<buffer.size();i++)
            {
                fileOutputStream.write(buffer.elementAt(i).getBytes());
                fileOutputStream.write("\n".getBytes());
            }
        }
        catch (IOException e) {e.printStackTrace();}
    }


    private void changeLanguageNames()
    {
        Vector<String> buffer=new Vector<>();

        try(FileInputStream fileInputStream = this.openFileInput(listName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream))) {
            String bufferLine;
            while ((bufferLine = reader.readLine()) != null) buffer.add(bufferLine);
        } catch (IOException e) { e.printStackTrace(); }

        buffer.set(0,languageOneName+";"+languageTwoName);

        try (FileOutputStream fileOutputStream = openFileOutput(listName, this.MODE_PRIVATE)) {
            for(int i=0;i<buffer.size();i++)
            {
                fileOutputStream.write(buffer.elementAt(i).getBytes());
                fileOutputStream.write("\n".getBytes());
            }
        }
        catch (IOException e) {e.printStackTrace();}
    }
}
