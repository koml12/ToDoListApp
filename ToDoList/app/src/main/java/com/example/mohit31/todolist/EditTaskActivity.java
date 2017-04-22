package com.example.mohit31.todolist;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class EditTaskActivity extends AppCompatActivity {
    private int priority = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        final EditText mTaskEditText;
        final TextView mEditDueDateTextView;
        final TextView mEditDueTimeTextView;
        Spinner mSpinner;
        Button mButton;

        mTaskEditText = (EditText) findViewById(R.id.task_et);
        mEditDueDateTextView = (TextView) findViewById(R.id.task_edit_duedate);
        mEditDueTimeTextView = (TextView) findViewById(R.id.task_edit_duetime);
        mSpinner = (Spinner) findViewById(R.id.edit_priority_spinner);
        mButton = (Button) findViewById(R.id.edit_task_button);

        Bundle bundle = getIntent().getExtras();

        String taskName = bundle.getString("TASK_NAME_EXTRA");
        String taskPriorityText = bundle.getString("TASK_PRIORITY_EXTRA");
        final int id = bundle.getInt("TASK_ID_EXTRA");
        String dueDateString = bundle.getString("TASK_DUE_DATE_EXTRA");
        String dueTimeString = bundle.getString("TASK_DUE_TIME_EXTRA");

        priority = ListAdapter.convertPriorityStringToInt(taskPriorityText, this);

        mTaskEditText.setText(taskName);
        mEditDueDateTextView.setText(dueDateString);
        mEditDueTimeTextView.setText(dueTimeString);

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, R.layout.spinner_list_item,
                this.getResources().getStringArray(R.array.priority_list)) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if (position == 0) textView.setTextColor(Color.GRAY);
                else {
                    textView.setTextColor(Color.BLACK);
                }
                return textView;
            }
        };

        mAdapter.setDropDownViewResource(R.layout.spinner_list_item);
        mSpinner.setAdapter(mAdapter);
        mSpinner.setSelection(priority);

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


        TodoListDbHelper dbHelper = new TodoListDbHelper(this);
        final SQLiteDatabase mDatabase = dbHelper.getWritableDatabase();


        mEditDueDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dateFragment = new DatePickerFragment(mEditDueDateTextView, getApplicationContext());
                dateFragment.show(getFragmentManager(), "datePicker");
            }
        });

        mEditDueTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timeFragment = new TimePickerFragment(mEditDueTimeTextView, getApplicationContext());
                timeFragment.show(getFragmentManager(), "timePicker");
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String changedName = mTaskEditText.getText().toString();
                String changedDueDate = mEditDueDateTextView.getText().toString();
                String changedDueTime = mEditDueTimeTextView.getText().toString();

                updateTask(changedName, priority, changedDueDate, changedDueTime, mDatabase, id);

                Intent intent = new Intent(EditTaskActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public long updateTask(String name, int taskPriority, String dueDate, String dueTime,
                           SQLiteDatabase database, int id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TodoListContract.TodoListEntry.COLUMN_TASK_NAME, name);
        contentValues.put(TodoListContract.TodoListEntry.COLUMN_PRIORITY, taskPriority);
        contentValues.put(TodoListContract.TodoListEntry.COLUMN_DUE_DATE, dueDate);
        contentValues.put(TodoListContract.TodoListEntry.COLUMN_DUE_TIME, dueTime);
        contentValues.put(TodoListContract.TodoListEntry._ID, id);
        Log.d("DATA", "ID is " + id);

        return database.update(TodoListContract.TodoListEntry.TABLE_NAME, contentValues,
                TodoListContract.TodoListEntry.COLUMN_ITEM_NUMBER + "=" + id, null);
    }

}
