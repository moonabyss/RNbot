package com.moonabyss;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by uncle on 11.05.2016.
 */
public class FixedQueue<T> extends ArrayList<T> {

    private int maxSize;

    public FixedQueue(int size) {
        maxSize = size;
    }

    @Override
    public boolean add(T t) {
        if (size() > 0 && t.equals(get(size() - 1))) {
            return false;
        }
        boolean r = super.add(t);
        if (size() > maxSize) {
            removeRange(0, size() - maxSize);
        }
        return r;
    }

}
