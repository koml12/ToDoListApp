package com.example.mohit31.todolist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MainActivity extends AppCompatActivity {
    private ListAdapter mAdapter;
    private SQLiteDatabase mDatabase;
    private EditText mAddTaskEditText;
    private Button mAddTaskButton;
    private Toolbar mToolbar;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(mToolbar);

        mAddTaskEditText = (EditText) findViewById(R.id.add_item);

        mAddTaskButton = (Button) findViewById(R.id.button_add_item);

        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);

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
                Log.d("DATA", "onClick called");
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.clear_tasks:
                mDatabase.execSQL("DELETE FROM " + TodoListContract.TodoListEntry.TABLE_NAME);
                mAdapter.swapCursor(getAllItems());
                Log.d("DATA", "drop table");
                return true;
            default:
                super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
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

        mAddTaskEditText.setText("");
        mAddTaskEditText.clearFocus();
    }
}
