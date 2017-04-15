package com.example.mehul.whatstheweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v){
        EditText e = (EditText) findViewById(R.id.editText);
        Log.i("onClick", "Clicked with value " + e.getText());
    }
}
