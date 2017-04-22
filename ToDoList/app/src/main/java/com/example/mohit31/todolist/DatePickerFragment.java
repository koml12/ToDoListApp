package com.example.mohit31.todolist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.DatePicker;
import android.widget.TextView;
import com.example.mohit31.todolist.Utils.DateTimeUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mohit31 on 4/9/17.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    private TextView mTextView;
    private Context mContext;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

       String dateString = mTextView.getText().toString();
       int[] date = DateTimeUtils.convertDateStringIntoInts(dateString);

       return new DatePickerDialog(getActivity(), this, date[0], date[1] - 1, date[2]);
    }


    DatePickerFragment(TextView textView, Context context) {
        mTextView = textView;
        mContext = context;
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        int[] date = {year, month + 1, day};
        mTextView.setText(DateTimeUtils.constructDateString(date));
        datePicker.updateDate(year, month, day);
    }
}
