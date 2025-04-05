package com.example.demo2.componentes;

import javafx.scene.control.ProgressBar;

import javax.swing.*;
import java.util.Random;

public class Hilo extends Thread{

    private ProgressBar pgbRuta;

    public Hilo (String nombre, ProgressBar pgb){

        this.pgbRuta = pgb;
    }

    @Override
    public void run() {

        super.run();
        double avance = 0.0;

        while (avance < 1){

            avance += (Math.random()*0.01);
            this.pgbRuta.setProgress(avance);
            System.out.println(avance);

            try {
                sleep((long)Math.random()*10000000);
            } catch (InterruptedException e) {
                //throw new RuntimeException(e);
            }
        }
    }
}