/*
 * Image.java
 * @author Carlos Daniel Avila Navarro
 * 22/02/2020
 */
package Image_Compression;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

public class Image 
{
	/*
	 * @param name is the name of the .bmp to be compressed.
	 * @function compresses the .bmp by converting the RGB to hexadecimal, then associating it to a shorter String, and writting it to a .ijw file.
	 */
	public static void compress(String name) throws Exception
	{	
		BufferedImage image = ImageIO.read(Image.class.getResource(name + ".bmp"));
		FileWriter writer = new FileWriter(name + ".ijw");
		String[] hexRGB = new String[image.getWidth()*image.getHeight()];
		writer.append(image.getWidth() + " " + image.getHeight() + " "); //Writes the width and height to the compressed file
		int z=0;
		for(int x=0; x<image.getWidth(); x++)
		{
			for(int y=0; y<image.getHeight(); y++)
			{
				hexRGB[z] = Compression.rgbToHex(new Color(image.getRGB(x, y))); //Changes the value of the RGB to its hexadecimal counterpart.
				z++;
			}
		}
		String[] dict = Maps.ABC();
		HashMap<String, String> map = Maps.repeated(hexRGB, dict);
		map = Maps.changeOrder(map);
		Maps.passDict(writer, map, dict);
		map = Maps.changeOrder(map);
		hexRGB = Compression.exchangeTo(map, hexRGB);
		writer.append("\n"); //Separates the dict from the information.
		Compression.writeRepetitions(writer, hexRGB);
		writer.close();
		System.out.println("The image has been compressed, its name is " + name + ".ijw");
	}

	/*
	 * @param name is the original name of the .ijw file, the substring before _Compression.ijw
	 * @function decompresses the .ijw file and makes a .bmp with the information in it.
	 */
	public static void decompress(String name) throws Exception
	{
		BufferedReader reader = new BufferedReader(new FileReader(name + ".ijw"));
		String[] information = reader.readLine().split(" ");
		String important = reader.readLine();
		int width = Integer.parseInt(information[0]);
		int height = Integer.parseInt(information[1]);
		String[] dict = Maps.ABC();
		HashMap<String, String> map = Decompression.createDict(information[2], dict);
		String[] separated = Decompression.separateInfo(important);
		String[] hexRGB = Decompression.expand(separated);
		hexRGB = Decompression.exchangeFrom(map, hexRGB);
		int[] arrayRGB = Decompression.hexToRGB(hexRGB);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int z=0;
		for(int x=0; x<width; x++)
		{
			for(int y=0; y<height; y++)
			{
				image.setRGB(x, y, arrayRGB[z]);
				z++;
			}
		}
		ImageIO.write(image, "bmp", new File(name + "_Decompressed.bmp"));
		reader.close();
		System.out.println("The image has been decompressed, its name is " + name + "Decompressed.bmp");
	}
}