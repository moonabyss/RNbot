package com.moonabyss.rn;

public class Main {

    public static void main(String[] args) {
        Runnable r = ()->{MyApp app = new MyApp(); if (args[0].equalsIgnoreCase("party=no")) {app.party = false;} app.doJob();};
        new Thread(r).start();
    }

}
