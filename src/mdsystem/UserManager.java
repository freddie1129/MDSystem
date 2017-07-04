/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdsystem;

import java.sql.SQLException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static mdsystem.globalParam.bglbLogin;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;
import mdsystem.Staff;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import static mdsystem.DBAdmin.printSQLException;
import static mdsystem.globalParam.glbCurUser;
import static mdsystem.globalParam.glbcuffStaff;
import mdsystem.UserUpdate;








class UserManager extends Stage {
    
        private final TableView<Staff> table = new TableView();
        
       private final ObservableList<Staff> tableuser = FXCollections.observableArrayList();
           private DBAdmin dbase;
       //new Staff("a","123","Jacob", "Smith", "jacob.smith@example.com"),
       //new Staff("rt","123","Jacob", "Smith", "jacob.smith@example.com"));
//        FXCollections.observableArrayList(
//            new Person("Jacob", "Smith", "jacob.smith@example.com"),
//            new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
//            new Person("Ethan", "Williams", "ethan.williams@example.com"),
//            new Person("Emma", "Jones", "emma.jones@example.com"),
//            new Person("Michael", "Brown", "michael.brown@example.com")
//        );
            

    public UserManager(Stage owner,DBAdmin db) {
        super();
        dbase = db;
        initOwner(owner);
        
        Group root = new Group();
        Scene scene = new Scene(root, 660, 500,Color.WHITE);
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
    
    
        TableColumn numberCol = new TableColumn("No.");
        numberCol.setMinWidth(20);
        numberCol.setCellValueFactory(new PropertyValueFactory<>("strNum"));

        TableColumn userNameCol = new TableColumn("Username");
        userNameCol.setMinWidth(40);
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("strName"));
        TableColumn staffIDNameCol = new TableColumn("StaffID");
        staffIDNameCol.setMinWidth(40);

        staffIDNameCol.setCellValueFactory(new PropertyValueFactory<>("strStaffID"));
        TableColumn firstNameCol = new TableColumn("fName");
        firstNameCol.setMinWidth(40);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("strFirstName"));
        TableColumn lastNameCol = new TableColumn("lName");
        lastNameCol.setMinWidth(40);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("strLastName"));
        TableColumn emailCol = new TableColumn("Email");
        emailCol.setMinWidth(160);
        emailCol.setCellValueFactory(new PropertyValueFactory<>("strEmail"));
        TableColumn passwordCol = new TableColumn("Password");
        passwordCol.setMinWidth(20);
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("strPassword"));
        
        table.setItems(tableuser);

        if (glbCurUser.equals("admin"))
            table.getColumns().addAll(numberCol,userNameCol,staffIDNameCol,firstNameCol, lastNameCol, emailCol,passwordCol);
        else
            table.getColumns().addAll(numberCol,userNameCol,staffIDNameCol,firstNameCol, lastNameCol, emailCol);
        
        tableuser.clear();
        dbase.listUser(glbCurUser,tableuser);
        
        
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(5,5,5,5));
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER_RIGHT);
        Button buttonAdd = new Button("Add");
        Button buttonModify = new Button("Modify");
        Button buttonDelete = new Button("Delete");
        buttonAdd.setPrefWidth(100);
        buttonModify.setPrefWidth(100);
        buttonDelete.setPrefWidth(100);
        
        hbox.getChildren().addAll(buttonAdd,buttonModify,buttonDelete);
        
        if (!glbcuffStaff.getStrName().equals("admin"))
        {
            buttonAdd.setDisable(true);
            buttonDelete.setDisable(true);
        }
        
        
        
        
        
        
        
        
        
        
        
       // tableuser.add(new Staff("a","123","Jacob", "Smith", "jacob.smith@example.com"));
       //         tableuser.add(new Staff("a","123","Jacob", "Smith", "jacob.smith@example.com"));
       //                 tableuser.add(new Staff("a","123","Jacob", "Smith", "jacob.smith@example.comdddddddddddddddddddddddddd"));
 
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table,hbox);
        root.getChildren().add(vbox);
        
        buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
////                UserManager um = new UserManager(primaryStage, database);
//
//                um.showAndWait();
Staff staff = table.getSelectionModel().getSelectedItem();
                UserUpdate diagUpdate = new UserUpdate(owner, dbase,staff, 1);
                diagUpdate.showAndWait();
                tableuser.clear();
                dbase.listUser(glbCurUser, tableuser);            

            }
        });

        buttonModify.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
//                UserManager um = new UserManager(primaryStage, database);
//                um.showAndWait();
Staff staff = table.getSelectionModel().getSelectedItem();
                  UserUpdate diagUpdate = new UserUpdate(owner, dbase, staff,2);
                diagUpdate.showAndWait();
                tableuser.clear();
                dbase.listUser(glbCurUser, tableuser);
            }
        });

        buttonDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
//                UserManager um = new UserManager(primaryStage, database);
//                um.showAndWait();

        Staff staff = table.getSelectionModel().getSelectedItem();
        if (staff.getStrName().equals("admin"))
        {
            String strLog = "admin can be deleted.";
                    Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");

        alert.setHeaderText("Error");
        alert.setContentText(strLog);
        alert.showAndWait();
        return;
        }
        dbase.deleteUser(staff);
                tableuser.clear();
                dbase.listUser(glbCurUser, tableuser);



            }
        });
        
        
    }
    

    
    
    public void setDB(DBAdmin db)
    {
        dbase = db;        
    }
    
    
}