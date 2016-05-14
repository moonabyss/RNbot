package com.moonabyss.rn;

import com.moonabyss.FixedQueue;
import com.moonabyss.FixedStringQueueWithDate;

/**
 * Created by uncle on 12.05.2016.
 */
public interface MyDisplay {

    void showMessages(FixedStringQueueWithDate<String> messages);

}
