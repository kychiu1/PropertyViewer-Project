
/**
 * Containers of statistics for the statistics boxes
 *
 * @author Kwan Yui Chiu(K21003778)
 * @version 18-03-2022
 */
public class Statistics<E>
{
    private E value;
    private String type;

    /**
     * Constructor for objects of class Statistics
     */
    public Statistics(String type,E value)
    {
        this.value = value;
        this.type = type;
    }
    
    /**
     * the name of the statistic in string form 
     * @return the name of the statistic in string form 
     */
    public String getType(){
        return this.type;
    }
    
    /**
     * the value of the statistic in string form 
     * @return the value of the statistic in string form 
     */
    public String getValue(){
        return this.value.toString();
    }
}
