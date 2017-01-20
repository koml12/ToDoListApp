package com.example.mohit31.todolistapp;

import android.util.Log;

/**
 * Created by mohit31 on 1/18/17.
 */

public class Task {
    private String mTaskName;

    public Task(String taskName) {
        mTaskName = taskName;
        Log.d("RECYCLER", "New task created.");
    }

    public String getTaskName() {
        return mTaskName;
    }

}