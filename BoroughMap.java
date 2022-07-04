import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert.*;
import java.util.HashMap;
import javafx.scene.paint.*;
import javafx.geometry.Pos;
import javafx.scene.input.*;
import java.awt.Color;
import javafx.scene.effect.ColorAdjust; 

/**
 * This class is responsible for creating the dynamic map interface for use within the application.
 * 
 * @author Archie Sage (k20003540)
 * @version 14.03.2022
 */
public class BoroughMap
{
    /*
     * Attributes
     */

    // Store all of the borough objects within this map.
    private List<Borough> boroughs;
    
    // Stores the parent GUI class instance.
    private PropertyViewerGUI parentGUI;
    
    // Stores the highest value borough within map.
    private int highestValue;
    
    /**
     * Create a new borough map visualisation.
     */
    public BoroughMap(PropertyViewerGUI parentGUI)
    {
        boroughs = new ArrayList<>();
        this.parentGUI = parentGUI;
        
        createBoroughs();
    }
    
    /*
     * Public methods
     */
    
    /**
     * Creates all of the map points for the locations of London boroughs.
     * 
     * @return The grid pane containing all of the colorised London boroughs.
     */
    public GridPane getBoroughMap()
    {
        // Will store all london boroughs
        GridPane boroughMap = new GridPane();
        
        // Loop through each borough and add it to the grid
        for (Borough borough : boroughs)
        {
            StackPane boroughCell = new StackPane();
            boroughCell.setId(borough.getLongName());
            
            /*
             * So that the brightness doesn't affect the label, a seperate pane with its own colour controls contrast.
             */
            Pane colourPane = new Pane();
            colourPane.setStyle("-fx-background-color: #75D3FA;"); 
            boroughCell.getChildren().add(colourPane);
                       
            /*
             * Color adjust object is user to make each boroughCell a different colour according to its value.
             * Cell brightness is calculated as this cells value as a percentage of the maximum cells value.
             */
            
            double cellBrightness = getBrightness(borough.getValue());
            
            ColorAdjust colourControl = new ColorAdjust(); 
            colourControl.setBrightness(cellBrightness); 
            colourPane.setEffect(colourControl);  
            
            /*
             * Event handling
             * Top event handler is in main GUI class as new window needs to be opened after borough selection.
             */ 
            boroughCell.setOnMouseClicked(parentGUI::boroughClicked);
            
            // Label contains key borough information.
            Label boroughLabel = new Label(borough.getShortName() + "\n(" + borough.getValue() + ")");
            boroughLabel.getStyleClass().add("boroughMapLabel");
            
            // Make text more readable if brightness is low
            if (cellBrightness > 0.3)
            {
                boroughLabel.setStyle("-fx-text-fill: #000000");
            }
            boroughCell.getChildren().add(boroughLabel);
            
            // Ensures that the cell scales when window is resized within limits.
            boroughMap.add(boroughCell, borough.getX(), borough.getY());
            boroughMap.setVgrow(boroughCell, Priority.ALWAYS);
            boroughMap.setHgrow(boroughCell, Priority.ALWAYS);
        }
        
        // Styling
        boroughMap.setHgap(10);
        boroughMap.setVgap(10);
        boroughMap.setAlignment(Pos.CENTER);
    
        return boroughMap;
    }
    
    /**
     * Assign the number of properties within a borough to that borough's object.
     * 
     * @param from The lowest price per night filter.
     * @param to The highest price per night filter.
     */
    public void assignValues(HashMap<String, Integer> boroughProperties)
    {
        // Assign each borough a value that is equal to the amount of properties within that borough.
        for (Borough currentBorough : boroughs)
        {
            int value = boroughProperties.get(currentBorough.getLongName());
            currentBorough.setValue(value);
            
            // Check for highest value
            if (value > highestValue)
            {
                highestValue = value;
            }
        }
    }
    
    /*
     * Private methods
     */
    
    /**
     * Gets the cell brightness by using percentage that value is of highest cell value.
     * 
     * @param value The value of the borough being assigned a brightness.
     * @return The brightness assigned to the borough with value of param.
     */
    private double getBrightness(int value)
    {
        return 1 - ((double) value / (double) highestValue);
    }
    
    /**
     * Gets a CSS compatible rgb string
     * 
     * @param colour The color object containing the RGB data
     * @return The CSS compatible rgb string
     */
    private String getRgbText(Color colour)
    {
        return "rgb(" + colour.getRed() + ", " + colour.getGreen() + ", " + colour.getBlue() + ")";
    }
    
    /**
     * Using the available information, each London Borough has its own MapPoint created for it.
     */
    private void createBoroughs()
    {
        boroughs.add(new Borough("Enfield", "ENF", 4, 0));
        boroughs.add(new Borough("Harrow", "HRW", 2, 1));
        boroughs.add(new Borough("Barnet", "BRN", 3, 1));
        boroughs.add(new Borough("Haringey", "HGY", 4, 1));
        boroughs.add(new Borough("Waltham Forest", "WTH", 5, 1));
        boroughs.add(new Borough("Hillingdon", "HDN", 0, 2));
        boroughs.add(new Borough("Ealing", "ELG", 1, 2));
        boroughs.add(new Borough("Brent", "BRT", 2, 2));
        boroughs.add(new Borough("Camden", "CMD", 3, 2));
        boroughs.add(new Borough("Islington", "ISL", 4, 2));
        boroughs.add(new Borough("Hackney", "HCK", 5, 2));
        boroughs.add(new Borough("Redbridge", "RDB", 6, 2));
        boroughs.add(new Borough("Havering", "HVG", 7, 2));
        boroughs.add(new Borough("Hounslow", "HNS", 0, 3));
        boroughs.add(new Borough("Hammersmith and Fulham", "HMS", 1, 3));
        boroughs.add(new Borough("Kensington and Chelsea", "KNS", 2, 3));
        boroughs.add(new Borough("Westminster", "WST", 3, 3));
        boroughs.add(new Borough("City of London", "CTY", 4, 3));
        boroughs.add(new Borough("Tower Hamlets", "TOW", 5, 3));
        boroughs.add(new Borough("Newham", "NVM", 6, 3));
        boroughs.add(new Borough("Barking and Dagenham", "BAR", 7, 3));
        boroughs.add(new Borough("Richmond upon Thames", "RCH", 1, 4));
        boroughs.add(new Borough("Wandsworth", "WNS", 2, 4));
        boroughs.add(new Borough("Lambeth", "LAM", 3, 4));
        boroughs.add(new Borough("Southwark", "SWR", 4, 4));
        boroughs.add(new Borough("Lewisham", "LSH", 5, 4));
        boroughs.add(new Borough("Greenwich", "GRN", 6, 4));
        boroughs.add(new Borough("Bexley", "BXL", 7, 4));
        boroughs.add(new Borough("Kingston upon Thames", "KNG", 2, 5));
        boroughs.add(new Borough("Merton", "MRT", 3, 5));
        boroughs.add(new Borough("Croydon", "CRD", 4, 5));
        boroughs.add(new Borough("Bromley", "BRM", 5, 5));
        boroughs.add(new Borough("Sutton", "STN", 3, 6));
    }
}
