package com.example.mohit31.todolist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import org.w3c.dom.Text;

public class ViewTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        TextView mSingleTaskTextView;
        TextView mSinglePriorityTextView;
        TextView mSingleDueDateTextView;
        TextView mSingleDueTimeTextView;
        FloatingActionButton fab;

        mSingleTaskTextView = (TextView) findViewById(R.id.task_view_name_tv);
        mSinglePriorityTextView = (TextView) findViewById(R.id.task_view_priority_tv);
        mSingleDueDateTextView = (TextView) findViewById(R.id.task_view_duedate);
        mSingleDueTimeTextView = (TextView) findViewById(R.id.task_view_duetime);
        fab = (FloatingActionButton) findViewById(R.id.edit_task_fab);

        Bundle bundle = getIntent().getExtras();
        final String taskName = bundle.getString("TASK_NAME_EXTRA");
        int taskPriority = bundle.getInt("TASK_PRIORITY_EXTRA");
        final int id = bundle.getInt("TASK_ID_EXTRA");
        final String dueDate = bundle.getString("TASK_DUE_DATE_EXTRA");
        final String dueTime = bundle.getString("TASK_DUE_TIME_EXTRA");

        final String priorityText = ListAdapter.convertPriorityIntToString(taskPriority, this);

        mSinglePriorityTextView.setText(priorityText);
        mSingleTaskTextView.setText(taskName);
        mSingleDueDateTextView.setText(dueDate);
        mSingleDueTimeTextView.setText(dueTime);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToEditIntent = new Intent(ViewTaskActivity.this, EditTaskActivity.class);
                goToEditIntent.putExtra("TASK_NAME_EXTRA", taskName);
                goToEditIntent.putExtra("TASK_PRIORITY_EXTRA", priorityText);
                goToEditIntent.putExtra("TASK_ID_EXTRA", id);
                goToEditIntent.putExtra("TASK_DUE_DATE_EXTRA", dueDate);
                goToEditIntent.putExtra("TASK_DUE_TIME_EXTRA", dueTime);
                startActivity(goToEditIntent);
            }
        });

    }
}
