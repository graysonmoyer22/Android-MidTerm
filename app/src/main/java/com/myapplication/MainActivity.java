package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public int REQUEST_CODE = 20;
    final ArrayList<String> titlesArray = new ArrayList<>();
    final ArrayList<String> notesArray = new ArrayList<>();
    ArrayAdapter<String> notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView notesList = (ListView)findViewById(R.id.notesList);
        notesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titlesArray);
        notesList.setAdapter(notesAdapter);


        Button newNoteButton = (Button)findViewById(R.id.newNoteButton);
        newNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NoteActivity.class);
                int noteNum = titlesArray.size();
                i.putExtra("noteNum", noteNum);
                startActivityForResult(i, REQUEST_CODE); // brings up the second activity
            }
        });

        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> notesAdapter, View notesList, int position,
                                    long arg3)
            {
                // based on the item clicked go to the relevant activity
                Intent i = new Intent(MainActivity.this, NoteActivity.class);
                String titleToPass = titlesArray.get(position);
                String noteToPass = notesArray.get(position);
                i.putExtra("noteNum", -1);
                i.putExtra("position", position);
                i.putExtra("title", titleToPass);
                i.putExtra("note", noteToPass);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String name = data.getStringExtra("title");
            String content = data.getStringExtra("content");
            int position = data.getIntExtra("position", 0);
            if(position != -1) {
                titlesArray.set(position, name);
                notesArray.set(position, content);
            }
            else {
                titlesArray.add(name);
                notesArray.add(content);
            }
            notesAdapter.notifyDataSetChanged();
        }
    }
}
