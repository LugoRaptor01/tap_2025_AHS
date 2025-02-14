package com.example.demo2.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class calculadora extends Stage {

    private Scene escena;
    private TextField txtDisplay;
    private VBox vBox;
    private GridPane gdpKeyboard; //matriz para teclado xd
    private Button[][] arBtnTeclado; //arreglo de botones para evitar declarar botones individuales
    String strTeclas[] = {"7", "4", "1", "0", "8", "5", "2", "/",
                          "9", "6", "3","+", ".", "*", "=", "-"};

    public calculadora(){

        CrearUI();

        this.setScene(escena); //primero mandar llamar el método de
        // creacion de la escena y despues cargar la escena
        this.setTitle("Calculadora");
        this.show();

    }

    public void CrearUI(){

        crearKeyboard();
        txtDisplay = new TextField("0");
        //txtDisplay.setPromptText("Teclea tu operacion:");
        txtDisplay.setEditable(false);
        txtDisplay.setAlignment(Pos.BASELINE_RIGHT);

        vBox = new VBox(txtDisplay, gdpKeyboard);
        vBox.setSpacing(10); //cantidad de pixeles de separacion entre botones
        vBox.setPadding(new Insets(10)); // cantidad de pixeles de separacion del margen de la ventana
                                            // a los objetos contenidos
        
        escena = new Scene(vBox, 200, 250); //tamaño de la ventana
        escena.getStylesheets().add(getClass().getResource("/styles/calculadora.css").toString());
        //esta instruccion es para darle la direccion en la que se ubica el archivo de estilos para el proyecto.


    }

    public void crearKeyboard(){

        arBtnTeclado = new Button[4][4];

        gdpKeyboard = new GridPane(); // instanciado de la matriz de botones
        gdpKeyboard.setHgap(10); // seteo de la separacion entre botones horizontal y verticalmente
        gdpKeyboard.setVgap(10);
        
        int pos = 0;
        
        for (int renglon = 0; renglon < 4; renglon++) {
            for (int columna = 0; columna < 4; columna++) {

                arBtnTeclado[renglon][columna] = new Button(strTeclas[pos]);

                if(strTeclas[pos].equals("*")){

                    arBtnTeclado[renglon][columna].setId("fontButton"); //asignarle un estilo especifico a un elemento
                                                                        //especifico
                    arBtnTeclado[renglon][columna].setStyle("-fx-background-color: rgba(31, 107, 45, 0.72);");
                    //asignación directa a un elemento individual.
                }

                int finalPos = pos;
                arBtnTeclado[renglon][columna].setOnAction(event -> EventoTeclado(strTeclas[finalPos]));
                arBtnTeclado[renglon][columna].setPrefSize(50,50);
                gdpKeyboard.add(arBtnTeclado[renglon][columna], renglon, columna);
                
                pos++;
            }
        }
    }

    private void EventoTeclado(String strTecla) {

        //txtDisplay.appendText(strTecla);

    }

}
