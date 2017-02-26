package com.example.mohit31.todolist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MainActivity extends AppCompatActivity {
    private ListAdapter mAdapter;
    private SQLiteDatabase mDatabase;
    private EditText mAddTaskEditText;
    private Button mAddTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAddTaskEditText = (EditText) findViewById(R.id.add_item);

        mAddTaskButton = (Button) findViewById(R.id.button_add_item);

        RecyclerView itemsRecyclerView;

        itemsRecyclerView = (RecyclerView) this.findViewById(R.id.rv_list);

        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        TodoListDbHelper dbHelper = new TodoListDbHelper(this);



        mDatabase = dbHelper.getWritableDatabase();

        Cursor cursor = getAllItems();

        mAdapter = new ListAdapter(this, cursor);

        itemsRecyclerView.setAdapter(mAdapter);

         mAddTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToList(view);
                mAddTaskEditText.clearComposingText();
                Log.d("DATA", "onClick called");
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
        if (mAddTaskEditText.getText().length() == 0) {
            return;
        }

        addTask(mAddTaskEditText.getText().toString());
        mAdapter.swapCursor(getAllItems());


        mAddTaskEditText.clearFocus();
    }
}
