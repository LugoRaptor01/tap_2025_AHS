package com.example.demo2.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class Rompecabezas extends Stage {

    private Scene escena;
    private Button btnTamano;
    private Button btnMezclar;
    private VBox root;
    private HBox botones;
    private HBox contenedorPuzzle;
    private GridPane grid;

    public Rompecabezas() {
        CrearUI();

        this.setTitle("Rompecabezas con Imágenes");
        this.setScene(escena);
        this.show();

    }

    private void CrearUI() {
        // Botones
        btnMezclar = new Button("Comenzar");
        btnTamano = new Button("Tamaño");

        botones = new HBox(10, btnMezclar, btnTamano);
        botones.setPadding(new Insets(10));

        // GridPane para piezas
        grid = new GridPane();
        grid.setPadding(new Insets(0));
        grid.setHgap(5);
        grid.setVgap(5);

        int filas = 3;
        int columnas = 3;

        for (int fila = 0; fila < filas; fila++) {
            for (int col = 0; col < columnas; col++) {
                String path = "/assets_rompecabezas_1/" + (fila + 1) + "-" + (col + 1) + ".png";
                URL url = Rompecabezas.class.getResource(path);

                if (url == null) {
                    System.err.println("No se encontró la imagen: " + path);
                    continue;
                }

                Image img = new Image(url.toExternalForm());
                ImageView imgView = new ImageView(img);

                // Ajustar el tamaño de las piezas
                imgView.setFitWidth(240); // Tamaño más grande
                imgView.setFitHeight(240); // Tamaño más grande
                imgView.setPreserveRatio(true);

                grid.add(imgView, col, fila);
            }
        }

        // HBox para contener el grid y centrarlo
        contenedorPuzzle = new HBox(grid);
        contenedorPuzzle.setPadding(new Insets(10));
        contenedorPuzzle.setAlignment(Pos.CENTER); // Centrado del grid

        // VBox raíz
        root = new VBox();
        root.getChildren().addAll(botones, contenedorPuzzle);

        escena = new Scene(root, 1080, 720);
    }
}