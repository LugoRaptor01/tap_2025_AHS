package com.example.demo2.vistas;

import com.example.demo2.componentes.Hilo;
import com.google.protobuf.ValueOrBuilder;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Celayork  extends Stage {

    private Scene escena;
    private VBox vBox;
    private GridPane gdpCalles;
    private Button btnIniciar;

    private Label[] lblRutas;
    private ProgressBar[] pgbRutas;
    private String[] strRutas = {"Ruta Pinos 60", "Ruta Laureles 46", "Ruta Delicias 40", "Ruta Puente 10", "Ruta Laja 13"};

    private Hilo[] thrRutas;

    public Celayork () {

        crearUI();

        this.setTitle("Boulevard Adolfo Lopez Mateos");
        this.setScene(escena);
        this.show();

    }

    public void crearUI(){

        btnIniciar = new Button("La salida, la salida!!!");

        pgbRutas = new ProgressBar[5];
        lblRutas = new Label[5];
        thrRutas = new Hilo[5];
        gdpCalles = new GridPane();

        for (int i = 0; i < pgbRutas.length; i++){

            lblRutas[i] = new Label(strRutas[i]);
            pgbRutas[i] = new ProgressBar(0);
            thrRutas[i] = new Hilo(strRutas[i], pgbRutas[i]);

            gdpCalles.add(lblRutas[i],  0, i);
            gdpCalles.add(pgbRutas[i], 1, i);

        }

        btnIniciar.setOnAction(event -> {
            for (int i = 0; i < pgbRutas.length; i++){

                thrRutas[i].start();
            }
        });

        vBox = new VBox(gdpCalles, btnIniciar);

        escena = new Scene(vBox, 300, 200);
    }
}
