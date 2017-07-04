package mdsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import mdsystem.DBAdmin;
import mdsystem.Staff;
import static mdsystem.globalParam.glbCurUser;
import static mdsystem.globalParam.glbcuffStaff;
import mdsystem.DBAdmin;
import javafx.scene.layout.GridPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author freddie
 */
class UserUpdate extends Stage {

    public DBAdmin dbase;

    //mode = 1 add
    //mode = 2 update
    public UserUpdate(Stage owner, DBAdmin db, Staff staff,int mode) {
        super();
        dbase = db;
        initOwner(owner);

        Group root = new Group();
        Scene scene = new Scene(root, 380, 400); //, Color.WHITE);
        setScene(scene);

        setTitle("User Management");

        final Label label = new Label("User Infomation");
        label.setFont(new Font("Arial", 20));

//    private final SimpleIntegerProperty strNum;
//    private final SimpleStringProperty strName;
//    private final SimpleStringProperty strStaffID;
//    private final SimpleStringProperty strFirstName;
//    private final SimpleStringProperty strLastName;
//    private final SimpleStringProperty strEmail;
        String strUsername, strPassword1, strPassword2, strStaffID, strFirstname, strLastname, strEmail;
        strUsername = "";
        strPassword1 = "";
        strPassword2 = "";
        strStaffID = "";
        strFirstname = "";
        strLastname = "";
        strEmail = "";
        switch (mode) {
            case 1:         //add
                strUsername = "";
                strPassword1 = "";
                strPassword2 = "";
                strStaffID = "";
                strFirstname = "";
                strLastname = "";
                strEmail = "";
                break;
            case 2:         //update

                strUsername = staff.getStrName();
                strPassword1 = staff.getStrPassword();
                strPassword2 = "";
                strStaffID = staff.getStrStaffID();
                strFirstname = staff.getStrFirstName();
                strLastname = staff.getStrLastName();
                strEmail = staff.getStrEmail();
                break;

            default:
                break;
        }

        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(20, 5, 10, 5));
        gridpane.setVgap(10);
        gridpane.setHgap(5);

        Label labelUsername = new Label("User name: ");
        gridpane.add(labelUsername, 0, 0);
        final TextField txtUsername = new TextField(strUsername);
        gridpane.add(txtUsername, 1, 0);
        if (mode == 2) {
            //update uneditable
            txtUsername.setEditable(false);
        }

        Label labelPassword1 = new Label("Password:");
        gridpane.add(labelPassword1, 0, 1);
        final PasswordField txtPassword1 = new PasswordField();
        txtPassword1.setText(strPassword1);

        gridpane.add(txtPassword1, 1, 1);

        Label labelPassword2 = new Label("Password Confirm: ");
        gridpane.add(labelPassword2, 0, 2);
        final PasswordField txtPassword2 = new PasswordField();
        txtPassword2.setText(strPassword2);
        gridpane.add(txtPassword2, 1, 2);

        Label labelStaffID = new Label("StaffID: ");
        gridpane.add(labelStaffID, 0, 3);
        final TextField txtStaffID = new TextField(strStaffID);
        gridpane.add(txtStaffID, 1, 3);

        Label labelFirstName = new Label("First name: ");
        gridpane.add(labelFirstName, 0, 4);
        final TextField txtFirstName = new TextField(strFirstname);
        gridpane.add(txtFirstName, 1, 4);

        Label labelLastName = new Label("Last name: ");
        gridpane.add(labelLastName, 0, 5);
        final TextField txtLastName = new TextField(strLastname);
        gridpane.add(txtLastName, 1, 5);

        Label labelEmail = new Label("Email: ");
        gridpane.add(labelEmail, 0, 6);
        final TextField txtEmail = new TextField(strEmail);
        gridpane.add(txtEmail, 1, 6);

//        
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(5, 5, 5, 5));
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER_RIGHT);
        Button buttonConfirm = new Button("Confirm");
        Button buttonCancel = new Button("Cancel");
        buttonConfirm.setPrefWidth(100);
         buttonCancel.setPrefWidth(100);
        hbox.getChildren().addAll(buttonConfirm, buttonCancel);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(gridpane, hbox);
        root.getChildren().add(vbox);

        buttonConfirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String strError = "";

                if (!txtPassword1.getText().equals(txtPassword2.getText())) {
                    strError = "Two passwords are not equal.";
                    showWarning(strError);
                    return;
                }

                if (txtUsername.getText().length() > 16) {
                    strError = "The length of username cann't be more the 16 characters.";
                    showWarning(strError);
                    return;
                }
                
                if (txtUsername.getText().length() == 0) {
                    strError = "Username can not be null";
                    showWarning(strError);
                    return;
                }
                if (txtPassword1.getText().length() == 0) {
                    strError = "Password can not be null";
                    showWarning(strError);
                    return;
                }
                if (txtPassword1.getText().length() > 6) {
                    strError = "The length of password cann't be more the 6 characters.";
                    showWarning(strError);
                    return;
                }
                if (txtPassword2.getText().length() > 6) {
                    strError = "The length of password cann't be more the 6 characters.";
                    showWarning(strError);
                    return;
                }
                if (txtStaffID.getText().length() > 6) {
                    strError = "The length of password cann't be more the 6 characters.";
                    showWarning(strError);
                    return;
                }
                if (txtFirstName.getText().length() > 16) {
                    strError = "The length of password cann't be more the 16 characters.";
                    showWarning(strError);
                    return;
                }
                if (txtLastName.getText().length() > 16) {
                    strError = "The length of password cann't be more the 16 characters.";
                    showWarning(strError);
                    return;
                }
                if (txtEmail.getText().length() > 50) {
                    strError = "The length of password cann't be more the 50 characters.";
                    showWarning(strError);
                    return;
                }
                



                Staff staff = new Staff(txtUsername.getText(),
                        txtPassword1.getText(),
                        txtStaffID.getText(),
                        txtFirstName.getText(),
                        txtLastName.getText(),
                        txtEmail.getText());
                if (mode == 1) {
                    dbase.addUser(staff);
                    close();
                }
                if (mode == 2) {
                    dbase.updateUser(staff);
                    close();
                }

            }
        });

        buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                close();
            }
        });

    }

    public void showWarning(String strLog) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");

        alert.setHeaderText("Error");
        alert.setContentText(strLog);
        alert.showAndWait();
    }

    public void setDB(DBAdmin db) {
        dbase = db;
    }

}
