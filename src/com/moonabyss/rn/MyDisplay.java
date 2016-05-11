package com.moonabyss.rn;

import com.moonabyss.FixedQueue;

/**
 * Created by uncle on 12.05.2016.
 */
public interface MyDisplay {
    void showMessages(FixedQueue<String> messages);
}
