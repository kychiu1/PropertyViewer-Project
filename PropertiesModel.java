import java.util.ArrayList;
import java.util.HashMap;
import java.text.DecimalFormat;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * This class involves storing all the properties listing of our data
 *
 * @author Kwan Yui Chiu (K21003778)
 * @version 14-03-2022
 */
public class PropertiesModel
{
    // constants for the model class
    private static final String DEFAULT_FILE = "property-london.csv";
    private static final DecimalFormat df = new DecimalFormat("0.00");
    
    // the name of file loaded to this model currently
    private String fileName;
    private ArrayList<PropertyListing> properties;
    
    // Various statistics 
    private Integer totalNumberOfHomesOrApart;
    private Integer totalAvailable;
    private Integer totalReviews;
    private String mostExpensiveBoroughName;
    private String boroughWithHighestListingCount;
    private Integer maxMinNight;
    private String hostWithMostListing;
    private String listingIDWithLongestAvailablePeriod;
    
    /**
     * Constructor for the model class
     * 
     * Will add parameter to allow future model choice
     */
    public PropertiesModel()
    {
        setProperties(DEFAULT_FILE);
    }
    
    /**
     * Constructor for the model class with option to start with a different file
     * 
     * @param String fileName file intended to load 
     */
    public PropertiesModel(String fileName)
    {
        setProperties(fileName);
    }
    
    /**
     * Returns the borough with the most listings
     * @return total number of properties
     */
    public String getBoroughWithHighestListingCount(){
        if (boroughWithHighestListingCount == null){
            HashMap<String,Integer> boroughCounters = new HashMap<>();
            for (PropertyListing property: this.properties){
                if (boroughCounters.get(property.getNeighbourhood()) == null){
                    boroughCounters.put(property.getNeighbourhood(),
                                            property.getCalculatedHostListingsCount());
                }
                else{
                    int currentValue = boroughCounters.get(property.getNeighbourhood());
                    int newValue = currentValue + property.getCalculatedHostListingsCount();
                    boroughCounters.put(property.getNeighbourhood(),newValue);
                }
            }
            
            int maximumCalculatedHostListingsCount = 0;
            
            for (String borough:boroughCounters.keySet()){
                if (boroughCounters.get(borough) > maximumCalculatedHostListingsCount){
                    boroughWithHighestListingCount = borough;
                    maximumCalculatedHostListingsCount = boroughCounters.get(borough);
                }            
            }
        }
        return boroughWithHighestListingCount;
    }
    
    /**
     * Returns the total number of properties in dataset 
     * @return total number of properties
     */
    public int getTotalNumberOfProperties(){
        return this.properties.size();
    }
    
    /**
     * Returns the name of the file loaded
     * @return the name of the file loaded
     */
    public String getFileName(){
        return this.fileName;
    }
    
    /**
     * Returns the actual list of properties
     * @return listings of properties from file loaded
     */
    public ArrayList<PropertyListing> getProperties(){
        return this.properties;
    }
    
    /**
     * Load properties using fileName
     * 
     * @param String fileName
     */
    private void setProperties(String fileName){
        this.fileName = fileName;
        DataLoader dataloader = new PropertyDataLoader();
        this.properties = dataloader.load(fileName);
    }
    
    /**
     * Calculates the average number of reviews excluding errorneus data
     * 
     * @return double for average number of reviews
     */
    public String getAverageNumberReviews(){
        if (totalReviews == null){
            totalReviews = this.properties.stream()
                                        .filter(p -> p.getReviewsPerMonth() > 0)
                                        .map(p -> p.getNumberOfReviews())
                                        .reduce(0, (total,review) -> total + review);
        }
        return df.format((double) totalReviews/this.properties.size());
    }
    
    /**
     * Calculates the number of available properties in the coming year
     * 
     * @return integer for number of available properties
     */
    public int getNumberOfAvailableProperties(){
        if (totalAvailable == null){
            totalAvailable = this.properties.stream()
                                .filter(p -> p.getAvailability365() > 0)
                                .map(p -> 1)
                                .reduce(0,(total,ONE) -> total + ONE);
        }   
        return totalAvailable;
    }
    
    /**
     * Calculates the number of apartment/entire home in the coming year
     * 
     * @return integer for number of homes or apartments
     */
    public int getNumberOfHomesOrApart(){
        if (totalNumberOfHomesOrApart == null){
            totalNumberOfHomesOrApart = this.properties.stream()
                                .filter(p -> "Entire home/apt".equals(p.getRoom_type()))
                                .map(p -> 1)
                                .reduce(0,(accumulated,ONE) -> accumulated + ONE);
        }
        return totalNumberOfHomesOrApart;
    }
    
    /**
     * Returns the maximum minimum night from all listing
     * 
     * @return integer of nights
     */
    private int getMaximumMinimumNight(){
        if (maxMinNight == null){
            maxMinNight = 0;
            for (PropertyListing property:this.properties){
                if (maxMinNight < property.getMinimumNights())
                    maxMinNight = property.getMinimumNights();
            }
        }
        return maxMinNight;
    }
    
    /**
     * Returns a list of properties within specific price range
     * @param int lower bound of price
     * @param int upper bound of price
     * @return the list of property listings in range
     */
    public ArrayList<PropertyListing> getPropertiesWithinSpecificRange(String Borough,int lowerBound,int upperBound){
        ArrayList<PropertyListing> propertiesInRange = new ArrayList<>();
        for (PropertyListing property: this.properties){
            int priceOfProperty = property.getPrice() * property.getMinimumNights();
            if (priceIsInRange(priceOfProperty, lowerBound, upperBound) && property.getNeighbourhood().equals(Borough)){
                propertiesInRange.add(property);
            }
        }
        return propertiesInRange;
    }    

    /**
     * Returns the name of the most expensive borough
     * 
     * @return name of the most expensive borough
     */
    public String getMostExpensiveBorough(){
        if (mostExpensiveBoroughName == null){
            int Max_minNight = getMaximumMinimumNight();
            
            HashMap<String,Long> boroughCounters = new HashMap<>();
            for (PropertyListing property: this.properties){
                long priceOfProperty = (long) property.getPrice() * Max_minNight;
                if (boroughCounters.get(property.getNeighbourhood()) == null){
                    boroughCounters.put(property.getNeighbourhood(),priceOfProperty);
                }
                else{
                    long currentValue = boroughCounters.get(property.getNeighbourhood());
                    long newValue = currentValue + priceOfProperty;
                    boroughCounters.put(property.getNeighbourhood(),newValue);
                }
            }
            
            long maximumTotalCost = 0;
            
            for (String borough:boroughCounters.keySet()){
                if (boroughCounters.get(borough) > maximumTotalCost){
                    mostExpensiveBoroughName = borough;
                    maximumTotalCost = boroughCounters.get(borough);
                }            
            }
        }
        return mostExpensiveBoroughName;
    }
    
    /**
     * @return boolean whether the price inputted is within range
     */
    private boolean priceIsInRange(int price, int lowerBound, int upperBound){
        return lowerBound <= price && price <= upperBound;
    }
    
    /**
     * @return the list of borough names
     */
    private Set<String> boroughNames(){
        return this.properties.stream()
                .map(property -> property.getNeighbourhood())
                .collect(Collectors.toSet());
    }
    
    /**
     * Return number of properties per borough with price per night filter with minimum and maximum price per night
     * 
     * @param int lowerBound for price per night
     * @param int upperBound for price per night
     * @return a dictionary with borough name and their relative number of properties
     */
    public HashMap<String,Integer> noPropertiesPerBorough(int lowerBoundPrice,int upperBoundPrice){
        HashMap<String,Integer> boroughCounters = new HashMap<>();
        
        boroughNames().forEach(borough -> boroughCounters.put(borough,0));
        
        for (PropertyListing property: this.properties){
            int propertyPrice = property.getPrice() * property.getMinimumNights();
            if (priceIsInRange(propertyPrice,lowerBoundPrice,upperBoundPrice)){ 
                int currentValue = boroughCounters.get(property.getNeighbourhood());
                int newValue = currentValue + 1;
                boroughCounters.put(property.getNeighbourhood(),newValue);
            }
        }
        return boroughCounters;
    }
    
    /**
     * Return number of properties per borough with price per night filter with minimum price per night
     * 
     * @param int lowerBound for price per night
     * @return a dictionary with borough name and their relative number of properties
     */
    public HashMap<String,Integer> noPropertiesPerBorough(int lowerBoundPrice){
        return noPropertiesPerBorough(lowerBoundPrice,Integer.MAX_VALUE);
    }
    
    /**
     * Return number of properties per borough with price per night filter
     * 
     * @return a dictionary with borough name and their relative number of properties
     */
    public HashMap<String,Integer> noPropertiesPerBorough(){
        return noPropertiesPerBorough(0,Integer.MAX_VALUE);
    }
    
    /**
     * Price for the cheapest property from ALL properties with minimum and maximum price per night
     * 
     * @param int lowerBound for price per night
     * @param int upperBound for price per night
     * @return the cheapest property pirce base on criteria
     */
    public int minPricePerNight(int lowerBound,int upperBound){
        int minPricePerNight = Integer.MAX_VALUE;
        for (PropertyListing property: this.properties){
            int priceOfProperty = property.getPrice() * property.getMinimumNights();
            if (priceOfProperty < minPricePerNight && priceIsInRange(priceOfProperty,lowerBound,upperBound)){
                minPricePerNight = priceOfProperty;
            }
        }
        return minPricePerNight;
    }
    
    /**
     * Price for the cheapest property from ALL properties with a minimum price per night
     * 
     * @param int lowerBound for price per night
     * @return the cheapest property price base on criteria
     */
    public int minPricePerNight(int lowerBound){
        return minPricePerNight(lowerBound,Integer.MAX_VALUE);
    }
    
    /**
     * Price for the cheapest property from ALL properties with a price filter
     * 
     * @return the cheapest property price
     */
    public int minPricePerNight(){
        return minPricePerNight(0,Integer.MAX_VALUE);
    }
    
    /**
     * Price for the most expensive property from ALL properties with minimum and maximum price per night
     * 
     * @param int lowerBound for price per night
     * @param int upperBound for price per night
     * @return most expensive property price from criteria
     */
    public int maxPricePerNight(int lowerBound, int upperBound){
        int maxPricePerNight = 0;
        for (PropertyListing property: this.properties){
            int priceOfProperty = property.getPrice() * property.getMinimumNights();
            if (priceOfProperty > maxPricePerNight && priceIsInRange(priceOfProperty,lowerBound,upperBound)){
                maxPricePerNight = priceOfProperty;
            }
        }
        return maxPricePerNight;
    }
    
    /**
     * Price for the most expensive property from ALL properties with a minimum price per night
     * @param int lowerBound for price per night
     * @return most expensive property price from criteria
     */
    public int maxPricePerNight(int lowerBound){
        return maxPricePerNight(lowerBound,Integer.MAX_VALUE);
    }
    
    /**
     * Price for the most expensive property from ALL properties
     * @return most expensive property price
     */
    public int maxPricePerNight(){
        return maxPricePerNight(0,Integer.MAX_VALUE);
    }
    
    /**
     * Returns the name of host with specific hostID
     * 
     *@param String corresponding hostID
     *@return name of host with the input hostID 
     */
    private String findHostName(String hostID){
        for (PropertyListing property:this.properties){
            if (hostID.equals(property.getHost_id())){
                return property.getHost_name();
            }
        }
        return "Not Found";
    }
    
    /**
     * Find the host with the most listings in the dataset
     * 
     * @return Name of the host with most listings
     */
    public String getHostWithMostListing(){
        if(hostWithMostListing == null){
            HashMap<String,Integer> hostCounters = new HashMap<>();
            for(PropertyListing property: this.properties){
                if (hostCounters.get(property.getHost_id()) == null){
                    hostCounters.put(property.getHost_id(),1);
                }
                else{
                    int currentValue = hostCounters.get(property.getHost_id());
                    currentValue++;
                    hostCounters.put(property.getHost_id(),currentValue);
                }
            }
            
            int maximum = 0;
            String maxHostID = "";
            for (String hostID:hostCounters.keySet()){
                if (hostCounters.get(hostID) > maximum){
                    maxHostID = hostID;
                    maximum = hostCounters.get(hostID);
                }            
            }
            
            hostWithMostListing = findHostName(maxHostID);
        }
        
        return hostWithMostListing;
    }
    
    /**
     * Method for finding the listing ID with the most available period
     * @return the listing ID With longest available period
     */
    public String getListingIDOfPropertyWithLongestAvailablePeriod(){
        if (listingIDWithLongestAvailablePeriod == null){
            int longestAvailable365 = 0;
            listingIDWithLongestAvailablePeriod = "";
            
            for (PropertyListing property: this.properties){
                if (property.getAvailability365() > longestAvailable365){
                    longestAvailable365 = property.getAvailability365(); 
                    listingIDWithLongestAvailablePeriod = property.getId();
                }
            }
        }
        
        return listingIDWithLongestAvailablePeriod;
    }
}