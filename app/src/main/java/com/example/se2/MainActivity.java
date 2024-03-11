package com.example.se2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;



public class MainActivity extends AppCompatActivity {

    private EditText  matrikelNummer;
    private TextView responseView;
    private static final String SERVER_DOMAIN = "se2-submission.aau.at";
    private static final int SERVER_PORT = 20080;

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
                if (!matNummer.isEmpty() && matNummer.matches("\\d{8}")) {
                    performNetworkRequest(matNummer);
                }else{
                    responseView.setText("Please enter valid Matrikelnummer!");
                }
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matNummer = matrikelNummer.getText().toString();
                if (!matNummer.isEmpty() && matNummer.matches("\\d{8}")) {
                    calculate(matNummer);
                }else{
                    responseView.setText("Please enter valid Matrikelnummer!");
                }
            }
        });
    }

    //network part

    private void performNetworkRequest(String matrikelnummer) {
        sendMatrikelnummerToServer(matrikelnummer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String response) {
                        responseView.setText(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private Observable<String> sendMatrikelnummerToServer(final String matrikelnummer) {
        return Observable.create(emitter -> {
            try {
                Socket socket = new Socket(SERVER_DOMAIN, SERVER_PORT);

                //write to socket using Printwriter
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(matrikelnummer);

                //read response using BufferedReader
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String response;
                response = in.readLine();

                out.close();
                in.close();
                socket.close();

                emitter.onNext(response);
                emitter.onComplete();
            } catch (IOException e) {
                emitter.onError(e);
            }
        });
    }





    private void calculate(String matrikelnummer) {
        List<String> result = new ArrayList<>();

        // Convert the Matrikelnummer string to an array of characters
        char[] chars = matrikelnummer.toCharArray();
        int n = chars.length;

        // Iterate over all pairs of digits
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int digit1 = Character.getNumericValue(chars[i]);
                int digit2 = Character.getNumericValue(chars[j]);

                // Check if both digits are greater than 1 and have a common divisor
                if (digit1 > 1 && digit2 > 1 && hasCommonDivisor(digit1, digit2)) {
                    result.add("(" + i + ", " + j + ")");
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Common Divisor Indices:\n");
        for (String indexPair : result) {
            stringBuilder.append(indexPair).append("\t,\t");
        }
        responseView.setText(stringBuilder.toString());

       
    }

        private boolean hasCommonDivisor(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a > 1;
    }
    }
