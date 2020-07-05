/*
 * Compression.java
 * @author Carlos Daniel Avila Navarro
 * 22/02/2020
 */
package Image_Compression;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Compression 
{
	/*
	 * @param num is the number to be converted to a 2 char hexadecimal number, num has a maximum of 255.
	 * @return a String of two chars with the number in hexadecimal form.
	 */
	private static String hexConversion(int num)
	{
		String hex = "";
		if(num > 16) //If the number needs two chars to be written.
		{
			hex += Integer.toHexString((int) Math.floor(num/16)) + Integer.toHexString((int) (num%16)); 
		}
		else
		{
			hex += "0" + Integer.toHexString((int) (num%16)); //Adds a 0 if the number can be written with 1 char. 
		}
		return hex;
	}
	
	/*
	 * @param color is the color to be converted to hexadecimal.
	 * @return a hexadecimal string of 6 chars with the color converted.
	 */
	public static String rgbToHex(Color color)
	{
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		String hex = hexConversion(r) + hexConversion(g) + hexConversion(b);
		return hex;
	}
	
	/*
	 * @param map is the Hashmap that has the hexadecimal String and the value to be exchanged to.
	 * @param array is the array with all the hexadecimal Strings.
	 * @return the String[] with all the hexadecimal Strings exchanged with its respective value.
	 */
	public static String[] exchangeTo(HashMap<String, String> map, String[] array)
	{
		for(int x=0; x<array.length; x++)
		{
			array[x] = map.get(array[x]);
		}
		return array;
	}
	
	/*
	 * @param writer is the FileWriter to the compressed file.
	 * @param array is the array with hexadecimal information.
	 * @function writes all information, checking if there are sets of information together; if so, it resumes it to a shorter form. 
	 */
	public static void writeRepetitions(FileWriter writer, String[] array) throws IOException
	{
		int counter = 1;
		for(int x=0; x<array.length; x++)
		{
			if((x+1) < array.length) //Checks if there's a next.
			{
				if(array[x].equals(array[x+1])) { counter++; } //If there's the same information next, it will add it to the counter.
				else
				{
					writer.append(counter + array[x]); //If the next one is different from the current one, it will write the current information.
					counter = 1;
				}
			}
			else
			{
				writer.append(counter + array[x]); //If the next one doesn't exist, it will write the current information.
				counter = 1;
			}
		}
		writer.flush();
	}
}