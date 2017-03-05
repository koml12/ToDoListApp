package com.example.mohit31.todolist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;

public class AddTaskActivity extends AppCompatActivity {

    private EditText mTaskEditText;
    private Button mAddTaskButton;
    private SQLiteDatabase mDatabase;
    private Spinner mSpinner;
    private int priority = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        mTaskEditText = (EditText) findViewById(R.id.add_activity_text);

        mAddTaskButton = (Button) findViewById(R.id.add_activity_button);

        mSpinner = (Spinner) findViewById(R.id.activity_priority_spinner);

        String[] priorityLevels = new String[] {
                "Priority",
                "Low",
                "Medium",
                "High"
        };



        ArrayAdapter<String> priorityArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_list_item, priorityLevels) {


            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if (position == 0) {
                    textView.setTextColor(Color.GRAY);
                } else {
                    textView.setTextColor(Color.BLACK);
                }
                return textView;
            }
        };

        priorityArrayAdapter.setDropDownViewResource(R.layout.spinner_list_item);
        mSpinner.setAdapter(priorityArrayAdapter);

        TodoListDbHelper todoListDbHelper = new TodoListDbHelper(this);
        mDatabase = todoListDbHelper.getWritableDatabase();

        mAddTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask(mTaskEditText.getText().toString());
                Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        priority = 0;
                        Log.d("DATA", "priority = 0");
                        break;
                    case 1:
                        priority = 1;
                        Log.d("DATA", "priority = 1");
                        break;
                    case 2:
                        priority = 2;
                        Log.d("DATA", "priority = 2");
                        break;
                    case 3:
                        priority = 3;
                        Log.d("DATA", "priority = 3");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public long addTask(String taskName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TodoListContract.TodoListEntry.COLUMN_TASK_NAME, taskName);
        Log.d("DATA", "priority before putting in DB is " + priority);
        contentValues.put(TodoListContract.TodoListEntry.COLUMN_PRIORITY, priority);
        return mDatabase.insert(TodoListContract.TodoListEntry.TABLE_NAME, null, contentValues);
    }


}
