package com.example.adelajda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LearningModeBStartActivity extends AppCompatActivity {

    private DataTransfer dataTransfer;

    private TextView nameListTextView;
    private TextView languageOneTextView;
    private TextView languageTwoTextView;

    private Button startButton;
    private Button goBackButton;

    private RadioGroup radioGroup;
    private RadioButton languageOneRB;
    private RadioButton languageTwoRB;
    private RadioGroup radioGroupMisspelling;

    private LearningMode selectedMode;
    private MisspellMode misspellMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.learning_mode_b_start);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dataTransfer=DataTransfer.getInstance();
        nameListTextView=findViewById(R.id.list_name);
        languageOneTextView=findViewById(R.id.language1);
        languageTwoTextView=findViewById(R.id.language2);
        startButton=findViewById(R.id.start);
        goBackButton=findViewById(R.id.go_back);
        radioGroup=findViewById(R.id.radioGroup);

        nameListTextView.setText(dataTransfer.currentListName);
        languageOneTextView.setText(dataTransfer.currentListLanguageOneName);
        languageTwoTextView.setText(dataTransfer.currentListLanguageTwoName);


        radioGroupMisspelling=findViewById(R.id.radioGroup2);
        languageOneRB=findViewById(R.id.radio_lan_1);
        languageTwoRB=findViewById(R.id.radio_lan_2);
        languageOneRB.setText(dataTransfer.currentListLanguageOneName);
        languageTwoRB.setText(dataTransfer.currentListLanguageTwoName);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.radio_lan_1) selectedMode=LearningMode.LAN_ONE;
                else if(checkedId==R.id.radio_lan_2) selectedMode=LearningMode.LAN_TWO;
                else if(checkedId==R.id.radio_both) selectedMode=LearningMode.BOTH;
            }
        });

        radioGroupMisspelling.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.radio_skip) misspellMode=MisspellMode.SKIP;
                else if(checkedId==R.id.radio_repeat) misspellMode=MisspellMode.REPEAT;
                else if(checkedId==R.id.radio_repeat_later) misspellMode=MisspellMode.REPEAT_LATER;
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedMode==null) selectedMode=LearningMode.BOTH;
                dataTransfer.chosenMode=selectedMode;
                if(misspellMode==null) misspellMode=MisspellMode.SKIP;
                dataTransfer.chosenMisspellMode=misspellMode;

                Intent intent = new Intent(LearningModeBStartActivity.this, LearningModeBActivity.class);
                startActivityForResult(intent,3);
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
