import java.util.ArrayList;

/**
 * A request is used to fill/empty the flight manifest.
 * @author Maxim Tiourin
 */
public interface Request 
{
	/**
	 * Implement. Returns the name of the service class this request is for
	 * @return String the name of the service class the request is for
	 */
	public String getServiceClassName();
	/**
	 * Implement. Returns a list of the names of people the request is for
	 * @return ArrayList<String> names of the people the reservation is for
	 */
	public ArrayList<String> getNames();
	
}
