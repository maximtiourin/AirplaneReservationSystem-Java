/**
 * The service class is an abstract base class that helps describe what
 * a service class should have on the airplane to aid with reservations, as
 * well as having helper functions for it's children.
 * @author Maxim Tiourin
 */
public abstract class ServiceClass 
{
	private static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private int rows;
	private int columns;
	private ReservedSeat[][] seats;
	
	public ServiceClass(int rows, int columns)
	{
		this.rows = rows;
		this.columns = columns;
		seats = new ReservedSeat[this.rows][this.columns];
	}
	
	/**
	 * Override. Should return whether or not the seat at this position is a window seat.
	 * @param c column
	 * @return boolean is window seat
	 */
	public abstract boolean isWindowSeat(int c);
	/**
	 * Override. Should return whether or not the seat at this position is a center seat.
	 * @param c column
	 * @return boolean is center seat
	 */
	public abstract boolean isCenterSeat(int c);
	/**
	 * Override. Should return whether or not the seat at this position is a aisle seat.
	 * @param c column
	 * @return boolean is aisle seat
	 */
	public abstract boolean isAisleSeat(int c);
	/**
	 * Override. Should return the name of the Service Class.
	 * @return String name of the service class
	 */
	public abstract String getClassName();
	/**
	 * Override. Should return the string representation of the row number for the service class.
	 * @param r row
	 * @return String string representation of row
	 */
	public abstract String getRowString(int r);
	/**
	 * Override. Should return the row number of the row, given the row's representation as a
	 * String for the service class.
	 * @param str row string
	 * @return int the row number of the row string
	 */
	public abstract int getRowFromRowString(String str);
	
	/**
	 * Sets the seat at the row and column to the newly supplied seat
	 * @param seat the seat to replace with
	 * @param r the row
	 * @param c the column
	 */
	public void setSeat(ReservedSeat seat, int r, int c)
	{
		seats[r][c] = seat;
	}
	
	/**
	 * Returns the seat at the row and column
	 * @param r the row
	 * @param c the column
	 */
	public ReservedSeat getSeat(int r, int c)
	{
		return seats[r][c];
	}
	
	/**
	 * Returns the seat array
	 * @return ReservedSeat[][] the seat array
	 */
	public ReservedSeat[][] getSeats()
	{
		return seats;
	}
	
	/**
	 * Returns whether or not the seat at the position is occupied
	 * @param r row
	 * @param c column
	 * @return boolean true if occupied, false if not
	 */
	public boolean isSeatOccupied(int r, int c)
	{
		if (seats[r][c] == null)
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * Returns the number of rows in the service class
	 */
	public int getRows()
	{
		return rows;
	}
	
	/**
	 * Returns the number of columns in the service class
	 */
	public int getColumns()
	{
		return columns;
	}
	
	/**
	 * Becomes a shallow copy of the argument service class, taking on it's seat array
	 * @param sc the ServiceClass to become a shallow copy of
	 */
	public void becomeShallowCopyOf(ServiceClass sc)
	{
		seats = sc.getSeats();
	}
	
	/**
	 * Returns a string representing the letter of the column position in
	 * the service class.
	 * @param pos the position of the column
	 * @return String representing the column position as a letter, 0 = A, 1 = B, and so on.
	 */
	public static String getColumnLetter(int pos)
	{
		if ((pos >= 0) && (pos < alphabet.length()))
		{
			return "" + alphabet.charAt(pos);
		}
		
		return "Error: Invalid alphabet position: " + pos + " [Expected: 0-25]";
	}
	
	/**
	 * Returns the position of the column corresponding to the supplied letter string.
	 * @param letter the letter to search for
	 * @return the position of the column with the letter, or -1 if no letter found.
	 */
	public static int getLetterColumn(String letter)
	{
		int pos = alphabet.indexOf(letter);
		
		if (pos != -1)
		{
			return pos;
		}
		
		return -1;
	}
}
