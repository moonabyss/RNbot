package com.moonabyss.rn;

public class Main {

    public static void main(String[] args) {
        boolean param1;
        int param2;
        if (args.length != 2) {
            param1 = true;
            param2 = 5;
        } else {
            //param1
            if (args[0].contains("solo=yes")) {
                param1 = true;
            } else {
                param1 = false;
            }
            //param2
            if (args[1].contains("assa")) {
                String[] aParam2 = args[1].split("=");
                try {
                    param2 = Integer.parseInt(aParam2[1]);
                } catch (NumberFormatException e) {
                    param2 = 5;
                }
            } else {
                param2 = 5;
            }
        }
        final int FPARAM2 = param2;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                MyApp app = new MyApp();
                app.solo = param1;
                app.assaSize = FPARAM2;
                app.doJob();
            }
        };
        new Thread(r).start();
    }

}
