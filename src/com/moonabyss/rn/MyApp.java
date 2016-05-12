package com.moonabyss.rn;

import com.moonabyss.FixedQueue;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by uncle on 10.05.2016.
 */
public class MyApp {

    private final static int RN_GREEN = new Color(37, 136, 69).getRGB();

    FixedQueue<String> messages = new FixedQueue<>(10);
    MyDisplay display = new MySwingForm();

    public MyApp() {
        messages.add("Бот загружен");
    }

    public void doJob() {
        while (true) {
            try {
                //координаты
                int x = MouseInfo.getPointerInfo().getLocation().x;
                int y = MouseInfo.getPointerInfo().getLocation().y;
                //messages.add("Mouse X:" + x + ", Y:" + y);

                //доступная реклама
                messages.add("Доступная реклама: " + checkAdv());

                display.showMessages(messages);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String checkAdv() {
        Color color;
        StringBuilder sb = new StringBuilder();
        Point[] aPoint = {new Point(1260, 725), new Point(1610, 639), new Point(1800, 496)};
        int i = 0;
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        while (i < 3) {
            boolean eq = false;
            /*try {
                color = new Robot().getPixelColor(aPoint[i].x, aPoint[i].y);
                if (color.getRGB() == RN_GREEN.getRGB()) {
                    sb.append(i+1).append(' ');
                } else {
                    sb.append('-').append(' ');
                }
            } catch (AWTException e) {
                e.printStackTrace();
            }*/
            BufferedImage bi = robot.createScreenCapture(new Rectangle(aPoint[i].x-16, aPoint[i].y-16, 32, 32));

            for (int x = 1; x < bi.getWidth() && !eq; x++) {
                for (int y = 1; y < bi.getHeight() && !eq; y++) {
                    if (bi.getRGB(x, y) == RN_GREEN) {
                        sb.append(i+1).append(' ');
                        eq = true;
                    }
                }
            }

            if (!eq) {
                sb.append('-').append(' ');
            }
            i++;
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }

}
