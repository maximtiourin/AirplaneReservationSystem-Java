/**
 * StringUtil contains static helper methods for dealing with strings
 * @author Maxim Tiourin
 */
public class StringUtil 
{
	/**
	 * Returns an optimized substring of the src string, by starting at
	 * the beginning of the string (inclusive), ending at the token in the string (exclusive).
	 * Returns empty String if no token is found in the src.
	 * @param src the source string
	 * @param token the token string to search for
	 * @return String the substring
	 */
	public static String substr(String src, String token)
	{
		int pos = src.indexOf(token);
		
		if (pos != -1)
		{
			return new String(src.substring(0, pos));
		}
		
		return "";
	}
	
	/**
	 * Returns an optimized substring between the start and end strings exclusively.
	 * @param src source string
	 * @param start the starting string
	 * @param end the ending string
	 * @return String the substring
	 */
	public static String substr(String src, String start, String end)
	{
		int pos1 = src.indexOf(start) + start.length();
		String newstr = new String(src.substring(pos1));
		int pos2 = newstr.indexOf(end);
		
		if ((pos1 == -1) || (pos2 == -1)) return "";
		
		newstr = new String(src.substring(pos1, pos1 + pos2));
		return newstr;
	}
	
	/**
	 * Returns an optimized substring starting from the token in the source exclusively, to the
	 * end of the string inclusively.
	 * @param src source string
	 * @param token the token string to look for
	 */
	public static String restOfString(String src, String token)
	{
		int pos = src.indexOf(token) + token.length();
		return new String(src.substring(pos));
	}
}
