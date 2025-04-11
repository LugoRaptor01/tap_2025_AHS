package com.example.demo2.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class Rompecabezas extends Stage {

    private Scene escena;

    private Button btnTamano;
    private Button btnMezclar;

    private VBox root;
    private VBox contenedorPiezas = new VBox(10);

    private HBox botones;
    private HBox contenedorPuzzle;

    private GridPane grid;

    private List<ImageView> piezas = new ArrayList<>();
    private List<ImageView> piezasOrdenadas = new ArrayList<>();

    private int filas, columnas;
    private long tiempoInicio;

    private double anchoPieza = 150;
    private double altoPieza = 100;

    private Timeline temporizador;

    private Button btnEmpezar;
    private Button btnTerminar;

    private Label lblTiempo;

    private ScrollPane scrollPiezas;

    private ImageView imagenReferencia;

    private boolean juegoEnCurso = false;

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
        this.setTitle("Rompecabezas");
        this.setScene(escena);
        this.setMaximized(true);
        this.show();
    }

    private void CrearUI() {
        btnMezclar = new Button("Mezclar");
        btnTamano = new Button("Tamaño");
        btnEmpezar = new Button("Empezar");
        btnTerminar = new Button("Terminar");
        lblTiempo = new Label("Tiempo: 0s");

        botones = new HBox(10, btnTamano, btnMezclar, btnEmpezar, btnTerminar, lblTiempo);
        botones.setPadding(new Insets(10));
        botones.setAlignment(Pos.CENTER);

        contenedorPuzzle = new HBox(20);
        contenedorPuzzle.setPadding(new Insets(10));
        contenedorPuzzle.setAlignment(Pos.CENTER);

        root = new VBox(10);
        root.getChildren().addAll(botones, contenedorPuzzle);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10));

        escena = new Scene(root, 1200, 800);

        btnTamano.setOnAction(e -> mostrarSelectorImagenes());
        btnMezclar.setOnAction(e -> mezclarPiezas());
        btnEmpezar.setOnAction(e -> iniciarTemporizador());
        btnTerminar.setOnAction(e -> terminarJuego());
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
            calcularTamanoCelda();

            if (temporizador != null) {
                temporizador.stop();
                temporizador = null;
            }
            lblTiempo.setText("Tiempo: 0s");

            cargarRompecabezas();
        });
    }

    private void calcularTamanoCelda() {
        double maxAnchoGrid = 800; // ajusta si quieres más pequeño
        double maxAltoGrid = 600;

        anchoPieza = maxAnchoGrid / columnas;
        altoPieza = maxAltoGrid / filas;
    }

    // Reemplaza el metodo cargarRompecabezas con esta nueva versión
    private void cargarRompecabezas() {
        piezas.clear();
        piezasOrdenadas.clear();
        contenedorPuzzle.getChildren().clear();
        juegoEnCurso = false;

        if (temporizador != null) {
            temporizador.stop();
            lblTiempo.setText("Tiempo: 0s");
        }

        VBox contenedorReferencia = new VBox(5);
        contenedorReferencia.setAlignment(Pos.CENTER);
        contenedorReferencia.setPadding(new Insets(10));

        String pathReferencia = "/" + rutaImagenes.get(imagenSeleccionada) + "/completa.jpg";
        URL urlReferencia = getClass().getResource(pathReferencia);
        if (urlReferencia != null) {
            Image imgRef = new Image(urlReferencia.toExternalForm());
            imagenReferencia = new ImageView(imgRef);
            imagenReferencia.setPreserveRatio(true);
            imagenReferencia.setFitWidth(300);
            contenedorReferencia.getChildren().addAll(
                    new Label("Imagen de referencia:"),
                    imagenReferencia,
                    new Label("Arrastra las piezas al área de juego")
            );
        }

        grid = new GridPane();
        grid.setPadding(new Insets(2));
        grid.setHgap(1);
        grid.setVgap(1);
        grid.setAlignment(Pos.CENTER);
        grid.setGridLinesVisible(true);

        contenedorPiezas = new VBox(5);
        contenedorPiezas.setAlignment(Pos.TOP_CENTER);
        contenedorPiezas.setPadding(new Insets(10));

        scrollPiezas = new ScrollPane(contenedorPiezas);
        scrollPiezas.setFitToWidth(true);
        scrollPiezas.setPrefViewportHeight(600);
        scrollPiezas.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        String carpeta = rutaImagenes.get(imagenSeleccionada);

        for (int fila = 0; fila < filas; fila++) {
            for (int col = 0; col < columnas; col++) {
                String path = "/" + carpeta + "/" + (fila + 1) + "-" + (col + 1) + ".jpg";
                URL url = getClass().getResource(path);
                if (url == null) {
                    System.err.println("No se encontró la imagen: " + path);
                    continue;
                }

                Image img = new Image(url.toExternalForm());
                ImageView imgView = new ImageView(img);
                imgView.setPreserveRatio(false);
                imgView.setFitWidth(anchoPieza);
                imgView.setFitHeight(altoPieza);

                configurarArrastrar(imgView);

                piezas.add(imgView);
                piezasOrdenadas.add(imgView);
            }
        }

        for (int fila = 0; fila < filas; fila++) {
            for (int col = 0; col < columnas; col++) {
                StackPane celda = crearCelda();
                grid.add(celda, col, fila);
            }
        }

        Collections.shuffle(piezas);
        contenedorPiezas.getChildren().clear();

        for (ImageView pieza : piezas) {
            ImageView imgView = new ImageView(pieza.getImage());
            imgView.setFitWidth(anchoPieza);
            imgView.setFitHeight(altoPieza);
            imgView.setPreserveRatio(false);
            configurarArrastrar(imgView);
            contenedorPiezas.getChildren().add(imgView);
        }

        HBox panelJuego = new HBox(20);
        panelJuego.setAlignment(Pos.CENTER);
        panelJuego.getChildren().addAll(scrollPiezas, grid);

        contenedorPuzzle.getChildren().addAll(contenedorReferencia, panelJuego);
    }


    private StackPane crearCelda() {
        StackPane celda = new StackPane();
        celda.setStyle("-fx-background-color: #2b2b2b; -fx-border-color: #444; -fx-border-width: 1;");

        celda.setMinSize(anchoPieza, altoPieza);
        celda.setPrefSize(anchoPieza, altoPieza);

        celda.setOnDragOver(event -> {
            if (event.getGestureSource() != celda && event.getDragboard().hasImage()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        celda.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasImage() && celda.getChildren().isEmpty()) {
                ImageView source = (ImageView) event.getGestureSource();
                ImageView nueva = new ImageView(db.getImage());
                nueva.setFitWidth(anchoPieza);
                nueva.setFitHeight(altoPieza);
                nueva.setPreserveRatio(false);

                celda.getChildren().add(nueva);
                contenedorPiezas.getChildren().remove(source);

                if (juegoEnCurso) verificarGanador();

                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
            event.consume();
        });

        return celda;
    }

    private void configurarArrastrar(ImageView imgView) {
        imgView.setOnDragDetected(event -> {
            Dragboard db = imgView.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putImage(imgView.getImage());
            db.setContent(content);
            db.setDragView(imgView.getImage());
            event.consume();
        });
    }

    private void mezclarPiezas() {
        if (piezas.isEmpty()) {
            mostrarAlerta("Error", "Selecciona primero una imagen.");
            return;
        }

        grid.getChildren().clear();
        for (int fila = 0; fila < filas; fila++) {
            for (int col = 0; col < columnas; col++) {
                StackPane celda = crearCelda();
                grid.add(celda, col, fila);
            }
        }

        Collections.shuffle(piezas);
        contenedorPiezas.getChildren().clear();

        for (ImageView pieza : piezas) {
            ImageView imgView = new ImageView(pieza.getImage());
            imgView.setFitWidth(anchoPieza);
            imgView.setFitHeight(altoPieza);
            imgView.setPreserveRatio(false);
            configurarArrastrar(imgView);
            contenedorPiezas.getChildren().add(imgView);
        }

        juegoEnCurso = true;
    }

    private void verificarGanador() {
        boolean ganado = true;

        // Verificar posición correcta de cada pieza
        for (int fila = 0; fila < filas; fila++) {
            for (int col = 0; col < columnas; col++) {
                Node node = getNodeByRowColumnIndex(fila, col, grid);
                if (!(node instanceof StackPane)) {
                    ganado = false;
                    break;
                }

                StackPane celda = (StackPane) node;
                if (celda.getChildren().isEmpty()) {
                    ganado = false;
                    break;
                }

                // Obtener la pieza que debería estar en esta posición
                int posicionCorrecta = fila * columnas + col;
                Image imagenCorrecta = piezasOrdenadas.get(posicionCorrecta).getImage();

                ImageView piezaActual = (ImageView) celda.getChildren().get(0);
                if (!piezaActual.getImage().equals(imagenCorrecta)) {
                    ganado = false;
                    break;
                }
            }
            if (!ganado) break;
        }

        if (ganado) {
            terminarJuego(true);
            // Resaltar el grid para confirmar
            grid.setStyle("-fx-border-color: green; -fx-border-width: 3px;");
        }
    }

    private void iniciarTemporizador() {
        if (!juegoEnCurso) {
            mostrarAlerta("Aviso", "Primero debes mezclar las piezas para comenzar el juego.");
            return;
        }

        tiempoInicio = System.currentTimeMillis();
        temporizador = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            long ahora = System.currentTimeMillis();
            long segundos = (ahora - tiempoInicio) / 1000;
            lblTiempo.setText("Tiempo: " + segundos + "s");
        }));
        temporizador.setCycleCount(Timeline.INDEFINITE);
        temporizador.play();
    }

    private void terminarJuego() {
        terminarJuego(false);
    }

    private void terminarJuego(boolean esGanador) {
        if (temporizador != null) {
            temporizador.stop();
        }

        if (!juegoEnCurso) {
            mostrarAlerta("Aviso", "No hay un juego en curso.");
            return;
        }

        juegoEnCurso = false;
        long tiempoTotal = (System.currentTimeMillis() - tiempoInicio) / 1000;

        if (esGanador) {
            mostrarAlerta("¡Felicidades!", "¡Completaste el rompecabezas en " + tiempoTotal + " segundos!");
        } else {
            boolean completado = verificarCompletado();
            if (completado) {
                mostrarAlerta("Resultado", "¡Completaste el rompecabezas en " + tiempoTotal + " segundos!");
            } else {
                int piezasColocadas = contarPiezasColocadas();
                mostrarAlerta("Resultado",
                        "Juego terminado.\n" +
                                "Tiempo: " + tiempoTotal + " segundos\n" +
                                "Piezas colocadas: " + piezasColocadas + " de " + (filas * columnas) + "\n" +
                                "Porcentaje completado: " + (piezasColocadas * 100 / (filas * columnas)) + "%");
            }
        }
    }

    private boolean verificarCompletado() {
        int index = 0;
        for (int fila = 0; fila < filas; fila++) {
            for (int col = 0; col < columnas; col++) {
                Node node = getNodeByRowColumnIndex(fila, col, grid);
                if (!(node instanceof StackPane) || ((StackPane)node).getChildren().isEmpty()) {
                    return false;
                }
                index++;
            }
        }
        return true;
    }

    private int contarPiezasColocadas() {
        int count = 0;
        for (Node node : grid.getChildren()) {
            if (node instanceof StackPane && !((StackPane)node).getChildren().isEmpty()) {
                count++;
            }
        }
        return count;
    }

    private Node getNodeByRowColumnIndex(int row, int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }
}