/**
 * The economy class is a service class
 * @author Maxim Tiourin
 */
public class EconomyClass extends ServiceClass
{
	private static final String NAME = "Economy";
	private static final int ROWS = 20;
	private static final int ROW_OFFSET = 10;
	private static final int COLUMNS = 6;
	
	public EconomyClass()
	{
		super(ROWS, COLUMNS);
	}

	@Override
	public boolean isWindowSeat(int c) {
		if ((c == 0) || (c == 5))
		{
			return true;
		}
		
		return false;
	}

	@Override
	public boolean isCenterSeat(int c) {
		if ((c == 1) || (c == 4))
		{
			return true;
		}
		
		return false;
	}

	@Override
	public boolean isAisleSeat(int c) {
		if ((c == 2) || (c == 3))
		{
			return true;
		}
		
		return false;
	}

	@Override
	public String getClassName() {
		return NAME;
	}

	@Override
	public String getRowString(int r) {
		return "" + (ROW_OFFSET + r);
	}
	
	@Override
	public int getRowFromRowString(String str)
	{
		return (Integer.getInteger(str) - ROW_OFFSET);
	}
}
