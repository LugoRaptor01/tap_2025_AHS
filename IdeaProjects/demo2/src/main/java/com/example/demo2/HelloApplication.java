package com.example.demo2;

import com.example.demo2.vistas.calculadora;
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

    private Menu menCompetencia1, getMenCompetencia2;

    private MenuItem mitCalculadora;

    void crearUI(){

        mitCalculadora = new MenuItem("Calculadora"); //asignacion de opciones para el menu de items
        mitCalculadora.setOnAction(event -> new calculadora());

        menCompetencia1 = new Menu("Competencia 1"); // asignacion de elementos del menu
        menCompetencia1.getItems().addAll(mitCalculadora);

        mnbPrincipal = new MenuBar(); //asignacion de la barra de menu
        mnbPrincipal.getMenus().addAll(menCompetencia1);

        vBox = new VBox(mnbPrincipal);
    }

    @Override

    public void start(Stage stage) throws IOException {

        crearUI();
        //vBox = new VBox();

        //creacion y configuracion de la escena
        stage.setTitle("Hola Mundo de Eventos ;)");
        stage.setScene(new Scene(vBox));
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

    void clickEvent(){

        //System.out.println("evento desde un metodo.");
    }



}