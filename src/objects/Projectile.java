package objects;
import graphics.Mesh;

/*
 * Author: Shashank Anand
 * Teacher: Mr. Radulovic
 * Date: 17 June 2019
 * This class is an abstract class that my cannonball class inherits from. The original plan was for me to include other types of projectiles and have them inherit from this class too. That didn't quite pan out.
 */

public abstract class Projectile {
	
	protected float direction;
	protected float xpos;
	protected float ypos;
	
	protected static Mesh mesh;
	
	abstract void move();
	
	
	
	//Getters and Setters
	public float getDirection() {
		return direction;
	}

	public void setDirection(float direction) {
		this.direction = direction;
	}
	
	public float getXpos() {
		return xpos;
	}

	public void setXpos(float xpos) {
		this.xpos = xpos;
	}

	public float getYpos() {
		return ypos;
	}

	public void setYpos(float ypos) {
		this.ypos = ypos;
	}

	public Mesh getModel() {
		return mesh;
	}
	
}
