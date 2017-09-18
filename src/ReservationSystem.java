import java.util.ArrayList;
import java.util.Scanner;

/**
 * The main reservation system that handles all of the 
 * parts of managing reservations for an airplane.
 * @author Maxim Tiourin
 */
public class ReservationSystem 
{	
	private ReservationScheduler scheduler;
	private ReservationFileHandler fileHandler;
	
	public ReservationSystem(String filepath)
	{
		scheduler = new ReservationScheduler();
		fileHandler = new ReservationFileHandler(filepath);
	}
	
	/**
	 * Runs the program loop for the reservation system.
	 */
	public void run() 
	{
		boolean quit = false;
		
		/*Load File and Initialize Data Structures*/
		fileHandler.loadFile();
		scheduler.initializeFirstClass(fileHandler.getText());
		scheduler.initializeEconomyClass(fileHandler.getText());
		
		/* Main Program Loop */
		//Greeting message
		System.out.println("   _______________________________________________________________");
		System.out.println("__/** Welcome to the Maxim Tiourin Airplane Reservation Service **\\__");
		System.out.println("    \\___________________________________________________________/\n\n");
		while (!quit) 
		{
			String input = "";
			Scanner in = new Scanner(System.in);
			
			//Main prompt
			System.out.println("Add [P]assenger, " +
							   	"Add [G]roup, " + 
								"[C]ancel Reservations, " + 
							   	"Print Seating [A]vailability Chart, " + 
								"Print [M]anifest, " + 
							   	"[Q]uit" +
								"\n");
			
			//Receive input for main prompt
			input = in.nextLine();
			input = input.toUpperCase();
			
			System.out.println();
			
			//Logic branch for prompt input
			if (input.equals("P"))
			{
				boolean once;
				
				//Add passenger branch
				String name = "";
				String serviceClass = "";
				String preference = "";
				
				System.out.printf("Name: ");
				input = in.nextLine();
				name = input;
				
				once = false;
				while (!((serviceClass.equals("First")) || (serviceClass.equals("Economy"))) || (!once))
				{
					if (once) System.out.println("~-~ Invalid Service Class ~-~");
					System.out.printf("Service Class [First, Economy]: ");
					input = in.nextLine();
					input = input.toLowerCase();
					String str1 = new String(input.substring(0, 1));
					str1 = str1.toUpperCase();
					String str2 = new String(input.substring(1));
					serviceClass = str1 + str2;
					
					once = true;
				}
				
				once = false;			
				while (!(preference.equals("W") || preference.equals("C") || preference.equals("A")) || (!once))
				{
					if (once) System.out.println("~-~ Invalid Seat Preference ~-~");
					System.out.printf("Seat Preference ([W]indow, [C]enter, [A]isle): ");
					input = in.nextLine();
					input = input.toUpperCase();
					preference = input;
					
					once = true;
				}
	
				//Attempt request
				ArrayList<Coordinate> result = scheduler.requestReservation(new IndividualRequest(name, serviceClass, preference)); 
				if (result != null)
				{						
					System.out.println("*** '" + name + "' successfully seated in " 
									+ serviceClass + " Class at seat '" 
									+ scheduler.getAvailabilityList().getServiceClass(serviceClass).getRowString(result.get(0).getRow()) 
									+ ServiceClass.getColumnLetter(result.get(0).getColumn()) + "' ***");
				}
				else
				{
					System.out.println("~!~ Request Failed: No Available Seats for Specified Preference ~!~");
				}				
				
				System.out.println();
			}
			else if (input.equals("G"))
			{
				boolean once;
				
				//Add group branch
				ArrayList<String> names = new ArrayList<String>();
				String groupName = "";
				String serviceClass = "";
				
				System.out.printf("Group Name: ");
				input = in.nextLine();
				groupName = input;
				
				once = false;
				while (!(names.size() > 1) || (!once))
				{
					if (once) {
						names.clear();
						System.out.println("~-~ Invalid amount of group members, must be more than one, seperate with commas ~-~");
					}
					System.out.printf("Names: ");
					input = in.nextLine();
					String[] tempnames = input.split(",");
					for (String e : tempnames)
					{
						names.add(e);
					}
					
					once = true;
				}
				
				once = false;
				while (!((serviceClass.equals("First")) || (serviceClass.equals("Economy"))) || (!once))
				{
					if (once) System.out.println("~-~ Invalid Service Class ~-~");
					System.out.printf("Service Class [First, Economy]: ");
					input = in.nextLine();
					input = input.toLowerCase();
					String str1 = new String(input.substring(0, 1));
					str1 = str1.toUpperCase();
					String str2 = new String(input.substring(1));
					serviceClass = str1 + str2;
					
					once = true;
				}				
				
				//Attempt request
				ArrayList<Coordinate> result = scheduler.requestReservation(new GroupRequest(names, serviceClass, groupName)); 
				if (result != null)
				{			
					int i = 0;
					for (String e : names)
					{
						System.out.println("*** Group (" + groupName + ") member '" + e + "' successfully seated in " 
									+ serviceClass + " Class at seat '" 
									+ scheduler.getAvailabilityList().getServiceClass(serviceClass).getRowString(result.get(i).getRow()) 
									+ ServiceClass.getColumnLetter(result.get(i).getColumn()) + "' ***");
						i++;
					}
				}
				else
				{
					System.out.println("~!~ Request Failed: No Available Seats for that group size ~!~");
				}				
				
				System.out.println();
			}
			else if (input.equals("C"))
			{
				boolean once = false;
				
				//Cancel reservation branch
				ArrayList<String> names = new ArrayList<String>();
				String cancelType = "";
				String groupName = "";
				String serviceClass = "";
				
				once = false;
				while (!((cancelType.equals("I")) || (cancelType.equals("G"))) || (!once))
				{
					if (once) System.out.println("~-~ Invalid Cancellation Type ~-~");
					System.out.printf("Cancel [G]roup, or [I]ndividual reservations?: ");
					input = in.nextLine();
					input = input.toUpperCase();
					cancelType = input;
					
					once = true;
				}
				
				once = false;
				while (!((serviceClass.equals("First")) || (serviceClass.equals("Economy"))) || (!once))
				{
					if (once) System.out.println("~-~ Invalid Service Class ~-~");
					System.out.printf("Service Class [First, Economy]: ");
					input = in.nextLine();
					input = input.toLowerCase();
					String str1 = new String(input.substring(0, 1));
					str1 = str1.toUpperCase();
					String str2 = new String(input.substring(1));
					serviceClass = str1 + str2;
					
					once = true;
				}		
				
				if (cancelType.equals("I"))
				{
					//Individual cancel
					once = false;
					while (!(names.size() > 0) || (!once))
					{
						if (once) {
							names.clear();
							System.out.println("~-~ Invalid amount of names, must be more than zero, seperate multiple with commas ~-~");
						}
						System.out.printf("Names: ");
						input = in.nextLine();
						String[] tempnames = input.split(",");
						for (String e : tempnames)
						{
							names.add(e);
						}
						
						once = true;
					}
					
					for (String e : names)
					{
						//Cancel Reservations
						boolean result = scheduler.requestCancellation(new IndividualRequest(e, serviceClass, null));
						if (result)
						{
							System.out.println("*** Successfully cancelled reservation for '" + e + "' ***");
						}
					}
				}
				else if (cancelType.equals("G"))
				{
					//Group cancel
					System.out.printf("Group Name: ");
					input = in.nextLine();
					groupName = input;
					
					boolean result = scheduler.requestCancellation(new GroupRequest(null, serviceClass, groupName));
					if (result)
					{
						System.out.println("*** Successfully cancelled reservation for Group '" + groupName + "' ***");
					}
					else
					{
						System.out.println("~-~ Invalid Group Name, try again or check another Service Class ~-~");
					}
				}				
			}
			else if (input.equals("A"))
			{
				//Display availability branch
				System.out.println(scheduler.getAvailabilityList().toString());
			}
			else if (input.equals("M"))
			{
				//Display manifest branch
				System.out.println(scheduler.getManifestList().toString());
			}
			else if (input.equals("Q")) 
			{
				//Quit branch
				//Offload the file
				fileHandler.offloadFile(scheduler.serviceClassesToString());
				
				quit = true;
			}
		}
		
		/* Off load Data Structures to file */
		System.out.println("Goodbye!");
	}
	
	public static void main(String[] args) 
	{
		if (args.length == 1) 
		{
			ReservationSystem reserveSystem = new ReservationSystem(args[0]);
			reserveSystem.run();
		}
		else 
		{
			System.err.println("Error: Incorrect amount of arguments supplied. \nUsage Info: ./programpath [filepath]");
		}
	}

}
