package com.example.mohit31.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ArrayList<String> tasks;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rvTasks = (RecyclerView) findViewById(R.id.rv_task_list);
        RecyclerView.LayoutManager mLinearLayout;
        //TODO: Get the task list from the entering interface. NOTE: May involve deleting the current list items and relying only on user input.

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
            }
        } else {
            tasks = new ArrayList<>();
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

    public boolean checkIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            return true;
        } else {
            return false;
        }
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
