package com.moonabyss;

import java.util.ArrayList;

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
        boolean r = super.add(t);
        if (size() > maxSize) {
            removeRange(0, size() - maxSize - 1);
        }
        return r;
    }

}
