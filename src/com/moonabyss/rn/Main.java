package com.moonabyss.rn;

public class Main {

    public static void main(String[] args) {

        final MyParams myParams = new MyParams();

        for (int i = 0; i < args.length; i++) {
            String[] params = args[i].split("=");

            // solo/assa mode
            if (params[0].equalsIgnoreCase("solo")){
                if (params[1].contains("yes") || params[1].contains("true")){
                    myParams.solo = true;
                } else {
                    myParams.solo = false;
                }
            }

            //assa's size
            if (params[0].equalsIgnoreCase("assa")){
                try {
                    myParams.size = Integer.parseInt(params[1]);
                } catch (NumberFormatException e) {
                    myParams.size = 5;
                }
            }

            // watch video
            if (params[0].equalsIgnoreCase("video")){
                if (params[1].contains("yes") || params[1].contains("true")){
                    myParams.video = true;
                } else {
                    myParams.video = false;
                }
            }

            //video duration
            if (params[0].equalsIgnoreCase("videoduration")){
                try {
                    myParams.videoDuration = Integer.parseInt(params[1]);
                } catch (NumberFormatException e) {
                    myParams.videoDuration = 80;
                }
            }

            // catch bonuses in assa during night
            if (params[0].equalsIgnoreCase("safenight")){
                if (params[1].contains("yes") || params[1].contains("true")){
                    myParams.safeNight = true;
                } else {
                    myParams.safeNight = false;
                }
            }

            //night duration
            if (params[0].equalsIgnoreCase("nightstart")){
                try {
                    myParams.nightStart = Integer.parseInt(params[1]);
                } catch (NumberFormatException e) {
                    myParams.nightStart = 2;
                }
            }
            if (params[0].equalsIgnoreCase("nightend")){
                try {
                    myParams.nightEnd = Integer.parseInt(params[1]);
                } catch (NumberFormatException e) {
                    myParams.nightEnd = 8;
                }
            }

            // debug log
            if (params[0].equalsIgnoreCase("debug")){
                if (params[1].contains("yes") || params[1].contains("true")){
                    myParams.debug = true;
                } else {
                    myParams.debug = false;
                }
            }
        }

        Runnable r = () -> {MyApp app = new MyApp(); app.setParams(myParams); app.doJob();};
        new Thread(r).start();
    }

}

class MyParams {
    boolean solo = false;
    boolean video = true;
    boolean safeNight = false;
    boolean debug = false;
    int size = 5;
    int videoDuration = 50;
    int nightStart = 2;
    int nightEnd = 8;
}