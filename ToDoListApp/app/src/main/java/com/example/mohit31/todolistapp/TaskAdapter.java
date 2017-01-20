package com.example.mohit31.todolistapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mohit31 on 1/18/17.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTaskNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTaskNameTextView = (TextView) itemView.findViewById(R.id.task_name);
            Log.d("RECYLER", "viewHolder created.");
        }
    }

    private ArrayList<String> mTaskList;

    private Context mContext;

    public TaskAdapter(Context context, ArrayList<String> tasks) {
        mTaskList = tasks;
        mContext = context;
        Log.d("RECYCLER", "New adapter created.");
    }

    private Context getContext() {
        Log.d("RECYCLER", "Got context.");
        return mContext;
    }

    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View taskView = inflater.inflate(R.layout.task_list, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(taskView);
        Log.d("RECYCLER", "Layout inflated.");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TaskAdapter.ViewHolder viewHolder, int position) {
        String task = mTaskList.get(position);

        TextView textView = viewHolder.mTaskNameTextView;

        textView.setText(task);
        Log.d("RECYCLER", "Bind text.");
    }

    @Override
    public int getItemCount() {
        Log.d("RECYCLER", "Got size.");
        return mTaskList.size();
    }
}
