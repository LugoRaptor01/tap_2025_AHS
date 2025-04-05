package com.example.demo2.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class calculadora extends Stage {

    private Scene escena;
    private TextField txtDisplay;
    private VBox vBox;
    private GridPane gdpKeyboard;
    private Button[][] arBtnTeclado;

    private String operador = "";
    private double operando1 = 0;
    private boolean banInicio = true;

    private String[] strTeclas = {
            "7", "4", "1", "0", "8", "5", "2", ".",
            "9", "6", "3", "=", "/", "*", "-", "+"
    };

    public calculadora() {

        CrearUI();

        this.setScene(escena);
        this.setTitle("Calculadora");
        this.show();
    }

    private void CrearUI() {

        crearKeyboard();

        txtDisplay = new TextField("0.0");
        txtDisplay.setEditable(false);
        txtDisplay.setAlignment(Pos.BASELINE_RIGHT);

        vBox = new VBox(txtDisplay, gdpKeyboard);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));

        escena = new Scene(vBox, 250, 250);
        escena.getStylesheets().add(getClass().getResource("/styles/calculadora.css").toExternalForm());
    }

    private void crearKeyboard() {

        arBtnTeclado = new Button[4][4];

        gdpKeyboard = new GridPane();
        gdpKeyboard.setHgap(10);
        gdpKeyboard.setVgap(10);

        int pos = 0;

        for (int renglon = 0; renglon < 4; renglon++) {

            for (int columna = 0; columna < 4; columna++) {

                String tecla = strTeclas[pos];

                Button btn = new Button(tecla);
                btn.setPrefSize(50, 50);
                btn.setOnAction(event -> EventoTeclado(tecla));

                arBtnTeclado[renglon][columna] = btn;
                gdpKeyboard.add(btn, renglon, columna);
                pos++;

            }
        }
    }

    private void EventoTeclado(String strTecla) {

        if (esErrorEnDisplay()) {
            txtDisplay.setText("0.0");
            txtDisplay.setAlignment(Pos.BASELINE_RIGHT); // Volver a alinear a la derecha
            operador = "";
            banInicio = true;
        }

        if (strTecla.matches("[0-9]")) {

            if (banInicio) {

                txtDisplay.setText(strTecla);
                banInicio = false;

            } else {

                txtDisplay.appendText(strTecla);
            }

        } else if (strTecla.equals(".")) {

            String textoActual = txtDisplay.getText();

            if (banInicio) {

                txtDisplay.setText("0.");
                banInicio = false;
                return;
            }

            if (textoActual.contains(".")) {

                mostrarError("Error: num inválido. Presiona =.");
                return;
            }

            txtDisplay.appendText(".");

        } else if (strTecla.matches("[-+*/]")) {

            if (!operador.isEmpty()) {

                mostrarError("Error: formato inválido. Presiona =.");
                return;
            }

            String textoActual = txtDisplay.getText();

            if (!esNumeroValido(textoActual)) {

                mostrarError("Error: num inválido. Presiona =..");
                return;
            }

            operador = strTecla;
            operando1 = Double.parseDouble(textoActual);

            banInicio = true;

        } else if (strTecla.equals("=")) {

            if (operador.isEmpty()) {

                return;
            }

            String textoActual = txtDisplay.getText();

            if (!esNumeroValido(textoActual)) {
                mostrarError("Error: num inválido. Presiona =.");
                return;
            }

            double operando2 = Double.parseDouble(textoActual);
            double resultado = realizarOperacion(operando1, operando2, operador);

            if (Double.isNaN(resultado)) {
                // Error ya mostrado dentro de realizarOperacion
            } else {

                txtDisplay.setText(String.valueOf(resultado));
                operando1 = resultado;
            }

            operador = "";
            banInicio = true;
        }
    }

    private double realizarOperacion(double operando1, double operando2, String operador) {

        double resultado = 0.0;

        switch (operador) {

            case "+":
                resultado = operando1 + operando2;
                break;

            case "-":

                resultado = operando1 - operando2;
                break;

            case "*":

                resultado = operando1 * operando2;
                break;

            case "/":

                if (operando2 == 0) {

                    mostrarError("Error: div 0. Presiona =.");
                    return Double.NaN;

                } else {

                    resultado = operando1 / operando2;
                }

                break;
        }

        return resultado;
    }

    private boolean esNumeroValido(String texto) {

        return texto.matches("-?\\d*(\\.\\d+)?");
    }

    private boolean esErrorEnDisplay() {

        return txtDisplay.getText().startsWith("Error");
    }

    private void mostrarError(String mensaje) {

        txtDisplay.setAlignment(Pos.BASELINE_LEFT); // Mostrar el error desde la izquierda
        txtDisplay.setText(mensaje);

        operador = "";
        banInicio = true;
    }
}