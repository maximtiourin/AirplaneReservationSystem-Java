/**
 * The economy class is a service class
 * @author Maxim Tiourin
 */
public class FirstClass extends ServiceClass
{
	private static final String NAME = "First";
	private static final int ROWS = 2;
	private static final int ROW_OFFSET = 1;
	private static final int COLUMNS = 4;
	
	public FirstClass()
	{
		super(ROWS, COLUMNS);
	}

	@Override
	public boolean isWindowSeat(int c) {
		if ((c == 0) || (c == 3))
		{
			return true;
		}
		
		return false;
	}

	@Override
	public boolean isCenterSeat(int c) {		
		return false;
	}

	@Override
	public boolean isAisleSeat(int c) {
		if ((c == 1) || (c == 2))
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
		return (Integer.parseInt(str) - ROW_OFFSET);
	}
}
