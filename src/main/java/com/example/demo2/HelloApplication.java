package com.example.demo2;

import com.example.demo2.componentes.Hilo;
import com.example.demo2.modelos.Conexion;
import com.example.demo2.vistas.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    /*
    private Button btnSaludo, btnSaludo2, btnSaludo3; //declaracion de botones
    */
    private Button saludo;

    private Scene escena;
    private VBox vBox; // contenedor para los botones

    private MenuBar mnbPrincipal;

    private Menu menCompetencia1, menCompetencia2;

    private MenuItem mitCalculadora, mitRompecabezas, mitRestaurante, mitRestauranTec, mitHilos;

    void crearUI(){

        mitCalculadora = new MenuItem("Calculadora"); //asignacion de opciones para el menu de items
        mitCalculadora.setOnAction(event -> new calculadora());

        mitRestaurante = new MenuItem("Restaurante");
        mitRestaurante.setOnAction(event -> new ListaClientes());

        mitRompecabezas = new MenuItem("Rompecabezas");
        mitRompecabezas.setOnAction(event -> new Rompecabezas());

        mitHilos = new MenuItem("Carrera de Buses!!!");
        mitHilos.setOnAction(event -> new Celayork());

        //mitRestauranTec = new MenuItem("RestauranTec");
        //mi

        menCompetencia1 = new Menu("Competencia 1"); // asignacion de elementos del menu
        menCompetencia1.getItems().addAll(mitCalculadora, mitRompecabezas, mitRestaurante);

        menCompetencia2 = new Menu("Competencia 2");
        menCompetencia2.getItems().addAll(mitHilos);

        mnbPrincipal = new MenuBar(); //asignacion de la barra de menu
        mnbPrincipal.getMenus().addAll(menCompetencia1, menCompetencia2);

        vBox = new VBox(mnbPrincipal);

        escena  = new Scene(vBox); //instanciado de la escena de la ventana principal.
        escena.getStylesheets().add(getClass().getResource("/styles/main.css").toString());

    }

    @Override

    public void start(Stage stage) throws IOException {
        /*
        new Hilo ("Ruta Pinos 60").start();
        new Hilo ("Ruta Laureles 26").start();
        new Hilo ("Ruta Delicias 40").start();
        new Hilo ("Ruta Puente 10").start();
        new Hilo("Ruta Laja 13").start();
        */

        Conexion.createConnection();
        crearUI();
        //vBox = new VBox();

        //creacion y configuracion de la escena
        stage.setTitle("Hola Mundo de Eventos ;)");
        stage.setScene(escena);
        stage.show();
        stage.setMaximized(true);

        /*
        //creacion de botones
        btnSaludo = new Button("Bienvenido");

        btnSaludo.setOnAction(event -> clickEvent());
        btnSaludo2 = new Button("Bienvenido");
        btnSaludo2.setOnAction(event -> clickEvent());
        btnSaludo3 = new Button("Bienvenido");
        */
        //seleccion de botones dentro del contenedor
        //vBox = new VBox(btnSaludo, btnSaludo2, btnSaludo3);

        /*
        vBox.setSpacin(10);
        vBox.setPadding(new Inserts(10, 0, 0, 0));
        */
    }

    public static void main(String[] args) {
        launch();
    }
    /*
    void clickEvent(){

        //System.out.println("evento desde un metodo.");
    }
     */
}