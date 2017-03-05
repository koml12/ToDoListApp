package com.example.mohit31.todolist;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        public TextView mListItemTextView;
        public TextView mItemPriorityTextView;

        public ListViewHolder(View itemView) {
            super(itemView);
            mListItemTextView = (TextView) itemView.findViewById(R.id.list_item);
            mItemPriorityTextView = (TextView) itemView.findViewById(R.id.priority_item);
        }
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        if (!mTaskNameCursor.moveToPosition(position)) {
            return;
        }
        String taskName = mTaskNameCursor.getString(mTaskNameCursor.getColumnIndex(TodoListContract.TodoListEntry.COLUMN_TASK_NAME));
        int taskPriority = mTaskNameCursor.getInt(mTaskNameCursor.getColumnIndex(TodoListContract.TodoListEntry.COLUMN_PRIORITY));
        switch (taskPriority) {
            case 0:
                Log.d("DATA", "set priority to nothing");
                break;
            case 1:
                holder.mItemPriorityTextView.setText(R.string.low);
                holder.mListItemTextView.setTextColor(Color.BLACK);
                holder.mItemPriorityTextView.setTextColor(Color.BLACK);
                Log.d("DATA", "set priority to low");
                break;
            case 2:
                holder.mItemPriorityTextView.setText(R.string.medium);
                holder.mListItemTextView.setTextColor(Color.BLUE);
                holder.mItemPriorityTextView.setTextColor(Color.BLUE);
                Log.d("DATA", "set priority to medium");
                break;
            case 3:
                holder.mItemPriorityTextView.setText(R.string.high);
                holder.mListItemTextView.setTextColor(Color.RED);
                holder.mItemPriorityTextView.setTextColor(Color.RED);
                Log.d("DATA", "set priority to high");
                break;
            default:
                Log.d("DATA", "this is physically impossible. something is fucked.");
                break;
        }
        holder.mListItemTextView.setText(taskName);

    }

    @Override
    public int getItemCount() {
        return mTaskNameCursor.getCount();
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View taskView = inflater.inflate(R.layout.list_item, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(taskView);
        return viewHolder;
    }

    public void swapCursor(Cursor newCursor) {
        if (mTaskNameCursor != null) {
            mTaskNameCursor.close();
        }
        mTaskNameCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
