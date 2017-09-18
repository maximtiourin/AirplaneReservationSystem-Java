import java.util.ArrayList;

/**
 * A group request is for a group of people
 * @author Maxim Tiourin
 */
public class GroupRequest implements Request 
{
	private String groupName;
	private String serviceClass;
	private ArrayList<String> names;
	
	public GroupRequest(ArrayList<String> names, String serviceClass, String groupName)
	{
		this.names = names;
		this.serviceClass = serviceClass;
		this.groupName = groupName;
	}

	@Override
	public String getServiceClassName() 
	{
		return serviceClass;
	}

	@Override
	public ArrayList<String> getNames() 
	{
		return names;
	}
	
	/**
	 * Returns the name of the group
	 * @return String name of the group
	 */
	public String getGroupName()
	{
		return groupName;
	}
}
