import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert.*;
import javafx.scene.input.*;
import javafx.animation.FadeTransition;
import javafx.scene.control.ComboBox;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import java.util.HashMap;

/**
 * This class is responsible for managing the graphical elements of the application.
 * Namely, loading the scene from the .fxml file created within Scene Viewer.
 * 
 * @author Archie Sage (K20003540)
 * @version 13.03.2022
 */
public class PropertyViewerGUI extends Application
{
    /*
     * Class variables
     */
    
    private static String TEAM_NAME = "Properties4u";
    
    // The increment that the combo boxes gets multiplied by.
    private static Integer COMBO_MULTIPLE = 5;
    
    /*
     * FXML Attributes
     */
    
    /*
     * This is the pane that the program/programmer has access to change.
     * Limited access is enforced to follow good coding practice.
     */ 
    @FXML
    private AnchorPane centrePane;
    
    // The name of the name: Properties4u
    @FXML
    private Label teamName;
    
    // Bottom bar buttons
    @FXML
    private Button previousButton, nextButton;
    
    // Price range fields
    @FXML
    private ComboBox fromCombo, toCombo;
    
    // Search button for price range fields.
    @FXML
    private Button searchButton;
    
    // Statistics panel button
    @FXML
    private Button statisticsButton;
    
    /*
     * Attributes
     */
    
    // Stores all of the panels to be displayed to the user (ones that can be navigated to via next and previous).
    private ArrayList<Pane> panels;
    
    // Used for stats.
    private PropertiesModel propertiesModel = new PropertiesModel();
    
    // Stores the panel index of the current panel (within panels).
    private int currentPanelIndex, to, from;
    
    /*
     * Public methods
     */
    
    /**
     * Application start method (runs when program is started)
     * Makes a new graphical user interface for the application.
     * Error message occurs if exception is seen
     * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.AlertType.html
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        try
        {
            // Setup initial app GUI.
            Pane root = getPanel("main.fxml");
            Scene scene = new Scene(root);
            
            scene.getStylesheets().add("stylesheet.css");
            stage.setTitle(TEAM_NAME);
            stage.setMinHeight(600);
            stage.setMinWidth(900);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception error)
        {
            displayPopup("Error", "This app has failed to start due to an error", error.toString(), AlertType.ERROR);
            
            // Exit program.
            System.exit(0);
        }
    }
    
    /**
     * Returns the model we are currently viewing on the map
     * @return The PropertiesModel instance
     */
    public PropertiesModel getPropertiesModel(){
        return this.propertiesModel;
    }
    /*
     * Private methods
     */
    
    /**
     * Start once all FXML fields have been populated.
     * This method is used instead of the constructor as it is slightly slower to be called.
     * This enables enough time for the FXML fields to be filled with non-null values and therefore, enables access.
     * 
     * This method is used to create the 'startup' state of the program.
     */
    @FXML
    private void initialize() throws Exception
    {
        try
        {
            // Get all panels the program will use
            propertiesModel = new PropertiesModel();
            panels = new ArrayList<Pane>();
            panels.add(getPanel("welcome.fxml"));
            
            // Enable access to statistics.
            statisticsButton.setVisible(false);
            
            /*
             * Set initial 'welcome' pane to visible
             * Don't want transition within panels as it is only on load.
             * User should not be able to navigate to it.
             */
            Pane welcomeTransition = getPanel("welcomeTransition.fxml");
            changePanel(welcomeTransition);
            executeWelcomeTransition(welcomeTransition);

            setApplicationIdentity();
            fillCombos();
        }
        catch (Exception error)
        {
            System.out.println(error.toString());
            displayPopup("Error", "This app has failed to start due to an error", error.toString(), AlertType.ERROR);
            
            // Exit program.
            System.exit(0);
        }
    }
    
    /**
     * This method executes the initial welcome screen when the application is started
     * 
     * @param Pane The pane to be added to the main window
     */
    private void executeWelcomeTransition(Pane pane)
    {
        try
        {
            SequentialTransition sequence = new SequentialTransition();
            // Create a new grid pane
            Pane welcomeTransition = pane;
            PauseTransition pause = new PauseTransition(Duration.millis(2000));
            FadeTransition fadeOut = new FadeTransition(Duration.millis(2000), welcomeTransition);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            sequence.getChildren().addAll(pause, fadeOut);
            
            sequence.play();
            sequence.setOnFinished((e) -> { changePanel(0); });
        }
        catch (Exception error)
        {
            error.printStackTrace();
            System.out.println("Failed to execute welcome transition");
        }
    }
    
    /**
     * Change which panel is being focused on by the user and ensure anchor is set.
     * According to INDEX.
     * 
     * @param panelIndex The index of the new panel to be shown within panels arraylist.
     */
    private void changePanel(int panelIndex)
    {
        Pane newPanel = panels.get(panelIndex);
        
        // Clear previous panel.
        centrePane.getChildren().clear();
        
        // Add new panel.
        centrePane.getChildren().add(newPanel);
            
        // Anchor
        centrePane.setTopAnchor(newPanel, 0.0);
        centrePane.setBottomAnchor(newPanel, 0.0);
        centrePane.setLeftAnchor(newPanel, 0.0);
        centrePane.setRightAnchor(newPanel, 0.0);
        
        // Set new panel index
        currentPanelIndex = panelIndex;
    }
    
    /**
     * Change which panel is being focused on by the user and ensure anchor is set.
     * According to PANE.
     * 
     * CurrentPanelIndex is assumed to be changed prior to this method being called.
     * 
     * @param newPanel The pane to be displayed to the user.
     */
    private void changePanel(Pane newPanel)
    {
        // Clear previous panel.
        centrePane.getChildren().clear();
        
        // Add new panel.
        centrePane.getChildren().add(newPanel);
            
        // Anchor
        centrePane.setTopAnchor(newPanel, 0.0);
        centrePane.setBottomAnchor(newPanel, 0.0);
        centrePane.setLeftAnchor(newPanel, 0.0);
        centrePane.setRightAnchor(newPanel, 0.0);
    }
    
    /**
     * Sets the indentifying fields of the program.
     */
    private void setApplicationIdentity()
    {
        teamName.setText(TEAM_NAME);
    }
    
    /**
     * Fills the combination boxes 'to' and 'from'.
     */
    private void fillCombos()
    {
        int lowestPrice = propertiesModel.minPricePerNight();
        int highestPrice = propertiesModel.maxPricePerNight();
        
        // The current value increment
        int currentTotal = lowestPrice;
        
        while (currentTotal < highestPrice)
        {
            fromCombo.getItems().add(currentTotal);
            toCombo.getItems().add(currentTotal);
            
            currentTotal *= COMBO_MULTIPLE;
        }
        
        fromCombo.getItems().add(highestPrice);
        toCombo.getItems().add(highestPrice);
    }
    
    /*
     * Event handlers
     */
    
    /**
     * Opens a new Panel Containing the List of all the properties located in 
     * the borough clicked
     * @param event The mouse handler event.
     */
    public void boroughClicked(MouseEvent event)
    {
        String boroughLongName = ((StackPane) event.getSource()).getId();
        System.out.println(boroughLongName);
        
                previousButton.setDisable(true);
                searchButton.setDisable(true);
                toCombo.setDisable(true);
                fromCombo.setDisable(true);
                statisticsButton.setVisible(true);
                
                ViewProperties listings = new ViewProperties(this, boroughLongName, from, to);
                
        try
        {
            
            VBox root = listings.getPropertiesList();
            
            
            Scene stats = new Scene(root, 600, 500);
            Stage stage = new Stage();
            stage.setTitle(boroughLongName);
            stage.setScene(stats);
            stage.sizeToScene();
            stage.show();
        }
        catch(Exception error)
        {
            System.out.println(error.toString());
        }
    }
    
    /**
     * Navigatates to the next panel when the 'next' button is pressed.
     * 
     * @param event The action handler event.
     */
    @FXML
    private void nextClicked(ActionEvent event)
    {
        changePanel(getNextPanel());
    }
    
    /**
     * Navigatates to the next panel when the 'previous' button is pressed.
     * 
     * @param event The action handler event.
     */
    @FXML
    private void previousClicked(ActionEvent event)
    {
        changePanel(getPreviousPanel());
    }
    
    /**
     * Attempt to set a price range.
     * 
     * @param event The action handler event object.
     */
    @FXML
    private void searchClicked(ActionEvent event)
    {
        try
        {
            to = Integer.parseInt(toCombo.getValue().toString());
            from = Integer.parseInt(fromCombo.getValue().toString());
            
            if (to > from)
            {
                // Stores the properties within the dataset that are within the price range.
                HashMap<String, Integer> propertiesWithinRange = propertiesModel.noPropertiesPerBorough(from, to);
                
                // Check that there are properties within price range.
                if (propertiesWithinRange.size() > 0)
                {
                    previousButton.setDisable(false);
                    nextButton.setDisable(false);
                    searchButton.setDisable(true);
                    toCombo.setDisable(true);
                    fromCombo.setDisable(true);
                    statisticsButton.setVisible(true);
                    
                    // Add map to panels.
                    BoroughMap map = new BoroughMap(this);
                    map.assignValues(propertiesWithinRange);
                    panels.add(map.getBoroughMap());
                    
                    // Show map.
                    changePanel(getNextPanel());
                }
                else
                {
                    displayPopup("Warning", "No properties found within your price range", "Please expand your price range", AlertType.WARNING);
                }
            }
            else 
            {
                displayPopup("Warning", "Your max price has to be higher than you minimum price", "Please make either your max price higher or your min price lower", AlertType.WARNING);
            }
            
        }
        catch (Exception error) 
        {
            displayPopup("Warning", "Invalid price range", "Please enter a valid price range", AlertType.WARNING);
        }
        
    }
    
    /**
     * 
     */
    @FXML
    private void statisticsClicked(ActionEvent actionEvent){
        try
        {
            URL url = getClass().getResource("statistics.fxml");
            
            FXMLLoader loader= new FXMLLoader(url);
            Pane root = loader.load();
            
            
            Scene stats = new Scene(root);
            Stage stage = new Stage();
            
            StatsController controller = (StatsController) loader.getController();
            controller.registerStage(stage);
            controller.update(this.propertiesModel);
            
            stats.getStylesheets().add("stylesheet.css");
            stage.setTitle(TEAM_NAME);
            stage.setScene(stats);
            stage.sizeToScene();
            stage.show();
        }
        catch(Exception error)
        {
            System.out.println(error.toString());
        }
    }
    
    /**
     * Quit application
     * 
     * @param event The action handler event object.
     */
    @FXML
    private void quitClicked(ActionEvent event)
    {
        try
        {
            System.exit(0);
        }
        catch (Exception error)
        {
            displayPopup("Error", "This app has failed to quit due to an error", error.toString(), AlertType.ERROR);
        }
    }
    
    /**
     * About application popup.
     * 
     * @param event The action handler event object.
     */
    @FXML
    private void aboutClicked(ActionEvent event)
    {
        displayPopup("About", "About this application", "This application was created as part of the King's College London PPA module for Computer Science", AlertType.INFORMATION);
    }
    
    /*
     * Utility methods
     */
    
    /**
     * Retrieves a panel by way of creating a Pane object from an FXML file using FXML Loader.
     * File has to be in correct directory, otherwise path will need to be described within resourceName variable.
     * 
     * @param resourceName The name of the FXML file.
     * @return The panel represented by the FXML file.
     */
    private Pane getPanel(String resourceName) throws Exception
    {
        URL url = getClass().getResource(resourceName);
        return FXMLLoader.load(url);
    }
    
    /**
     * Contains loop functionality to insure null pointer exception is never encountered.
     * 
     * @return The pane that was previous displayed to the user.
     */
    private Pane getPreviousPanel()
    {
        // Loop through list if other options exhausted.
        if (currentPanelIndex - 1 < 0)
        {
            currentPanelIndex = panels.size() - 1;
            return panels.get(currentPanelIndex);
        }
        else
        {
            currentPanelIndex--;;
            return panels.get(currentPanelIndex);
        }
    }
    
    /**
     * Contains loop functionality to insure null pointer exception is never encountered.
     * 
     * @return The pane that is due to be displayed to the user next.
     */
    private Pane getNextPanel()
    {
        // Loop through list if other options exhausted.
        if (currentPanelIndex + 1 > panels.size() - 1)
        {
            currentPanelIndex = 0;
            return panels.get(currentPanelIndex);
        }
        else
        {
            currentPanelIndex++;;
            return panels.get(currentPanelIndex);
        }
    }
    
    /**
     * Displays a popup to the user.
     * 
     * @param title The title displayed on the popup.
     * @param heading The main heading displayed on the popup.
     * @param body The bulk of text on the popup.
     * @param alertType The type of popup shown.
     */
    private void displayPopup(String title, String heading, String body, AlertType alertType)
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(heading);
        alert.setContentText(body);
        alert.showAndWait();
    }
}
