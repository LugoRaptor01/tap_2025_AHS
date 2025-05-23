package com.example.demo2.vistas;

import com.example.demo2.modelos.ClientesDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Cliente extends Stage {

    private Button btnGuardar;
    private TextField txtNombre, txtDireccion, txtTelefono, txtEmail;
    private VBox vBox;
    private ClientesDAO objC;
    private Scene escena;
    private TableView<ClientesDAO> tbvClientes;

    public Cliente(TableView<ClientesDAO> tbvCte, ClientesDAO obj){

        this.tbvClientes = tbvCte;

        CrearUI();

        if(obj == null){

            new ClientesDAO();

        } else {

            objC = obj;

            txtNombre.setText(objC.getNomCte());
            txtDireccion.setText(objC.getDireccion());
            txtTelefono.setText(objC.getTelCte());
            txtEmail.setText(objC.getEmailCte());
        }

        //objC = obj == null ?  new ClientesDAO() : obj;

        this.setTitle("Registro de Cliente");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI (){

        txtNombre = new TextField();
        txtDireccion = new TextField();
        txtTelefono = new TextField();
        txtEmail = new TextField();

        //objC = new ClientesDAO();

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> {

            objC.setNomCte(txtNombre.getText());
            objC.setDireccion(txtDireccion.getText());
            objC.setTelCte(txtTelefono.getText());
            objC.setEmailCte(txtEmail.getText());

            if (objC.getIdCte() > 0){

                objC.UPDATE();

            } else {

                objC.INSERT();

                tbvClientes.setItems(objC.SELECT());
                tbvClientes.refresh();

                this.close();
            }

        });

        vBox = new VBox(txtNombre, txtDireccion, txtTelefono, txtEmail, btnGuardar);

        escena = new Scene(vBox, 200, 125);
        // objetos requeridos: 1 vBox, 4 textFields y un button
    }
}