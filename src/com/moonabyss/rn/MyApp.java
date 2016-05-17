package com.moonabyss.rn;

import com.moonabyss.FixedQueue;
import com.moonabyss.FixedStringQueueWithDate;
import com.moonabyss.FlashCrashException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Random;

/**
 * Created by uncle on 10.05.2016.
 */
public class MyApp {

    boolean solo = false;
    int assaSize = 5;
    private final static Color RN_GREEN = new Color(37, 136, 69);
    private final static Color RN_BLACK = Color.BLACK;
    private static  final Point[] aPointBonus = {new Point(1050, 766), new Point(1403, 683), new Point(1582, 539)};
    private static  final Point[] aPointRedBonus = {new Point(1100, 760), new Point(1450, 675), new Point(1655, 535)};
    private static  final Point[] aPointAdv = {new Point(1260, 765), new Point(1610, 679), new Point(1800, 536)};
    private static  final Point[] aPointAssaIn = {new Point(1894, 622), new Point(1707, 400), new Point(1726, 516)};
    private static  final Point[] aPointAssaOut = {new Point(1880, 35), new Point(1893, 374)};

    FixedStringQueueWithDate<String> messages = new FixedStringQueueWithDate<>(10);
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
        if (solo) {
            messages.add("Соло режим");
        } else {
            messages.add("Размер ассы " + assaSize);
        }
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                //координаты
                /*int x = MouseInfo.getPointerInfo().getLocation().x;
                int y = MouseInfo.getPointerInfo().getLocation().y;
                messages.add("Mouse X:" + x + ", Y:" + y);*/
/*
                if (imagesAreEqual(robot.createScreenCapture(new Rectangle(823, 1018, 36, 24)), stationButton)) {messages.add("совпадают");messages.add(String.valueOf(robot.getPixelColor(823, 1018).getRGB()));} else {messages.add("не совпадают");messages.add(String.valueOf(robot.getPixelColor(823, 1018).getRGB()));}
                display.showMessages(messages);
                Thread.sleep(1000);
*/
                flashCrash();
                //info loop

                //messages.add("Mouse X:" + x + ", Y:" + y + " " + robot.getPixelColor(1582, 494));
                if (atStation()) {
                    messages.add("Бонус: " + checkBonus() + "\tВидео: " + checkAdv());
                } else {
                    messages.add("Станция закрыта");
                    checkErrorBuilding();
                }
                display.showMessages(messages);
                Thread.sleep(1000);

if (true) {
                //Main loop
                if (atStation()) {
                    showMyBonuses();
                    getBonus();
                    Thread.sleep(2000);
                    if (!solo) {
                        assaMove(aPointAssaIn);
                        getBonusInAssa("Bonus");
                    }
                }
                if (atStation()) {
                    showMyBonuses();
                    getBonus();
                    viewVideo();
                    Thread.sleep(2000);
                    if (!solo) {
                        assaMove(aPointAssaIn);
                        getBonusInAssa("Video");
                    } else {
                        antiSleep();
                    }
                }
}
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (FlashCrashException e) {
                continue;
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

            if (findColorPoint(bi, colorRgb[i]) && !(robot.getPixelColor(aPointRedBonus[i].x, aPointRedBonus[i].y).getRed() >= 100 && robot.getPixelColor(aPointRedBonus[i].x, aPointRedBonus[i].y).getRed() <= 170)) {
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

                //hack for vmware
                int imageRgb = image.getRGB(x, y);
                if (imageRgb - colorRgb == 0 || Math.abs(imageRgb - colorRgb) == 0x00010101) {
                    return true;
                }
                /*if (image.getRGB(x, y) == colorRgb) {
                    return true;
                }*/
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

                //hack for vmware
                int rgb1 = image1.getRGB(x, y);
                int rgb2 = image2.getRGB(x, y);
                if (rgb1 - rgb2 != 0) {
                    if (Math.abs(rgb2 - rgb1) != 0x00010101) {
                        return false;
                    }
                }
                /*if (image1.getRGB(x, y) != image2.getRGB(x, y)) {
                    return false;
                }*/
            }
        }
        return true;
    }

    private void loadImages() {
        try {
            stationButton = ImageIO.read(getClass().getResource("/img/station.bmp"));
            stationAssaButton = ImageIO.read(getClass().getResource("/img/stationAssa.bmp"));
            crest = ImageIO.read(getClass().getResource("/img/crest.bmp"));
            advCrest = ImageIO.read(getClass().getResource("/img/advCrest.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void moveMouseAndClick(Point point) throws InterruptedException, FlashCrashException {
        final int STEP = 4;
        Random rnd = new Random();
        point.x = point.x  + rnd.nextInt(STEP * 2) - STEP;
        point.y = point.y  + rnd.nextInt(STEP * 2) - STEP;
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
        flashCrash();
    }

    private void assaMove(Point[] mouseCoord) throws InterruptedException, FlashCrashException {
        Point[] aPoint = mouseCoord;
        for (int i = 0; i < aPoint.length; i++) {
            moveMouseAndClick(aPoint[i]);
            Thread.sleep(2000);
        }
        robot.mouseMove(960, 500);
    }

    private void getBonus() throws InterruptedException, FlashCrashException{
        String bonuses = "";
        bonuses = checkBonus();

        for (int i = 0; i < 3; i++) {
            if (!(atStation() || inAssa())) {
                return;
            }
            if (bonuses.contains(String.valueOf(i + 1))) {
                moveMouseAndClick(aPointBonus[i]);
                Thread.sleep(1500);
                checkModalWindow();
            }
        }
    }

    private void getBonusInAssa(String mode) throws InterruptedException, FlashCrashException {
        Point nextPlayer = new Point(1865, 1030);
        BufferedImage currentPlayer = null;
        int counter = 0;
        Thread.sleep(3000);
        BufferedImage firstPlayer = robot.createScreenCapture(new Rectangle(45, 105, 225, 55));
        Thread.sleep(1000);
        do {
            if (!inAssa()) {
                return;
            }
            counter++;
            messages.add("Бонус: " + checkBonus() + "\tВидео: " + checkAdv());
            display.showMessages(messages);
            getBonus();
            if (mode.equalsIgnoreCase("Video")) {
                viewVideo();
                getBonus();
            }

            moveMouseAndClick(nextPlayer);
            Thread.sleep(5000);
            currentPlayer = robot.createScreenCapture(new Rectangle(45, 105, 225, 55));
            if (counter > assaSize) {
                break;
            }
        } while (!imagesAreEqual(firstPlayer, currentPlayer));
        assaMove(aPointAssaOut);
        Thread.sleep(3000);
    }

    private void viewVideo() throws InterruptedException, FlashCrashException{
        String videos = "";
        videos = checkAdv();
        final Point advClose = new Point(960, 680);
        final Point advBonus = new Point(975, 760);
        final Point advCrestik = new Point(1180, 375);

        for (int i = 0; i < 3; i++) {
            if (!(atStation() || inAssa())) {
                return;
            }
            if (videos.contains(String.valueOf(i + 1))) {
                moveMouseAndClick(aPointAdv[i]);
                do {
                    Thread.sleep(3000);
                } while (imagesAreEqual(robot.createScreenCapture(new Rectangle(1258, 320, 32, 32)), crest));
                Thread.sleep(3000);
                if (imagesAreEqual(robot.createScreenCapture(new Rectangle(1165, 356, 32, 32)), advCrest)) {
                    moveMouseAndClick(advClose);
                } else {
                    moveMouseAndClick(advBonus);
                    do {
                        Thread.sleep(3000);
                    } while (imagesAreEqual(robot.createScreenCapture(new Rectangle(1258, 320, 32, 32)), crest));
                    moveMouseAndClick(advCrestik);
                }
                Thread.sleep(3000);
            }
        }
    }

    private void checkModalWindow() throws InterruptedException, FlashCrashException{
        Point closeModalWindow = new Point(1155, 385);
        if (imagesAreEqual(robot.createScreenCapture(new Rectangle(1142, 371, 32, 32)), crest)) {
            moveMouseAndClick(closeModalWindow);
            Thread.sleep(3000);
        }
    }

    private void flashCrash() throws InterruptedException, FlashCrashException{
        int black = RN_BLACK.getRGB();
        //int black = RN_BLACK.getRGB();
        Point restartFlash = new Point(310, 18);
        Point openStation = new Point(840, 1040);
        if (robot.getPixelColor(400, 250).getRGB() == black
                && robot.getPixelColor(1500, 250).getRGB() == black
                && robot.getPixelColor(400, 850).getRGB() == black
                && robot.getPixelColor(1500, 20).getRGB() == new Color(252, 235, 162).getRGB()) {
            messages.add("Рестарт");
            display.showMessages(messages);
            moveMouseAndClick(restartFlash);
            Thread.sleep(40000);
            moveMouseAndClick(openStation);
            Thread.sleep(10000);
            robot.mouseMove(1070, 425);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseMove(600, 300);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            throw new FlashCrashException();
        }
    }

    private void antiSleep() throws InterruptedException {
        int x = MouseInfo.getPointerInfo().getLocation().x;
        int y = MouseInfo.getPointerInfo().getLocation().y;
        Thread.sleep(5000);
        if (x == MouseInfo.getPointerInfo().getLocation().x && y == MouseInfo.getPointerInfo().getLocation().y) {
            robot.mouseMove(x + 50, y);
            Thread.sleep(500);
            robot.mouseMove(x, y);
        }
    }

    private boolean atStation () {
        return imagesAreEqual(robot.createScreenCapture(new Rectangle(823, 1018, 36, 24)), stationButton);
    }

    private boolean inAssa () {
        return imagesAreEqual(robot.createScreenCapture(new Rectangle(1826, 19, 72, 32)), stationAssaButton);
    }

    private void showMyBonuses() {
        messages.add("Бонус: " + checkBonus() + "\tВидео: " + checkAdv());
        display.showMessages(messages);
    }

    private void checkErrorBuilding() throws FlashCrashException, InterruptedException {
        Point closeModalWindow = new Point(1155, 385);
        if (imagesAreEqual(robot.createScreenCapture(new Rectangle(1142, 371, 32, 32)), crest)) {
            moveMouseAndClick(closeModalWindow);
            Thread.sleep(3000);
            if (inAssa()) {
                assaMove(aPointAssaOut);
                Thread.sleep(2000);
            }
        }
    }

}
