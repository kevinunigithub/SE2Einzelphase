package com.example.se2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private EditText  matrikelNummer;
    private TextView responseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matrikelNummer = findViewById(R.id.matrikel);
        responseView = findViewById(R.id.responseTextView);

        Button sendButton = findViewById(R.id.send);

        Button calculateButton  = findViewById(R.id.button2);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matNummer = matrikelNummer.getText().toString();
                if (!matNummer.isEmpty()) {
                    sendToServer(matNummer);
                }
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matNummer = matrikelNummer.getText().toString();
                if (!matNummer.isEmpty()) {
                    calculate(matNummer);
                }
            }
        });
    }



        private void sendToServer(String matrikelNummer){
            //TODO
            responseView.setText(matrikelNummer);
        }

        private void calculate(String matrikelNummer){
            char[] chars = matrikelNummer.toCharArray();
            StringBuilder evenDigits = new StringBuilder();
            StringBuilder oddDigits = new StringBuilder();

            for (char c: chars){
                if(Character.isDigit(c)){
                    int digit = Character.getNumericValue(c);
                    if(digit % 2 == 0 ){
                        evenDigits.append(digit);
                    }else{
                        oddDigits.append(digit);
                    }
                }
            }
            char[] sortedEvenDigits = evenDigits.toString().toCharArray();
            char[] sortedOddDigits = oddDigits.toString().toCharArray();

            Arrays.sort(sortedEvenDigits);
            Arrays.sort(sortedOddDigits);

            StringBuilder sortedMatNummr = new StringBuilder();

            sortedMatNummr.append(sortedEvenDigits);
            sortedMatNummr.append(sortedOddDigits);
            responseView.setText(sortedMatNummr.toString());
        }
    }
