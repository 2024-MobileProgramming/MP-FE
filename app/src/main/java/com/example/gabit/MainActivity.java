package com.example.gabit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn1; //만약 이 버튼을 눌렀을 때 button name 변경 필요.
    Intent myIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent = new Intent(getApplicationContext(), Mission_Calendar.class);
                startActivity(myIntent);
                //finish(); //finish함수를 이용하면 activity가 살아있다. 뒤로가기를 눌러서 사용 가능.
            }
        });
    }

}