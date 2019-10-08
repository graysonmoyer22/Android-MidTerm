package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NoteActivity extends AppCompatActivity {

    int notePosition = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        final EditText title = (EditText)findViewById(R.id.noteTitle);
        final EditText content = (EditText)findViewById(R.id.noteContent);
        Intent i = getIntent();
        int mode = (i.getIntExtra("noteNum", 0));
        if(mode == -1) {
            title.setText(i.getStringExtra("title"));
            content.setText(i.getStringExtra("note"));
            notePosition = i.getIntExtra("position", 0);
        }
        else {
            title.setText("Note" + mode);
            content.setHint("Enter your new note here");
        }

        Button saveButton = (Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent();
                j.putExtra("title", title.getText().toString());
                j.putExtra("content", content.getText().toString());
                j.putExtra("position", notePosition);
                setResult(RESULT_OK, j);
                finish();
            }
        });
    }

}
