import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import com.opencsv.CSVReader;
import java.net.URISyntaxException;

/**
 * This class handles the loading of data from a csv file
 * 
 * @author Kwan Yui Chiu (K21003778)
 * @version 19-03-2022
 */
public class PropertyDataLoader implements DataLoader
{
    /**
     * Constructor for PropertyDataLoader
     */
    public PropertyDataLoader(){
        
    }
    
    /** 
     * Return an ArrayList containing the rows in the Property London data set csv file.
     * 
     * @return an ArrayList containing the rows in the Property London data set csv file.
     */
    public ArrayList<PropertyListing> load(String filename) 
    {
        System.out.print("Begin loading london dataset...");
        ArrayList<PropertyListing> listings = new ArrayList<PropertyListing>();
        
        try
        {
            URL url = getClass().getResource(filename);
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            
            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) 
            {
                String id = line[0];
                String name = line[1];
                String host_id = line[2];
                String host_name = line[3];
                String neighbourhood = line[4];
                double latitude = convertDouble(line[5]);
                double longitude = convertDouble(line[6]);
                String room_type = line[7];
                int price = convertInt(line[8]);
                int minimumNights = convertInt(line[9]);
                int numberOfReviews = convertInt(line[10]);
                String lastReview = line[11];
                double reviewsPerMonth = convertDouble(line[12]);
                int calculatedHostListingsCount = convertInt(line[13]);
                int availability365 = convertInt(line[14]);

                PropertyListing listing = new PropertyListing(id, name, host_id,
                        host_name, neighbourhood, latitude, longitude, room_type,
                        price, minimumNights, numberOfReviews, lastReview,
                        reviewsPerMonth, calculatedHostListingsCount, availability365
                    );
                    
                listings.add(listing);
            }
        } 
        catch(IOException | URISyntaxException e)
        {
            System.out.println("Failure! Something went wrong");
            e.printStackTrace();
        }
        System.out.println("Success! Number of loaded records: " + listings.size());
        return listings;
    }

    /**
     * Takes in a string that represents a double then coverting it to a double instance
     *
     * @param doubleString the string to be converted to Double type
     * @return the Double value of the string, or -1.0 if the string is 
     * either empty or just whitespace
     */
    private Double convertDouble(String doubleString)
    {
        if(doubleString != null && !doubleString.trim().equals(""))
        {
            return Double.parseDouble(doubleString);
        }
        return -1.0;
    }

    /**
     * Takes in a string that represents a integer then coverting it to an integer instance
     *
     * @param intString the string to be converted to Integer type
     * @return the Integer value of the string, or -1 if the string is 
     * either empty or just whitespace
     */
    private Integer convertInt(String intString)
    {
        if(intString != null && !intString.trim().equals(""))
        {
            return Integer.parseInt(intString);
        }
        return -1;
    }

}
