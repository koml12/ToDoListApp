package com.example.mohit31.todolist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTaskActivity extends AppCompatActivity {

    private EditText mTaskEditText;
    private Button mAddTaskButton;
    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        mTaskEditText = (EditText) findViewById(R.id.add_activity_text);
        mAddTaskButton = (Button) findViewById(R.id.add_activity_button);
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


    }

    public Cursor getAllItems() {
        String[] selection = {TodoListContract.TodoListEntry.COLUMN_TASK_NAME};
        return mDatabase.query(TodoListContract.TodoListEntry.TABLE_NAME,
                selection,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public long addTask(String taskName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TodoListContract.TodoListEntry.COLUMN_TASK_NAME, taskName);
        return mDatabase.insert(TodoListContract.TodoListEntry.TABLE_NAME, null, contentValues);
    }

    public void addToList(View view) {
        if (mTaskEditText.getText().length() == 0) {
            return;
        }

        addTask(mTaskEditText.getText().toString());
        //mAdapter.swapCursor(getAllItems());

        mTaskEditText.setText("");
        mTaskEditText.clearFocus();
    }
}
