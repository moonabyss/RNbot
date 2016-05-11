package com.moonabyss.rn;

import com.moonabyss.FixedQueue;

/**
 * Created by uncle on 10.05.2016.
 */
public class MyApp {

    FixedQueue<String> messages = new FixedQueue<>(10);
    MyDisplay display = new MySwingForm();

    public MyApp() {
        messages.add("Бот загружен");
        display.showMessages(messages);
    }

}
