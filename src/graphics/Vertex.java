package graphics;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class Vertex {
	private Vector3f position, colour;
	private Vector2f textureCoord;
	
	public Vertex(Vector3f position, Vector3f colour, Vector2f textureCoord) {
		this.position = position;
		this.colour = colour;
		this.textureCoord = textureCoord;
	}
	
	/*
	public Vertex(Vector3f position, Vector2f textureCoord) {
		this.position = position;
		this.textureCoord = textureCoord;
	}*/
	
	public Vector3f getPosition() {
		return position;
	}
	
	public Vector3f getColour() {
		return colour;
	}

	public Vector2f getTextureCoord() {
		return textureCoord;
	}
	
}
