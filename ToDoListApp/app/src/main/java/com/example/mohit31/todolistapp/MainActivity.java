package com.example.mohit31.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

//TODO: Add touch handling to delete items from the task list when they are long pressed.
//UPDATE: Set an onClickListener, but it can only delete one item, and after it deletes the item, the list refreshes back to the original values.
//        Maybe could find some way to get the task list only once.
//        Or, could update the RecyclerView after each click.

//TODO: Add a view that says there are no tasks right now whenever the task list is empty.


//TODO: Figure out how to add another field to the task list to show the priority of a task. Might involve a database, as the Task object will actually have to be used and therefore won't be able to be passed across activities.

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ArrayList<String> tasks;

        TextView mNoTasksTextView;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rvTasks = (RecyclerView) findViewById(R.id.rv_task_list);
        RecyclerView.LayoutManager mLinearLayout;
        mNoTasksTextView = (TextView) findViewById(R.id.no_tasks);


        if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null){
                tasks = extras.getStringArrayList("EXTRA");

                mLinearLayout = new LinearLayoutManager(this);

                rvTasks.setLayoutManager(mLinearLayout);

                Log.d("RECYCLER", String.valueOf(tasks.size()));

                TaskAdapter adapter = new TaskAdapter(this, tasks);
                rvTasks.setAdapter(adapter);

            } else {
                tasks = new ArrayList<>();
                rvTasks.setVisibility(View.INVISIBLE);
                mNoTasksTextView.setVisibility(View.VISIBLE);
            }
        } else {
            tasks = new ArrayList<>();
            rvTasks.setVisibility(View.INVISIBLE);
            mNoTasksTextView.setVisibility(View.VISIBLE);

        }




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                intent.putExtra("EXTRA", tasks);
                startActivity(intent);
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
