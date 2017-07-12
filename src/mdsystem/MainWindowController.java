/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdsystem;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import static mdsystem.Staff.num;
import static mdsystem.globalParam.glbcuffStaff;
import static mdsystem.globalParam.glbstrServerAddress;
import static mdsystem.globalParam.glbstrServerPort;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import socketfx.Constants;
import socketfx.FxSocketClient;
import socketfx.SocketListener;


/**
 *
 * @author freddie
 */

public class MainWindowController implements Initializable{
    @FXML TableView tableviewMonitor;
    @FXML TableView tableviewLog;
    @FXML Button buttonManage;
    @FXML Button buttonConnect;
    @FXML Button buttonDisConnect;
     
    ////


     
    private final ObservableList<MonitorVariable> viewdata = FXCollections.observableArrayList();
    public final List<String> listVariableName = new ArrayList<String>();
    public List<Monitor> listMonitor = new ArrayList<Monitor>();
    private DBAdmin database;
        private FxSocketClient socket;
    
    
    private final ObservableList<LogItem> logData = FXCollections.observableArrayList();
    
    
    
    public void setMonitorList(List<Monitor> list)
    {
        listMonitor = list;
    }
    
    public void log(String strlog)
    {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        LogItem log = new LogItem(dateFormat.format(date),strlog);
        logData.add(log);
    }
    

    
    
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
            database = new DBAdmin(this);

            String projectFile = "project.xml";
            if (!LoadingLayout(projectFile)) {
                log("Fail to load project.xml.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Loading Error");
                String strLog = String.format("Fail to load Project Layout File: %s", "project.xml");
                alert.setHeaderText("Loading Error");
                alert.setContentText(strLog);
                alert.showAndWait();
                return;
            }
            log("Load project.xml sucessfully.");
            database.addTable(viewdata);

            if (!showDialogLogin()) {
                return;
            }
                
                
         //Initiate tableview Log
        tableviewLog.setEditable(false);
        TableColumn colLogTime = new TableColumn("Time");
        colLogTime.setMinWidth(70);
         colLogTime.setCellValueFactory(new PropertyValueFactory<>("strTime"));
        TableColumn colLogComment = new TableColumn("Log");
        colLogComment.setMinWidth(600);
        colLogComment.setCellValueFactory(new PropertyValueFactory<>("strComment"));
        tableviewLog.setItems(logData);
        tableviewLog.getColumns().addAll(colLogTime, colLogComment);

         
        //Initiate tableview
        tableviewMonitor.setEditable(false);
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
        ColValue.setMinWidth(150);
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
                                    setTextFill(Color.RED); 
                                } else {
                                    setTextFill(Color.BLACK); 
                                }
                            }
                        }
                    }
                }
            };
        });

        tableviewMonitor.setItems(viewdata);
        tableviewMonitor.getColumns().addAll(ColNum, ColID, ColName, ColValue, ColMin, ColMax, ColOption, ColUnit, ColInfo);
    
        buttonDisConnect.setDisable(true);
        buttonConnect.setDisable(false);
    }
    
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

    public boolean LoadingLayout(String projectFile) {
        try {

            File fXmlFile = new File(projectFile);
            if (!fXmlFile.exists()) {
                return false;
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
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

                    viewdata.add(new MonitorVariable(
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
    
    @FXML
    protected void handleButtonManage(ActionEvent event) {
        final Stage primaryStage = (Stage) buttonManage.getScene().getWindow();
        UserManager um = new UserManager(primaryStage, database);
        um.setMainWindows(this);
                log("Enter User Management.");
                um.showAndWait();
                log("Exit User Management.");
    }
    @FXML 
    protected void handleButtonConnect(ActionEvent event)
    {
        socket = new FxSocketClient(new MainWindowController.FxSocketListener(),
                glbstrServerAddress,
                Integer.parseInt(glbstrServerPort),
                Constants.instance().DEBUG_SEND);
        socket.SetGui(this);
        if (socket.connect1()) {
            String str = String.format("Connect server %s:%s", glbstrServerAddress, glbstrServerPort);
            log(str);
            socket.startReading();
            buttonDisConnect.setDisable(false);
            buttonConnect.setDisable(true);
        } else {
            String str = String.format("Fail to connect server %s:%s", glbstrServerAddress, glbstrServerPort);
            log(str);
            return;
        }         
    }
        @FXML 
    protected void handleButtonDisConnect(ActionEvent event)
    {
        socket.shutdown();
        buttonDisConnect.setDisable(true);
        buttonConnect.setDisable(false);
    }
    
    public boolean showDialogLogin()
    {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DialogLogin.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene((Pane) loader.load()));
            DialogLoginController controller
                    = loader.<DialogLoginController>getController();
            controller.initDatabase(database);
            stage.showAndWait();
            String strUsername = controller.getUsername();

            if (strUsername.equals(""))
            {
                String strlog = "Login failed.";
                log(strlog);
                return false;
            }
            else
            {
                String strlog = String.format("%s login successfully.", strUsername);
                log(strlog);
                return true;
            }          
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }
            

    }
    
    
    class FxSocketListener implements SocketListener {

        @Override
        public void onMessage(String line) {
            if (line != null && !line.equals("")) {
                System.out.println(line);
            }
        }

        @Override
        public void onClosedStatus(boolean isClosed) {
        }

    }
    
        public void UpdataTable(String tag, String value) {
        for (int i = 0; i < viewdata.size(); i++) {
            String t = viewdata.get(i).getColID();
            System.out.println(t);
            if (viewdata.get(i).getColID().equals(tag)) {
                viewdata.get(i).setColValue(value);
                tableviewMonitor.refresh();
                return;
            }
        }
        System.out.printf("No item tagged by: %s\n", tag);
    }

    public void UpdataTable(String tag, byte[] b) {

        for (int i = 0; i < viewdata.size(); i++) {
            String t = viewdata.get(i).getColID();
            System.out.println(t);
            if (viewdata.get(i).getColID().equals(tag)) {

                String log = listMonitor.get(i).getDisplayStr(b);
                listMonitor.get(i).varify(b);
                System.out.printf("%s:%s", tag, log);
                viewdata.get(i).setColValue(log);
                if (listMonitor.get(i).type == Monitor.Type.ENUM) {
                    int a = Integer.parseInt(log);
                    database.addData(tag, a);
                } else if (listMonitor.get(i).type == Monitor.Type.YESNO) {
                    int a = log.equals("Normal") ? 1 : 0;
                    database.addData(tag, a);
                } else {
                    database.addData(tag, Double.parseDouble(log));
                }
                tableviewMonitor.refresh();
                return;
            }
        }
        System.out.printf("No item tagged by: %s\n", tag);
    }

    public void UpdataTable() {
        Random randomGenerator = new Random();
        for (int i = 0; i < viewdata.size(); i++) {
            viewdata.get(i).setColValue(Integer.toString(randomGenerator.nextInt(100)));
        }

    }
    
    
}
