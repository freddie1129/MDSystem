/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdsystem;

import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;

/**
 *
 * @author freddie
 */

public class DialogTest extends Stage {

    @FXML private TextField textUsername;
    @FXML private TextField textPassword;
    @FXML private  Button buttonLogin;
    @FXML private Button buttonCancel;
    
    public DialogTest(Stage owner) {
        super();
        initOwner(owner);
        setTitle("FXML Welcome");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("DialogLogin.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(DialogLogin.class.getResource("DialogLogin.css").toExternalForm());
            setScene(scene);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
        @FXML protected void handleAction(ActionEvent event) {
        System.out.println("click login");
    }
}
