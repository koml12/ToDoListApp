package com.example.mohit31.todolist.Database;

import android.provider.BaseColumns;

/**
 * Created by mohit31 on 2/24/17.
 */

public final class TodoListContract {
    private TodoListContract() {

    }

    public static class TodoListEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_TASK_NAME = "taskName";
        public static final String COLUMN_PRIORITY = "priority";
        public static final String COLUMN_DUE_DATE = "dueDate";
        public static final String COLUMN_ITEM_NUMBER  = "itemNumber";
        public static final String COLUMN_DUE_TIME = "dueTime";
    }
}
