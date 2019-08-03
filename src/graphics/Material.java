package graphics;


import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

//Note: Textures only work if the source image's dimensions are powers of 2
public class Material {
	private String path;
	private Texture texture;
	private float width, height;
	private int textureID;
	
	public Material(String path) {
		this.path = path;
	}
	
	public void create() {
		try {
			//texture = TextureLoader.getTexture(path.split("[.]")[1], Material.class.getResourceAsStream(path), GL11.GL_NEAREST);
			String fileExtension = path.split("[.]")[1];
			
			
			Class cls = Class.forName("graphics.Material");
			ClassLoader cLoader = cls.getClassLoader();
			InputStream is = cLoader.getResourceAsStream(path);
			
			
			//InputStream is = Material.class.getResourceAsStream("TitleImage4.jpg");
			
			/*
			Class cls = Class.forName("graphics.Material");
			ClassLoader cLoader = cls.getClassLoader();
			File file = new File(cls.getResource("TitleImage4.jpg").getFile());
			InputStream is = new FileInputStream(file);
			*/
			texture = TextureLoader.getTexture(fileExtension, is, GL11.GL_LINEAR);
			width = texture.getWidth();
			height = texture.getHeight();
			textureID = texture.getTextureID();
		} catch (IOException e) {
			System.err.println("Can't find texture at " + path);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void destroy() {
		GL13.glDeleteTextures(textureID);
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public int getTextureID() {
		return textureID;
	}
}