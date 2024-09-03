package com.example.adelajda;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Vector;

public class LearningModeAActivity extends AppCompatActivity {

    private TextView wordATextView;
    private TextView wordBTextView;
    private TextView commentTextView;

    private Button revealButton;
    private Button previousButton;
    private Button nextButton;
    private Button resetButton;
    private Button endButton;

    private DataTransfer dataTransfer;
    private TableLayout tableLayout;

    private TextView languageOneField;
    private TextView languageTwoField;

    private Vector<String> languageOneWords;
    private Vector<String> languageTwoWords;
    private Vector<String> comments;
    private Vector<String> colors;
    private Vector<Integer> numbers;
    private int currentNumber;

    private boolean currentWordIsLanguageOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.learning_mode_a);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setUpComponents();
        setUpRandom();
        setWord();

        revealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentWordIsLanguageOne==true)
                {
                    wordBTextView.setText(languageTwoWords.elementAt(numbers.elementAt(currentNumber)));
                    languageTwoField.setText(dataTransfer.currentListLanguageTwoName);
                }
                else
                {
                    wordBTextView.setText(languageOneWords.elementAt(numbers.elementAt(currentNumber)));
                    languageTwoField.setText(dataTransfer.currentListLanguageOneName);

                }
                commentTextView.setText(comments.elementAt(numbers.elementAt(currentNumber)));
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentNumber==0) return;
                currentNumber=currentNumber-1;
                if(dataTransfer.chosenMode==LearningMode.BOTH) currentWordIsLanguageOne=!currentWordIsLanguageOne;
                setNewWord();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentNumber+1<languageOneWords.size())
                {
                    currentNumber++;
                    if(dataTransfer.chosenMode==LearningMode.BOTH) currentWordIsLanguageOne=!currentWordIsLanguageOne;
                    setNewWord();
                }
                else endOfData();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOver();
            }
        });

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void setUpComponents()
    {
        dataTransfer=DataTransfer.getInstance();
        wordATextView=findViewById(R.id.word_a);
        wordBTextView=findViewById(R.id.word_b);
        commentTextView=findViewById(R.id.comment);

        revealButton=findViewById(R.id.reveal);
        previousButton=findViewById(R.id.previous_word);
        nextButton=findViewById(R.id.next_word);
        resetButton=findViewById(R.id.reset);
        endButton=findViewById(R.id.end);
        tableLayout=findViewById(R.id.main);
        currentNumber=0;

        languageOneField=findViewById(R.id.language1);
        languageTwoField=findViewById(R.id.language2);
    }


    private void setUpRandom()
    {
        getDataFromFile();
        numbers=new Vector<>();
        for(int i=0;i<languageOneWords.size();i++) numbers.add(i);

        generateRandom();
    }


    private void getDataFromFile()
    {
        languageOneWords= new Vector<>();
        languageTwoWords=new Vector<>();
        comments=new Vector<>();
        colors=new Vector<>();

        String fileName= dataTransfer.currentListName;
        Vector<String> listContentBuffer=new Vector<>();

        try(FileInputStream fileInputStream = this.openFileInput(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream))) {
            String bufferLine;
            while ((bufferLine = reader.readLine()) != null) listContentBuffer.add(bufferLine);
        } catch (IOException e) { e.printStackTrace(); }

        if(listContentBuffer.size()>1)
        {
            for(int i=1;i<listContentBuffer.size();i++)
            {
                String[] lineBuffer=listContentBuffer.elementAt(i).split(";");

                languageOneWords.add(lineBuffer[0]);
                languageTwoWords.add(lineBuffer[1]);
                comments.add(lineBuffer[2]);
                colors.add(lineBuffer[3]);
            }
        }
    }


    private void generateRandom()
    {
        Collections.shuffle(numbers);
    }


    private void setWord()
    {
        if(dataTransfer.chosenMode==LearningMode.LAN_ONE)
        {
            wordATextView.setText(languageOneWords.elementAt(numbers.elementAt(currentNumber)));
            currentWordIsLanguageOne=true;
            languageOneField.setText(dataTransfer.currentListLanguageOneName);
        }
        else
        {
            wordATextView.setText(languageTwoWords.elementAt(numbers.elementAt(currentNumber)));
            currentWordIsLanguageOne=false;
            languageOneField.setText(dataTransfer.currentListLanguageTwoName);
        }

        languageTwoField.setText("");
        wordBTextView.setText("");
        commentTextView.setText("");
        setColor(colors.elementAt(numbers.elementAt(currentNumber)));
    }


    private void setNewWord()
    {
        if(currentWordIsLanguageOne==true)
        {
            wordATextView.setText(languageOneWords.elementAt(numbers.elementAt(currentNumber)));
            languageOneField.setText(dataTransfer.currentListLanguageOneName);
        }
        else
        {
            wordATextView.setText(languageTwoWords.elementAt(numbers.elementAt(currentNumber)));
            languageOneField.setText(dataTransfer.currentListLanguageTwoName);
        }
        languageTwoField.setText("");

        wordBTextView.setText("");
        commentTextView.setText("");
        setColor(colors.elementAt(numbers.elementAt(currentNumber)));
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


    private void endOfData()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(LearningModeAActivity.this);
        builder.setTitle("End");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.text, null);
        builder.setView(dialogView);

        TextView txt = dialogView.findViewById(R.id.text);
        txt.setText("Congratulations! You have reach an end of words in this list.");

        builder.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton("Start over", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startOver();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void startOver()
    {
        generateRandom();
        currentNumber=0;
        setWord();
    }
}
