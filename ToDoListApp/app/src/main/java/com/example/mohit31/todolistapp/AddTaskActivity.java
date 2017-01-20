package com.example.mohit31.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class AddTaskActivity extends AppCompatActivity {


    private EditText mAddTask;
    private Button mAddTaskButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ArrayList<String> tasks;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        tasks = extras.getStringArrayList("EXTRA");

        mAddTask = (EditText) findViewById(R.id.task_text_field);
        mAddTaskButton = (Button) findViewById(R.id.add_task_button);

        mAddTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTask = mAddTask.getText().toString();
                tasks.add(newTask);
                Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
                intent.putStringArrayListExtra("EXTRA", tasks);
                startActivity(intent);
            }
        });
    }

}
