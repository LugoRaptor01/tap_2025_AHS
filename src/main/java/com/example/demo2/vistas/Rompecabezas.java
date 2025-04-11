package com.example.demo2.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class Rompecabezas extends Stage {

    private Scene escena;
    private Button btnTamano;
    private Button btnMezclar;
    private VBox root;
    private HBox botones;
    private HBox contenedorPuzzle;
    private GridPane grid;
    private List<ImageView> piezas = new ArrayList<>();
    private int filas, columnas;

    // Mapeo de nombres visibles -> rutas reales y tamaños
    private final Map<String, String> rutaImagenes = Map.of(
            "Mila3x3", "Mila3x3",
            "Su33D5x5", "Su33D5x5",
            "FordGTLM5x8", "FordGTLM5x8"
    );

    private final Map<String, int[]> tamanos = Map.of(
            "Mila3x3", new int[]{3, 3},
            "Su33D5x5", new int[]{5, 5},
            "FordGTLM5x8", new int[]{5, 8}
    );

    private String imagenSeleccionada;

    public Rompecabezas() {
        CrearUI();
        this.setTitle("Rompecabezas con Imágenes");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        btnMezclar = new Button("Mezclar");
        btnTamano = new Button("Tamaño");

        botones = new HBox(10, btnTamano, btnMezclar);
        botones.setPadding(new Insets(10));
        botones.setAlignment(Pos.CENTER);

        contenedorPuzzle = new HBox();
        contenedorPuzzle.setPadding(new Insets(10));
        contenedorPuzzle.setAlignment(Pos.CENTER);

        root = new VBox();
        root.getChildren().addAll(botones, contenedorPuzzle);

        escena = new Scene(root, 1080, 720);

        // Eventos
        btnTamano.setOnAction(e -> mostrarSelectorImagenes());
        btnMezclar.setOnAction(e -> mezclarPiezas());
    }

    private void mostrarSelectorImagenes() {
        List<String> opciones = new ArrayList<>(rutaImagenes.keySet());
        ChoiceDialog<String> dialog = new ChoiceDialog<>(opciones.get(0), opciones);
        dialog.setTitle("Seleccionar imagen");
        dialog.setHeaderText("Elige una imagen para el rompecabezas");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(nombre -> {
            imagenSeleccionada = nombre;
            int[] tam = tamanos.get(nombre);
            filas = tam[0];
            columnas = tam[1];
            cargarRompecabezas();
        });
    }

    private void cargarRompecabezas() {
        grid = new GridPane();
        grid.setPadding(new Insets(0));
        grid.setHgap(5);
        grid.setVgap(5);
        piezas.clear();

        String carpeta = rutaImagenes.get(imagenSeleccionada);

        for (int fila = 0; fila < filas; fila++) {
            for (int col = 0; col < columnas; col++) {
                String path = "/" + carpeta + "/" + (fila + 1) + "-" + (col + 1) + ".jpg";
                System.out.println("Buscando: " + path);
                URL url = getClass().getResource(path);

                if (url == null) {
                    System.err.println("No se encontró la imagen: " + path);
                    continue;
                }

                Image img = new Image(url.toExternalForm());
                ImageView imgView = new ImageView(img);
                imgView.setFitWidth(720.0 / columnas);
                imgView.setFitHeight(720.0 / filas);
                imgView.setPreserveRatio(true);

                piezas.add(imgView);
                grid.add(imgView, col, fila);
            }
        }

        contenedorPuzzle.getChildren().clear();
        contenedorPuzzle.getChildren().add(grid);
    }


    private void mezclarPiezas() {
        if (piezas.isEmpty()) {
            System.out.println("Selecciona primero una imagen.");
            return;
        }

        Collections.shuffle(piezas);
        grid.getChildren().clear();

        int index = 0;
        for (int fila = 0; fila < filas; fila++) {
            for (int col = 0; col < columnas; col++) {
                grid.add(piezas.get(index++), col, fila);
            }
        }
    }
}
