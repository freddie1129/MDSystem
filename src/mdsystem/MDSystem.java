/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdsystem;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Random;

import java.util.Optional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.ParsePosition;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.FileHandler;

import javafx.geometry.Side;

import javafx.scene.shape.Circle;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import socketfx.FxSocketClient;

import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.event.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.BorderWidths;
import javafx.geometry.Insets;
import javafx.scene.text.FontWeight;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;

import socketfx.Constants;
import socketfx.FxSocketServer;
import socketfx.SocketListener;

import mdsystem.Monitor;
import mdsystem.MonitorBoolean;
import mdsystem.MonitorEnum;
import mdsystem.MonitorMax;
import mdsystem.MonitorMaxMin;
import mdsystem.MonitorMin;
import mdsystem.DBAdmin;
import mdsystem.MonitorVariable;
import mdsystem.UserManager;
import static mdsystem.globalParam.*;

import javafx.scene.control.Alert.AlertType;
import javafx.stage.WindowEvent;

import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.Level;
import javafx.beans.value.ChangeListener;

////////////////////////
//import java.util.AbstractMap.SimpleEntry;
//import java.util.Map.Entry;
//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.StackPane;
//import javafx.stage.Stage;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Text;
//import javafx.scene.text.Font;
//import javafx.scene.text.FontWeight;
//import javafx.geometry.Insets;
//import javafx.scene.paint.Color;
//import javafx.scene.control.Hyperlink;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.paint.LinearGradient;
//import javafx.scene.paint.CycleMethod;
//import javafx.geometry.Pos;
//import javafx.scene.paint.Stop;
//import javafx.scene.layout.Priority;
//import javafx.scene.layout.GridPane;
//import javafx.scene.image.ImageView;
//import javafx.scene.image.Image;
//import javafx.geometry.VPos;
//import javafx.scene.control.Label;
/**
 *
 * @author freddie
 */
class gobalParam {

    public gobalParam() {

    }
    public static boolean bLogin;
}

public class MDSystem extends Application {

    public enum ConnectionDisplayState {

        DISCONNECTED, WAITING, CONNECTED, AUTOCONNECTED, AUTOWAITING
    }
    private FxSocketClient socket;
    private DBAdmin database;
    // private FxSocketServer socket;
    private boolean isConnected;

    //  private final static Logger LOGGER
    //        = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private void connect() {
        //     socket = new FxSocketServer(new FxSocketListener(),
        //            2015,
        //             Constants.instance().DEBUG_NONE);
        //    socket.connect();
    }

    class FxSocketListener implements SocketListener {

        @Override
        public void onMessage(String line) {
            if (line != null && !line.equals("")) {
                System.out.println(line);
                //rcvdMsgsData.add(line);
            }
        }

        @Override
        public void onClosedStatus(boolean isClosed) {
        }
//
//        @Override
//        public void onClosedStatus(boolean isClosed) {
//            if (isClosed) {
//                isConnected = false;
//                if (true) { /*autoConnectCheckBox.isSelected()*/
//                    System.out.println("ConnectionDisplayState.AUTOWAITING");
//                   // displayState(ConnectionDisplayState.AUTOWAITING);
//                    connect();
//                } else {
//                     System.out.println("ConnectionDisplayState.DISCONNECTED");
//                    //displayState(ConnectionDisplayState.DISCONNECTED);
//                }
//            } else {
//                isConnected = true;
//                if (true/*autoConnectCheckBox.isSelected()*/) {
//                     System.out.println("ConnectionDisplayState.AUTOCONNECTED");
//                    //displayState(ConnectionDisplayState.AUTOCONNECTED);
//                } else {
//                     System.out.println("ConnectionDisplayState.CONNECTED");
//                    //displayState(ConnectionDisplayState.CONNECTED);
//                }
//            }
//        }
    }

    //TableView Variable    
    private final ObservableList<MonitorVariable> data = FXCollections.observableArrayList();

    final PageData[] pages = new PageData[]{
        new PageData("Apple",
        "The apple is the pomaceous fruit of the apple tree, species Malus "
        + "domestica in the rose family (Rosaceae). It is one of the most "
        + "widely cultivated tree fruits, and the most widely known of "
        + "the many members of genus Malus that are used by humans. "
        + "The tree originated in Western Asia, where its wild ancestor, "
        + "the Alma, is still found today.",
        "Malus domestica"),
        new PageData("Hawthorn",
        "The hawthorn is a large genus of shrubs and trees in the rose "
        + "family, Rosaceae, native to temperate regions of the Northern "
        + "Hemisphere in Europe, Asia and North America. "
        + "The name hawthorn was "
        + "originally applied to the species native to northern Europe, "
        + "especially the Common Hawthorn C. monogyna, and the unmodified "
        + "name is often so used in Britain and Ireland.",
        "Crataegus monogyna"),
        new PageData("Ivy",
        "The ivy is a flowering plant in the grape family (Vitaceae) native to"
        + " eastern Asia in Japan, Korea, and northern and eastern China. "
        + "It is a deciduous woody vine growing to 30 m tall or more given "
        + "suitable support,  attaching itself by means of numerous small "
        + "branched tendrils tipped with sticky disks.",
        "Parthenocissus tricuspidata"),
        new PageData("Quince",
        "The quince is the sole member of the genus Cydonia and is native to "
        + "warm-temperate southwest Asia in the Caucasus region. The "
        + "immature fruit is green with dense grey-white pubescence, most "
        + "of which rubs off before maturity in late autumn when the fruit "
        + "changes color to yellow with hard, strongly perfumed flesh.",
        "Cydonia oblonga")
    };

    final String[] viewOptions = new String[]{
        "Title",
        "Binomial name",
        "Picture",
        "Description"
    };

    public static void main(String[] args) {
        launch(args);
    }

    public String projectName;
    public String projectDate;
    public String projectInfo;
    public final List<String> listVariableName = new ArrayList<String>();

    private final Node rootIcon = new ImageView(
            new Image(getClass().getResourceAsStream("new.png"))
    );

    public MenuBar addMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        return menuBar;
    }
    final TableView<MonitorVariable> table = new TableView();
    final VBox vboxProject = new VBox();

    public HBox addDataHBox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(5, 10, 0, 10));
        hbox.setSpacing(10);

        //project properties information
        Label labName = new Label("Project Name:");
        labName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        labName.setTextFill(Color.BLUE);
        Text labelProjectName = new Text(projectName);
        labelProjectName.setFont(Font.font("Arial", FontWeight.NORMAL, 16));

        Separator separator1 = new Separator();
        separator1.setOrientation(Orientation.HORIZONTAL);

        Label labTime = new Label("Create Data:");
        labTime.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        labTime.setTextFill(Color.BLUE);
        Text labelProjectDate = new Text(projectDate);
        labelProjectDate.setFont(Font.font("Arial", FontWeight.NORMAL, 16));

        Separator separator2 = new Separator();
        separator2.setOrientation(Orientation.HORIZONTAL);

        Label labInfo = new Label("Information:");
        labInfo.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        labInfo.setTextFill(Color.BLUE);
        
        
        Label labelProjectInfo = new Label(projectInfo);
        labelProjectInfo.setMaxWidth(250);//, glblDay);
      //  labelProjec/tInfo.set
        //       labelProjectInfo.setMinSize(250,100);//, glblDay);
            //    labelProjectInfo.autosize();
      //  labelProjectInfo.setPadding(new Insets(,0,0,0));
        //labelProjectInfo.setWrapText(true);
        labelProjectInfo.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        //labelProjectInfo.setScrollTop(0);

        //Separator separator3 = new Separator();
        //separator3.setOrientation(Orientation.HORIZONTAL);
        //separator3.setPadding(new Insets(0, 0, 30, 0));

        //Label labWarning = new Label("WARNING!!!");
        //labWarning.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        //labWarning.setTextFill(Color.RED);

        vboxProject.getChildren().addAll(labName, labelProjectName, separator1,
                labTime, labelProjectDate, separator2,
                labInfo,labelProjectInfo
                );
        vboxProject.setBorder(new Border(new BorderStroke(Color.DARKGRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        vboxProject.setMinWidth(250);
        vboxProject.setSpacing(10);
        vboxProject.setPadding(new Insets(5, 10, 10, 10));
        vboxProject.setAlignment(Pos.BASELINE_CENTER);

        //Variable table 
        // final TableView table = new TableView();
        table.setEditable(false);

        TableColumn ColNum = new TableColumn("No");
        ColNum.setMinWidth(50);
        ColNum.setCellValueFactory(new PropertyValueFactory<>("ColNum"));

        TableColumn ColID = new TableColumn("ID");
        ColID.setMinWidth(80);
        ColID.setCellValueFactory(new PropertyValueFactory<>("ColID"));

        TableColumn ColName = new TableColumn("Name");
        ColName.setMinWidth(100);
        ColName.setCellValueFactory(new PropertyValueFactory<>("ColName"));

        TableColumn ColValue = new TableColumn("Value");
        ColValue.setMinWidth(100);
        ColValue.setCellValueFactory(new PropertyValueFactory<>("ColValue"));

        TableColumn ColMax = new TableColumn("Maximum");
        ColMax.setMinWidth(100);
        ColMax.setCellValueFactory(new PropertyValueFactory<>("ColMax"));

        TableColumn ColMin = new TableColumn("Minimum");
        ColMin.setMinWidth(100);
        ColMin.setCellValueFactory(new PropertyValueFactory<>("ColMin"));

        TableColumn ColOption = new TableColumn("Option");
        ColMin.setMinWidth(100);
        ColOption.setCellValueFactory(new PropertyValueFactory<>("ColEnum"));

        TableColumn ColUnit = new TableColumn("Unit");
        ColUnit.setMinWidth(100);
        ColUnit.setCellValueFactory(new PropertyValueFactory<>("ColUnit"));

        TableColumn ColInterval = new TableColumn("Unit");
        ColInterval.setMinWidth(100);
        ColInterval.setCellValueFactory(new PropertyValueFactory<>("ColInterval"));

        TableColumn ColInfo = new TableColumn("Info");
        ColInfo.setMinWidth(200);
        ColInfo.setCellValueFactory(new PropertyValueFactory<>("ColInfo"));

        ColValue.setCellFactory(column -> {
            return new TableCell<MonitorVariable, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty); //This is mandatory

                    if (item == null || empty) { //If the cell is empty
                        setText(null);
                        setStyle("");
                    } else { //If the cell is not empty

                        setText(item); //Put the String data in the cell

                        //We get here all the info of the Person of this row
                        MonitorVariable auxParam = getTableView().getItems().get(getIndex());

                        // Style all persons wich name is "Edgard"
                        String strTag = auxParam.getColID();
                        for (int i = 0; i < listMonitor.size(); i++) {
                            Monitor temMon = listMonitor.get(i);
                            if (strTag.equals(temMon.getID())) {
                                if (!temMon.getStatus()) {
                                    setTextFill(Color.RED); //The text in red
                                    //setStyle("-fx-background-color: yellow"); //The background of the cell in yellow
                                    //System.out.println("Set background");
                                } else {
                                    setTextFill(Color.BLACK); //The text in red
                                    //  setStyle("-fx-background-color: white"); //The background of the cell in yellow
                                    //System.out.println("Set background");
                                }
                            }
                        }

                        //    if (auxPerson.getFirstName().equals("Emma") && auxPerson.getLastName().equals("Jones")) {
                        //        setTextFill(Color.RED); //The text in red
                        //        setStyle("-fx-background-color: yellow"); //The background of the cell in yellow
                        //        System.out.println("Set background");
                        //    } else {
                        //        //Here I see if the row of this cell is selected or not
                        //        if(getTableView().getSelectionModel().getSelectedItems().contains(auxPerson))
                        //            setTextFill(Color.WHITE);
                        //        else
                        //            setTextFill(Color.BLACK);
                        //    }
                    }
                }
            };
        });

        table.setItems(data);
        table.getColumns().addAll(ColNum, ColID, ColName, ColValue, ColMin, ColMax, ColOption, ColUnit, ColInfo);

        //table.setMinSize(600, 550);
        // table.setMaxWidth(1030);
      //  hbox.getChildren().addAll(vboxProject, table);

        return hbox;
    }

    public ToolBar addToolBar() {
        ToolBar toolBar = new ToolBar(
                new Button("New"),
                new Button("Open"),
                new Button("Save"),
                //  new Separator(VPos.CENTER),
                new Button("Clean"),
                new Button("Compile"),
                new Button("Run"),
                // new Separator(VPos.CENTER),
                new Button("Debug"),
                new Button("Connect")
        );
        return toolBar;
    }

    public HBox addHBoxStatus() {
        HBox statusbar = new HBox();
        //  statusbar.setSpacing(5);

        statusbar.setMinHeight(20);
        //Label status_Time = new Label("Time");
        //Label status_Waring = new Label("OK");
        statusbar.setBorder(new Border(new BorderStroke(Color.DARKGRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        //statusbar.getChildren().addAll(status_Time, status_Waring);
        return statusbar;
    }

    public HBox addInfoHBox() {
        HBox hbox = new HBox();
        TabPane tabPane = new TabPane();

        ListView<String> listInfo = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList(
                "Single", "Double", "Suite", "Family App", "Family App", "Family App", "Family App", "Family App", "Family App", "Family App", "Family App", "Family App", "Family App", "Family App", "Family App", "Family App", "Family App");
        listInfo.setItems(items);

        ListView<String> listWarning = new ListView<>();
        ObservableList<String> Warningitems = FXCollections.observableArrayList(
                "Single999999999", "Double", "Suite", "Family App", "Family App", "Family App", "Family App", "Family App", "Family App", "Family App", "Family App", "Family App", "Family App", "Family App", "Family App", "Family App", "Family App");
        listWarning.setItems(Warningitems);

        Tab tabLog = new Tab();
        tabLog.setText("   Log   ");
        tabLog.setContent(listInfo);
        tabPane.getTabs().add(tabLog);
        Tab tabWarning = new Tab();
        tabWarning.setText("Warning");
        tabWarning.setContent(listWarning);
        tabPane.getTabs().add(tabWarning);

        tabPane.setSide(Side.BOTTOM);
        tabPane.setBorder(new Border(new BorderStroke(Color.DARKGRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        // Separator separator = new Separator();
        // separator.setMaxWidth(100);
        // separator.setHalignment(HPos.CENTER);
        //Border b = new Border();
        //separator.setBorder(Border. EMPTY);
        //for chart
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        //creating the chart
        final LineChart<Number, Number> lineChart
                = new LineChart<Number, Number>(xAxis, yAxis);

        //lineChart.setTitle("Stock Monitoring, 2010");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        //series.setName("My portfolio");
        //populating the series with data
        series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        series.getData().add(new XYChart.Data(6, 36));
        series.getData().add(new XYChart.Data(7, 22));
        series.getData().add(new XYChart.Data(8, 45));
        series.getData().add(new XYChart.Data(9, 43));
        series.getData().add(new XYChart.Data(10, 17));
        series.getData().add(new XYChart.Data(11, 29));
        series.getData().add(new XYChart.Data(12, 25));

        lineChart.setBorder(new Border(new BorderStroke(Color.DARKGRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        //lineChart.setPadding(new Insets(0,0,0,10));
        lineChart.getData().add(series);
        lineChart.setLegendVisible(false);

        //chart 2
        final NumberAxis xAxis2 = new NumberAxis();
        final NumberAxis yAxis2 = new NumberAxis();
        xAxis2.setLabel("Time");

        final LineChart<Number, Number> lineChart2
                = new LineChart<Number, Number>(xAxis2, yAxis2);

        XYChart.Series series2 = new XYChart.Series();
        //series.setName("My portfolio");
        //populating the series with data
        series2.getData().add(new XYChart.Data(1, 23));
        series2.getData().add(new XYChart.Data(2, 14));
        series2.getData().add(new XYChart.Data(3, 15));
        series2.getData().add(new XYChart.Data(4, 24));
        series2.getData().add(new XYChart.Data(5, 34));
        series2.getData().add(new XYChart.Data(6, 36));
        series2.getData().add(new XYChart.Data(7, 22));
        series2.getData().add(new XYChart.Data(8, 45));
        series2.getData().add(new XYChart.Data(9, 43));
        series2.getData().add(new XYChart.Data(10, 17));
        series2.getData().add(new XYChart.Data(11, 29));
        series2.getData().add(new XYChart.Data(12, 25));

        lineChart2.setBorder(new Border(new BorderStroke(Color.DARKGRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        lineChart2.getData().add(series2);
        lineChart2.setLegendVisible(false);
        ///////////////////////////////
        lineChart.setPrefWidth(450);
        lineChart2.setPrefWidth(450);
        tabPane.setPrefWidth(400);

        hbox.setSpacing(5);
        hbox.setPadding(new Insets(0, 0, 0, 5));
        hbox.getChildren().addAll(lineChart, lineChart2, tabPane);
        return hbox;
    }

    public ListView<String> listLog = new ListView<>();
    public ObservableList<String> loglist;

    public ListView<String> listviewtable = new ListView<>();
    public ObservableList<String> listtable;

    public void log(String str) {
        LOGGER.info(str);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String strLog = dateFormat.format(date) + " " + str;
        loglist.add(strLog);
    }

    @Override
    public void start(Stage primaryStage) {

        loglist = FXCollections.observableArrayList();
        try {
            Handler handler = new FileHandler("test.log", 10000, 2);
            LOGGER.getLogger("").addHandler(handler);
        } catch (Exception e) {
            System.out.println(e);
        }

        primaryStage.setTitle("Environment Monitoring & Displaying System");
        //database Admin
        database = new DBAdmin(this);
        DialogTest dt = new DialogTest(primaryStage);
        dt.showAndWait();
        
                
        String projectFile = "project.xml";
        if (!initTableView(projectFile)) {
            log("Fail to load project.xml.");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Loading Error");
            String strLog = String.format("Fail to load Project Layout File: %s", "project.xml");
            alert.setHeaderText("Loading Error");
            alert.setContentText(strLog);
            alert.showAndWait();
            return;
        }
        log("Load project.xml");
        database.addTable(data);
        DialogLogin myDialog = new DialogLogin(primaryStage);
        myDialog.setDB(database);
        myDialog.sizeToScene();
        myDialog.showAndWait();
        if (!bglbLogin) {
            // This is a unsucessful login, just exit;
            database.closeDB();
            return;
        }

        VBox rootHbox = new VBox();
        rootHbox.setSpacing(5);
        rootHbox.setPadding(new Insets(0, 0, 2, 0));

        HBox dataHBox = addDataHBox();

        HBox statusHBox = addHBoxStatus();

        HBox infoHBox = new HBox();

        listLog.setItems(loglist);

        GridPane gridLog = new GridPane();
        gridLog.setAlignment(Pos.CENTER);
        gridLog.setHgap(5);
        gridLog.setVgap(5);
        gridLog.setPadding(new Insets(5, 5, 5, 5));

        Label labelLog = new Label("Log:");
        Button buttonClearLog = new Button("Clear");
        HBox hboxClear = new HBox();
        hboxClear.setPadding(new Insets(0, 5, 0, 0));
        hboxClear.getChildren().add(buttonClearLog);
        hboxClear.setAlignment(Pos.CENTER_RIGHT);

        HBox hboxloglabel = new HBox();
        hboxloglabel.setSpacing(10);
        hboxloglabel.setAlignment(Pos.CENTER_LEFT);
        hboxloglabel.setPadding(new Insets(5, 5, 5, 5));
        hboxloglabel.getChildren().add(labelLog);

        gridLog.setHgap(5);
        gridLog.setVgap(5);
        gridLog.setPadding(new Insets(5, 5, 5, 5));

        gridLog.add(hboxloglabel, 0, 0);
        gridLog.add(hboxClear, 1, 0);
        gridLog.add(listLog, 0, 1, 2, 1);

        Separator separator1 = new Separator();
        separator1.setOrientation(Orientation.HORIZONTAL);
        separator1.setPadding(new Insets(5, 10, 0, 10));

        Label labelServerAddress = new Label("Server Address:");
        final TextField textServerAddress = new TextField(glbstrServerAddress);
        Label labelServerPort = new Label("Port:");
        final TextField textServerPort = new TextField(glbstrServerPort);
        Button btnConnect = new Button("Connect");
        Button btnDisConnect = new Button("Disconnect");
        Button btnClearError = new Button("ClearWarning");
        Button btnUserManagement = new Button("Management");
        btnConnect.setPrefWidth(120);
        btnDisConnect.setPrefWidth(120);
        btnClearError.setPrefWidth(120);
        btnUserManagement.setPrefWidth(120);
        HBox hboxNetManage = new HBox();
        hboxNetManage.setSpacing(10);
        hboxNetManage.setAlignment(Pos.CENTER_LEFT);
        hboxNetManage.setPadding(new Insets(10, 25, 10, 25));
        hboxNetManage.getChildren().addAll(labelServerAddress, textServerAddress,
                labelServerPort, textServerPort,
                btnConnect, btnDisConnect, btnClearError, btnUserManagement);
        btnDisConnect.setDisable(true);
        HBox testHBox = new HBox();
        Button btnSelectDB = new Button("selectdb");
        Button btnCloseDB = new Button("closedb");
        Button btnTest = new Button("test");
        //testHBox.getChildren().addAll(btnClearError, btnSelectDB, btnCloseDB, btnTest);

        VBox vboxSQL = new VBox();
        GridPane gridpane = new GridPane();
        gridpane.setAlignment(Pos.CENTER);
        gridpane.setHgap(0);
        gridpane.setVgap(5);
        gridpane.setPadding(new Insets(0, 0, 0, 0));
        Label labelTag = new Label("Tag");
        ObservableList<String> optionsTag = FXCollections.observableArrayList();
        for (int i = 0; i < data.size(); i++) {
            optionsTag.add(data.get(i).getColID());
        }
        final ComboBox comboBox = new ComboBox(optionsTag);
        if (data.size() >= 1) {
            comboBox.setValue(data.get(0).getColID());
        }
        final TextField textStartTime = new TextField();
        textStartTime.setPrefColumnCount(20);
        textStartTime.setPrefWidth(200);
        textStartTime.setPromptText("YYYY-MM-DD hh:mm:ss");
        final TextField textEndTime = new TextField();
        textEndTime.setPrefColumnCount(20);
        textEndTime.setPrefWidth(200);
        textEndTime.setPromptText("YYYY-MM-DD hh:mm:ss");
        final CheckBox chkStart = new CheckBox("Start");
        final CheckBox chkEnd = new CheckBox("End");
        final CheckBox chkOnly = new CheckBox("Warning");
        final Button butSql = new Button("Query");
        HBox hboxSql = new HBox();
        hboxSql.setSpacing(10);
        hboxSql.setAlignment(Pos.CENTER_LEFT);
        hboxSql.setPadding(new Insets(5, 5, 5, 5));
        hboxSql.getChildren().addAll(labelTag, comboBox, chkStart, textStartTime, chkEnd, textEndTime, chkOnly, butSql);
        vboxSQL.getChildren().addAll(hboxSql, listviewtable);
        infoHBox.getChildren().addAll(gridLog, vboxSQL);

        chkStart.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (chkStart.isSelected()) {
                Date preDay = new Date((new Date()).getTime() - glblDay);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                textStartTime.setText(formatter.format(preDay).toString());
            } else {
                textStartTime.setText("");
            }
        });

        chkEnd.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            // chkStart.setSelected(!newValue);

            if (chkEnd.isSelected()) {
                Date preDay = new Date((new Date()).getTime());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                textEndTime.setText(formatter.format(preDay).toString());
            } else {
                textEndTime.setText("");
            }
        });

//        		final VBox vBox = new VBox();
//		vBox.getChildren().add(new Label("Date/Time"));
//		vBox.getChildren().add(new DateTimePicker());
        //   Label labelStart = new Label("Start");
        //   Label labelEnd = new Label("time");
        //                 final TextField userNameFld = new TextField("admin");
        //   gridpane.add(userNameLbl, 0, 1);
        //   final TextField userNameFld = new TextField("admin");
        //  infoHBox.getChildren().add(gridpane);
        //infoHBox.setSpacing(0);
        // infoHBox.setPadding(new Insets(0,5,0,5));
        // rootHbox.setPadding(new Insets(5,15,5,5));
        //  btnUserManagement
        btnUserManagement.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                UserManager um = new UserManager(primaryStage, database);
                log("Enter user management.");
                um.showAndWait();

            }
        });

        MDSystem sysTem = this;
        btnConnect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                socket = new FxSocketClient(new FxSocketListener(),
                        glbstrServerAddress,
                        Integer.parseInt(glbstrServerPort),
                        Constants.instance().DEBUG_SEND);
                socket.SetGui(sysTem);
                if (socket.connect1())
                {
                    String str = String.format("Connect server %s:%s",glbstrServerAddress,glbstrServerPort);
                    log(str);
                    socket.startReading();
                     btnDisConnect.setDisable(false);
                     btnConnect.setDisable(true);                     
                }
                else
                {
                    String str = String.format("Fail to connect server %s:%s",glbstrServerAddress,glbstrServerPort);
                    log(str);
                    return;
                }
                
            }
        });
        listtable = FXCollections.observableArrayList();
        database.setSelectList(listtable);
        listviewtable.setItems(listtable);
        butSql.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String s;
                Date datestart = new Date();
                Date dateend = new Date();
                // dateend.
                String strStartTime;
                String strEndTime;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (chkStart.isSelected()) {
                    //datestart = formatter.parse("2017-06-21 00:00:00");
                    try {
                        datestart = formatter.parse(textStartTime.getText());
                        strStartTime = textStartTime.getText();
                    } catch (ParseException ex) {
                        Logger.getLogger(MDSystem.class.getName()).log(Level.SEVERE, null, ex);
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Input Error");
                        String strLog = String.format("Start time %s is illegal", textStartTime.getText());
                        alert.setHeaderText("Input Error");
                        alert.setContentText(strLog);
                        alert.showAndWait();
                        return;
                    }
                } else {
                    strStartTime = "";
                }

                if (chkEnd.isSelected()) {
                    //datestart = formatter.parse("2017-06-21 00:00:00");
                    try {
                        dateend = formatter.parse(textEndTime.getText());
                        strEndTime = textEndTime.getText();
                    } catch (ParseException ex) {
                        Logger.getLogger(MDSystem.class.getName()).log(Level.SEVERE, null, ex);
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Input Error");
                        String strLog = String.format("End time %s is illegal", textStartTime.getText());
                        alert.setHeaderText("Input Error");
                        alert.setContentText(strLog);
                        alert.showAndWait();
                        return;
                    }
                } else {
                    strEndTime = "";
                }
                boolean bOnlywarning = chkOnly.isSelected();
                String strTag = comboBox.getSelectionModel().getSelectedItem().toString();
                listtable.clear();
                database.select(strTag, listMonitor, bOnlywarning, strStartTime, strEndTime);

                //    database.select("A001",listMonitor,);
                // listtable.add("add");
            }
        });

        btnSelectDB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                MonitorVariable var = table.getSelectionModel().getSelectedItem();
                // database.select(var.getColID());
                //System.out.println(var.getColID());
            }
        });

        btnCloseDB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                database.closeDB();
            }
        });

        btnTest.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                //double d = 89.999;
                //database.addData("A001", d);
                //loglist.add("DDDDDDDDDDDDdd");
                log("dad");
            }
        });

        btnDisConnect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                socket.shutdown();
                btnDisConnect.setDisable(true);
                btnConnect.setDisable(false);
                //loglist.clear();
            }
        });

        btnClearError.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                for (int i = 0; i < listMonitor.size(); i++) {
                    listMonitor.get(i).clearError();

                }

            }
        });

        rootHbox.setPadding(new Insets(5, 5, 5, 5));
        rootHbox.setSpacing(10);
        hboxNetManage.setPadding(new Insets(10, 5, 10, 5));
        hboxNetManage.setSpacing(10);
        dataHBox.setPadding(new Insets(0, 5, 0, 5));
        dataHBox.setSpacing(10);
        table.setPadding(new Insets(0, 0, 0, 0));
        // table.setMaxSize(720, 250);
        separator1.setPadding(new Insets(0, 5, 0, 5));
        infoHBox.setPadding(new Insets(0, 5, 0, 5));
        infoHBox.setSpacing(10);

        gridLog.setHgap(0);
        gridLog.setVgap(5);
        gridLog.setPadding(new Insets(0, 0, 0, 0));
        hboxloglabel.setPadding(new Insets(0, 0, 0, 0));
        hboxloglabel.setSpacing(10);
        hboxClear.setPadding(new Insets(0, 5, 0, 0));
        hboxClear.setSpacing(10);
        vboxSQL.setSpacing(5);
        vboxSQL.setPadding(new Insets(0, 0, 0, 0));
        hboxSql.setPadding(new Insets(0, 0, 0, 0));
        hboxSql.setSpacing(10);

        table.setBorder(new Border(new BorderStroke(Color.DARKGRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        hboxNetManage.setBorder(new Border(new BorderStroke(Color.DARKGRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        GridPane mainPane = new GridPane();
        mainPane.setHgap(10);
        mainPane.setVgap(10);
        mainPane.setPadding(new Insets(0, 0, 0, 0));
        mainPane.add(vboxProject, 0, 0);
        mainPane.add(table, 1, 0);
        mainPane.add(separator1, 0, 1, 2, 1);
        mainPane.add(gridLog, 0, 2);
        mainPane.add(vboxSQL, 1, 2);

        //  rootHbox.getChildren().addAll(hboxNetManage,/*testHBox,*/dataHBox, separator1,infoHBox, statusHBox);
        rootHbox.getChildren().addAll(hboxNetManage, mainPane, statusHBox);
        Scene scene = new Scene(rootHbox, 1200, 700);

        primaryStage.setScene(scene);
        // primaryStage.setFullScreen(true);

        connect();
        primaryStage.show();
    }

    public void UpdataTable(String tag, String value) {
        for (int i = 0; i < data.size(); i++) {
            String t = data.get(i).getColID();
            System.out.println(t);
            if (data.get(i).getColID().equals(tag)) {
                data.get(i).setColValue(value);
                table.refresh();
                return;
            }
        }
        System.out.printf("No item tagged by: %s\n", tag);
    }

    public void UpdataTable(String tag, byte[] b) {

        for (int i = 0; i < data.size(); i++) {
            String t = data.get(i).getColID();
            System.out.println(t);
            if (data.get(i).getColID().equals(tag)) {

                String log = listMonitor.get(i).getDisplayStr(b);
                listMonitor.get(i).varify(b);
                System.out.printf("%s:%s", tag, log);
                data.get(i).setColValue(log);
                if (listMonitor.get(i).type == Monitor.Type.ENUM) {
                    int a = Integer.parseInt(log);
                    database.addData(tag, a);
                } else if (listMonitor.get(i).type == Monitor.Type.YESNO) {
                    int a = log.equals("Normal") ? 1 : 0;
                    database.addData(tag, a);
                } else {
                    database.addData(tag, Double.parseDouble(log));
                }
                table.refresh();
                return;
            }
        }
        System.out.printf("No item tagged by: %s\n", tag);
    }

    public void UpdataTable() {
        Random randomGenerator = new Random();
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setColValue(Integer.toString(randomGenerator.nextInt(100)));
        }

    }

    public String GetProjectInfo() {
        return "This project is for Apple Q1 building!";
    }

    private class PageData {

        public String name;
        public String description;
        public String binNames;
        public Image image;

        public PageData(String name, String description, String binNames) {
            this.name = name;
            this.description = description;
            this.binNames = binNames;
            image = new Image(getClass().getResourceAsStream(name + ".jpg"));
        }
    }

    public List<Monitor> listMonitor = new ArrayList<Monitor>();

    private Monitor GenMonitor(String strID, String strName, String strUnit, String strInfo, String strMin, String strMax,
            String strEnum) {
        boolean bMin, bMax;
        double dMin, dMax;
        dMin = 0;
        dMax = 0;
        bMin = true;
        try {
            dMin = Double.parseDouble(strMin);
        } catch (NumberFormatException e) {
            bMin = false;
        }
        bMax = true;
        try {
            dMax = Double.parseDouble(strMax);
        } catch (NumberFormatException e) {
            bMax = false;
        }

        if (bMax && bMin) {
            //MinMax mode
            MonitorMaxMin Mon = new MonitorMaxMin(strID, strName, strUnit, strInfo, dMin, dMax);
            return Mon;
        } else if (bMax && !bMin) {
            //Max mode
            MonitorMax Mon = new MonitorMax(strID, strName, strUnit, strInfo, dMax);
            return Mon;
        } else if (!bMax && bMin) {
            //Min mode
            MonitorMin Mon = new MonitorMin(strID, strName, strUnit, strInfo, dMin);
            return Mon;
        } else if (!bMax && !bMin) {
            if (strEnum.equals("null")) {
                //Boolean mode
                MonitorBoolean Mon = new MonitorBoolean(strID, strName, strUnit, strInfo);
                return Mon;
            } else {
                //Enumate mode
                String[] arrStr = strEnum.split(",");
                int[] arrEnum = new int[arrStr.length];
                for (int i = 0; i < arrStr.length; i++) {
                    try {
                        arrEnum[i] = Integer.parseInt(arrStr[i]);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Parsing Enumate");
                        return null;
                    }
                }
                MonitorEnum Mon = new MonitorEnum(strID, strName, strUnit, strInfo, arrEnum);
                return Mon;
            }
        }
        return null;
    }

    public boolean initTableView(String projectFile) {
        try {

            File fXmlFile = new File(projectFile);
            if (!fXmlFile.exists()) {
                return false;
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            projectName = doc.getElementsByTagName("projectname").item(0).getTextContent();
            projectDate = doc.getElementsByTagName("projecttime").item(0).getTextContent();
            projectInfo = doc.getElementsByTagName("projectInfo").item(0).getTextContent();

//            System.out.println("projectName : " + projectName);
//            System.out.println("projectDate : " + projectDate);
//            System.out.println("projectInfe : " + projectInfo);
            NodeList nList = doc.getElementsByTagName("variable");

            String strID, strName, strMin, strMax, strEnum, strUnit, strInfo;

            for (int temp = 0; temp < nList.getLength(); temp++) {
                org.w3c.dom.Node nNode = nList.item(temp);
                if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    //System.out.println("Staff id : " + eElement.getAttribute("id"));
                    //System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
                    //System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
                    //System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
                    //System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());
                    strID = eElement.getElementsByTagName("VarId").item(0).getTextContent();
                    strName = eElement.getElementsByTagName("VarName").item(0).getTextContent();
                    strMax = eElement.getElementsByTagName("VarMax").item(0).getTextContent();
                    strMin = eElement.getElementsByTagName("VarMin").item(0).getTextContent();
                    strUnit = eElement.getElementsByTagName("VarUnit").item(0).getTextContent();
                    strEnum = eElement.getElementsByTagName("VarEnum").item(0).getTextContent();
                    strInfo = eElement.getElementsByTagName("VarInfo").item(0).getTextContent();

                    Monitor tempMon = GenMonitor(strID, strName, strUnit, strInfo, strMin, strMax, strEnum);
                    if (tempMon != null) {
                        listMonitor.add(tempMon);
                    }

                    data.add(new MonitorVariable(
                            strID,
                            strName,
                            "",
                            strMin,
                            strMax,
                            strEnum,
                            strUnit,
                            strInfo));
                    listVariableName.add(eElement.getElementsByTagName("VarId").item(0).getTextContent() + eElement.getElementsByTagName("VarName").item(0).getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //Monitoring Variable
}


/*    GridPane gridProjectInfo = new GridPane();
        gridProjectInfo.setHgap(10);
        gridProjectInfo.setVgap(10);
        gridProjectInfo.setPadding(new Insets(0, 10, 0, 10));
        
        Text labelProjectName = new Text(projectName);
        Text labelProjectDate = new Text(projectDate);
        Text labelProjectInfo = new Text(projectInfo);
        gridProjectInfo.add(labelProjectName, 0, 0);
        gridProjectInfo.add(labelProjectDate, 1, 0);
        gridProjectInfo.add(labelProjectInfo, 0, 1, 2, 1);
       
        
        
        GridPane gridInfo = new GridPane();
        gridInfo.setHgap(10);
        gridInfo.setVgap(10);
        gridInfo.setPadding(new Insets(0, 10, 0, 10));
        Label labLog = new Label("Info");
        Label labWarning = new Label("Warning");

        
 */
//        ListView<String> listInfo = new ListView<>();
//ObservableList<String> items =FXCollections.observableArrayList (
//    "Single", "Double", "Suite", "Family App","Family App","Family App","Family App","Family App","Family App","Family App","Family App","Family App","Family App","Family App","Family App","Family App","Family App");
//listInfo.setItems(items);
//
//
//        ListView<String> listWarning = new ListView<>();
//ObservableList<String> Warningitems =FXCollections.observableArrayList (
//    "Single999999999", "Double", "Suite", "Family App","Family App","Family App","Family App","Family App","Family App","Family App","Family App","Family App","Family App","Family App","Family App","Family App","Family App");
//listWarning.setItems(Warningitems);
//        ListView<String> listProjectInfo = new ListView<>();
//ObservableList<String> ProjectInfoitems =FXCollections.observableArrayList (
//    "Single", "Double", "Suite", "Family App","Family App","Family App","Family App","Family App","Family App","Family App","Family App","Family App","Family App","Family App","Family App","Family App","Family App");
//listProjectInfo.setItems(ProjectInfoitems);
//        gridInfo.add(labLog, 0, 0);
//        gridInfo.add(labWarning, 1, 0);
//        gridInfo.add(listInfo, 0, 1);
//        gridInfo.add(listWarning, 1, 1);
//        
//        TreeItem<String> rootItem = new TreeItem<> ("Project", rootIcon);
//        rootItem.setExpanded(true);
//        for (int i = 0; i < listVariableName.size(); i++) {
//            TreeItem<String> item = new TreeItem<> (listVariableName.get(i));            
//            rootItem.getChildren().add(item);
//        }        
//        TreeView<String> tree = new TreeView<> (rootItem);
//        Label labDisplay = new Label("Project Information");
//        labDisplay.setFont(new Font("Arial", 20));      
//Create Table View
//        final TableView table = new TableView();
//        table.setEditable(false);
//        
//        TableColumn ColNum = new TableColumn("No");
//        ColNum.setMinWidth(100);
//        ColNum.setCellValueFactory(new PropertyValueFactory<>("ColNum"));
//        
//        TableColumn ColID = new TableColumn("ID");
//        ColID.setMinWidth(100);
//        ColID.setCellValueFactory(new PropertyValueFactory<>("ColID"));
//        
//        
//        TableColumn ColName = new TableColumn("Name");
//        ColName.setMinWidth(100);
//        ColName.setCellValueFactory(new PropertyValueFactory<>("ColName"));
//        
//        TableColumn ColValue = new TableColumn("Value");
//        ColValue.setMinWidth(100);
//        ColValue.setCellValueFactory(new PropertyValueFactory<>("ColValue"));
//        
//        TableColumn ColMax = new TableColumn("Maximum");
//        ColMax.setMinWidth(100);
//        ColMax.setCellValueFactory(new PropertyValueFactory<>("ColMax"));
//        
//        TableColumn ColMin = new TableColumn("Minimum");
//        ColMin.setMinWidth(100);
//        ColMin.setCellValueFactory(new PropertyValueFactory<>("ColMin"));
//        
//        TableColumn ColUnit = new TableColumn("Unit");
//        ColUnit.setMinWidth(100);
//        ColUnit.setCellValueFactory(new PropertyValueFactory<>("ColUnit"));
//        
//        TableColumn ColInterval = new TableColumn("Unit");
//        ColInterval.setMinWidth(100);
//        ColInterval.setCellValueFactory(new PropertyValueFactory<>("ColInterval"));
//        
//        TableColumn ColInfo = new TableColumn("Info");
//        ColInfo.setMinWidth(100);
//        ColInfo.setCellValueFactory(new PropertyValueFactory<>("ColInfo"));      
//        
//
//        
//        table.setItems(data);
//        
//        table.getColumns().addAll(ColNum,ColID, ColName, ColValue,ColMax,ColMin,ColUnit,ColInterval,ColInfo);
//        
//        
//        //tool bar
//         ToolBar toolBar = new ToolBar(
//     new Button("New"),
//     new Button("Open"),
//     new Button("Save"),
//   //  new Separator(VPos.CENTER),
//     new Button("Clean"),
//     new Button("Compile"),
//     new Button("Run"),
//    // new Separator(VPos.CENTER),
//     new Button("Debug"),
//     new Button("Profile")
// );
//       border.setTop(toolBar);
//final HBox hbox1 = new VBox();
//        final VBox vboxCentral = new VBox();
//        vboxCentral.setSpacing(0);
//        vboxCentral.setPadding(new Insets(10, 0, 0, 10));
//    final VBox vboxLeft = new VBox();
//    vboxLeft.setSpacing(0);
//    vboxLeft.setPadding(new Insets(10, 0, 0, 10));
//    vboxLeft.getChildren().addAll(tree,listProjectInfo);
//   border.setLeft(vboxLeft);
//        HBox statusbar = new HBox();
//        Label status_Time = new Label("Time");
//        Label status_Waring = new Label("OK");
//        statusbar.getChildren().addAll(status_Time,status_Waring);
//   border.setBottom(statusbar);
//        TabPane tabPane = new TabPane();
//        Tab tabLog = new Tab();
//        tabLog.setText("Log Info");
//        tabLog.setContent(listInfo);
//       tabPane.getTabs().add(tabLog);        
//        Tab tabWarning = new Tab();
//        tabWarning.setText("Warning Info");
//        tabWarning.setContent(listWarning);
//        tabPane.getTabs().add(tabWarning);
//    
//        tabPane.setSide(Side.BOTTOM);
//        
//        
//        Separator separator = new Separator();
//        separator.setMaxWidth(100);
//        separator.setHalignment(HPos.CENTER);
//Border b = new Border();
//separator.setBorder(Border. EMPTY);
//for chart
//        final NumberAxis xAxis = new NumberAxis();
//        final NumberAxis yAxis = new NumberAxis();
//        xAxis.setLabel("Number of Month");
//        //creating the chart
//        final LineChart<Number,Number> lineChart = 
//                new LineChart<Number,Number>(xAxis,yAxis);
//                
//        lineChart.setTitle("Stock Monitoring, 2010");
//        //defining a series
//        XYChart.Series series = new XYChart.Series();
//        series.setName("My portfolio");
//        //populating the series with data
//        series.getData().add(new XYChart.Data(1, 23));
//        series.getData().add(new XYChart.Data(2, 14));
//        series.getData().add(new XYChart.Data(3, 15));
//        series.getData().add(new XYChart.Data(4, 24));
//        series.getData().add(new XYChart.Data(5, 34));
//        series.getData().add(new XYChart.Data(6, 36));
//        series.getData().add(new XYChart.Data(7, 22));
//        series.getData().add(new XYChart.Data(8, 45));
//        series.getData().add(new XYChart.Data(9, 43));
//        series.getData().add(new XYChart.Data(10, 17));
//        series.getData().add(new XYChart.Data(11, 29));
//        series.getData().add(new XYChart.Data(12, 25));
//        
//
//        lineChart.getData().add(series);
//        
//        
//        
//        //chart 2
//        final LineChart<Number,Number> lineChart2 = 
//                new LineChart<Number,Number>(xAxis,yAxis);
//                
//        lineChart2.setTitle("Stock Monitoring, 2010");
//        //defining a series
//             
//
//        lineChart2.getData().add(series);
//        ///////////////////////////////
//       
//        HBox hboxInfo = new HBox();
//        hboxInfo.getChildren().addAll(lineChart,lineChart2,tabPane);
//        
//        
//        HBox hboxTable = new HBox();
//        hboxTable.getChildren().addAll(listProjectInfo,table);
// vboxCentral.getChildren().addAll(hboxTable,separator,hboxInfo);
//border.setTop(gridProjectInfo);
// border.setCenter(vboxCentral);
//     Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("this is called every 1 seconds on UI thread");
//                UpdataTable();
//                table.refresh();
//          //      table.setItems(data);
//                 
//        //table.getColumns().addAll(ColNum,ColID, ColName, ColValue,ColMax,ColMin,ColUnit,ColInterval,ColInfo);
//        
//                
//            }
//        }));
//        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
//        fiveSecondsWonder.play();
//        
//        primaryStage.setScene(scene);
//        primaryStage.setFullScreen(true);
//        primaryStage.show();
//    }
//        public HBox addHBox() {
//        HBox hbox = new HBox();
//        hbox.setPadding(new Insets(15, 12, 15, 12));
//        hbox.setSpacing(10);
//        hbox.setStyle("-fx-background-color: #336699;");
//        Button buttonCurrent = new Button("Current");
//        buttonCurrent.setPrefSize(100, 20);
//        Button buttonProjected = new Button("Projected");
//        buttonProjected.setPrefSize(100, 20);
//        hbox.getChildren().addAll(buttonCurrent, buttonProjected);
//        return hbox;
//    }
//class DialogLogin extends Stage {
//
//    public DialogLogin(Stage owner) {
//        super();
//        initOwner(owner);
//        setTitle("Login");
//        Group root = new Group();
//        Scene scene = new Scene(root, 320, 220, Color.WHITE);
//        setScene(scene);
//
//        GridPane gridpane = new GridPane();
//
//        gridpane.setAlignment(Pos.CENTER);
//        gridpane.setHgap(10);
//        gridpane.setVgap(10);
//        gridpane.setPadding(new Insets(25, 25, 25, 25));
//
//        Text scenetitle = new Text("Welcome");
//        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
//        gridpane.add(scenetitle, 0, 0, 2, 1);
//
//        Label userNameLbl = new Label("Username: ");
//        gridpane.add(userNameLbl, 0, 1);
//        final TextField userNameFld = new TextField("admin");
//        gridpane.add(userNameFld, 1, 1);
//
//        Label passwordLbl = new Label("Password: ");
//        gridpane.add(passwordLbl, 0, 2);
//        final PasswordField passwordFld = new PasswordField();
//        passwordFld.setText("admin");
//        gridpane.add(passwordFld, 1, 2);
//
//        Button login = new Button("Login");
//        Button cancellogin = new Button("Cancel");
//        Label hint = new Label("hints for test: username:admin,password:admin");
//        login.setOnAction(new EventHandler<ActionEvent>() {
//
//            public void handle(ActionEvent event) {
//                int ret = dbase.loginCheck(userNameFld.getText(), passwordFld.getText());
//                switch (ret) {
//                    case 1: {
//                        bglbLogin = true;
//                        glbCurUser = userNameFld.getText();
//                        close();
//                        break;
//                    }
//                    case -1: {
//                        bglbLogin = false;
//                        Alert alert = new Alert(AlertType.ERROR);
//                        alert.setTitle("Login Error");
//                        String strLog = String.format("Username %s is not exist.", userNameFld.getText());
//                        alert.setHeaderText("Logining Error");
//                        alert.setContentText(strLog);
//                        alert.showAndWait();
//                        break;
//                    }
//                    case 0: {
//                        bglbLogin = false;
//                        Alert alert = new Alert(AlertType.ERROR);
//                        alert.setTitle("Login Error");
//                        String strLog = String.format("Password is not matched by Username: %s", userNameFld.getText());
//                        alert.setHeaderText("Logining Error");
//                        alert.setContentText(strLog);
//                        alert.showAndWait();
//                        break;
//                    }
//                    default:
//                        break;
//                }
//            }
//        });
//
//        cancellogin.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent event) {
//                bglbLogin = false;
//                close();
//            }
//        });
//
//        HBox logBox = new HBox();
//        logBox.setSpacing(5);
//        logBox.setAlignment(Pos.BOTTOM_RIGHT);
//        logBox.getChildren().addAll(login, cancellogin);
//
//        gridpane.add(logBox, 1, 4);
//        gridpane.add(hint, 0, 5,2,2);
//        
//        GridPane.setHalignment(login, HPos.RIGHT);
//        root.getChildren().add(gridpane);
//    }
//
//    private DBAdmin dbase;
//
//    public void setDB(DBAdmin db) {
//        dbase = db;
//    }
//}
