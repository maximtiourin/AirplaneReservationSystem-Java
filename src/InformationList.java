import java.util.ArrayList;

/**
 * An information list is a list of flight classes that can be displayed
 * in informative ways.
 * @author Maxim Tiourin
 */
public abstract class InformationList 
{
	private ArrayList<ServiceClass> serviceClasses;
	
	public InformationList()
	{
		serviceClasses = new ArrayList<ServiceClass>();
	}
	
	/**
	 * Override. Should return the string representation of the list, as desired output
	 * for the console window.
	 * @return String string representation
	 */
	public abstract String toString();
	
	/**
	 * Adds the service class to the list
	 * @param sc the service class
	 */
	public void addServiceClass(ServiceClass sc)
	{
		serviceClasses.add(sc);
	}
	
	/**
	 * Returns the service class at the index in the service class list
	 * @param index the index to return
	 * @return ServiceClass the service class at the index
	 */
	public ServiceClass getServiceClass(int index)
	{
		return serviceClasses.get(index);
	}
	
	/**
	 * Returns the service class with the name in the service class list
	 * @param name the name of the service class to return
	 * @return ServiceClass the service class with the name or null if none exists
	 */
	public ServiceClass getServiceClass(String name)
	{
		for (ServiceClass e : serviceClasses)
		{
			if (e.getClassName().equals(name))
			{
				return e;
			}
		}
		
		return null;
	}
	
	public ArrayList<ServiceClass> getServiceClassList()
	{		
		return serviceClasses;
	}
	
	/**
	 * Updates the service class with the same name as the argument to be the new service class
	 * @param sc the new service class to update as
	 */
	public void updateList(ServiceClass sc)
	{
		for (ServiceClass e : serviceClasses)
		{
			if (e.getClassName().equals(sc.getClassName()))
			{
				e.becomeShallowCopyOf(sc);
			}
		}
	}
	
	/**
	 * Returns the size of the service class list
	 * @return int the size of the service class list
	 */
	public int getServiceClassCount()
	{
		return serviceClasses.size();
	}
}
