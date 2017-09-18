/**
 * The manifest list displays the current flight manifest.
 * @author Maxim Tiourin
 */
public class ManifestList extends InformationList 
{
	public ManifestList()
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
			
			str += e.getClassName() + "\n\n";
			
			for (int r = 0; r < e.getRows(); r++)
			{
				for (int c = 0; c < e.getColumns(); c++)
				{
					if (e.isSeatOccupied(r, c))
					{
						str += e.getRowString(r) + ServiceClass.getColumnLetter(c) + ": " + e.getSeat(r, c).getName() + "\n";
					}
				}
			}
			
			
			classCount++;
		}
		
		return str;
	}
}
