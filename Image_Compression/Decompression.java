/*
 * Decompression.java
 * @author Carlos Daniel Avila Navarro
 * 22/02/2020
 */
package Image_Compression;

import java.awt.*;
import java.util.HashMap;

public class Decompression 
{
	/*
	 * @param all is the String that has all the hexadecimal Strings.
	 * @param dict is the array with all the posible combinations of A-Z and a-z with 3 characters.
	 * @return a Hashmap with <dict value, array value>.
	 */
	public static HashMap<String, String> createDict(String all, String[] dict)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		String[] separated = new String[all.length()]; //Creates a String[] with the capacity for all the different Strings in all.
		int limit = 0;
		for(int x=0; x<all.length(); x++)
		{
			separated[limit] = all.substring(x, x+6); //Separates the String every 6 chars.
			limit++;
			x+=5;
		}
		for(int x=0; x<limit; x++)
		{
			if(!map.containsKey(separated[x]))
			{
				map.put(dict[x], separated[x]);
			}
		}
		return map;
	}
	
	/*
	 * @param all is the String that has all the RGB values in its dictionary form.
	 * @return a String[] with all the RGB values separated.
	 */
	public static String[] separateInfo(String all)
	{
		String[] array = new String[all.length()];
		boolean endInt = false; //Used to know if the int has already ended, helps to separate the words.
		int pastN=0, w=0; //pastN is the start of the substring. w is the number of words existing.
		for(int x=0; x<all.length(); x++)
		{
			if(x == 0) 
			{ 
				pastN = x;
				continue;
			}
			if(!Character.isDigit(all.charAt(x)) && !endInt)
			{
				endInt = true;
				continue;
			}
			if(Character.isDigit(all.charAt(x)) && endInt)
			{
				array[w] = all.substring(pastN, x);
				w++;
				pastN = x;
				endInt = false;
			}
		}
		String[] array2 = new String[w];
		for(int x=0; x<array2.length; x++)
		{
			array2[x] = array[x];
		}
		return array2;
	}
	
	/*
	 * @param array is the separated String of the RGB information.
	 * @return a String[] with the complete information.
	 */
	public static String[] expand(String[] array)
	{
		String[] separated = new String[array.length*10]; //We create a string of 10 times the length of the original array to compensate for the multiplied information.
		String substring = "";
		boolean endWord = false;
		int w = 0, multiplier = 0; //w is the number of Strings in separated. multiplier is the number of times a String is repeated.
		for(int x=0; x<array.length; x++)
		{
			endWord = false;
			for(int y=0; y<array[x].length(); y++)
			{
				if(!Character.isDigit(array[x].charAt(y)) && !endWord)
				{
					multiplier = Integer.parseInt(array[x].substring(0, y)); //This substring is the number of times the String is repeated.
					substring = array[x].substring(y, array[x].length()); //This substring is the String to be repeated.
				}
				if(multiplier != 0)
				{
					for(int z=0; z<multiplier; z++) //Saves the multiplied String the times necessary.
					{
						separated[w] = substring;
						w++;
						endWord = true;
					}
					multiplier = 0;
				}
			}
		}
		String[] multiplied = new String[w]; 
		for(int x=0; x<w; x++)
		{
			multiplied[x] = separated[x];
		}
		return multiplied;
	}
	
	/*
	 * @param dict is the Hashmap with <dict value, array value>.
	 * @param array contains all the Strings as a dict value.
	 * @return a Sting[] with all the values as its dictionary counterpart.
	 */
	public static String[] exchangeFrom(HashMap<String, String> dict, String[] array)
	{
		for(int x=0; x<array.length; x++)
		{
			array[x] = dict.get(array[x]);
		}
		return array;
	}
	
	/*
	 * @param array is the array with the color in hexadecimal.
	 * @return the int[] with the int of the RGB.
	 */
	public static int[] hexToRGB(String[] array)
	{
		int[] arrayRGB = new int[array.length*2];
		for(int x=0; x<array.length; x++)
		{
			arrayRGB[x] = Color.decode("#" + array[x]).getRGB();
		}
		return arrayRGB;
	}
}