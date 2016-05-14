package com.moonabyss;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by uncle on 15.05.2016.
 */
public class FixedStringQueueWithDate<T extends String> extends ArrayList<T> {
    private int maxSize;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    public FixedStringQueueWithDate(int size) {
        maxSize = size;
    }

    public void setDateFormat(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    @Override
    public boolean add(T t) {
        Calendar calendar = Calendar.getInstance();
        if (size() > 0) {
            if (t.equals(get(size() - 1).substring(6))) {
                return false;
            }
        }
        String tt = sdf.format(calendar.getTime()) + " " + t.toString();
        boolean r = super.add((T)tt);
        if (size() > maxSize) {
            removeRange(0, size() - maxSize);
        }
        return r;
    }
}
