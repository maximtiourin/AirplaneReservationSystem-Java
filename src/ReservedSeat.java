/**
 * A reserved seat stores reservation information for a specific seat.
 * @author Maxim Tiourin
 */
public class ReservedSeat 
{
	private String name;
	private String group;
	
	public ReservedSeat(String name, String group)
	{
		this.name = name;
		this.group = group;
	}
	
	/**
	 * Returns the name of the person reserved to the seat
	 * @return String name of person
	 */
	public String getName()
	{
		return name;
	}
	
	public String getGroup()
	{
		if (group == null) return "";
		return group;
	}
	
	/**
	 * Returns whether or not this seat is for a group reservation
	 * @return boolean whether or not this is a group seat
	 */
	public boolean isGroupSeat()
	{
		if ((group.equals("")) || (group == null))
		{
			return false;
		}
		
		return true;
	}
}
