package com.example.mohit31.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mohit31 on 2/24/17.
 */

public class TodoListDbHelper extends SQLiteOpenHelper {
    public static final String CREATE_TABLE = "CREATE TABLE "+
            TodoListContract.TodoListEntry.TABLE_NAME + " (" +
            TodoListContract.TodoListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TodoListContract.TodoListEntry.COLUMN_TASK_NAME+ " TEXT, "
            + TodoListContract.TodoListEntry.COLUMN_PRIORITY + " INTEGER, "
            + TodoListContract.TodoListEntry.COLUMN_ITEM_NUMBER + " INTEGER, "
            + TodoListContract.TodoListEntry.COLUMN_DUE_TIME + " TEXT, "
            + TodoListContract.TodoListEntry.COLUMN_DUE_DATE + " TEXT)";

    public static final String DELETE_TABLE = "DROP TABLE " + TodoListContract.TodoListEntry.TABLE_NAME;

    public static final String DATABASE_NAME = "Tasks.db";
    public static final int DATABASE_VERSION = 1;

    public TodoListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
