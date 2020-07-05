/*
 * mainCompression.java
 * @author Carlos Daniel Avila Navarro
 * 22/02/2020
 */
package Image_Compression;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class mainCompression 
{
	
	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		int option = 0;
		String name = "";
		boolean correct = false;
		while(!correct)
		{
			System.out.println("Good morning, what do you want to do?");
			System.out.println("1. Compress");
			System.out.println("2. Decompress");
			try
			{
				option = in.nextInt();
				correct = true;
			}
			catch(InputMismatchException ime) 
			{ 
				System.out.println("Please input a valid option");
				correct = false;
				in.nextLine();
			}
			if(option != 1 && option != 2)
			{ 
				System.out.println("Please input a valid option");
				correct = false;
				in.nextLine();
			}
		}
		if(option == 1)
		{
			correct = false;
			while(!correct)
			{
				System.out.println("Enter the name of the image without extension");
				name = in.next();
				try 
				{
					ImageIO.read(mainCompression.class.getResource(name + ".bmp"));
					correct = true;
				} 
				catch (IOException e) 
				{ 
					e.printStackTrace();
					correct = false;
				}
				catch (IllegalArgumentException iae) 
				{ 
					System.out.println("Please input an existing image");
					correct = false;
				}
			}
			try
			{
				Image.compress(name);
			}
			catch (Exception e) { e.printStackTrace(); }
		}
		else
		{
			correct = false;
			while(!correct)
			{
				System.out.println("Enter the name of the image without extension");
				name = in.next();
				File file = new File(name + ".ijw");
				correct = true;
				if(!file.exists())
				{
					System.out.println("Please input an existing image");
					correct = false;
				}
			}
			try 
			{
				Image.decompress(name);
			} 
			catch (Exception e) { e.printStackTrace(); }
		}
	}
}