import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import java.net.URL;
import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Statistic Controller handles the events occuring in the statistics panel
 *
 * @author Kwan Yui Chiu(K21003778)
 * @version 17-03-2022
 */
public class StatsController
{
    private Stage stage;
    // Statistic boxes buttons
    @FXML
    private Button S1LeftButton,S1RightButton; 
    
    @FXML
    private Button S2LeftButton,S2RightButton;
    
    @FXML
    private Button S3LeftButton,S3RightButton;
    
    @FXML
    private Button S4LeftButton,S4RightButton;
    
    // Top bar components
    @FXML
    private Label titleLabel;
    
    @FXML
    private Button loadFileButton;
    // Statistic boxes textLabels
    @FXML
    private Label S1NameLabel,S1NumLabel;
    
    @FXML
    private Label S2NameLabel,S2NumLabel;
    
    @FXML
    private Label S3NameLabel,S3NumLabel;
    
    @FXML
    private Label S4NameLabel,S4NumLabel;
    
    // Label for the bottom status bar
    @FXML
    private Label statusLabel;

    //Statistics containers for each box
    private Statistics S1Stats,S2Stats,S3Stats,S4Stats;
    
    //A circular queue used to hold unused Statistics 
    private CircularQueue<Statistics> statsQueue;
    
    // The model currently displaying on panel
    private PropertiesModel propertiesModel;
    
    /**
     * Constructor for StatsController
     */
    public StatsController(){
    
    }
    
    /**
     * Keep a reference to the statistics panel
     * 
     * @param Stage the window that it is currently displaying on
     */
    public void registerStage(Stage stage){
        this.stage = stage;
    }
    
    /**
     * Initialize the Statistic when loaded up
     * 
     * @param PropertiesModel the default property model 
     */
    public void update(PropertiesModel pModel) {
        
        this.propertiesModel = pModel;
        
        titleLabel.setText("Statistics of entire data set");
        updateStatusBar(pModel.getFileName() + " loaded");
        S1Stats = new Statistics("Average number of reviews",propertiesModel.getAverageNumberReviews());
        S2Stats = new Statistics("Borough with highest listing",propertiesModel.getBoroughWithHighestListingCount());
        S3Stats = new Statistics("Most expensive borough",propertiesModel.getMostExpensiveBorough());
        S4Stats = new Statistics("Available properties",propertiesModel.getNumberOfAvailableProperties());
        
        this.statsQueue = new CircularQueue<>(4);
        statsQueue.enqueue(new Statistics("Number of homes or apartment",propertiesModel.getNumberOfHomesOrApart()));
        statsQueue.enqueue(new Statistics("Total number of properties",propertiesModel.getTotalNumberOfProperties()));
        statsQueue.enqueue(new Statistics("Host with the most listings",propertiesModel.getHostWithMostListing()));
        statsQueue.enqueue(new Statistics("ListingID with the longest\n available period"
                                        ,propertiesModel.getListingIDOfPropertyWithLongestAvailablePeriod()));
        
        S1NameLabel.setText(S1Stats.getType());
        S2NameLabel.setText(S2Stats.getType());
        S3NameLabel.setText(S3Stats.getType());
        S4NameLabel.setText(S4Stats.getType());
        
        S1NumLabel.setText(S1Stats.getValue());
        S2NumLabel.setText(S2Stats.getValue());
        S3NumLabel.setText(S3Stats.getValue());
        S4NumLabel.setText(S4Stats.getValue());
    }
    
    /**
     * Change the statistic at statistics box 1
     * 
     * @param ActionEvent the action handler event object
     */
    @FXML
    private void S1ArrowButtonClicked(ActionEvent actionEvent){
        Statistics oldStats = S1Stats;
        S1Stats = statsQueue.dequeue();
        S1NameLabel.setText(S1Stats.getType());
        S1NumLabel.setText(S1Stats.getValue());
        statsQueue.enqueue(oldStats);
    }
    
    /**
     * Change the statistic at statistics box 2
     * 
     * @param ActionEvent the action handler event object
     */
    @FXML
    private void S2ArrowButtonClicked(ActionEvent actionEvent){
        Statistics oldStats = S2Stats;
        S2Stats = statsQueue.dequeue();
        S2NameLabel.setText(S2Stats.getType());
        S2NumLabel.setText(S2Stats.getValue());
        statsQueue.enqueue(oldStats);
    }
    
    /**
     * Change the statistic at statistics box 3
     * 
     * @param ActionEvent the action handler event object
     */
    @FXML
    private void S3ArrowButtonClicked(ActionEvent actionEvent){
        Statistics oldStats = S3Stats;
        S3Stats = statsQueue.dequeue();
        S3NameLabel.setText(S3Stats.getType());
        S3NumLabel.setText(S3Stats.getValue());
        statsQueue.enqueue(oldStats);
    }
    
    /**
     * Change the statistic at statistics box 4
     * 
     * @param ActionEvent the action handler event object
     */
    @FXML
    private void S4ArrowButtonClicked(ActionEvent actionEvent){
        Statistics oldStats = S4Stats;
        S4Stats = statsQueue.dequeue();
        S4NameLabel.setText(S4Stats.getType());
        S4NumLabel.setText(S4Stats.getValue());
        statsQueue.enqueue(oldStats);
    }
    
    /**
     * Load seperate data set
     * @param ActionEvent the action handler event object
     */
    @FXML
    private void loadFileButtonClicked(ActionEvent actionEvent){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File file = fileChooser.showOpenDialog(this.stage);
        if (file != null) { // if the dialogue is cancelled, file is null
            openFile(file.getName());
            updateStatusBar(file.getName() + " loaded");
        }
    }
    
    /**
     * create a new model for the statistics panel
     * @param String the name of the file
     */
    private void openFile(String fileName){
        try {
            PropertiesModel temp = new PropertiesModel(fileName);
            this.propertiesModel = temp;
            update(this.propertiesModel);
        }
        catch(Exception error){
            displayPopup("Error","Invalid file loading","Application ran into issue when loading " + fileName, AlertType.ERROR);
        }
    }
    
    /**
     * Update the text on the status bar
     */
    private void updateStatusBar(String status){
        statusLabel.setText(status);
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
