/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdsystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;
import static mdsystem.globalParam.bglbLogin;
import static mdsystem.globalParam.glbCurUser;

/**
 *
 * @author freddie
 */
public class DialogLoginController implements Initializable {

    @FXML
    TextField textUsername;
    @FXML
    TextField textPassword;
    @FXML
    Button buttonLogin;
    @FXML
    Button buttonCancel;

    private DBAdmin dbase;
    String strUsername;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        strUsername = "";
        buttonLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int ret = dbase.loginCheck(textUsername.getText(), textPassword.getText());
                switch (ret) {
                    case 1: {
                        bglbLogin = true;
                        glbCurUser = textUsername.getText();
                        strUsername = textUsername.getText();
                        final Stage stage = (Stage) buttonLogin.getScene().getWindow();
                        stage.close();
                        break;
                    }
                    case -1: {
                        bglbLogin = false;
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Login Error");
                        String strLog = String.format("Username %s is not exist.", textUsername.getText());
                        alert.setHeaderText("Logining Error");
                        alert.setContentText(strLog);
                        alert.showAndWait();
                        break;
                    }
                    case 0: {
                        bglbLogin = false;
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Login Error");
                        String strLog = String.format("Password is not matched by Username: %s", textUsername.getText());
                        alert.setHeaderText("Logining Error");
                        alert.setContentText(strLog);
                        alert.showAndWait();
                        break;
                    }
                    default:
                        break;
                }
            }
        });

        buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                bglbLogin = false;
                final Stage stage = (Stage) buttonLogin.getScene().getWindow();
                stage.close();
            }
        });

    }

    public void initDatabase(DBAdmin db) {
        dbase = db;
    }

    public String getUsername() {
        return strUsername;
    }
}
