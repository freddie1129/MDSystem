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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import org.w3c.dom.*;
import org.w3c.dom.Element;
import java.io.File;
import java.util.Random;









import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.event.*;
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
public class MDSystem extends Application {
    
    //private Timeline timeline;
    

    
     
      
  private final ObservableList<MonitorVariable> data =  FXCollections.observableArrayList();
   
//    private final ObservableList<MonitorVariable> data
//            = FXCollections.observableArrayList(
//                    new MonitorVariable("Smith1", "221", "111", "121", "V1", "1voltage in fche"),
//                    new MonitorVariable("Smith2", "222", "112", "122", "V2", "2voltage in fche"),
//                    new MonitorVariable("Smith3", "223", "113", "123", "V3", "3voltage in fche"),
//                    new MonitorVariable("Smith4", "224", "114", "124", "V4", "4voltage in fche"),
//                    new MonitorVariable("Smith5", "225", "115", "125", "V5", "5voltage in fche"),
//                    new MonitorVariable("Smith6", "226", "116", "126", "V6", "6voltage in fche"),
//                    new MonitorVariable("Smith7", "227", "117", "127", "V7", "7voltage in fche")
//            );

    final PageData[] pages = new PageData[] {
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
 
    final String[] viewOptions = new String[] {
        "Title", 
        "Binomial name", 
        "Picture", 
        "Description"
    };
 
    final Entry<String, Effect>[] effects = new Entry[] {
        new SimpleEntry<>("Sepia Tone", new SepiaTone()),
        new SimpleEntry<>("Glow", new Glow()),
        new SimpleEntry<>("Shadow", new DropShadow())
    };
 
    final ImageView pic = new ImageView();
    final Label name = new Label();
    final Label binName = new Label();
    final Label description = new Label();
    
                    //about table view

    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage) {
        
        
    


      //  Timeline timeline = new Timeline(new KeyFrame(
       // Duration.millis(2500),
       // ae -> doSomething()));
        

        
         try {
             
             
               
                 
    File fXmlFile = new File("Lab.xml");
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(fXmlFile);


    doc.getDocumentElement().normalize();

	//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
    NodeList nList = doc.getElementsByTagName("variable");

    //System.out.println("----------------------------");
    for (int temp = 0;temp< nList.getLength ();temp++) {
       org.w3c.dom.Node nNode = nList.item(temp);
       // System.out.println("\nCurrent Element :" + nNode.getNodeName());
        if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;
            System.out.println("Staff id : " + eElement.getAttribute("id"));
            
            //System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
            //System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
            //System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
            //System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());
            data.add(new MonitorVariable(
            eElement.getElementsByTagName("VarId").item(0).getTextContent(),
            eElement.getElementsByTagName("VarName").item(0).getTextContent(),
                           "",
            eElement.getElementsByTagName("VarMax").item(0).getTextContent(),
            eElement.getElementsByTagName("VarMin").item(0).getTextContent(),
            eElement.getElementsByTagName("VarUnit").item(0).getTextContent(),
            eElement.getElementsByTagName("VarInfo").item(0).getTextContent(),
            eElement.getElementsByTagName("TimeInterval").item(0).getTextContent()));
            
            
                    
        }
    }
}
    catch (Exception e) {
	e.printStackTrace();
    }
        
        
        primaryStage.setTitle("Menu Sample");
        //Scene scene = new Scene(new VBox(), 400, 350);
         
        MenuBar menuBar = new MenuBar(); 
        // --- Menu File
        Menu menuFile = new Menu("File"); 
        // --- Menu Edit
        Menu menuEdit = new Menu("Edit"); 
        // --- Menu View
        Menu menuView = new Menu("View"); 
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);

        
        BorderPane border = new BorderPane();
        //set project information in the top area
        String strProjectInfo;
        strProjectInfo = GetProjectInfo();
        Label labProjectInfo = new Label(strProjectInfo);
        

        border.setTop(labProjectInfo); 
        
        VBox rootHbox = new VBox();
        Label labDisplay = new Label("Project Information");
        labDisplay.setFont(new Font("Arial", 20));
        
        
                //Create Table View
        final TableView table = new TableView();
        table.setEditable(false);
        
        TableColumn ColNum = new TableColumn("No");
        ColNum.setMinWidth(100);
        ColNum.setCellValueFactory(new PropertyValueFactory<>("ColNum"));
        
        TableColumn ColID = new TableColumn("ID");
        ColID.setMinWidth(100);
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
        
        TableColumn ColUnit = new TableColumn("Unit");
        ColUnit.setMinWidth(100);
        ColUnit.setCellValueFactory(new PropertyValueFactory<>("ColUnit"));
        
        TableColumn ColInterval = new TableColumn("Unit");
        ColInterval.setMinWidth(100);
        ColInterval.setCellValueFactory(new PropertyValueFactory<>("ColInterval"));
        
        TableColumn ColInfo = new TableColumn("Info");
        ColInfo.setMinWidth(100);
        ColInfo.setCellValueFactory(new PropertyValueFactory<>("ColInfo"));      
        

        
        table.setItems(data);
        
        table.getColumns().addAll(ColNum,ColID, ColName, ColValue,ColMax,ColMin,ColUnit,ColInterval,ColInfo);
        
        
 
        
        
        
        
        final VBox vboxCentral = new VBox();
        vboxCentral.setSpacing(5);
        vboxCentral.setPadding(new Insets(10, 0, 0, 10));
        vboxCentral.getChildren().addAll(labDisplay, table);
        border.setCenter(vboxCentral);
        
        
        
        
        
            ListView<String> list = new ListView<>();
            ObservableList<String> items =FXCollections.observableArrayList (
    "Single", "Double", "Suite", "Family App");
            list.setItems(items);
            list.setPrefWidth(100);
list.setPrefHeight(70);

        

        
        
        
        
        
        
        rootHbox.getChildren().addAll(menuBar,border);
      
        
        
        Scene scene = new Scene(rootHbox, 800, 600);
        


     Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("this is called every 1 seconds on UI thread");
                UpdataTable();
                table.refresh();
          //      table.setItems(data);
                 
        //table.getColumns().addAll(ColNum,ColID, ColName, ColValue,ColMax,ColMin,ColUnit,ColInterval,ColInfo);
        
                
            }
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
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
 
    


    public void UpdataTable()
    {
        Random randomGenerator = new Random();
        for (int i = 0; i < data.size(); i++)
        {
            data.get(i).setColValue(Integer.toString(randomGenerator.nextInt(100)));
        }       
                
    }
    public String GetProjectInfo()
    {
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
    
    
    
    //Monitoring Variable
    public static class MonitorVariable {
        public static int num = 1;
        private final SimpleStringProperty ColID;
        private final SimpleIntegerProperty ColNum;
        private final SimpleStringProperty ColName;
        private final SimpleStringProperty ColValue;
        private final SimpleStringProperty ColMax;
        private final SimpleStringProperty ColMin;
        private final SimpleStringProperty ColUnit;
        private final SimpleStringProperty ColInfo;
        private final SimpleStringProperty ColInterval;

        private MonitorVariable(
                String ColID,
                String ColName,
                String ColValue,
                String ColMax,
                String ColMin,
                String ColUnit,
                String ColInfo,
                String ColInterval) {
            this.ColNum = new SimpleIntegerProperty(num++);
            this.ColID = new SimpleStringProperty(ColID);
            this.ColName = new SimpleStringProperty(ColName);
            this.ColValue = new SimpleStringProperty(ColValue);
            this.ColMax = new SimpleStringProperty(ColMax);
            this.ColMin = new SimpleStringProperty(ColMin);
            this.ColUnit = new SimpleStringProperty(ColUnit);
            this.ColInfo = new SimpleStringProperty(ColInfo);
            this.ColInterval = new SimpleStringProperty(ColInterval);
        }

        public int getColNum() {
            return ColNum.get();
        }
        
        public String getColID() {
            return ColID.get();
        }

        public String getColName() {
            return ColName.get();
        }

        public String getColValue() {
            return ColValue.get();
        }

        public String getColMax() {
            return ColMax.get();
        }

        public String getColMin() {
            return ColMin.get();
        }

        public String getColUnit() {
            return ColUnit.get();
        }

        public String getColInfo() {
            return ColInfo.get();
        }
        
        public String getColInterval() {
            return ColInterval.get();
        }
        //set value
        
        public void setColID(String fID) {
            ColID.set(fID);
        }
                
        public void setColName(String fName) {
            ColName.set(fName);
        }

        public void setColValue(String fValue) {
            ColValue.set(fValue);
        }

        public void setColMax(String fMax) {
            ColMax.set(fMax);
        }

        public void setColMin(String fMin) {
            ColMin.set(fMin);
        }

        public void setColUnit(String fUnit) {
            ColUnit.set(fUnit);
        }

        public void setColInfo(String fInfo) {
            ColInfo.set(fInfo);
        }
        
        public void setColInterval(String fInterval) {
            ColInterval.set(fInterval);
        }

    }
    
    
    
}
 
//    /*
//    
//    
//    @Override
//    public void start(Stage primaryStage) {
//        /*Button btn = new Button();
//        btn.setText("Say 'Hello World'");
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//            
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("Hello World!");
//            }
//        });
//        
//        StackPane root = new StackPane();
//        root.getChildren().add(btn);
//        */
//        
//        BorderPane border = new BorderPane();
//        //HBox hbox = addHBox();
//       // border.setTop(hbox);
//       // border.setLeft(addVBox());
//       // addStackPane(hbox); // Add stack to HBox in top region
//       // border.setCenter(addGridPane());
//      //  border.setRight(addFlowPane());
//
//        Scene scene = new Scene(border);
//        
//        primaryStage.setTitle("Hello World!");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//    
//    public HBox addHBox() {
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
//    
//    public VBox addVBox()
//    {
//        VBox vbox = new VBox();
//        vbox.setPadding(new Insets(10));
//        vbox.setSpacing(8);
//        Text title = new Text("Data");
//        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
//        vbox.getChildren().add(title);
//        //StackPane Using Built - in Layout Panes 1-5
//        Hyperlink options[] = new Hyperlink[]{
//            new Hyperlink("Sales"),
//            new Hyperlink("Marketing"),
//            new Hyperlink("Distribution"),
//            new Hyperlink("Costs")};
//        for (int i = 0; i < 4; i++) {
//            VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
//            vbox.getChildren().add(options[i]);
//        }
//        return vbox;
//    }
//    
//    public void addStackPane(HBox hb) {
//        StackPane stack = new StackPane();
//        Rectangle helpIcon = new Rectangle(30.0, 25.0);
//        helpIcon.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
//                new Stop[]{
//                    new Stop(0, Color.web("#4977A3")),
//                    new Stop(0.5, Color.web("#B0C6DA")),
//                    new Stop(1, Color.web("#9CB6CF")),}));
//        helpIcon.setStroke(Color.web("#D0E6FA"));
//        helpIcon.setArcHeight(3.5);
//        helpIcon.setArcWidth(3.5);
//        Text helpText = new Text("?");
//        helpText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
//        helpText.setFill(Color.WHITE);
//        helpText.setStroke(Color.web("#7080A0"));
//        stack.getChildren().addAll(helpIcon, helpText);
//        stack.setAlignment(Pos.CENTER_RIGHT); // Right-justify nodes in stack
//        StackPane.setMargin(helpText, new Insets(0, 10, 0, 0)); // Center "?"
//        hb.getChildren().add(stack); // Add to HBox from Example 1–2
//        HBox.setHgrow(stack, Priority.ALWAYS); // Give stack any extra space
//    }
//
//    public GridPane addGridPane() {
//        GridPane grid = new GridPane();
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(0, 10, 0, 10));
//        // Category in column 2, row 1
//        Text category = new Text("Sales:");
//        category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
//        grid.add(category, 1, 0);
//        // Title in column 3, row 1
//        Text chartTitle = new Text("Current Year");
//        chartTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
//        grid.add(chartTitle, 2, 0);
//        // Subtitle in columns 2-3, row 2
//        Text chartSubtitle = new Text("Goods and Services");
//        grid.add(chartSubtitle, 1, 1, 2, 1);
//        // House icon in column 1, rows 1-2
//        ImageView imageHouse = new ImageView(
//                new Image(MDSystem.class.getResourceAsStream("graphics/play1.png")));
//        grid.add(imageHouse, 0, 0, 1, 2);
//        // Left label in column 1 (bottom), row 3
//        Text goodsPercent = new Text("Goods\n80%");
//        GridPane.setValignment(goodsPercent, VPos.BOTTOM);
//        grid.add(goodsPercent, 0, 2);
//        // Chart in columns 2-3, row 3
//        ImageView imageChart = new ImageView(
//                new Image(MDSystem.class.getResourceAsStream("graphics/play2.png")));
//        grid.add(imageChart, 1, 2, 2, 1);
//        // Right label in column 4 (top), row 3
//        Text servicesPercent = new Text("Services\n20%");
//        GridPane.setValignment(servicesPercent, VPos.TOP);
//        grid.add(servicesPercent, 3, 2);
//        return grid;
//    }
//    /**
//     * @param args the command line arguments
//     */
//    
//    private class PageData {
//
//        public String name;
//        public String description;
//        public String binNames;
//        public Image image;
//
//        public PageData(String name, String description, String binNames) {
//            this.name = name;
//            this.description = description;
//            this.binNames = binNames;
//            image = new Image(getClass().getResourceAsStream(name + ".jpg"));
//        }
//    }
//
//
//
// //   public static void main(String[] args) {
//  //      launch(args);
//  //  }
//    
////}
