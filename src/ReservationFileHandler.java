import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The ReservationFileHandler handles the creation, loading, and saving of the
 * Reservation System's information.
 * @author Maxim Tiourin
 */
public class ReservationFileHandler 
{
	private File file;
	private String text;
	private boolean loaded;
	
	public ReservationFileHandler(String filepath)
	{
		file = new File(filepath);
		text = "";
		loaded = false;
	}
	
	/**
	 * Creates a new file if the current file doesn't exist,
	 * and returns true if a file was created.
	 */
	public boolean createNewFile()
	{
		try 
		{
			if (!file.exists())
			{
				file.createNewFile();
				
				return true;
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Loads the current file and initializes the data structures.
	 */
	public void loadFile()
	{
		try
		{
			if (!createNewFile())
			{
				FileReader fr = new FileReader(file);
				BufferedReader in = new BufferedReader(fr);
				
				String str = "";
				while ((str = in.readLine()) != null)
				{
					text += str;
				}
				
				in.close();
				
				loaded = true;
			}
			else
			{
				loaded = false;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Offloads the data structures to the current file
	 */
	public void offloadFile(String text)
	{
		try
		{
			FileWriter fw = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fw);
			
			out.write(text);
			
			out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the text from the file, or an empty string if the file was not loaded
	 */
	public String getText()
	{
		if (loaded)
		{
			return text;
		}
		
		return "";
	}
}
