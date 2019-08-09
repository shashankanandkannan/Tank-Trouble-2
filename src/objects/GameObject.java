package objects;
import javax.vecmath.Vector3f;

import graphics.Mesh;

public class GameObject {
	protected Vector3f position, rotation, scale;
	protected Mesh mesh;
	private float temp = 0;
	
	public GameObject(Vector3f position, Vector3f rotation, Vector3f scale, Mesh mesh) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.mesh = mesh;
	}
	
	public GameObject(Vector3f position, Vector3f rotation, Vector3f scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}

	public void update() { //Temporary, remove later
		temp += 0.02;
		position.setX((float)Math.sin(temp));
		//rotation.set((float)Math.sin(temp) * 360, (float)Math.sin(temp) * 360, (float)Math.sin(temp) * 360);
		//rotation.set(temp, 0, 0);
		//scale.set((float)Math.sin(temp), (float)Math.sin(temp), (float)Math.sin(temp));
		//position.setX(temp);
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public Vector3f getScale() {
		return scale;
	}

	public Mesh getMesh() {
		return mesh;
	}
	
}
