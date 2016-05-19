package com.moonabyss.rn;

import com.moonabyss.FixedQueue;
import com.moonabyss.FixedStringQueueWithDate;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by uncle on 12.05.2016.
 */
public class MySwingForm extends JFrame implements MyDisplay {

    private static final String NEW_LINE = "\r\n";
    private static final String SPACE = " ";
    private Color bgColor;
    public MyImagePanel image = new MyImagePanel();
    private JTextArea textArea;

    public MySwingForm() {
        super("RN bot");
        setBounds(355, 10, 400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        //add(image);

        textArea = new JTextArea(10, 40);
        textArea.setEditable(false);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);
        add(textArea);

        setVisible(true);
    }

    public void showMessages(FixedStringQueueWithDate<String> messages) {
        textArea.setText("");
        for (String str : messages) {
            textArea.append(str+NEW_LINE);
            //setTitle(str);
        }

        /** grab image
        try {
            image.setImage(new Robot().createScreenCapture(new Rectangle(1165, 356, 32, 32)));
            ImageIO.write(new Robot().createScreenCapture(new Rectangle(823, 1018, 36, 24)),"bmp", new File("station.bmp"));
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**/
    }

}
