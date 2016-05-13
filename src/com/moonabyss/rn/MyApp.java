package com.moonabyss.rn;

import com.moonabyss.FixedQueue;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by uncle on 10.05.2016.
 */
public class MyApp {

    private final static Color RN_GREEN = new Color(37, 136, 69);

    FixedQueue<String> messages = new FixedQueue<>(10);
    MyDisplay display = new MySwingForm();
    private BufferedImage stationButton;
    private BufferedImage stationAssaButton;

    public MyApp() {
        loadImages();
        messages.add("Бот загружен");
    }

    public void doJob() {
        while (true) {
            try {
                //координаты
                int x = MouseInfo.getPointerInfo().getLocation().x;
                int y = MouseInfo.getPointerInfo().getLocation().y;
                //messages.add("Mouse X:" + x + ", Y:" + y);

                try {
                    boolean atStation = imagesAreEqual(new Robot().createScreenCapture(new Rectangle(823, 978, 36, 24)), stationButton)
                            || imagesAreEqual(new Robot().createScreenCapture(new Rectangle(1826, 80, 72, 32)), stationAssaButton);
                    //messages.add("Mouse X:" + x + ", Y:" + y + " " + new Robot().getPixelColor(1582, 494));
                    if (atStation) {
                        messages.add("Видео: " + checkAdv() + "\tБонус: " + checkBonus());
                    } else {
                        messages.add("Станция закрыта");
                    }
                } catch (AWTException e) {
                    e.printStackTrace();
                }

                display.showMessages(messages);

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String checkAdv() throws AWTException {
        final int REC_WIDTH = 16;
        Color controlColor = RN_GREEN;
        Point[] aPoint = {new Point(1260, 725), new Point(1610, 639), new Point(1800, 496)};
        StringBuilder sb = new StringBuilder();
        int i = 0;

        Robot robot = new Robot();
        BufferedImage bi = null;
        while (i < 3) {
            bi = robot.createScreenCapture(new Rectangle(aPoint[i].x-REC_WIDTH, aPoint[i].y-REC_WIDTH, REC_WIDTH*2, REC_WIDTH*2));

            if (findColorPoint(bi, controlColor)) {
                sb.append(i+1).append(' ');
            } else {
                sb.append('-').append(' ');
            }

            i++;
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }

    private String checkBonus() throws AWTException {
        final int REC_WIDTH = 10;
        Point[] aPoint = {new Point(1050, 721), new Point(1403, 638), new Point(1582, 494)};
        Color[] colorRgb = {new Color(66, 123, 65), new Color(52, 100, 52), new Color(194, 87, 87)};
        BufferedImage bi = null;
        StringBuilder sb = new StringBuilder();
        int i = 0;

        Robot robot = new Robot();

        while (i < 3) {
            bi = robot.createScreenCapture(new Rectangle(aPoint[i].x-REC_WIDTH, aPoint[i].y-REC_WIDTH, REC_WIDTH*2, REC_WIDTH*2));

            if (findColorPoint(bi, colorRgb[i])) {
                sb.append(i+1).append(' ');
            } else {
                sb.append('-').append(' ');
            }

            i++;
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }

    private boolean findColorPoint(BufferedImage image, Color colorPoint) {
        final int colorRgb = colorPoint.getRGB();
        for (int x = 1; x < image.getWidth(); x++) {
            for (int y = 1; y < image.getHeight(); y++) {
                if (image.getRGB(x, y) == colorRgb) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean imagesAreEqual(BufferedImage image1, BufferedImage image2) {
        if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight()) {
            return false;
        }
        for (int x = 1; x < image2.getWidth(); x++) {
            for (int y = 1; y < image2.getHeight(); y++) {
                if (image1.getRGB(x, y) != image2.getRGB(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void loadImages() {
        try {
            stationButton = ImageIO.read(getClass().getResource("/img/station.png"));
            stationAssaButton = ImageIO.read(getClass().getResource("/img/stationAssa.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
