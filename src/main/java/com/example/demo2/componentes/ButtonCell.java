package com.example.demo2.componentes;

import com.example.demo2.modelos.ClientesDAO;
import com.example.demo2.vistas.Cliente;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;

import java.util.Optional;

public class ButtonCell extends TableCell<ClientesDAO, String> {

    private Button btnCelda;
    private String strLabelBtn;

    public ButtonCell (String label){

        //ClientesDAO objC = this.getTableView().getItems().get(this.getIndex());

        strLabelBtn = label;
        btnCelda = new Button(strLabelBtn);

        btnCelda.setOnAction(event -> {

            ClientesDAO objC  = this.getTableView().getItems().get(this.getIndex());

            if (strLabelBtn.equals("Editar")) {

                new Cliente(this.getTableView(), objC);

            } else {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Mensaje del Sistema");
                alert.setContentText("¿Desea eliminar el registro seleccionado, krnl?");

                Optional<ButtonType> opcion = alert.showAndWait();

                if (opcion.get() == ButtonType.OK) {

                    objC.DELETE();
                }
            }

            this.getTableView().setItems(objC.SELECT());
            this.getTableView().refresh();
        });
    }

    protected void updateItem (String item, boolean empty){

        super.updateItem(item, empty);

        if (!empty){

            this.setGraphic(btnCelda);


        }
    }
}