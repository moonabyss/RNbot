package com.moonabyss;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by uncle on 10.05.2016.
 */
public class MyApp extends JFrame {
    Color bgColor;
    MyImagePanel image = new MyImagePanel();
    BufferedImage signCross = null;

    public MyApp() {
        super("RN bot");
        setBounds(800, 100, 200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        add(image);

        try {
            signCross = ImageIO.read(getClass().getResource("/img/advCrest.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
