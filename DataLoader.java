import java.util.ArrayList;

/**
 * This class involves storing all the properties listing of our data
 *
 * @author Kwan Yui Chiu (K21003778)
 * @version 14-03-2022
 */
public interface DataLoader
{
    abstract public ArrayList<PropertyListing> load(String filename);
}
