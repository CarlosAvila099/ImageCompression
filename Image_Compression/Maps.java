/*
 * Maps.java
 * @author Carlos Daniel Avila Navarro
 * 22/02/2020
 */
package Image_Compression;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Maps 
{
	/*
	 * @return String[] with all the posible combinations of A-Z and a-z with 3 characters.
	 */
	public static String[] ABC()
	{
		String[] array = new String[(52*52*52) + (52*52) + 52];
		int w = 0, chars = 0; //w is the number of values in the array. chars is the number of chars in each string.
		for(int x=64; x<123; x++) //Goes from A-Z and a-z in ASCII.
		{
			if(x == 91) { x=97; } //Skips the chars from Z-a
			for(int y=65; y<123; y++) //Goes from A-Z and a-z in ASCII.
			{
				if(y == 91) { y=97; } //Skips the chars from Z-a
				for(int z=65; z<123; z++) //Goes from A-Z and a-z in ASCII.
				{
					if(z == 91) { z=97; } //Skips the chars from Z-a
					if(chars == 0)
					{
						array[w] = "" + (char) z;
						w++;
					}
					else if(chars == 1)
					{
						array [w] = "" + (char) y + (char) z;
						w++;
					}
					else
					{
						array [w] = "" + (char) x + (char) y + (char) z;
						w++;
					}
				}
				if(chars == 0) 
				{ 
					chars=1;
					y-=1;
				}
			}
			if(chars == 1) { chars=2; }
		}
		return array;
	}
	
	/* 
	 * @param array is the array that has all the colors in hexadecimal code.
	 * @param dict is the array that has the combinations A-Z and a-z with 3 characters.
	 * @return a Hashmap that has <array value, dict value>.
	 */
	public static HashMap<String, String> repeated(String[] array, String[] dict)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		int position = 0;
		for(int x=0; x<array.length; x++)
		{
			if(!map.containsKey(array[x]))
			{
				map.put(array[x], dict[position]);
				position++;
			}
		}
		return map;
	}

	/*
	 * @param writer is the FileWriter associated with the compressed file.
	 * @param map is the Hashmap with <array value, dict value>.
	 * @param dict is the array that has the combinations A-Z and a-z with 3 characters.
	 * @function appends the Hashmap key to the compressed file. 
	 */
	public static void passDict(FileWriter writer, HashMap<String, String> map, String[] dict) throws IOException
	{
		for(int x=0; x<dict.length; x++)
		{
			if(map.containsKey(dict[x]))
			{
				writer.append(map.get(dict[x]));
			}
			else
			{
				break;
			}
		}
		writer.flush();
	}
	
	/*
	 * @param map is the Hashmap whose order will be changed.
	 * @return the Hashmap received, with the new order <value, key>.
	 */
	public static HashMap<String, String> changeOrder(HashMap<String, String> map)
	{
		HashMap<String, String> map2 = new HashMap<String, String>();
		for(Map.Entry<String, String> entry : map.entrySet())
		{
			map2.put(entry.getValue(), entry.getKey());
		}
		return map2;
	}
}
