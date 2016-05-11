package com.moonabyss.rn;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by uncle on 11.05.2016.
 */
public class MyImagePanel extends JPanel {

    private BufferedImage image;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, null);
        }
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }
}
