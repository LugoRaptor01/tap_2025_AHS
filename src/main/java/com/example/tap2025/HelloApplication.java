package com.example.tap2025;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
/*
    private Button btnSaludo, btnSaludo2, btnSaludo3; //declaracion de botones
*/
    private VBox vBox; // contenedor para los botones

    private MenuBar mnbPrincipal;

    private Menu menCompetencia1, getMenCompetencia2;

    private MenuItem mitCalculadora;

    private Scene escena;

    void crearUI(){

        mitCalculadora = new MenuItem("Calculadora"); //asignacion de opciones para el menu de items

        menCompetencia1 = new Menu("Competencia 1"); // asignacion de elementos del menu
        menCompetencia1.getItems().addAll(mitCalculadora);

        mnbPrincipal = new MenuBar(); //asignacion de la barra de menu
        mnbPrincipal.getMenus().addAll(menCompetencia1);
    }

    @override

    public void start(Stage stage) throws IOException {
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

        vBox = new VBox();

        //creacion y configuracion de la escena
        stage.setTittle("Hola Mundo de Eventos ;)");
        stage.setScene(new Scene(vBox,200, 200));
        stage.show();
        stage.setMaximized(true);

    }

    public static void main(String[] args) {
        launch();
    }
/*
    void clickEvent(){

        System.out.println("evento desde un metodo.");
    }


 */
}