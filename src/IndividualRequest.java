import java.util.ArrayList;

/**
 * An individual request is for one person
 * @author Maxim Tiourin
 */
public class IndividualRequest implements Request 
{
	private String name;
	private String serviceClass;
	private String preference;
	
	public IndividualRequest(String name, String serviceClass, String preference)
	{
		this.name = name;
		this.serviceClass = serviceClass;
		this.preference = preference;
	}

	@Override
	public String getServiceClassName() 
	{
		return serviceClass;
	}

	@Override
	public ArrayList<String> getNames() 
	{
		ArrayList<String> names = new ArrayList<String>();
		names.add(name);
		return names;
	}
	
	/**
	 * Returns the seat preference
	 * @return String seat preference
	 */
	public String getSeatPreference()
	{
		return preference;
	}
}
