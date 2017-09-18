/**
 * The availibility list shows the currently available seats on the flight.
 * @author Maxim Tiourin
 */
public class AvailabilityList extends InformationList 
{
	public AvailabilityList()
	{
		super();
	}

	@Override
	public String toString() {
		String str = "";
		int classCount = 0;
		
		for (ServiceClass e : this.getServiceClassList())
		{			
			if (classCount > 0)
			{
				str += "\n";
			}
			
			str += e.getClassName() + "\n";
			
			for (int r = 0; r < e.getRows(); r++)
			{
				int count = 0;
				str += "\n" + e.getRowString(r) + ": ";
				
				for (int c = 0; c < e.getColumns(); c++)
				{
					if (isSeatAvailable(e.getClassName(), r, c))
					{
						if (count > 0)
						{
							str += ", ";
						}
						
						str += ServiceClass.getColumnLetter(c);
								
						count++;
					}
					/*else {
						str += "   ";
					}*/ //Allows for more readability
				}
			}
			
			str += "\n";
			
			classCount++;
		}
		
		return str;
	}
	
	/**
	 * Returns whether or not the seat is available on the Service Class name
	 */
	public boolean isSeatAvailable(String className, int r, int c)
	{
		ServiceClass sc = this.getServiceClass(className);
		
		if (sc != null)
		{
			if (!sc.isSeatOccupied(r, c))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Attempts to find an available window seat in the service class
	 * @param className the name of the service class to search in
	 * @return Coordinate the location of the available seat
	 */
	public Coordinate findAvailableWindowSeat(String className)
	{
		ServiceClass sc = this.getServiceClass(className);
		
		if (sc != null)
		{
			for (int r = 0; r < sc.getRows(); r++)
			{
				for (int c = 0; c < sc.getColumns(); c++)
				{
					if (sc.isWindowSeat(c))
					{
						if (!sc.isSeatOccupied(r, c))
						{
							return new Coordinate(r, c);
						}
					}
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Attempts to find an available center seat in the service class
	 * @param className the name of the service class to search in
	 * @return Coordinate the location of the available seat
	 */
	public Coordinate findAvailableCenterSeat(String className)
	{
		ServiceClass sc = this.getServiceClass(className);
		
		if (sc != null)
		{
			for (int r = 0; r < sc.getRows(); r++)
			{
				for (int c = 0; c < sc.getColumns(); c++)
				{
					if (sc.isCenterSeat(c))
					{
						if (!sc.isSeatOccupied(r, c))
						{
							return new Coordinate(r, c);
						}
					}
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Attempts to find an available aisle seat in the service class
	 * @param className the name of the service class to search in
	 * @return Coordinate the location of the available seat
	 */
	public Coordinate findAvailableAisleSeat(String className)
	{
		ServiceClass sc = this.getServiceClass(className);
		
		if (sc != null)
		{
			for (int r = 0; r < sc.getRows(); r++)
			{
				for (int c = 0; c < sc.getColumns(); c++)
				{
					if (sc.isAisleSeat(c))
					{
						if (!sc.isSeatOccupied(r, c))
						{
							return new Coordinate(r, c);
						}
					}
				}
			}
		}
		
		return null;
	}
}
