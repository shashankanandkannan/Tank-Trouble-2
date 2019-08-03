/*
 * Author: Mr. Radulovic
 * Teacher: Mr. Radulovic
 * Date: 17 June 2019
 * This is the texture class that came with Mr. Radulovic's sample code. This would have been used to put images on the screen and perhaps to make the models on screen look good.
 */

import static org.lwjgl.opengl.GL11.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Texture {
	private int id;
	private int width;
	private int height;
	
	public Texture(String filename)
	{
		BufferedImage bi;
		try {
			bi = ImageIO.read(new File(filename));
			width = bi.getWidth();
			height = bi.getHeight();
			
			int[] pixels_raw = new int[width*height*4];
			pixels_raw = bi.getRGB(0, 0, width, height, null, 0, width);
			
			ByteBuffer pixels = BufferUtils.createByteBuffer(width*height*4);
			
			for(int i=0; i<height; i++)
			{
				for(int j=0; j<width; j++)
				{
					int pixel = pixels_raw[i*width + j];
					pixels.put((byte)((pixel >> 16) & 0xFF));		// RED
					pixels.put((byte)((pixel >> 8) & 0xFF));		// GREEN
					pixels.put((byte)(pixel & 0xFF));				// BLUE
					pixels.put((byte)((pixel >> 24) & 0xFF));		// ALPHA
				}
			}
			pixels.flip();
			id = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, id);
			
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}

}