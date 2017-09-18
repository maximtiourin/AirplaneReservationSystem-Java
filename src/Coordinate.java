/**
 * A coordinate is a row and column position
 * @author Maxim Tiourin
 */
public class Coordinate 
{
	private int row;
	private int column;
	
	public Coordinate(int r, int c)
	{
		row = r;
		column = c;
	}
	
	/**
	 * Returns the row
	 * @return int row
	 */
	public int getRow() 
	{
		return row;
	}
	
	/**
	 * Sets the row
	 * @param row row to set to
	 */
	public void setRow(int row) 
	{
		this.row = row;
	}
	
	/**
	 * Returns the column
	 * @return int column
	 */
	public int getColumn() 
	{
		return column;
	}
	
	/**
	 * Sets the column
	 * @param column column to set to
	 */
	public void setColumn(int column) 
	{
		this.column = column;
	}	
}
