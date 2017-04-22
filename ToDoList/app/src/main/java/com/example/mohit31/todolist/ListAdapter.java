package com.example.mohit31.todolist;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.CardView;

/**
 * Created by mohit31 on 2/24/17.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private Context mContext;
    private Cursor mTaskNameCursor;


    public ListAdapter(Context context, Cursor cursor) {
        mContext = context;
        mTaskNameCursor = cursor;
    }


    class ListViewHolder extends RecyclerView.ViewHolder {
        TextView mListItemTextView;
        TextView mItemPriorityTextView;
        TextView mItemDueDateTextView;
        TextView mItemDueTimeTextView;
        CardView mCardView;


        ListViewHolder(final View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mListItemTextView = (TextView) itemView.findViewById(R.id.list_item);
            mItemPriorityTextView = (TextView) itemView.findViewById(R.id.item_priority);
            mItemDueDateTextView = (TextView) itemView.findViewById(R.id.item_due_date);
            mItemDueTimeTextView = (TextView) itemView.findViewById(R.id.item_due_time);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // This next line is f***ing lit, I didn't even know why I'd need to pass context into the
                    // constructor until now.
                    Intent goToTaskViewActivity = new Intent(mContext, ViewTaskActivity.class);

                    // Get task name and priority from the the TextViews.
                    String taskName = mListItemTextView.getText().toString();
                    String taskPriority = mItemPriorityTextView.getText().toString();

                    // Convert the task priority to an int so it can go into the database.
                    int priorityNumber = convertPriorityStringToInt(taskPriority, mContext);

                    // Put data in as extras.
                    goToTaskViewActivity.putExtra("TASK_NAME_EXTRA", taskName);
                    Log.d("DATA", "Put task name: " + taskName + " as extra");

                    goToTaskViewActivity.putExtra("TASK_PRIORITY_EXTRA", priorityNumber);
                    Log.d("DATA", "Put priority: " + priorityNumber + " as extra");

                    int position = getAdapterPosition() + 1;
                    goToTaskViewActivity.putExtra("TASK_ID_EXTRA", position);

                    String dueDate = getDueDate(position);
                    goToTaskViewActivity.putExtra("TASK_DUE_DATE_EXTRA", dueDate);
                    Log.d("DATA", "sending date of " + dueDate);

                    String dueTime = getDueTime(position);
                    goToTaskViewActivity.putExtra("TASK_DUE_TIME_EXTRA", dueTime);
                    Log.d("DATA", "sending time of " + dueTime);

                    mContext.startActivity(goToTaskViewActivity);
                }
            });
        }
    }


    // Set the text for each view to the row of the database corresponding to the position.
    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        if (!mTaskNameCursor.moveToPosition(position)) {
            return;
        }
        long itemID = mTaskNameCursor.getLong(mTaskNameCursor.getColumnIndex(TodoListContract.TodoListEntry._ID));
        Log.d("DATA", "ID is " + itemID);

        setDefaultText(holder.mListItemTextView);
        setTaskPriority(holder.mItemPriorityTextView);
        setTaskDueDate(holder.mItemDueDateTextView);
        setTaskDueTime(holder.mItemDueTimeTextView);

        holder.itemView.setTag(itemID);
    }


    // Use cursor for task count, since data is in a database.
    @Override
    public int getItemCount() {
        return mTaskNameCursor.getCount();
    }


    // Boilerplate code for RecyclerView ViewHolder.
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View taskView = inflater.inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(taskView);
    }


    // Moves database cursor to new location, for data updating purposes.
    public void swapCursor(Cursor newCursor) {
        if (mTaskNameCursor != null) {
            mTaskNameCursor.close();
        }
        mTaskNameCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }


    // Sets text to the task name column in the database.
    private void setDefaultText(TextView textView) {
        String taskName = mTaskNameCursor.getString(mTaskNameCursor.getColumnIndex(TodoListContract.TodoListEntry.COLUMN_TASK_NAME));
        textView.setText(taskName);
    }


    // Sets the text of the task priority TextView to the String associated with the integer in that column of the table.
    private void setTaskPriority(TextView textView) {
        int taskPriority = mTaskNameCursor.getInt(mTaskNameCursor.getColumnIndex(TodoListContract.TodoListEntry.COLUMN_PRIORITY));
        String priorityText = convertPriorityIntToString(taskPriority, mContext);
        textView.setText(priorityText);
    }


    private void setTaskDueDate(TextView textView) {
        String dueDate = mTaskNameCursor.getString(mTaskNameCursor.getColumnIndex(TodoListContract.TodoListEntry.COLUMN_DUE_DATE));
        textView.setText(dueDate);
    }


    private void setTaskDueTime(TextView textView) {
        String dueTime = mTaskNameCursor.getString(mTaskNameCursor.getColumnIndex(TodoListContract.TodoListEntry.COLUMN_DUE_TIME));
        textView.setText(dueTime);
    }


    // Gets the due date from the database, does NOT account for errors in cursor movement.
    private String getDueDate(int position) {
        // Cursor is indexed at 0 vs adapter indexing at 1, so correct by subtracting 1 from the desired position.
        mTaskNameCursor.moveToPosition(position-1);
        return mTaskNameCursor.getString(mTaskNameCursor.getColumnIndex(TodoListContract.TodoListEntry.COLUMN_DUE_DATE));
    }


    // Gets the due time from the database, does NOT account for errors in cursor movement.
    private String getDueTime(int position) {
        mTaskNameCursor.moveToPosition(position-1);
        return mTaskNameCursor.getString(mTaskNameCursor.getColumnIndex(TodoListContract.TodoListEntry.COLUMN_DUE_TIME));
    }


    // Converts the priority int in the database to a string that we can use in a TextView
    public static String convertPriorityIntToString(int priority, Context context) {
        switch (priority) {
            case 0:
                return "";
            case 1:
                return context.getResources().getString(R.string.low);
            case 2:
                return context.getResources().getString(R.string.medium);
            case 3:
                return context.getResources().getString(R.string.high);
            default:
                return "";
        }
    }


    // Converts the user-friendly priority string representation to the computer-friendly int representation
    public static int convertPriorityStringToInt(String priorityText, Context context) {
        String lowString = context.getResources().getString(R.string.low);
        String mediumString = context.getResources().getString(R.string.medium);
        String highString = context.getResources().getString(R.string.high);

        if (priorityText.equals("")) {
            return 0;
        } else if (priorityText.equals(lowString)) {
            return 1;
        } else if (priorityText.equals(mediumString)) {
            return 2;
        } else if (priorityText.equals(highString)) {
            return 3;
        } else {
            return 0;
        }
    }
}
