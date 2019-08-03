import javax.vecmath.Vector2f;
import java.awt.geom.GeneralPath;
import graphics;

/*
 * Author: Shashank Anand
 * Teacher: Mr. Radulovic
 * Date: 17 June 2019
 * This class is used to create Tank objects, which are controlled by the player. They move, shoot, and turn. This class also handles the tanks' collision with terrain and cannonballs.
 */

public class Tank {

	private static final float SPEED = 0.01f;
	private static final float TURNINGSPEED = 0.075f;
	
	public float direction; 					//note: direction = 0 means the tank is pointed to the right	
	private float xpos;
	private float ypos;
	
	private Cannonball[] cannonballs = new Cannonball[5]; //Each tank can have up to 5 cannonballs out
	private byte numOfCannonballs;
	
	private static LinkedList tankList = new LinkedList();
	
	private static final float SCALEFACTOR = 120.0f;
	 
	private static final float WIDTH = 9.75445f/SCALEFACTOR; 		//Width and length variables describe the tank's rectangular bottom's dimensions
	private static final float LENGTH = 16.0924f/SCALEFACTOR;

	
	private static Model model;
	
	private static Model tankBottom;
	private static Model tankTop;
	private static Model tankBarrel;
	
	private float[] topColour = new float[3];
	private float[] bottomColour = new float[3];
	
	public Tank() {
		
		tankBottom = OBJReader.read("resources/TankBottom8.obj", SCALEFACTOR);
		tankTop = OBJReader.read("resources/TankTop8.obj", SCALEFACTOR);
		tankBarrel = OBJReader.read("resources/TankBarrel8.obj", SCALEFACTOR);

		tankList.add(this);
		
		spawn();
	}
	
	
	//Methods to move the tank
	public void moveForward() {
		
		/*
		boolean[] collisions = collisionCheck();
		
		if (!(Math.cos(direction) < 0 && collisions[0]) && !(Math.cos(direction) > 0 && collisions[1]))
			xpos += SPEED*Math.cos(direction);				 //Note: this is in radians
		if (!(Math.sin(direction) < 0 && collisions[3]) && !(Math.sin(direction) > 0 && collisions[2]))
			ypos += SPEED*Math.sin(direction);*/
		
		float newx = (float) (xpos + SPEED*Math.cos(direction));
		float newy = (float) (ypos + SPEED*Math.sin(direction));
		
		boolean[] collisions = moveCollisionCheck(newx, newy);
		
		if(!collisions[0]) {
			xpos = newx;
		}
		if(!collisions[1]) {
			ypos = newy;
		}
		
	}
	
	public void moveBackward() {

		boolean[] collisions = collisionCheck();
		
		if (!(Math.cos(direction) > 0 && collisions[0]) && !(Math.cos(direction) < 0 && collisions[1]))
			xpos -= SPEED*Math.cos(direction);				 //Note: this is in radians
		if (!(Math.sin(direction) > 0 && collisions[3]) && !(Math.sin(direction) < 0 && collisions[2]))
			ypos -= SPEED*Math.sin(direction);
	}
	
	public void turnRight() {
		
			direction = direction%(2*(float)Math.PI) - TURNINGSPEED;
	}
	
	public void turnLeft() {

		direction = direction%(2*(float)Math.PI) + TURNINGSPEED;

	}
	
	
	
	//Spawns a cannonball object in front of the tank
	public void shoot() {
		if(numOfCannonballs <= 4) {
			
			float x = xpos + (float)((LENGTH/2 + 0.012)*Math.cos(direction));  //Spawns the cannonballs at the front of the tank
			float y = ypos + (float)((LENGTH/2 + 0.012)*Math.sin(direction));
			
			cannonballs[numOfCannonballs] = new Cannonball(x, y, direction, numOfCannonballs);
			numOfCannonballs++;
		}
	}

	//Checks for collision with the walls
	public boolean[] collisionCheck() {
		
		boolean collideRight = false;
		boolean collideLeft = false;
		boolean collideUp = false;
		boolean collideDown = false;
		
		
		float diagonalLength = (float)Math.sqrt((WIDTH/2)*(WIDTH/2) + (LENGTH/2)*(LENGTH/2));
		float diagonalAngle = (float)Math.atan(LENGTH/WIDTH);
		
		Vector2f[] corners = {
			new Vector2f(diagonalLength * (float)Math.cos(direction + diagonalAngle - Math.PI/2), diagonalLength * (float)Math.sin(direction + diagonalAngle - Math.PI/2)),
			new Vector2f(diagonalLength * (float)Math.cos(direction - diagonalAngle + Math.PI/2), diagonalLength * (float)Math.sin(direction - diagonalAngle + Math.PI/2)),
			new Vector2f(diagonalLength * (float)Math.cos(direction + diagonalAngle + Math.PI/2), diagonalLength * (float)Math.sin(direction + diagonalAngle + Math.PI/2)),
			new Vector2f(diagonalLength * (float)Math.cos(direction - diagonalAngle + 3*Math.PI/2), diagonalLength * (float)Math.sin(direction - diagonalAngle + 3*Math.PI/2))
		};
		
		for (Vector2f v : corners) {
			if(xpos + v.x >= 1-Wall.getWallWidth()) {
				collideRight = true;
			}
			else if(xpos + v.x <= -1+Wall.getWallWidth()) {
				collideLeft = true;
			}
			if (ypos + v.y <= -1 + Wall.getWallWidth()) {
				collideDown = true;
			}
			else if(ypos + v.y >= 1 - Wall.getWallWidth()) {
				collideUp = true;
			}
			
		}
		
		boolean[] collide = {collideLeft, collideRight, collideUp, collideDown};
		
		return collide;
	}
	
	private boolean[] moveCollisionCheck(float newx, float newy) {
	
		boolean collidex = false;
		boolean collidey = false;
		
		Vector2f[] vertices = getVertices();
		
		Wall[][] map = Wall.getMap();
		
		for(Wall[] wallList : map) {
			for (Wall w: wallList) {
				if(w != null) {
					
					GeneralPath path = new GeneralPath();
					path.moveTo(w.getXpos(), w.getYpos());
					path.moveTo(w.getXpos() + Wall.getWallWidth(), w.getYpos());
					path.moveTo(w.getXpos() + Wall.getWallWidth(), w.getYpos() - Wall.getWallWidth());
					path.moveTo(w.getXpos(), w.getYpos() - Wall.getWallWidth());
					path.closePath();
					
					for(Vector2f v : vertices) {
						
						if (path.contains(newx + v.x, ypos + v.y)) {
							collidex = true;
System.out.println("collidex");
							break;
						}
						
						if(path.contains(xpos + v.x, newy + v.y)) {
							collidey = true;
System.out.println("collidey");
							break;
						}
					}
					
					if(collidex && collidey) {
						break;
					}
					
				}
			}
			
			if(collidex && collidey) {
				break;
			}
		}
		
		boolean[] collide = {collidex, collidey};
		return collide;
	}
	
	private boolean turnCollisionCheck(float newDirection) {
		Vector2f[] vertices = getVertices(newDirection);
		boolean collide = false;
		
		Wall[][] map = Wall.getMap();
		
		for(Wall[] wallList : map) {
			for (Wall w: wallList) {
				if(w != null) {
					
					GeneralPath path = new GeneralPath();
					path.moveTo(w.getXpos(), w.getYpos());
					path.moveTo(w.getXpos() + Wall.getWallWidth(), w.getYpos());
					path.moveTo(w.getXpos() + Wall.getWallWidth(), w.getYpos() - Wall.getWallWidth());
					path.moveTo(w.getXpos(), w.getYpos() - Wall.getWallWidth());
					path.closePath();
					
					for(Vector2f v : vertices) {
						if(path.contains(v.x, v.y)) {
							collide = true;
						}
					}
				}
			}
		}
		
		return collide;
		
	}
	
	//Checks each cannonball to see if they've bounced more than 5 times, and if so, deletes them. 
	public void pollCannonballs() {
		for (int i = 0; i < numOfCannonballs; i++) {
			if(cannonballs[i].getNumberOfBounces() >= 6) {
				numOfCannonballs--;
				cannonballs[i] = null;
				
				for(int j = i; j < 4; j++) {
					cannonballs[j] = cannonballs[j+1];
				}
				cannonballs[4] = null;
			}
		}
	}
	
	//Spawns the tanks in random locations.
	public void spawn() {
		direction = (float)(Math.random()*2*Math.PI);
		
		xpos = -10;
		ypos = -10;
		
		boolean moveOn = false;
		
		while(!moveOn) {
			xpos = (float)(Math.random()*(1-Wall.getWallWidth())*2 - (1 - Wall.getWallWidth()));
			ypos = (float)(Math.random()*(1-Wall.getWallWidth())*2 - (1 - Wall.getWallWidth()));
		
			boolean[] collision = collisionCheck();
			
			if(!(collision[0] || collision[1] || collision[2] || collision[3]))
				moveOn = true;
			
		}
	}
		
	//Runs the checkHit method on each cannonball
	public boolean collidesWithCannonballs() {
		boolean flag = false;
		
		Cannonball[] totalCannonballs = new Cannonball[10];
		
		for(int i = 0; i < 10; i++) {
			totalCannonballs[i] =  tankList.get(i/5).getCannonballs()[i%5];
		}
		
		for (Cannonball c: totalCannonballs) {
			if (c != null) {
				if(checkHit(c)) {
					flag = true;
					break;
				}
			}
		}
		
		return flag;
	}

	//Checks if a specified cannonball has hit the tank
	private boolean checkHit(Cannonball c) {
		
		Vector2f[] vertices = getVertices();
		
    	//The GeneralPath class allows me to create a rectangle from 4 points to represent the tank.
		//The contains() method allows me to check if that rectangle contains the point at the centre of the cannonball
	    GeneralPath path = new GeneralPath();
	    path.moveTo(vertices[0].x + xpos, vertices[0].y + ypos);
	    path.lineTo(vertices[1].x + xpos, vertices[1].y + ypos);
	    path.lineTo(vertices[2].x + xpos, vertices[2].y + ypos);
	    path.lineTo(vertices[3].x + xpos, vertices[3].y + ypos);
	    path.closePath();


		return path.contains(c.getXpos(), c.getYpos());
    }
	
	public static void addTank(Tank t) {
		tankList.add(t);
	}
	
	public static void clearTankList() {
		tankList.clear();
	}
	
	
	
	//Getters and setters
	public void setTopColour(float r, float g, float b) {
		topColour[0] = r;
		topColour[1] = g;
		topColour[2] = b;
	}
	
	public void setBottomColour(float r, float g, float b) {
		bottomColour[0] = r;
		bottomColour[1] = g;
		bottomColour[2] = b;
	}
	
	public static Model getModel() {
		return model;
	}
	
	public static Model getModelTop() {
		return tankTop;
	}
	
	public static Model getModelBottom() {
		return tankBottom;
	}
	
	public static Model getModelBarrel() {
		return tankBarrel;
	}

	
	public float getXpos() {
		return xpos;
	}

	public float getYpos() {
		return ypos;
	}
	
	public float getDirection() {
		return direction;
	}
	
	public byte getNumOfCannonballs() {
		return numOfCannonballs;
	}

	public Cannonball[] getCannonballs() {
		return cannonballs;
	}
	
	public float[] getTopColour() {
		return topColour;
	}
	
	public float[] getBottomColour() {
		return bottomColour;
	}

	public static LinkedList getTankList() {
		return tankList;
	}
	
	private Vector2f[] getVertices() {
		float diagonalLength = (float)Math.sqrt((WIDTH/2)*(WIDTH/2) + (LENGTH/2)*(LENGTH/2));
		float diagonalAngle = (float)Math.atan(LENGTH/WIDTH);
		
		Vector2f[] vertices = {
			new Vector2f(diagonalLength * (float)Math.cos(direction + diagonalAngle - Math.PI/2.0), diagonalLength * (float)Math.sin(direction + diagonalAngle - Math.PI/2.0)),
			new Vector2f(diagonalLength * (float)Math.cos(direction - diagonalAngle + Math.PI/2.0), diagonalLength * (float)Math.sin(direction - diagonalAngle + Math.PI/2.0)),
			new Vector2f(diagonalLength * (float)Math.cos(direction + diagonalAngle + Math.PI/2.0), diagonalLength * (float)Math.sin(direction + diagonalAngle + Math.PI/2.0)),
			new Vector2f(diagonalLength * (float)Math.cos(direction - diagonalAngle - Math.PI/2.0), diagonalLength * (float)Math.sin(direction - diagonalAngle - Math.PI/2.0))
		};
		
		return vertices;
	}
	
	private Vector2f[] getVertices(float newDirection) {
		float diagonalLength = (float)Math.sqrt((WIDTH/2)*(WIDTH/2) + (LENGTH/2)*(LENGTH/2));
		float diagonalAngle = (float)Math.atan(LENGTH/WIDTH);
		
		Vector2f[] vertices = {
			new Vector2f(diagonalLength * (float)Math.cos(newDirection + diagonalAngle - Math.PI/2.0), diagonalLength * (float)Math.sin(newDirection + diagonalAngle - Math.PI/2.0)),
			new Vector2f(diagonalLength * (float)Math.cos(newDirection - diagonalAngle + Math.PI/2.0), diagonalLength * (float)Math.sin(newDirection - diagonalAngle + Math.PI/2.0)),
			new Vector2f(diagonalLength * (float)Math.cos(newDirection + diagonalAngle + Math.PI/2.0), diagonalLength * (float)Math.sin(newDirection + diagonalAngle + Math.PI/2.0)),
			new Vector2f(diagonalLength * (float)Math.cos(newDirection - diagonalAngle - Math.PI/2.0), diagonalLength * (float)Math.sin(newDirection - diagonalAngle - Math.PI/2.0))
		};
		
		return vertices;
	}

}
