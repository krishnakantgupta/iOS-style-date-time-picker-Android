package com.kk.iosstyledatetimepicker;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by krishnakantgupta for BottomSheet data time picker library on date 2/12/2018.
 */

public class IOSStyleSateTimePicker extends Dialog implements View.OnClickListener, NumberPicker.OnValueChangeListener {

    private int daysOfMonth = 31;
    private static String DATE_PATTERN_AT_TITLE = "MMM d,yyyy hh:mm a";

    private OnDateTimeSelectListener mOnDateTimeSelectListener;
    private OnDateTimeSelectListener mOnDateTimeChangeListener;

    private NumberPicker dayPicker;
    private NumberPicker monthPicker;
    private NumberPicker yearPicker;

    private NumberPicker hourPicker;
    private NumberPicker minutePicker;
    private NumberPicker am_pm_Picker;

    private TextView tvDate;

    private Calendar cal = Calendar.getInstance();
    private Date mFinalDate;

    public static final String MONTH_KEY = "monthValue";
    public static final String DAY_KEY = "dayValue";
    public static final String YEAR_KEY = "yearValue";

    int monthVal = -1, dayVal = -1, yearVal = -1;
    Context context;

    public IOSStyleSateTimePicker(@NonNull Context context, Date date) {
        super(context, R.style.BottomOptionsDialogTheme);
        this.context = context;
        init(date);
    }

    private void init(Date date) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        WindowManager.LayoutParams param = window.getAttributes();
        param.dimAmount = 0.8f;
        param.horizontalMargin = 0;
        param.width = window.getWindowManager().getDefaultDisplay().getWidth();
        param.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        param.gravity = Gravity.BOTTOM;

        setContentView(R.layout.dialog_spinner_date_time_picker);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        findViewById(R.id.btn_select).setOnClickListener(this);

        tvDate = (TextView) findViewById(R.id.tv_date);
        dayPicker = (NumberPicker) findViewById(R.id.picker_day);
        monthPicker = (NumberPicker) findViewById(R.id.picker_month);
        yearPicker = (NumberPicker) findViewById(R.id.picker_year);

        hourPicker = (NumberPicker) findViewById(R.id.picker_hours);
        minutePicker = (NumberPicker) findViewById(R.id.picker_minuts);
        am_pm_Picker = (NumberPicker) findViewById(R.id.picker_am_pm);

        if (date != null) {
            cal.setTime(date);
        }
        int maxYear = cal.get(Calendar.YEAR);//Current Year - yyyy


        yearPicker.setWrapSelectorWheel(false);
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);

        if (monthVal != -1)// && (monthVal > 0 && monthVal < 13))
            monthPicker.setValue(monthVal);
        else
            monthPicker.setValue(cal.get(Calendar.MONTH) + 1);

        monthPicker.setDisplayedValues(new String[]{"Jan", "Feb", "Mar", "Apr", "May", "June", "July",
                "Aug", "Sep", "Oct", "Nov", "Dec"});


        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(daysOfMonth);

        if (dayVal != -1)
            dayPicker.setValue(dayVal);
        else
            dayPicker.setValue(cal.get(Calendar.DAY_OF_MONTH));


        final int minYear = 1997;
        int arraySize = maxYear - minYear;

        String[] tempArray = new String[arraySize];
        tempArray[0] = "---";
        int tempYear = minYear + 1;

        for (int i = 0; i < arraySize; i++) {
            tempArray[i] = " " + tempYear + "";
            tempYear++;
        }
        yearPicker.setMinValue(minYear + 1);
        yearPicker.setMaxValue(maxYear);
        yearPicker.setDisplayedValues(tempArray);
        yearPicker.setValue(maxYear);


        String hourDisplayValue[] = new String[24];
        for (int i = 0; i < 24; i++) {
            if (i < 12) {
                hourDisplayValue[i] = (i % 12) + 1 + "";
            } else {
                hourDisplayValue[i] = (i % 12) + 1 + "";
            }
        }
        hourPicker.setMinValue(1);
        hourPicker.setMaxValue(24);
        hourPicker.setValue(cal.get(Calendar.HOUR_OF_DAY));
        hourPicker.setDisplayedValues(hourDisplayValue);

        String minuteDisplayValue[] = new String[60];
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                minuteDisplayValue[i] = "0" + i;
            } else {
                minuteDisplayValue[i] = i + "";
            }
        }
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        minutePicker.setDisplayedValues(minuteDisplayValue);
        minutePicker.setValue(cal.get(Calendar.MINUTE));


        am_pm_Picker.setMinValue(1);
        am_pm_Picker.setMaxValue(2);
        am_pm_Picker.setDisplayedValues(new String[]{"AM", "PM"});
        am_pm_Picker.setValue(cal.get(Calendar.HOUR_OF_DAY) < 12 ? 1 : 2);

        yearPicker.setOnValueChangedListener(this);
        monthPicker.setOnValueChangedListener(this);
        dayPicker.setOnValueChangedListener(this);
        hourPicker.setOnValueChangedListener(this);
        minutePicker.setOnValueChangedListener(this);
        am_pm_Picker.setOnValueChangedListener(this);

        updateTime();
//        setNumberPickerTextColor(yearPicker, 0xffff0000);
    }

    public static boolean isLeapYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }

    public void setDateDisplayPattern(String pattern) {
        DATE_PATTERN_AT_TITLE = pattern;
        updateTime();
    }

    public static boolean isLeapYear2(int year) {
        if (year % 4 != 0) {
            return false;
        } else if (year % 400 == 0) {
            return true;
        } else if (year % 100 == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void setDateTimeSelectListener(OnDateTimeSelectListener listener) {
        mOnDateTimeSelectListener = listener;
    }

    public void setDateTimeChangeListener(OnDateTimeSelectListener listener) {
        mOnDateTimeChangeListener = listener;
    }


    private boolean setNumberPickerTextColor(NumberPicker numberPicker, int color) {
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof TextView) {
                try {
                    Field selectorWheelPaintField = NumberPicker.class.getDeclaredField("mSelectionDivider");
                    selectorWheelPaintField.setAccessible(true);
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    selectorWheelPaintField.set(numberPicker, colorDrawable);//context.getResources().getColor(R.color.colorPrimary));
                    numberPicker.invalidate();
                    return true;
                } catch (Exception e) {
                }
            }
        }
        return false;
    }

    private void setSelectedTextColor(NumberPicker numberPicker) {
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof TextView) {
                try {
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(0xffff0000);
                    ((TextView) child).setTextColor(0xffff0000);
                    numberPicker.invalidate();
                    break;
                } catch (Exception e) {
//                    Log.w("setNumberPickerTextColor", e);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_select) {
            if (mOnDateTimeSelectListener != null && mFinalDate != null) {
                mOnDateTimeSelectListener.onDateTimeSet(mFinalDate);
            }
            dismiss();
        }
    }

    private void updateTime() {
        int mYear = yearPicker.getValue();
        int mMonth = monthPicker.getValue() - 1;
        int mDay = dayPicker.getValue();
        int mHour = hourPicker.getValue();
        int mMinute = minutePicker.getValue();
        mFinalDate = CommonUtils.getDate(mYear, mMonth, mDay, mHour, mMinute);
        String formateValue = CommonUtils.getDateInFormat(mFinalDate, DATE_PATTERN_AT_TITLE);
        tvDate.setText(formateValue);
        if (mOnDateTimeChangeListener != null) {
            mOnDateTimeChangeListener.onDateTimeSet(mFinalDate);
        }
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
        if (numberPicker == yearPicker) {
            try {
                if (isLeapYear2(yearPicker.getValue())) {
                    daysOfMonth = 29;
                    dayPicker.setMaxValue(daysOfMonth);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (numberPicker == monthPicker) {
            switch (newVal) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    daysOfMonth = 31;
                    dayPicker.setMaxValue(daysOfMonth);
                    break;
                case 2:
                    daysOfMonth = 28;
                    dayPicker.setMaxValue(daysOfMonth);
                    break;

                case 4:
                case 6:
                case 9:
                case 11:
                    daysOfMonth = 30;
                    dayPicker.setMaxValue(daysOfMonth);
                    break;
            }
        } else if (numberPicker == hourPicker) {
            if (hourPicker.getValue() >= 12 && hourPicker.getValue() < 24) {
                am_pm_Picker.setValue(2);
            } else {
                am_pm_Picker.setValue(1);
            }
        } else if (numberPicker == am_pm_Picker) {
            if (am_pm_Picker.getValue() == 1 && hourPicker.getValue() > 12) {
                hourPicker.setValue(hourPicker.getValue() - 12);
            } else if (am_pm_Picker.getValue() == 2 && hourPicker.getValue() < 12) {
                hourPicker.setValue(hourPicker.getValue() + 12);
            }
        }
        updateTime();
    }

}
