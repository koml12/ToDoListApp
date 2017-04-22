package com.example.mohit31.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private ListAdapter mAdapter;
    private SQLiteDatabase mDatabase;
    private Toolbar mToolbar;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(mToolbar);

        Log.d("DATA", "onCreate");

        final RecyclerView itemsRecyclerView;

        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        itemsRecyclerView = (RecyclerView) this.findViewById(R.id.rv_list);

        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        TodoListDbHelper dbHelper = new TodoListDbHelper(this);

        mDatabase = dbHelper.getWritableDatabase();

        Cursor cursor = getAllItems();

        mAdapter = new ListAdapter(this, cursor);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long itemID = 0;
                try {
                    itemID = (long) viewHolder.itemView.getTag();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                removeTask(itemID);
                mAdapter.swapCursor(getAllItems());

            }
        }).attachToRecyclerView(itemsRecyclerView);

        itemsRecyclerView.setAdapter(mAdapter);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

    }

    public Cursor getAllItems() {
        String[] selection = {TodoListContract.TodoListEntry.COLUMN_TASK_NAME, TodoListContract.TodoListEntry.COLUMN_PRIORITY,
                TodoListContract.TodoListEntry.COLUMN_DUE_DATE, TodoListContract.TodoListEntry.COLUMN_DUE_TIME,
                TodoListContract.TodoListEntry.COLUMN_ITEM_NUMBER, TodoListContract.TodoListEntry._ID};
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
                Log.d("DATA", "rows removed");
                return true;
            default:
                super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean removeTask(long id) {
        return mDatabase.delete(TodoListContract.TodoListEntry.TABLE_NAME,
                TodoListContract.TodoListEntry._ID + "=" + id, null) > 0;
    }



}
