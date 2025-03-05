package com.example.demo2.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Rompecabezas extends Stage {

    private Scene escena;

    private Button btnTamano;
    private Button btnMezclar;
    //private Button btn;

    private VBox vBox;

    public Rompecabezas(){

        CrearUI();

        this.setTitle("Rompecabezas");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI(){

        vBox = new VBox(btnMezclar);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        btnMezclar = new Button("Comenzar");

        escena = new Scene(vBox, 800, 600);
    }
}