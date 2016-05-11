package com.moonabyss;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    final static Color RN_GREEN = new Color(37, 136, 69);

    public static void main(String[] args) {
        MyApp app = new MyApp();
        app.setVisible(true);
        BufferedImage bi;
        BufferedImage advCrest = app.signCross;

        //for (int i = 0; i < 10; i++) {
        while (true) {
            try {
                int x = MouseInfo.getPointerInfo().getLocation().x;
                int y = MouseInfo.getPointerInfo().getLocation().y;
                //app.setTitle(x+" "+y);
                /*for (int i = 1790; i < 1820; i++) {
                    for (int j = 487; j < 500; j++) {
                        Color color = new Robot().getPixelColor(i, j);
                        if (color.getRGB() == RN_GREEN.getRGB()) {
                            System.out.print(color);
                            System.out.println(" "+i+" "+j);
                        }
                    }
                }*/
                bi = new Robot().createScreenCapture(new Rectangle(1258, 331, 32, 32));
                app.image.setImage(bi);
                app.setTitle(String.valueOf(imagesAreEqual(bi, advCrest)));
                //ImageIO.write(bi,"png", new File(i+".png"));
                //app.getContentPane().setBackground(color);
                //System.out.println(color);
                //app.setTitle(checkAdv());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (AWTException e) {
                e.printStackTrace();
            }

        }
    }

    public static String checkAdv() {
        Color color;
        StringBuilder sb =new StringBuilder();
        Point[] aPoint = {new Point(1260, 725), new Point(1610, 639), new Point(1800, 496)};
        for (int i = 0; i < 3; i++) {
            try {
                color = new Robot().getPixelColor(aPoint[i].x, aPoint[i].y);
                if (color.getRGB() == RN_GREEN.getRGB()) {
                    sb.append(i+1).append(' ');
                } else {
                    sb.append('-').append(' ');
                }
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }

    public static boolean imagesAreEqual(BufferedImage image1, BufferedImage image2) {
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
}
