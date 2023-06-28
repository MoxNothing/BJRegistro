package com.example.cregistro;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DDatos {
    @FXML
    private TextField nombreTextField;

    @FXML
    private TextField apellidoTextField;

    @FXML
    private TextField generoTextField;

    @FXML
    private ChoiceBox<String> gradoChoiceBox;

    @FXML
    private ChoiceBox<String> modalidadChoiceBox;

    @FXML
    private TextField carreraTextField;

    @FXML
    private Label montoLabel;

    @FXML
    private Label duracionLabel;

    private Object dataSource;


    public void generarReporte() {
        String nombre = nombreTextField.getText();
        String apellido = apellidoTextField.getText();
        String genero = generoTextField.getText();
        String grado = gradoChoiceBox.getValue();
        String modalidad = modalidadChoiceBox.getValue();
        String carrera = carreraTextField.getText();

        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("nombre", nombre);
            parameters.put("apellido", apellido);
            parameters.put("genero", genero);
            parameters.put("grado", grado);
            parameters.put("modalidad", modalidad);
            parameters.put("carrera", carrera);

            JasperPrint jasperPrint = JasperFillManager.fillReport("ruta/al/reporte.jasper", parameters, dataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "ruta/del/archivo.pdf");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("report.fxml"));
            VBox root = loader.load();
            Label nombreLabel = (Label) root.lookup("#nombreLabel");
            nombreLabel.setText(nombre);
            Label apellidoLabel = (Label) root.lookup("#apellidoLabel");
            apellidoLabel.setText(apellido);

            Button descargarButton = new Button("Descargar reporte");
            descargarButton.setOnAction(event -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Guardar reporte");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
                File file = fileChooser.showSaveDialog(root.getScene().getWindow());
                if (file != null) {
                    try {
                        JasperExportManager.exportReportToPdfFile(jasperPrint, file.getAbsolutePath());
                    } catch (JRException e) {
                        e.printStackTrace();
                    }
                }
            });
            root.getChildren().add(descargarButton);

            // Create a new window to display the collected information
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException | JRException e) {
            e.printStackTrace();
        }
    }

    public void limpiarCampos() {
        nombreTextField.clear();
        apellidoTextField.clear();
        generoTextField.clear();
        gradoChoiceBox.setValue(null);
        modalidadChoiceBox.setValue(null);
        carreraTextField.clear();
        montoLabel.setText("Label");
        duracionLabel.setText("Label");
    }

    public void initialize() {
        gradoChoiceBox.getItems().addAll("Licenciatura", "Maestría", "Doctorado");
        modalidadChoiceBox.getItems().addAll("Presencial", "En línea");
    }

    public void setDataSource(Object dataSource) {
        this.dataSource = dataSource;
    }
}