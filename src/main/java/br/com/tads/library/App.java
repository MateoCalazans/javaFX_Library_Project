package br.com.tads.library;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carregar o arquivo FXML da tela welcome.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/welcome.fxml"));
        Parent root = loader.load();

        // Configurar a cena
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Welcome - Library");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


