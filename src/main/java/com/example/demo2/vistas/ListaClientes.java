package com.example.demo2.vistas;

import com.example.demo2.componentes.ButtonCell;
import com.example.demo2.modelos.ClientesDAO;
import com.mysql.cj.xdevapi.Client;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

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

        tbvClientes = new TableView<>();

        btnAgregar = new Button();
        btnAgregar.setOnAction(event -> new Cliente(tbvClientes, null));
        ImageView imv = new ImageView(getClass().getResource("/images/addIcon.png").toString());

        btnAgregar.setPrefSize(24, 24);
        imv.setFitHeight(20);
        imv.setFitWidth(20);
        btnAgregar.setGraphic(imv);

        tblMenu = new ToolBar(btnAgregar);
        CreateTable();

        vBox = new VBox(tblMenu, tbvClientes);

        escena = new Scene(vBox, 800, 600);
    }

    private void CreateTable() {

        ClientesDAO objC = new ClientesDAO();

        TableColumn<ClientesDAO, String> tbcNomCte = new TableColumn<>("Nombre");
        tbcNomCte.setCellValueFactory(new PropertyValueFactory<>("nomCte"));

        TableColumn<ClientesDAO, String> tbcTelCte = new TableColumn<>("Teléfono");
        tbcTelCte.setCellValueFactory(new PropertyValueFactory<>("telCte"));

        TableColumn<ClientesDAO, String> tbcDireccion = new TableColumn<>("Dirección");
        tbcDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        TableColumn<ClientesDAO, String> tbcEmailCte = new TableColumn<>("Email");
        tbcEmailCte.setCellValueFactory(new PropertyValueFactory<>("emailCte"));

        TableColumn<ClientesDAO, String> tbcEditar = new TableColumn<>("Editar");
        TableColumn<ClientesDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        //Creacion de los botones para modificacion y eliminacion de tuplas.

        tbcEditar.setCellFactory(new Callback<TableColumn<ClientesDAO, String>, TableCell<ClientesDAO, String>>() {
            @Override
            public TableCell<ClientesDAO, String> call(TableColumn<ClientesDAO, String> clientesDAOStringTableColumn) {
                return new ButtonCell("Editar");
            }
        });

        tbcEliminar.setCellFactory(new Callback<TableColumn<ClientesDAO, String>, TableCell<ClientesDAO, String>>() {
            @Override
            public TableCell<ClientesDAO, String> call(TableColumn<ClientesDAO, String> clientesDAOStringTableColumn) {
                return new ButtonCell("Eliminar");
            }
        });

        tbvClientes.getColumns().addAll(tbcNomCte, tbcDireccion, tbcTelCte, tbcEmailCte, tbcEditar, tbcEliminar);
        tbvClientes.setItems(objC.SELECT());
    }
}