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
    //private double operando2 = 0;
    //private double resultado;
    //private int contOperador = 0;
    private boolean banInicio = true;

    private String[] strTeclas = {"7", "4", "1", "0", "8", "5", "2", ".",
                                                     "9", "6", "3", "=", "/", "*", "-", "+"};

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

        escena = new Scene(vBox, 200, 250);

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

                arBtnTeclado[renglon][columna] = new Button(tecla);
                arBtnTeclado[renglon][columna].setPrefSize(50, 50);
                arBtnTeclado[renglon][columna].setOnAction(event -> EventoTeclado(tecla));

                gdpKeyboard.add(arBtnTeclado[renglon][columna], renglon, columna);
                pos++;
            }
        }
    }

    private void EventoTeclado(String strTecla) {

        if (txtDisplay.getText().startsWith("Error")) { //detecta si hay un error actualmente en el display y se borra
                                                                                      //presionando cualquier tecla
            txtDisplay.setText("0.0");
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
        } else if (strTecla.equals(".")) { // error para cuando hay más de 2 puntos por operando-

            if (txtDisplay.getText().contains(".") && !banInicio) {

                txtDisplay.setText("Error: número inválido");
                return;
            }

            txtDisplay.appendText(".");
            banInicio = false;

        } else if (strTecla.matches("[-+*/]")) {

            if (!operador.isEmpty()) { //error por si hay dos operadores al mismo tiempo.

                txtDisplay.setText("Error: formato inválido");
                return;

            }

            operador = strTecla;

            operando1 = Double.parseDouble(txtDisplay.getText());

            banInicio = true; // Permite escribir el siguiente número desde cero

        } else if (strTecla.equals("=")) {

            if (operador.isEmpty()){

                return; // Si no hay operador, no hacer nada
            }

            double operando2 = Double.parseDouble(txtDisplay.getText());
            double resultado = realizarOperacion(operando1, operando2, operador);

            if (Double.isNaN(resultado)) { //división entre 0.

                txtDisplay.setText("Error: división por 0");

            } else {

                txtDisplay.setText(String.valueOf(resultado));
                operando1 = resultado; // Guarda el resultado para otras operaciones
            }

            operador = ""; // resetea el operador
            banInicio = true;
        }
        /*if (strTecla.matches("[0-9.]")) {

            if (banInicio) {

                txtDisplay.setText(strTecla);
                banInicio = false;

            } else {

                txtDisplay.appendText(strTecla);
            }

        } else if (strTecla.matches("[-+/*]")) {

            //txtDisplay.appendText(strTecla);
            contOperador++;

            if (strTecla.equals("/")){


            }

            if (contOperador > 1){

                txtDisplay.setText("Error 1.");
                contOperador = 0;

            } else {

                operador = strTecla;
                operando1 = Double.parseDouble(txtDisplay.getText());
                banbanInicio = true;

            }

        } else if (strTecla.equals("=")) {

            operando2 = Double.parseDouble(txtDisplay.getText());
            resultado = realizarOperacion(operando1, operando2, operador);
            contOperador--;

            if (Double.isNaN(resultado)) {
                return;
            }

            txtDisplay.setText(String.valueOf(resultado));
            //txtDisplay.setText("0.0");
            banInicio = true;

        }*/
    }

    private double realizarOperacion(double operando1, double operando2, String operador) {

        double  resultado = 0.0;

        switch (operador) {

            case "+":

                    //contOperador++;
                    resultado = operando1 + operando2;
                    //contOperador--;
                break;
            case "-":

                //contOperador++;
                resultado = operando1 - operando2;
                break;
            case "*":

                //contOperador++;
                resultado = operando1 * operando2;
                break;
            case "/":

                if (operando2 == 0) {

                    txtDisplay.setText("Error: División por 0");
                    return Double.NaN;

                } else {

                    resultado = operando1 / operando2;
                }

                break;
        }

        return  resultado;
    }
}