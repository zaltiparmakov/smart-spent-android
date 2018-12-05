package si.smartspent.smartspent;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimePickerFragment extends DialogFragment implements OnDateSetListener, OnTimeSetListener {
    private int day, month, year, hour, minute;
    /**
     * Listener in order to pass data to the parent activity
     */
    public interface DateTimeChangeListener {
        void onDateTimeChange(Date date);
    }

    private DateTimeChangeListener dateListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof DateTimeChangeListener) {
            dateListener = (DateTimeChangeListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement DateTimeChangeListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        this.year = year;
        this.month = month;
        this.day = dayOfMonth;

        TimePickerDialog timePicker = new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getContext()));
        timePicker.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;
        // create new date with values chosen via date/time picker
        Calendar calendar = Calendar.getInstance();
        calendar.set(this.year, this.month, this.day, this.hour, this.minute);
        Date date = new Date().from(calendar.toInstant());

        // format data and pass data to the subscriber
        dateListener.onDateTimeChange(date);
    }
}
