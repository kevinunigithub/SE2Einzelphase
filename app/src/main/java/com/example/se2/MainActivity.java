package com.example.se2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matrikelnummer = matrikelNummer.getText().toString();
                if (!matrikelnummer.isEmpty()) {
                    sendToServer(matrikelnummer);
                }
            }
        });
    }



        private void sendToServer(String matrikelNummer){
            //TODO
        }
    }
