import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;

/**
 * Property viewer panel.
 * 
 * @author Zubair Rahman (K20051986)
 * @version 20.03.2022
 */

public class ViewProperties
{
    
    
    /**
     * Attributes
     */
    private PropertyViewerGUI viewer = new PropertyViewerGUI();
    private Button mapButton;
    private ComboBox comboBox = new ComboBox();
    private TableColumn<String, PropertyListing> columnF1 = new TableColumn("Host Name");
    private TableColumn<Integer, PropertyListing> columnF2 = new TableColumn("Price");
    private TableColumn<Integer, PropertyListing> columnF3 = new TableColumn("Minimum Nights");
    private TableColumn<Integer, PropertyListing> columnF4 = new TableColumn("Number of Reviews");
    private TableView table = new TableView();
    private PropertyViewerGUI parentGUI;
    private final ObservableList<PropertyListing> dataList
            = FXCollections.observableArrayList();    
    /**
     * Create a new Panel with Properties listed in a table.
     */
    public ViewProperties(PropertyViewerGUI parentGUI, String neighbourhood, int from, int to)
    {
        this.parentGUI = parentGUI;
        loadProperties(neighbourhood, from, to);
        setUpComboBox();
        propertyClicked();
    }
    
    /**
     * This Method will load the properties in the table.
     */
    public void loadProperties(String neighbourhood, int from, int to){
        
        columnF1.setCellValueFactory(
                new PropertyValueFactory<>("host_name"));
        columnF1.prefWidthProperty().bind(table.widthProperty().divide(4));
        columnF1.setSortType(TableColumn.SortType.ASCENDING);

        columnF2.setCellValueFactory(
                new PropertyValueFactory<>("price"));
        columnF2.prefWidthProperty().bind(table.widthProperty().divide(4));
        columnF2.setSortType(TableColumn.SortType.ASCENDING);
 
        columnF3.setCellValueFactory(
                new PropertyValueFactory<>("minimumNights"));
        columnF3.prefWidthProperty().bind(table.widthProperty().divide(4));
        columnF3.setSortType(TableColumn.SortType.ASCENDING);
 
        columnF4.setCellValueFactory(
                new PropertyValueFactory<>("numberOfReviews"));
        columnF4.prefWidthProperty().bind(table.widthProperty().divide(4));
        columnF4.setSortType(TableColumn.SortType.ASCENDING);
        
        table.setItems(dataList);
        table.getColumns().addAll( columnF1, columnF2, columnF3, columnF4);
        
        PropertyDataLoader loader = new PropertyDataLoader();
        
        ArrayList<PropertyListing> list = parentGUI.getPropertiesModel().getPropertiesWithinSpecificRange(neighbourhood, from, to);
        table.getItems().addAll(list);
        
    }
    
    
    /**
     * This Method Returns a VBox panel with properties listed in a TableView.
     */
    public VBox getPropertiesList()
    {
            
            Pane pane = new Pane();
            Text text1 = new Text("Properties4U");
            text1.setStyle("-fx-font: normal 20px 'Dubai' ");
            text1.setFill(Color.WHITE);
            StackPane sPane = new StackPane(text1);
            sPane.setStyle("-fx-background-color: #75D3FA;");
            pane.getChildren().add(comboBox);
            pane.setStyle("-fx-background-color: #75D3FA;");
            VBox vbox = new VBox();
            VBox.setVgrow(table, Priority.ALWAYS);
            vbox.getChildren().add(pane);
            vbox.getChildren().add(table);
            vbox.getChildren().add(sPane);
    
        return vbox;
    }
    
    
    /**
     * This Method is to set Up all the Values in the ComboBox.
     */
    private void setUpComboBox(){
            comboBox.getItems().add("Alphabatically");
            comboBox.getItems().add("price (Low to High)");
            comboBox.getItems().add("price (High to Low)");
            comboBox.getItems().add("Number Of Reviews (Low to High)");
            comboBox.getItems().add("Number Of Reviews (High to Low)");
            comboBox.setPromptText("Sort by");
            comboBox.setOnAction((event) -> {
            
                int selectedIndex =
                    comboBox.getSelectionModel().getSelectedIndex();
                    sortBy(selectedIndex);
                });
    }
    
    /**
     * This Method is to sort the table according to the index of comboBox chosen.
     */
    private void sortBy(int index){
        if(index==0){
            table.getSortOrder().clear();
            table.getSortOrder().add(columnF1);
            table.sort();
        }else if(index==1){
            table.getSortOrder().clear();
            columnF2.setSortType(TableColumn.SortType.ASCENDING);
            table.getSortOrder().add(columnF2);
            table.sort();
        }else if(index==2){
            table.getSortOrder().clear();
            columnF2.setSortType(TableColumn.SortType.DESCENDING);
            table.getSortOrder().add(columnF2);
            table.sort();
        }else if(index==3){
            table.getSortOrder().clear();
            columnF4.setSortType(TableColumn.SortType.ASCENDING);
            table.getSortOrder().add(columnF4);
            table.sort();
        }else if(index==4){
            table.getSortOrder().clear();
            columnF4.setSortType(TableColumn.SortType.DESCENDING);
            table.getSortOrder().add(columnF4);
            table.sort();
        }
    }
    
    /**
     * open the individual Property Details Panel when a row is clicked in the table.
     */
    private void propertyClicked(){
        table.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
        PropertyListing row;
        row = (PropertyListing) table.getSelectionModel().getSelectedItem();
        try
        {
            Text[] labels = new Text[14];
            StackPane[] panes = new StackPane[15];
            labels[0] = new Text("  ID of the Property: " + row.getId());
            labels[1] = new Text("  Name of the Property: " + row.getName());
            labels[2] = new Text("  Host's ID for the Property: " + row.getHost_id());
            labels[3] = new Text("  Host's Name of the Property: " + row.getHost_name());
            labels[4] = new Text("  Neighbourhood of the Property: " + row.getNeighbourhood());
            labels[5] = new Text("  Latitude of the Property: " + row.getLatitude());
            labels[6] = new Text("  Longitude of the Property: " + row.getLongitude());
            labels[7] = new Text("  Room Type: " + row.getRoom_type());
            labels[8] = new Text("  Minimum Nights: " + row.getMinimumNights());
            labels[9] = new Text("  Number of Reviews: " + row.getNumberOfReviews());
            labels[10] = new Text("  Last Review: " + row.getLastReview());
            labels[11] = new Text("  Reviews Per Month: " + row.getReviewsPerMonth());
            labels[12] = new Text("  Calculated Host Listings Count: " + row.getCalculatedHostListingsCount());
            labels[13] = new Text("  Availability: " + row.getAvailability365());
            
            mapButton = new Button("Show Property On Map");
            Text text1 = new Text("   Properties4U");
            text1.setStyle("-fx-font: normal 20px 'Dubai' ");
            text1.setFill(Color.WHITE);
            StackPane pane = new StackPane(text1);
            pane.setStyle("-fx-background-color: #75D3FA;");
            pane.setAlignment(text1, Pos.CENTER_LEFT);
            
            VBox root = new VBox();
            root.getChildren().add(pane);
            for (int i=0;i<14; i++ ){
                panes[i] = new StackPane();
                labels[i].setStyle("-fx-font: normal 15px 'Dubai' ");
                if(i%2==0)
                panes[i].setStyle("-fx-background-color: #EFFFFD;");
                else
                panes[i].setStyle("-fx-background-color: #75D3FA;");
                panes[i].getChildren().add(labels[i]);
                root.getChildren().add(panes[i]);
            }
            panes[14] = new StackPane();
            panes[14].getChildren().add(mapButton);
            panes[14]. setStyle("-fx-background-color: #EFFFFD;");
            root.getChildren().add(panes[14]);
            root.setVgrow(panes[14], Priority.ALWAYS);
            mapClicked(row);
            Scene stats = new Scene(root, 600, 450);
            Stage stage = new Stage();
            stage.setTitle(row.getName());
            stage.setScene(stats);
            stage.sizeToScene();
            stage.show();
        }
        catch(Exception error)
        {
            error.printStackTrace();
        }  
    
    });
}

/**
 * This will show the map when mapClicked Button is pressed.
 * @params PropertyListing the currrent property 
 */
private void mapClicked(PropertyListing property) throws Exception {
    
        mapButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
        GoogleMapViewController.latitude = property.getLatitude();
        GoogleMapViewController.longitude = property.getLongitude();
        GoogleMapViewController.name = property.getName();
        GoogleMapViewController.address = property.getNeighbourhood();
        GoogleMapViewController.price = property.getPrice();
        try{
        Pane webPane = getPanel("googleMapView.fxml");
        Scene webScene = new Scene(webPane);
        webScene.getStylesheets().add("googleWebViewStyles.css");
        Stage stage = new Stage();
        stage.setScene(webScene);
        stage.setTitle(property.getName());

        // Show the Stage (window)
        stage.show();
    } catch (Exception error){
        error.printStackTrace();
    }
    }
    }   );
}

/**
 * To Load the Map FXML file.
 */
private Pane getPanel(String resourceName) throws Exception
    {
        URL url = getClass().getResource(resourceName);
        return FXMLLoader.load(url);
    }
}
