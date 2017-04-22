package com.example.mohit31.todolist.Utils;

import java.util.Calendar;

/**
 * Created by mohit31 on 4/9/17.
 */
public class DateTimeUtils {

    public static int[] getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        int[] date = new int[3];
        date[0] = calendar.get(Calendar.YEAR);
        date[1] = calendar.get(Calendar.MONTH) + 1;
        date[2] = calendar.get(Calendar.DAY_OF_MONTH);
        return date;
    }


    public static String constructDateString(int[] date) {
        String year = String.valueOf(date[0]);
        String month = String.valueOf(date[1]);
        String day = String.valueOf(date[2]);
        return month + "/" + day + "/" + year;
    }


    public static int[] convertDateStringIntoInts(String dateString) {
        String[] splitString = dateString.split("/");
        int[] dateInts = new int[3];
        // dateInts format: {year,month,day}
        dateInts[0] = Integer.parseInt(splitString[2]);
        dateInts[1] = Integer.parseInt(splitString[0]);
        dateInts[2] = Integer.parseInt(splitString[1]);
        return dateInts;
    }


    public static int[] getCurrentTime() {
        int[] time = new int[2];
        Calendar calendar = Calendar.getInstance();
        time[0] = calendar.get(Calendar.HOUR_OF_DAY);
        time[1] = calendar.get(Calendar.MINUTE);
        return time;
    }


    public static String constructTimeString(int[] time) {
        String hourString;
        String minuteString;
        boolean isAM;

        hourString = String.valueOf(time[0]);

        // Handle PM times (that aren't 12 PM)
        if (time[0] > 12) {
            hourString = String.valueOf(time[0] - 12);
        }

        // Take care of the 12AM/ 12PM problems with time.
        if (time[0] == 0) {
            hourString = "12";
        } else if (time[0] == 12) {
            hourString = "12";
        }

        // Figure out if time is AM or PM
        isAM = time[0] >= 0 && time[0] < 12;

        minuteString = String.valueOf(time[1]);
        if (time[1] < 10) {
            minuteString = "0" + minuteString;
        }

        // Construct the time string
        if (isAM) {
            return hourString + ":" + minuteString + "AM";
        } else {
            return hourString + ":" + minuteString + "PM";
        }
    }


    public static int[] convertTimeStringToInts(String timeString) {
        boolean isAM = timeString.charAt(timeString.length() - 2) == 'A';

        // If timeString was 5:43PM, this would give {5, 43}
        String[] timeSplit = timeString.substring(0, timeString.length() - 2).split(":");

        // Get the hours and minutes and convert into ints
        int[] timeNums = new int[2];
        timeNums[0] = Integer.parseInt(timeSplit[0]);
        timeNums[1] = Integer.parseInt(timeSplit[1]);

        // Handle '12' cases while checking for AM or PM
        if (isAM) {
            if (timeNums[0] == 12) {
                timeNums[0] = 0;
            }
        } else {
            if  (timeNums[0] == 12) {
                timeNums[0] = 12;
            }
        }
        return timeNums;
    }
}
