module pt.isec.gestaodepinformatica {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens pt.isec.gestaodepinformatica to javafx.fxml;
    exports pt.isec.gestaodepinformatica;

    opens pt.isec.gestaodepinformatica.Modelo to javafx.fxml;
    exports pt.isec.gestaodepinformatica.Modelo;


    opens pt.isec.gestaodepinformatica.Controlador to javafx.fxml;
    exports pt.isec.gestaodepinformatica.Controlador;
}