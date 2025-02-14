package com.example.demo2.vistas;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class VentasRestaurante extends Stage {

    private Scene escena;
    private Panel pnlRestaurante;

    public VentasRestaurante(){

        crearUI();
        this.setTitle("Fondita Doña Lupe");
        this.setScene(escena);
        this.show();

    }

    void crearUI(){

        pnlRestaurante = new Panel("Tacos el Inge");
        pnlRestaurante.getStyleClass().add("panel-primary");
        escena = new Scene(pnlRestaurante, 300, 300);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

    }
}
