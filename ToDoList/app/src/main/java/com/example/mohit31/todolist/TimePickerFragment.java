package com.example.mohit31.todolist;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;
import com.example.mohit31.todolist.Utils.DateTimeUtils;

/**
 * Created by mohit31 on 4/16/17.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private TextView mTimeTextView;
    private Context mContext;

    public TimePickerFragment(TextView textView, Context context) {
        mTimeTextView = textView;
        mContext = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String timeString =  mTimeTextView.getText().toString();
        int[] time = DateTimeUtils.convertTimeStringToInts(timeString);
        return new TimePickerDialog(getActivity(), this, time[0], time[1], false);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        int[] time = {hour, minute};
        mTimeTextView.setText(DateTimeUtils.constructTimeString(time));
    }
}
