package gui.controller;

import gui.model.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;

import java.awt.event.*;
import java.io.*;

public class LogInController {

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField userId;
    @FXML
    private TextField userPass;

    private Model model = Model.getModel();

    public void logIn(ActionEvent actionEvent) {
        model.loadUserList();
        model.loginUser(userId.getText(), userPass.getText());
        if(model.getCurrentUser()!=null){
            try {
                Parent root = FXMLLoader.load(getClass().getResource("gui/view/HomeView.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Event Manager");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load App.fxml");
                alert.showAndWait();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong username or password");
            alert.showAndWait();
        }
    }
    
}
