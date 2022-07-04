import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import java.net.URL;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.image.ImageView;
import javafx.scene.control.ProgressBar;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEvent;
import javafx.event.ActionEvent;
import javafx.application.Application;
import java.net.URI;

/**
 * This class controls the google map view fxml file
 * When the google maps view is initialized, it loads the index.html file into 
 * the webview and updates the labels
 *
 * @author Reibjok Othow k21009059
 * @version 21/03/2021
 */
public class GoogleMapViewController implements Initializable
{
    @FXML
    private WebView webView;
    
    @FXML
    private ImageView loading;
    
    @FXML
    private Button streetViewButton;
    
    @FXML
    private Button refreshButton;
    
    @FXML
    private Label propertyName;
    
    @FXML
    private Label propertyAddress;
    
    @FXML
    private Label propertyPrice;
    
    private WebEngine engine;
    
    public static double latitude;
    
    public static double longitude;
    
    public static String name;
    
    public static String address;
    
    public static int price;
    
    /**
     * initialize a webViewer and shows a map based on the the latitude and 
     * longitude values using google map API
     * it loads the index.html file into the webview
     */
    
    public void initialize(URL location, ResourceBundle resources)
    {
        engine = webView.getEngine();
        URL urlGoogleMaps = getClass().getResource("index.html");
        engine.load(urlGoogleMaps.toExternalForm());
        
        engine.setOnAlert(new EventHandler<WebEvent<String>>() {
                @Override
                public void handle(WebEvent<String> e) {
                    System.out.println(e.toString());
                }
            });
        
        engine.getLoadWorker().stateProperty().addListener(
            new ChangeListener<State>() {
              @Override public void changed(ObservableValue ov, State oldState, State newState) {

                  if (newState == Worker.State.SUCCEEDED) {
                    // hide progress bar then page is ready
                    engine.executeScript("" +
                        "window.lat = " + latitude + ";" +
                        "window.lon = " + longitude + ";" +
                        "document.goToLocation(window.lat, window.lon);"
                    );
                    loading.setVisible(false);
                }
                else{
                    loading.setVisible(true);
                }
                  
                }
            });
            
        propertyName.setText(name);
        propertyAddress.setText(address);
        propertyPrice.setText("£"+price);
    }
    
    /**
     * This method is called when the refresh button is clicked
     * it reloads the webview
     */
    public void refresh(){
        engine.reload();
    }
    
    /**
     * This method is called when the streetView button is clicked
     * it opens up a street view of the property in the browser
     */
    public void streetViewClicked() throws java.net.URISyntaxException,java.io.IOException{
        //https://www.google.com/maps/@40.7140769,-74.0061719,3a,75y,38.73h,90.1t/data=!3m6!1e1!3m4!1swDdAf27G3Vp313NZPcRRrA!2e0!7i16384!8i8192
        String streetViewURL = "http://maps.google.com/maps?q=&layer=c&cbll=" + latitude + ","+ longitude +"&cbp=11,0,0,0,0";
        java.awt.Desktop.getDesktop().browse(new URI(streetViewURL));
    }
    
    /**
     * This method sets the latitude of the property
     * @params double lat the latitude of the porperty
     */
    public void setLat(double lat){
        latitude = lat;
    }
    
    /**
     * This method sets the longitude of the property
     * @params double lon the longitude of the porperty
     */
    public void setLon(double lon){
        longitude = lon;
    }
}
