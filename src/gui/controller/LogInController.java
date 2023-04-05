package gui.controller;

import gui.model.*;
import io.github.palexdev.materialfx.controls.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;

import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable{
    @FXML private MFXPasswordField passwordField;
    @FXML
    private ImageView backgroundLogin, logoEASV;

    @FXML
    private TextField userId;


    private Model model = Model.getModel();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            Image backgroundImage = new Image(new FileInputStream("resources/images/background-login.png"));
            Image logoImage = new Image(new FileInputStream("resources/images/logoEASV.png"));
            backgroundLogin.setImage(backgroundImage);
            logoEASV.setImage(logoImage);


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void logIn(javafx.event.ActionEvent actionEvent) {
        model.loadUserList();
        model.loginUser(userId.getText(), passwordField.getText());
        if (model.getCurrentUser() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/HomeView.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
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
