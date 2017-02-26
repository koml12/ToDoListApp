package com.example.mohit31.todolist;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by mohit31 on 2/24/17.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public ListAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        public TextView mListItemTextView;

        public ListViewHolder(View itemView) {
            super(itemView);
            mListItemTextView = (TextView) itemView.findViewById(R.id.list_item);
        }
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        String taskName = mCursor.getString(mCursor.getColumnIndex(TodoListContract.TodoListEntry.COLUMN_TASK_NAME));
        holder.mListItemTextView.setText(taskName);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
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
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
