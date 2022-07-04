import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class PropertiesModelTest.
 *
 * @author  Kwan Yui Chiu (K21003778)
 * @version 15-03-2022
 */
public class PropertiesModelTest
{
    private PropertiesModel properti1;
    /**
     * Default constructor for test class PropertiesModelTest
     */
    public PropertiesModelTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        this.properti1 = new PropertiesModel();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
    
    /**
     * A test for the test class to work
     */
    @Test
    public void emptyTest()
    {
        assertTrue(true);
    }
    
    /**
     * Test if data is loaded successfully
     */
    @Test
    public void canLoadData()
    {
        assertEquals(53904,this.properti1.getProperties().size());
        assertTrue("property-london.csv".equals(this.properti1.getFileName()));
    }
    
    /**
     * Test if different files can be loaded
     */
    @Test
    public void canLoadSeperateData()
    {
        PropertiesModel p2 = new PropertiesModel("property-bristol.csv");
        assertEquals(1631,p2.getProperties().size());
        assertTrue("property-bristol.csv".equals(p2.getFileName()));
    }
    
    /**
     * Test for getting the correct most expensive borough
     */
    @Test
    public void getCorrectMostExpensiveBorough()
    {
        assertTrue(this.properti1.getMostExpensiveBorough().equals("Westminster"));
    }
    
    /**
     * Test for getting correct number of borough
     */
    @Test
    public void getCorrectNoBorough()
    {
        assertEquals(33,this.properti1.noPropertiesPerBorough(13).size());
    }
    
    /**
     * Test for getting correct number of homes or apartments from london data set
     */
    @Test
    public void getCorrectNumberHomesOrApart()
    {
        assertEquals(27175,this.properti1.getNumberOfHomesOrApart());
    }
    
    /**
     * Test for getting correct average of number of reviews from london data set
     */
    @Test
    public void getCorrectAverageNumberReviews()
    {
        assertEquals("12.47",this.properti1.getAverageNumberReviews());
    }
    
    /**
     * Test for getting correct number of properties with no price filters
     */
    @Test
    public void getCorrectNumberPropertiesWithNoBound()
    {
        assertEquals(33,this.properti1.noPropertiesPerBorough().size());
        assertEquals(5613,this.properti1.noPropertiesPerBorough().get("Tower Hamlets"));
    }
    
    /**
     * Test for getting correct number of properties with lower bound filter from london data set
     */
    @Test
    public void getCorrectNumberPropertiesWithLB()
    {
        assertEquals(33,this.properti1.noPropertiesPerBorough(100).size());
        assertEquals(4184,this.properti1.noPropertiesPerBorough(100).get("Westminster"));
    }
    
    /**
     * Test for getting correct number of properties with LB and UB price filter from london data set
     */
    @Test
    public void getCorrectNumberPropertiesWithLB_andUB()
    {
        assertEquals(33,this.properti1.noPropertiesPerBorough(100,10000).size());
        assertEquals(4160,this.properti1.noPropertiesPerBorough(100,10000).get("Westminster"));
    }
    
    /**
     * Test for getting correct list of properties with LB and UB price filter from london data set
     */
    @Test
    public void getCorrectNumberPropertiesWithLB_andUB_withPropertiesObject()
    {
        assertEquals(4160,this.properti1.getPropertiesWithinSpecificRange("Westminster", 100, 10000).size());
        assertTrue(this.properti1.getPropertiesWithinSpecificRange("Westminster", 100, 10000).get(0) instanceof PropertyListing);
    }
    
    /**
     * Test for getting correct cheapest price of properties with no price filter from london data set
     */
    @Test
    public void getCorrectCheapestPropertyWithNoBound()
    {
        assertEquals(33,this.properti1.noPropertiesPerBorough().size());
        assertEquals(8,this.properti1.minPricePerNight());
    }
    
    /**
     * Test for getting correct cheapest price of properties with LB price filter from london data set
     */
    @Test
    public void getCorrectCheapestPropertyWithLB()
    {
       assertEquals(33,this.properti1.noPropertiesPerBorough(100).size());
       assertEquals(100,this.properti1.minPricePerNight(100));
    }
    
    /**
     * Test for getting correct cheapest price of properties with LB and UB price filter from london data set
     */
    @Test
    public void getCorrectCheapestPropertyWithLB_andUB()
    {
        assertEquals(33,this.properti1.noPropertiesPerBorough(100,10000).size());
        assertEquals(100,this.properti1.minPricePerNight(100, 10000));
    }
    
    /**
     * Test for getting correct most expensive price of properties with no price filter from london data set
     */
    @Test
    public void getCorrectExpensivePropertyWithNoBound()
    {
        assertEquals(33,this.properti1.noPropertiesPerBorough().size());
        assertEquals(2000000,this.properti1.maxPricePerNight());
    }
    
    /**
     * Test for getting correct most expensive price of properties with LB price filter from london data set
     */
    @Test
    public void getCorrectExpensivePropertyWithLB()
    {
       assertEquals(33,this.properti1.noPropertiesPerBorough(100).size());
       assertEquals(2000000,this.properti1.maxPricePerNight(100));
    }
    
    /**
     * Test for getting correct most expensive price of properties with UB and LB price filter from london data set
     */
    @Test
    public void getCorrectExpensivePropertyWithLB_andUB()
    {
        assertEquals(33,this.properti1.noPropertiesPerBorough(100,10000).size());
        assertEquals(9900,this.properti1.maxPricePerNight(100, 10000));
    }
    
    /**
     * Test for getting correct most host listing count borough from london data set
     */
    @Test
    public void getCorrectMostCalculatedHostListingCountBorough()
    {
        assertTrue(this.properti1.getBoroughWithHighestListingCount().equals("Kensington and Chelsea"));
    }
    
    /**
     * Test for getting correct most listing host from london data set
     */
    @Test
    public void getCorrectHostWithMostListing(){
        assertTrue("Tom".equals(this.properti1.getHostWithMostListing()));
    }
    
    /**
     * Test for getting correct most listing ID with longest available period from london data set
     */
    @Test
    public void getCorrectListingIDWithLongestAvailablePeriod(){
        assertTrue("735298".equals(this.properti1.getListingIDOfPropertyWithLongestAvailablePeriod()));
    }
}


