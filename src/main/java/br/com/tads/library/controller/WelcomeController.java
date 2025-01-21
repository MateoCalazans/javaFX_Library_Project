package br.com.tads.library.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class WelcomeController {

    @FXML
    private Button goToCrudButton;

    @FXML
    private void goToCrud(ActionEvent event) {
        try {
            // Carregar a tela de Students (CRUD)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Students.fxml"));
            Parent root = loader.load();

            // Criar a nova cena
            Scene scene = new Scene(root);

            // Obter o stage atual e trocar a cena
            Stage stage = (Stage) goToCrudButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Students - Library");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
