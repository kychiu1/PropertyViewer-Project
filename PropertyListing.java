 /**
 * Represents one listing of a property for rental on Properties4u.
 * This is essentially one row in the data table. Each column
 * has a corresponding field.
 * 
 * @author Archie Sage (K20003540), King's Informatics Department
 * @version 12.03.2022
 */ 

public class PropertyListing 
{
    // The id and name of the individual property
    private String id;
    private String name;
    
    /*
     * The id and name of the host for this listing.
     * Each listing has only one host, but one host may
     * list many properties.
     */
    private String host_id;
    private String host_name;

    /*
     * The grouped location to where the listed property is situated.
     * For this data set, it is a london borough.
     */
    private String neighbourhood;

    /*
     * The location on a map where the property is situated.
     */
    private double latitude;
    private double longitude;

    /*
     * The type of property, either "Private room" or "Entire Home/apt".
     */
    private String room_type;

    /*
     * The price per night's stay
     */
    private int price;

    /*
     * The minimum number of nights the listed property must be booked for.
     */
    private int minimumNights;
    private int numberOfReviews;

    /*
     * The date of the last review, but as a String
     */
    private String lastReview;
    private double reviewsPerMonth;

    /*
     * The total number of listings the host holds across Properties4u
     */
    private int calculatedHostListingsCount;
    
    /*
     * The total number of days in the year that the property is available for.
     */
    private int availability365;

    
    /**
     * Constructor to create a new Property Listing.
     * 
     * @param id The id of the property.
     * @param name The The name of the property.
     * @param host_id The id of this listing's host.
     * @param host_name The name of this listing's host.
     * @param neighbourhood The neighourhood that the property resides in.
     * @param latitude The property's latitude.
     * @param longitude The property's longitude.
     * @param room_type The room type displayed for this listing.
     * @param price The price per night for this listing.
     * @param minimumNights The minimum amount of nights a guest can stay at the property.
     * @param numberOfReviews The amount of reviews that the property has associated with it.
     * @param lastReview The date of the last review in string format.
     * @param reviewsPerMonth The amount of reviews per month for the property listing.
     * @param calculatedHostListingsCount The total number of listings the host holds across Properties4u.
     * @param availability365 The total number of days in the year that the property is available for.
     */
    public PropertyListing(String id, String name, String host_id,
                         String host_name, String neighbourhood, double latitude,
                         double longitude, String room_type, int price,
                         int minimumNights, int numberOfReviews, String lastReview,
                         double reviewsPerMonth, int calculatedHostListingsCount, int availability365) 
    {
        this.id = id;
        this.name = name;
        this.host_id = host_id;
        this.host_name = host_name;
        this.neighbourhood = neighbourhood;
        this.latitude = latitude;
        this.longitude = longitude;
        this.room_type = room_type;
        this.price = price;
        this.minimumNights = minimumNights;
        this.numberOfReviews = numberOfReviews;
        this.lastReview = lastReview;
        this.reviewsPerMonth = reviewsPerMonth;
        this.calculatedHostListingsCount = calculatedHostListingsCount;
        this.availability365 = availability365;
    }
    
    /*
     * Overridden methods
     */

    /**
     * Concatenates various different listing attributes to form a string that describes the listing as a whole.
     * 
     * @return A string where almost all of the listing's properties have been concatenated together in a JSON-like format.
     */
    @Override
    public String toString() 
    {
        return "AirbnbListing{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", host_id='" + host_id + '\'' +
                ", host_name='" + host_name + '\'' +
                ", neighbourhood='" + neighbourhood + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", room_type='" + room_type + '\'' +
                ", price=" + price +
                ", minimumNights=" + minimumNights +
                ", numberOfReviews=" + numberOfReviews +
                ", lastReview='" + lastReview + '\'' +
                ", reviewsPerMonth=" + reviewsPerMonth +
                ", calculatedHostListingsCount=" + calculatedHostListingsCount +
                ", availability365=" + availability365 +
                '}';
    }
    
    /*
     * Getters
     */
    
    /**
     * The id of the property listing.
     * @return The id of the property listing.
     */
    public String getId() 
    {
        return id;
    }

    /**
     * The name of the property listing.
     * @return The name of the property listing.
     */
    public String getName() 
    {
        return name;
    }

    /**
     * The id of the host responsible for this property listing.
     * @return The id of the host responsible for this property listing.
     */
    public String getHost_id() 
    {
        return host_id;
    }

    /**
     * The name of the host responsible for this property listing.
     * @return The name of the host responsible for this property listing.
     */
    public String getHost_name() 
    {
        return host_name;
    }

    /**
     * The neighbourhood in which this listing's property resides.
     * @return The neighbourhood in which this listing's property resides.
     */
    public String getNeighbourhood() 
    {
        return neighbourhood;
    }

    /**
     * The latitude of the property's location.
     * @return The latitude of the property's location.
     */
    public double getLatitude() 
    {
        return latitude;
    }

    /**
     * The longitude of the property's location.
     * @return The longitude of the property's location.
     */
    public double getLongitude() 
    {
        return longitude;
    }

    /**
     * The price associated with this listing.
     * @return The type of room showcased within this listing.
     */
    public String getRoom_type() 
    {
        return room_type;
    }

    /**
     * The price associated with this listing.
     * @return The price associated with this listing.
     */
    public int getPrice() 
    {
        return price;
    }

    /**
     * The minimum amount of nights the room can be temporarily rented for.
     * @return The minimum amount of nights the room can be temporarily rented for.
     */
    public int getMinimumNights() 
    {
        return minimumNights;
    }

    /**
     * The amount of reviews this listing has associated with it.
     * @return The amount of reviews this listing has associated with it.
     */
    public int getNumberOfReviews() 
    {
        return numberOfReviews;
    }

    /**
     * The date of the last review left on this listing as a string.
     * @return The date of the last review left on this listing as a string.
     */
    public String getLastReview() 
    {
        return lastReview;
    }

    /**
     * The reviews per month for this listing.
     * @return The reviews per month for this listing.
     */
    public double getReviewsPerMonth() 
    {
        return reviewsPerMonth;
    }

    /**
     * The amount of listings this listing's host is responsible for.
     * @return The amount of listings this listing's host is responsible for.
     */
    public int getCalculatedHostListingsCount() 
    {
        return calculatedHostListingsCount;
    }

    /**
     * Return the number of days within the year that the listing is available for
     * @return The number of days within the year that the property is available for.
     */
    public int getAvailability365() 
    {
        return availability365;
    }
    
    
}
