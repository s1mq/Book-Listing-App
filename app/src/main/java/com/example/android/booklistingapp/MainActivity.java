package com.example.android.booklistingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText searchBox;
    private String searchWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.search_button);
        searchBox = (EditText) findViewById(R.id.search_box);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchWord = searchBox.getText().toString();
                Intent intent = new Intent(getApplicationContext(), BooksList.class);
                intent.putExtra("TITLE", searchWord);
                startActivity(intent);

            }
        });
    }
}
