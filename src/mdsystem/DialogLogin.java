/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdsystem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static mdsystem.globalParam.bglbLogin;
import static mdsystem.globalParam.glbCurUser;

/**
 *
 * @author freddie
 */
public class DialogLogin extends Stage {

    public DialogLogin(Stage owner) {
        super();
        initOwner(owner);
        setTitle("Login");
        Group root = new Group();
        Scene scene = new Scene(root);
        this.setResizable(false);
        scene.getStylesheets().add(DialogLogin.class.getResource("DialogLogin.css").toExternalForm());
        setScene(scene);

        GridPane gridpane = new GridPane();
        gridpane.getStyleClass().add("grid");

//        gridpane.setAlignment(Pos.CENTER);
//        gridpane.setHgap(10);
//        gridpane.setVgap(10);
//        gridpane.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setId("welcome-text");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridpane.add(scenetitle, 0, 0, 2, 1);

        Label userNameLbl = new Label("Username: ");
        gridpane.add(userNameLbl, 0, 1);
        final TextField userNameFld = new TextField("admin");
        gridpane.add(userNameFld, 1, 1);

        Label passwordLbl = new Label("Password: ");
        gridpane.add(passwordLbl, 0, 2);
        final PasswordField passwordFld = new PasswordField();
        passwordFld.setText("admin");
        gridpane.add(passwordFld, 1, 2);

        Button login = new Button("Login");
        Button cancellogin = new Button("Cancel");
        Label hint = new Label("Test account admin pw ： admin");
        login.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                int ret = dbase.loginCheck(userNameFld.getText(), passwordFld.getText());
                switch (ret) {
                    case 1: {
                        bglbLogin = true;
                        glbCurUser = userNameFld.getText();
                        close();
                        break;
                    }
                    case -1: {
                        bglbLogin = false;
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Login Error");
                        String strLog = String.format("Username %s is not exist.", userNameFld.getText());
                        alert.setHeaderText("Logining Error");
                        alert.setContentText(strLog);
                        alert.showAndWait();
                        break;
                    }
                    case 0: {
                        bglbLogin = false;
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Login Error");
                        String strLog = String.format("Password is not matched by Username: %s", userNameFld.getText());
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

        cancellogin.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                bglbLogin = false;
                close();
            }
        });

        HBox logBox = new HBox();
        logBox.setSpacing(5);
        logBox.setAlignment(Pos.BOTTOM_RIGHT);
        logBox.getChildren().addAll(login, cancellogin);

        gridpane.add(logBox, 1, 4);
        gridpane.add(hint, 0, 5,2,2);
        
        GridPane.setHalignment(login, HPos.RIGHT);
        root.getChildren().add(gridpane);
    }

    private DBAdmin dbase;

    public void setDB(DBAdmin db) {
        dbase = db;
    }
}

