import java.util.ArrayList;

/**
 * The Reservation Scheduler handles reservation requests and cancellations.
 * @author Maxim Tiourin
 */
public class ReservationScheduler 
{
	private AvailabilityList availability;
	private ManifestList manifest;
	private FirstClass firstClass;
	private EconomyClass economyClass;
	
	public ReservationScheduler()
	{
		availability = new AvailabilityList();
		manifest = new ManifestList();
		firstClass = null;
		economyClass = null;
	}
	
	/**
	 * Attempts to fill the given reservation request
	 * @param request the reservation request to fill
	 * @return ArrayList<Coordinate> seat coordinates of successful request, null if failed
	 */
	public ArrayList<Coordinate> requestReservation(Request request)
	{
		ArrayList<Coordinate> seats = new ArrayList<Coordinate>();
		
		if (request instanceof IndividualRequest)
		{
			IndividualRequest req = (IndividualRequest) request;
			
			//Check to see if seat with preference is available
			if (req.getSeatPreference().equals("W"))
			{
				Coordinate coord = availability.findAvailableWindowSeat(req.getServiceClassName()); 
				if (coord != null)
				{
					seats.add(coord);
				}
			}
			else if (req.getSeatPreference().equals("C"))
			{
				Coordinate coord = availability.findAvailableCenterSeat(req.getServiceClassName()); 
				if (coord != null)
				{
					seats.add(coord);
				}
			}
			else if (req.getSeatPreference().equals("A"))
			{
				Coordinate coord = availability.findAvailableAisleSeat(req.getServiceClassName()); 
				if (coord != null)
				{
					seats.add(coord);
				}
			}
			
			if (seats.size() > 0)
			{					
				if (request.getServiceClassName().equals("First"))
				{
					for (Coordinate e : seats)
					{
						firstClass.setSeat(new ReservedSeat(req.getNames().get(0), null), e.getRow(), e.getColumn());
					}
				}
				else if (request.getServiceClassName().equals("Economy"))
				{
					for (Coordinate e : seats)
					{
						economyClass.setSeat(new ReservedSeat(req.getNames().get(0), null), e.getRow(), e.getColumn());
					}
				}
				
				return seats;
			}
		}
		else if (request instanceof GroupRequest)
		{
			GroupRequest req = (GroupRequest) request;
			ServiceClass sc;
			
			//Figure out the right service class
			if (request.getServiceClassName().equals("First"))
			{
				sc = firstClass;
			}
			else if (request.getServiceClassName().equals("Economy"))
			{
				sc = economyClass;
			}
			else {
				return null;
			}
			
			//Begin attempting to find adjacent seats
			int total = req.getNames().size();
			int count = 0;
			int availableSeats = 0;
			
			//Create a shallow copy of the names list
			ArrayList<String> remainder = new ArrayList<String>();
			for (String e : req.getNames())
			{
				remainder.add(e);
			}
			
			//Find amount of available seats
			for (int r = 0; r < sc.getRows(); r++)
			{
				for (int c = 0; c < sc.getColumns(); c++)
				{
					if (!sc.isSeatOccupied(r, c))
					{
						availableSeats++;
					}
				}
			}
			
			//If not enough seats, abort request, otherwise, start filling
			if (availableSeats < total)
			{
				return null;
			}
			else 
			{
				//Find largest adjacent slot for remaining members, and repeat filling it in
				while (count < total)
				{
					int streak = 0;
					int highestStreak = 0;
					Coordinate streakCoord = null;
					
					for (int r = 0; r < sc.getRows(); r++)
					{
						streak = 0;
						int c = 0;
						
						for (c = 0; c < sc.getColumns(); c++)
						{
							if (!sc.isSeatOccupied(r, c))
							{								
								streak = 1;
								
								int i = c + 1;
								while (i < sc.getColumns())
								{
									if (!sc.isSeatOccupied(r, i))
									{
										streak++;
									}
									
									i++;
								}
								
								if (streak > highestStreak)
								{
									highestStreak = streak;
									streakCoord = new Coordinate(r, c);
								}
							}
						}
					}
					
					//Fill the adjacent slots, increment count, then keep trying
					int i = 0;
					while ((i < highestStreak) && (count < total))
					{
						seats.add(new Coordinate(streakCoord.getRow(), streakCoord.getColumn() + i));
						sc.setSeat(new ReservedSeat(remainder.get(0), req.getGroupName()), streakCoord.getRow(), streakCoord.getColumn() + i);
						remainder.remove(0);
						count++;
						i++;
					}
				}
				
				return seats;
			}
		}
		
		return null;
	}
	
	/**
	 * Attempts to cancel the reservation
	 * @param request the cancellation request to fulfill
	 * @return boolean true if the reservation was cancelled
	 */
	public boolean requestCancellation(Request request)
	{
		ServiceClass sc;
		
		//Figure out the right service class
		if (request.getServiceClassName().equals("First"))
		{
			sc = firstClass;
		}
		else if (request.getServiceClassName().equals("Economy"))
		{
			sc = economyClass;
		}
		else {
			return false;
		}		
		
		if (request instanceof IndividualRequest)
		{
			IndividualRequest req = (IndividualRequest) request;
			boolean removedAtLeastOne = false;
			for (int r = 0; r < sc.getRows(); r++)
			{
				for (int c = 0; c < sc.getColumns(); c++)
				{
					if (sc.getSeat(r, c) != null)
					{
						if (sc.getSeat(r, c).getName().equals(req.getNames().get(0)))
						{
							sc.setSeat(null, r, c);
							removedAtLeastOne = true;
						}
					}
				}
			}
			
			if (removedAtLeastOne)
			{
				return true;
			}
		}
		else if (request instanceof GroupRequest)
		{
			GroupRequest req = (GroupRequest) request;
			
			boolean removedAtLeastOne = false;
			for (int r = 0; r < sc.getRows(); r++)
			{
				for (int c = 0; c < sc.getColumns(); c++)
				{
					if (sc.getSeat(r, c) != null)
					{
						if (sc.getSeat(r, c).getGroup() != null)
						{
							if (!sc.getSeat(r, c).getGroup().equals(""))
							{
								if (sc.getSeat(r, c).getGroup().equals(req.getGroupName()))
								{
									sc.setSeat(null, r, c);
									removedAtLeastOne = true;
								}
							}
						}
					}
				}
			}
			
			if (removedAtLeastOne)
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Initializes the First Class service class, using the data from the text string, if the string
	 * is empty or null, it initializes an empty First Class.
	 * @param text the text data to initialize from
	 */
	public void initializeFirstClass(String text)
	{		
		if (!((text.equals("")) || (text == null)))
		{
			firstClass = new FirstClass();
			
			//get text inside the first class area
			String ftext = StringUtil.substr(text, "[First]", "[Economy]");
			String temptext = ftext;
			String line = StringUtil.substr(temptext, "{", "}");
			while (line.length() > 0)
			{
				ArrayList<String> tokens = new ArrayList<String>();
				for (String e : line.split(","))
				{
					tokens.add(e);
				}
				
				if (tokens.size() < 4) tokens.add("");
				
				firstClass.setSeat(new ReservedSeat(tokens.get(2), tokens.get(3)), Integer.parseInt(tokens.get(0)), Integer.parseInt(tokens.get(1)));
				
				temptext = StringUtil.restOfString(temptext, "}"); 
				line = StringUtil.substr(temptext, "{", "}");
			}			
		}
		else
		{			
			firstClass = new FirstClass();
		}
		
		availability.addServiceClass(firstClass);
		manifest.addServiceClass(firstClass);
	}
	
	/**
	 * Initializes the Economy Class service class, using the data from the text string, if the string
	 * is empty or null, it initializes an empty Economy Class.
	 * @param text the text data to initialize from
	 */
	public void initializeEconomyClass(String text)
	{
		if (!((text.equals("")) || (text == null)))
		{
			economyClass = new EconomyClass();
			
			//get text inside the economy class area
			String etext = StringUtil.restOfString(text, "[Economy]");
			String temptext = etext;
			String line = StringUtil.substr(temptext, "{", "}");
			while (line.length() > 0)
			{
				ArrayList<String> tokens = new ArrayList<String>();
				for (String e : line.split(","))
				{
					tokens.add(e);
				}
				
				if (tokens.size() < 4) tokens.add("");
				
				economyClass.setSeat(new ReservedSeat(tokens.get(2), tokens.get(3)), Integer.parseInt(tokens.get(0)), Integer.parseInt(tokens.get(1)));
				
				temptext = StringUtil.restOfString(temptext, "}"); 
				line = StringUtil.substr(temptext, "{", "}");
			}	
		}
		else
		{
			economyClass = new EconomyClass();
		}
		
		availability.addServiceClass(economyClass);
		manifest.addServiceClass(economyClass);
	}
	
	/**
	 * Returns the current availability list
	 * @return availability list
	 */
	public AvailabilityList getAvailabilityList()
	{
		return availability;
	}
	
	/**
	 * Returns the current manifest list
	 * @return manifest list
	 */
	public ManifestList getManifestList()
	{
		return manifest;
	}
	
	/**
	 * Returns a string representation of the service classes the scheduler is
	 * in charge of.
	 */
	public String serviceClassesToString()
	{
		String str = "";
		
		str += "[" + firstClass.getClassName() + "]\n";
		
		for (int r = 0; r < firstClass.getRows(); r++)
		{
			for (int c = 0; c < firstClass.getColumns(); c++)
			{
				if (firstClass.isSeatOccupied(r, c))
				{
					str += "{" + r + "," + c + "," + firstClass.getSeat(r, c).getName() + "," + firstClass.getSeat(r, c).getGroup() + "}\n";
				}
			}
		}
		
		str += "[" + economyClass.getClassName() + "]\n";
		
		for (int r = 0; r < economyClass.getRows(); r++)
		{
			for (int c = 0; c < economyClass.getColumns(); c++)
			{
				if (economyClass.isSeatOccupied(r, c))
				{
					str += "{" + r + "," + c + "," + economyClass.getSeat(r, c).getName() + "," + economyClass.getSeat(r, c).getGroup() + "}\n";
				}
			}
		}
		
		return str;
	}
}
