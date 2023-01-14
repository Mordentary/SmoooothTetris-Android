package com.e.testgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


public class RestartActivity extends AppCompatActivity {


    private Button btn1, btn2;
    private TextView tv;
    private DBHelper _dbHelper;
    private EditText editText;
    public static int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _dbHelper = new DBHelper(this);


        setContentView(R.layout.game_over_activity);

        editText = findViewById(R.id.editText);

        tv = findViewById(R.id.textView);

        tv.setText("YOUR SCORE: " + score);

        btn1 = findViewById(R.id.button3);
        btn2 = findViewById(R.id.button2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().isEmpty()) {
                    _dbHelper.insertUserData(editText.getText().toString(), Integer.toString(score));
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}

