package com.moonabyss.rn;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args) {
        Runnable r = ()->{MyApp app = new MyApp(); app.doJob();};
        new Thread(r).start();

        //BufferedImage bi;
        //BufferedImage advCrest = app.signCross;

        //for (int i = 0; i < 10; i++) {
        while (true) {
            try {
                //int x = MouseInfo.getPointerInfo().getLocation().x;
                //int y = MouseInfo.getPointerInfo().getLocation().y;
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
                //bi = new Robot().createScreenCapture(new Rectangle(1258, 331, 32, 32));
                //app.image.setImage(bi);
                //app.setTitle(String.valueOf(imagesAreEqual(bi, advCrest)));
                //ImageIO.write(bi,"png", new File(i+".png"));
                //app.getContentPane().setBackground(color);
                //System.out.println(color);
                //app.setTitle(checkAdv());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
