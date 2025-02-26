package com.example.demo2.vistas;

import com.example.demo2.modelos.ClientesDAO;
import com.mysql.cj.xdevapi.Client;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListaClientes extends Stage {

    private Scene escena;

    private ToolBar tblMenu;
    private TableView<ClientesDAO> tbvClientes;
    private VBox vBox;
    private Button btnAgregar;

    public ListaClientes(){

        CrearUI();

        this.setTitle("Listado de Clientes krnl");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI (){

        ImageView imv = new ImageView(getClass().getResource("/images/addIcon.png").toString());
        btnAgregar = new Button();
        //btnAgregar.setPrefSize(24, 24);
        imv.setFitHeight(20);
        imv.setFitWidth(20);
        btnAgregar.setGraphic(imv);

        tblMenu = new ToolBar(btnAgregar);

        tbvClientes = new TableView<>();

        vBox = new VBox(tblMenu, tbvClientes);

        escena = new Scene(vBox, 600, 200);
    }
}
