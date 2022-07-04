
/**
 * Represents an individual borough on the map.
 * 
 * @author Archie Sage (K20003540)
 * @version 14.03.2022
 */
public class Borough
{
    /*
     * Attributes
     */
    
    // Represents the name of the borough this map point represents.
    private String shortName;
    private String longName;
    
    // Represents the value associated with this map point (a.k.a the number of properties within this location).
    private int value;
    
    // Represents the cordinates on the map.
    private int x, y;
    
    /**
     * Makes a new borough.
     * 
     * @param longName The full name of the borough.
     * @param shortName The abbreviation for the borough.
     * @param x The x-coordinate for the borough on the map.
     * @param y The y-coordinate for the borough on the map.
     */
    public Borough(String longName, String shortName, int x, int y)
    {
        this.longName = longName;
        this.shortName = shortName;
        this.x = x;
        this.y = y;
    }
    
    /*
     * Setters
     */
    
    /**
     * @param value The value to be assigned to this borough.
     */
    public void setValue(int value)
    {
        this.value = value;
    }
    
    /*
     * Getters
     */
    
    /**
     * @return The value associated with the borough.
     */
    public int getValue()
    {
        return value;
    }
    
    /**
     * @return The full name of the borough.
     */
    public String getLongName()
    {
        return longName;
    }
    
    /**
     * @return The short name of the borough.
     */
    public String getShortName()
    {
        return shortName;
    }
    
    /**
     * @return The x-coordinate for the borough on the map.
     */
    public int getX()
    {
        return x;
    }
    
    /**
     * @return The y-coordinate for the borough on the map.
     */
    public int getY()
    {
        return y;
    }
}
