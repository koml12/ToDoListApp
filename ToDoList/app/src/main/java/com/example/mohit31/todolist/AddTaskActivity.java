package com.example.mohit31.todolist;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.mohit31.todolist.Utils.DateTimeUtils;

public class AddTaskActivity extends AppCompatActivity {

    private EditText mTaskEditText;
    private Button mAddTaskButton;
    private TextView mDateTextView;
    private SQLiteDatabase mDatabase;
    private Spinner mSpinner;
    private int priority = 0;
    private TextView mTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        mTaskEditText = (EditText) findViewById(R.id.add_activity_text);
        mAddTaskButton = (Button) findViewById(R.id.add_activity_button);
        mSpinner = (Spinner) findViewById(R.id.activity_priority_spinner);
        mDateTextView = (TextView) findViewById(R.id.edit_task_date_display);
        mTimeTextView = (TextView) findViewById(R.id.add_task_time);


        TodoListDbHelper todoListDbHelper = new TodoListDbHelper(this);
        mDatabase = todoListDbHelper.getWritableDatabase();


        ArrayAdapter<String> priorityArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_list_item, this.getResources().getStringArray(R.array.priority_list)) {
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


        mAddTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask(mTaskEditText.getText().toString(), priority, mDateTextView.getText().toString(),
                        mTimeTextView.getText().toString(), mDatabase);
                Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        mDateTextView.setText(DateTimeUtils.constructDateString(DateTimeUtils.getCurrentDate()));
        mDateTextView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               DialogFragment dialogFragment = new DatePickerFragment(mDateTextView, getApplicationContext());
               dialogFragment.show(getFragmentManager(), "datePicker");
           }
        });


        mTimeTextView.setText(DateTimeUtils.constructTimeString(DateTimeUtils.getCurrentTime()));
        mTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment fragment = new TimePickerFragment(mTimeTextView, getApplicationContext());
                fragment.show(getFragmentManager(), "timePicker");
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


    public long addTask(String taskName, int priority, String dueDate, String dueTime, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TodoListContract.TodoListEntry.COLUMN_TASK_NAME, taskName);

        long numberOfRows = getTotalRows(database, TodoListContract.TodoListEntry.TABLE_NAME);
        numberOfRows++;
        contentValues.put(TodoListContract.TodoListEntry.COLUMN_ITEM_NUMBER, numberOfRows);
        Log.d("DATA", "Number of rows: " + numberOfRows);

        Log.d("DATA", "priority before putting in DB is " + priority);
        contentValues.put(TodoListContract.TodoListEntry.COLUMN_PRIORITY, priority);

        contentValues.put(TodoListContract.TodoListEntry.COLUMN_DUE_DATE, dueDate);
        Log.d("DATA", "put date of " + dueDate);

        contentValues.put(TodoListContract.TodoListEntry.COLUMN_DUE_TIME, dueTime);
        Log.d("DATA", "put time of " + dueTime);

        return database.insert(TodoListContract.TodoListEntry.TABLE_NAME, null, contentValues);
    }


    public static long getTotalRows(SQLiteDatabase database, String tableName) {
        return DatabaseUtils.queryNumEntries(database, tableName);
    }
}
