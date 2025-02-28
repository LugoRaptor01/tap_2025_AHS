package com.example.demo2.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClienteForm extends Stage {

    private Button btnGuardar;
    private TextField txtNombre, txtDireccion, txtTelefono, txtEmail;
    private VBox vBox;

    private Scene escena;

    public Cliente(){

        this.setTitle("Registro de Cliente");
        this.setScene(escena);
        this.show();
    }

    private CrearUI (){

        txtNombre = new TextField();
        txtDireccion = new TextField();
        txtTelefono = new TextField();
        txtEmail = new TextField();

        btnGuardar = new Button("Guardar");

        vBox = new VBox(txtNombre, txtDireccion, txtTelefono, txtTelefono, btnGuardar);

        escena = new Scene(vBox, 120, 50);
        // objetos requeridos: 1 vBox, 4 textFields y un button
    }
}
