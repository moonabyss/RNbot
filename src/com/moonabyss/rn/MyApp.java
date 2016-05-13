package com.moonabyss.rn;

import com.moonabyss.FixedQueue;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;

/**
 * Created by uncle on 10.05.2016.
 */
public class MyApp {

    private final static Color RN_GREEN = new Color(37, 136, 69);
    private static  final Point[] aPointBonus = {new Point(1050, 766), new Point(1403, 683), new Point(1582, 539)};
    private static  final Point[] aPointAdv = {new Point(1260, 765), new Point(1610, 679), new Point(1800, 536)};
    private static  final Point[] aPointAssaIn = {new Point(1894, 622), new Point(1707, 400), new Point(1726, 516)};
    private static  final Point[] aPointAssaOut = {new Point(1880, 35), new Point(1893, 374)};

    FixedQueue<String> messages = new FixedQueue<>(10);
    private MyDisplay display = new MySwingForm();
    private BufferedImage stationButton;
    private BufferedImage stationAssaButton;
    private BufferedImage crest;
    private BufferedImage advCrest;

    private Robot robot;

    public MyApp() {
        loadImages();
        messages.add("Бот загружен");
    }

    public void doJob() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                //координаты
                int x = MouseInfo.getPointerInfo().getLocation().x;
                int y = MouseInfo.getPointerInfo().getLocation().y;
                //messages.add("Mouse X:" + x + ", Y:" + y);
/*
                if (imagesAreEqual(robot.createScreenCapture(new Rectangle(1165, 356, 32, 32)), advCrest)) {messages.add("совпадают");} else {messages.add("не совпадают");}
                display.showMessages(messages);
                Thread.sleep(1000);
*/

                //info loop
                boolean atStation = imagesAreEqual(robot.createScreenCapture(new Rectangle(823, 1018, 36, 24)), stationButton)
                        || imagesAreEqual(robot.createScreenCapture(new Rectangle(1826, 19, 72, 32)), stationAssaButton);
                //messages.add("Mouse X:" + x + ", Y:" + y + " " + robot.getPixelColor(1582, 494));
                if (atStation) {
                    messages.add("Видео: " + checkAdv() + "\tБонус: " + checkBonus());
                } else {
                    messages.add("Станция закрыта");
                }
                Thread.sleep(1000);
                display.showMessages(messages);
if (true) {
                //Main loop
                if (atStation) {
                    getBonus();
                    Thread.sleep(5000);
                    assaMove(aPointAssaIn);
                    Thread.sleep(5000);
                    getBonusInAssa();
                    assaMove(aPointAssaOut);
                    Thread.sleep(5000);

                    viewVideo();


                    //Thread.sleep(5000);
                }
}
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String checkAdv(){
        final int REC_WIDTH = 16;
        Color controlColor = RN_GREEN;
        StringBuilder sb = new StringBuilder();
        int i = 0;

        BufferedImage bi = null;
        while (i < 3) {
            bi = robot.createScreenCapture(new Rectangle(aPointAdv[i].x-REC_WIDTH, aPointAdv[i].y-REC_WIDTH, REC_WIDTH*2, REC_WIDTH*2));

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

    private String checkBonus() {
        final int REC_WIDTH = 10;
        Color[] colorRgb = {new Color(66, 123, 65), new Color(52, 100, 52), new Color(194, 87, 87)};
        BufferedImage bi = null;
        StringBuilder sb = new StringBuilder();
        int i = 0;

        while (i < 3) {
            bi = robot.createScreenCapture(new Rectangle(aPointBonus[i].x-REC_WIDTH, aPointBonus[i].y-REC_WIDTH, REC_WIDTH*2, REC_WIDTH*2));

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
            crest = ImageIO.read(getClass().getResource("/img/crest.png"));
            advCrest = ImageIO.read(getClass().getResource("/img/advCrest.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void moveMouseAndClick(Point point) throws InterruptedException {
        int x = MouseInfo.getPointerInfo().getLocation().x;
        int y = MouseInfo.getPointerInfo().getLocation().y;

        for (int i = 0; i < 25; i++) {
            robot.mouseMove(x + ((point.x - x) / 25 * i), y + ((point.y - y) / 25 * i));
            Thread.sleep(25);
        }
        robot.mouseMove(point.x, point.y);
        Thread.sleep(300);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    private void assaMove(Point[] mouseCoord) throws InterruptedException {
        Point[] aPoint = mouseCoord;
        for (int i = 0; i < aPoint.length; i++) {
            moveMouseAndClick(aPoint[i]);
            Thread.sleep(2000);
        }
    }

    private void getBonus() throws InterruptedException{
        String bonuses = "";
        bonuses = checkBonus();

        for (int i = 0; i < 3; i++) {
            if (bonuses.contains(String.valueOf(i + 1))) {
                moveMouseAndClick(aPointBonus[i]);
                Thread.sleep(3000);
                checkModalWindow();
            }
        }
    }

    private void getBonusInAssa() throws InterruptedException {
        Point nextPlayer = new Point(1865, 1030);
        BufferedImage currentPlayer = null;
        Thread.sleep(3000);
        BufferedImage firstPlayer = robot.createScreenCapture(new Rectangle(45, 105, 225, 55));
        do {
            messages.add("Видео: " + checkAdv() + "\tБонус: " + checkBonus());
            display.showMessages(messages);
            getBonus();
            moveMouseAndClick(nextPlayer);
            Thread.sleep(5000);
            currentPlayer = robot.createScreenCapture(new Rectangle(45, 105, 225, 55));
        } while (!imagesAreEqual(firstPlayer, currentPlayer));
    }

    private void viewVideo() throws InterruptedException{
        String videos = "";
        videos = checkAdv();
        final Point advClose = new Point(960, 680);
        final Point advBonus = new Point(975, 760);

        for (int i = 0; i < 3; i++) {
            if (videos.contains(String.valueOf(i + 1))) {
                moveMouseAndClick(aPointAdv[i]);
                do {
                    Thread.sleep(3000);
                    messages.add("Идет видео");
                    display.showMessages(messages);
                } while (imagesAreEqual(robot.createScreenCapture(new Rectangle(1258, 320, 32, 32)), crest));
                messages.add("end");
                display.showMessages(messages);
                if (imagesAreEqual(robot.createScreenCapture(new Rectangle(1165, 356, 32, 32)), advCrest)) {
                    messages.add("окончание видео");
                    moveMouseAndClick(advClose);
                } else {
                    moveMouseAndClick(advBonus);
                    do {
                        Thread.sleep(3000);
                        messages.add("Идет бонусное видео");
                        display.showMessages(messages);
                    } while (imagesAreEqual(robot.createScreenCapture(new Rectangle(1258, 320, 32, 32)), crest));
                }
                messages.add("end bonus video");
                display.showMessages(messages);
                moveMouseAndClick(advClose);
                Thread.sleep(3000);
            }
        }
    }

    private void checkModalWindow() throws InterruptedException{
        Point closeModalWindow = new Point(1155, 385);
        if (imagesAreEqual(robot.createScreenCapture(new Rectangle(1142, 371, 32, 32)), crest)) {
            moveMouseAndClick(closeModalWindow);
            Thread.sleep(3000);
        }
    }

}
