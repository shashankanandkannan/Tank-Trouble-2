package objects;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import java.awt.geom.GeneralPath;

import graphics.Material;
import graphics.Mesh;
import graphics.OBJReader;
import graphics.Vertex;
import math.Matrix4f;
import utils.LinkedList;

/*
 * Author: Shashank Anand
 * Teacher: Mr. Radulovic
 * Date: 17 June 2019
 * This class is used to create Tank objects, which are controlled by the player. They move, shoot, and turn. This class also handles the tanks' collision with terrain and cannonballs.
 */

public class Tank extends GameObject{

	private static final float SPEED = 0.1f;
	private static final float TURNINGSPEED = 0.075f;
	
	public float direction; 							  //Direction = 0 means the tank is pointed to the right	
	
	private Cannonball[] cannonballs = new Cannonball[5]; //Each tank can have up to 5 cannonballs out
	private byte numOfCannonballs;
	
	private static LinkedList tankList = new LinkedList();
	
	private static final float SCALEFACTOR = 20;
	 
	private static final float WIDTH = 9.75445f/SCALEFACTOR; 		//Width and length variables describe the tank's rectangular bottom's dimensions
	private static final float LENGTH = 16.0924f/SCALEFACTOR;


	public Tank() {
		
		super(new Vector3f(0, 0,-3), new Vector3f(0,0,0), new Vector3f(1,1,1));
		mesh = OBJReader.read("resources/TriTank2.obj", SCALEFACTOR);
		mesh.create();
		
		rotation.setX((float)(-Math.PI/2));
		
		tankList.add(this);
	}
	
	
	//Methods to move the tank
	public void moveForward() {
		
		/*
		boolean[] collisions = collisionCheck();
		
		if (!(Math.cos(direction) < 0 && collisions[0]) && !(Math.cos(direction) > 0 && collisions[1]))
			position.x += SPEED*Math.cos(direction);				 //Note: this is in radians
		if (!(Math.sin(direction) < 0 && collisions[3]) && !(Math.sin(direction) > 0 && collisions[2]))
			position.y += SPEED*Math.sin(direction);*/
		
		float newx = (float) (position.x + SPEED*Math.cos(rotation.z));
		float newy = (float) (position.y + SPEED*Math.sin(rotation.z));
		
		//boolean[] collisions = moveCollisionCheck(newx, newy);
		
		position.x = newx;
		position.y = newy;
		
	
	}
	
	public void moveBackward() {

		//boolean[] collisions = collisionCheck();
		
//		if (!(Math.cos(direction) > 0 && collisions[0]) && !(Math.cos(direction) < 0 && collisions[1]))
//			position.x -= SPEED*Math.cos(direction);				 //Note: this is in radians
//		if (!(Math.sin(direction) > 0 && collisions[3]) && !(Math.sin(direction) < 0 && collisions[2]))
//			position.y -= SPEED*Math.sin(direction);
		
		//float newx = (float) (position.x - SPEED*Math.cos(rotation.z));
		//float newy = (float) (position.y - SPEED*Math.sin(rotation.z));
		
		//position.x = newx;
		//position.y = newy;
		
		position.x -= SPEED*Math.cos(rotation.z);
		position.y -= SPEED*Math.sin(rotation.z);
	}
	
	public void turnRight() {
		
			rotation.z = (rotation.z)%(2*(float)Math.PI) - TURNINGSPEED;
			//System.out.println(Matrix4f.rotate(rotation.z, new Vector3f(0, 0, 1)).toString());
	}
	
	public void turnLeft() {

		rotation.z = (rotation.z)%(2*(float)Math.PI) + TURNINGSPEED;
		//System.out.println(Matrix4f.rotate(rotation.z, new Vector3f(0, 0, 1)).toString());
		
	}
	
//	
//	
//	//Spawns a cannonball object in front of the tank
//	public void shoot() {
//		if(numOfCannonballs <= 4) {
//			
//			float x = position.x + (float)((LENGTH/2 + 0.012)*Math.cos(direction));  //Spawns the cannonballs at the front of the tank
//			float y = position.y + (float)((LENGTH/2 + 0.012)*Math.sin(direction));
//			
//			cannonballs[numOfCannonballs] = new Cannonball(x, y, direction, numOfCannonballs);
//			numOfCannonballs++;
//		}
//	}
//
//	//Checks for collision with the walls
//	public boolean[] collisionCheck() {
//		
//		boolean collideRight = false;
//		boolean collideLeft = false;
//		boolean collideUp = false;
//		boolean collideDown = false;
//		
//		
//		float diagonalLength = (float)Math.sqrt((WIDTH/2)*(WIDTH/2) + (LENGTH/2)*(LENGTH/2));
//		float diagonalAngle = (float)Math.atan(LENGTH/WIDTH);
//		
//		Vector2f[] corners = {
//			new Vector2f(diagonalLength * (float)Math.cos(direction + diagonalAngle - Math.PI/2), diagonalLength * (float)Math.sin(direction + diagonalAngle - Math.PI/2)),
//			new Vector2f(diagonalLength * (float)Math.cos(direction - diagonalAngle + Math.PI/2), diagonalLength * (float)Math.sin(direction - diagonalAngle + Math.PI/2)),
//			new Vector2f(diagonalLength * (float)Math.cos(direction + diagonalAngle + Math.PI/2), diagonalLength * (float)Math.sin(direction + diagonalAngle + Math.PI/2)),
//			new Vector2f(diagonalLength * (float)Math.cos(direction - diagonalAngle + 3*Math.PI/2), diagonalLength * (float)Math.sin(direction - diagonalAngle + 3*Math.PI/2))
//		};
//		
//		for (Vector2f v : corners) {
//			if(position.x + v.x >= 1-Wall.getWallWidth()) {
//				collideRight = true;
//			}
//			else if(position.x + v.x <= -1+Wall.getWallWidth()) {
//				collideLeft = true;
//			}
//			if (position.y + v.y <= -1 + Wall.getWallWidth()) {
//				collideDown = true;
//			}
//			else if(position.y + v.y >= 1 - Wall.getWallWidth()) {
//				collideUp = true;
//			}
//			
//		}
//		
//		boolean[] collide = {collideLeft, collideRight, collideUp, collideDown};
//		
//		return collide;
//	}
//	
//	private boolean[] moveCollisionCheck(float newx, float newy) {
//	
//		boolean collidex = false;
//		boolean collidey = false;
//		
//		Vector2f[] vertices = getVertices();
//		
//		Wall[][] map = Wall.getMap();
//		
//		for(Wall[] wallList : map) {
//			for (Wall w: wallList) {
//				if(w != null) {
//					
//					GeneralPath path = new GeneralPath();
//					path.moveTo(w.getXpos(), w.getYpos());
//					path.moveTo(w.getXpos() + Wall.getWallWidth(), w.getYpos());
//					path.moveTo(w.getXpos() + Wall.getWallWidth(), w.getYpos() - Wall.getWallWidth());
//					path.moveTo(w.getXpos(), w.getYpos() - Wall.getWallWidth());
//					path.closePath();
//					
//					for(Vector2f v : vertices) {
//						
//						if (path.contains(newx + v.x, position.y + v.y)) {
//							collidex = true;
//System.out.println("collidex");
//							break;
//						}
//						
//						if(path.contains(position.x + v.x, newy + v.y)) {
//							collidey = true;
//System.out.println("collidey");
//							break;
//						}
//					}
//					
//					if(collidex && collidey) {
//						break;
//					}
//					
//				}
//			}
//			
//			if(collidex && collidey) {
//				break;
//			}
//		}
//		
//		boolean[] collide = {collidex, collidey};
//		return collide;
//	}
//	
//	private boolean turnCollisionCheck(float newDirection) {
//		Vector2f[] vertices = getVertices(newDirection);
//		boolean collide = false;
//		
//		Wall[][] map = Wall.getMap();
//		
//		for(Wall[] wallList : map) {
//			for (Wall w: wallList) {
//				if(w != null) {
//					
//					GeneralPath path = new GeneralPath();
//					path.moveTo(w.getXpos(), w.getYpos());
//					path.moveTo(w.getXpos() + Wall.getWallWidth(), w.getYpos());
//					path.moveTo(w.getXpos() + Wall.getWallWidth(), w.getYpos() - Wall.getWallWidth());
//					path.moveTo(w.getXpos(), w.getYpos() - Wall.getWallWidth());
//					path.closePath();
//					
//					for(Vector2f v : vertices) {
//						if(path.contains(v.x, v.y)) {
//							collide = true;
//						}
//					}
//				}
//			}
//		}
//		
//		return collide;
//		
//	}
//	
//	//Checks each cannonball to see if they've bounced more than 5 times, and if so, deletes them. 
//	public void pollCannonballs() {
//		for (int i = 0; i < numOfCannonballs; i++) {
//			if(cannonballs[i].getNumberOfBounces() >= 6) {
//				numOfCannonballs--;
//				cannonballs[i] = null;
//				
//				for(int j = i; j < 4; j++) {
//					cannonballs[j] = cannonballs[j+1];
//				}
//				cannonballs[4] = null;
//			}
//		}
//	}
//	
//	//Spawns the tanks in random locations.
//	public void spawn() {
//		direction = (float)(Math.random()*2*Math.PI);
//		
//		position.x = -10;
//		position.y = -10;
//		
//		boolean moveOn = false;
//		
//		while(!moveOn) {
//			position.x = (float)(Math.random()*(1-Wall.getWallWidth())*2 - (1 - Wall.getWallWidth()));
//			position.y = (float)(Math.random()*(1-Wall.getWallWidth())*2 - (1 - Wall.getWallWidth()));
//		
//			boolean[] collision = collisionCheck();
//			
//			if(!(collision[0] || collision[1] || collision[2] || collision[3]))
//				moveOn = true;
//			
//		}
//	}
//		
//	//Runs the checkHit method on each cannonball
//	public boolean collidesWithCannonballs() {
//		boolean flag = false;
//		
//		Cannonball[] totalCannonballs = new Cannonball[10];
//		
//		for(int i = 0; i < 10; i++) {
//			totalCannonballs[i] =  tankList.get(i/5).getCannonballs()[i%5];
//		}
//		
//		for (Cannonball c: totalCannonballs) {
//			if (c != null) {
//				if(checkHit(c)) {
//					flag = true;
//					break;
//				}
//			}
//		}
//		
//		return flag;
//	}
//
//	//Checks if a specified cannonball has hit the tank
//	private boolean checkHit(Cannonball c) {
//		
//		Vector2f[] vertices = getVertices();
//		
//    	//The GeneralPath class allows me to create a rectangle from 4 points to represent the tank.
//		//The contains() method allows me to check if that rectangle contains the point at the centre of the cannonball
//	    GeneralPath path = new GeneralPath();
//	    path.moveTo(vertices[0].x + position.x, vertices[0].y + position.y);
//	    path.lineTo(vertices[1].x + position.x, vertices[1].y + position.y);
//	    path.lineTo(vertices[2].x + position.x, vertices[2].y + position.y);
//	    path.lineTo(vertices[3].x + position.x, vertices[3].y + position.y);
//	    path.closePath();
//
//
//		return path.contains(c.getXpos(), c.getYpos());
//    }
	
	public static void addTank(Tank t) {
		tankList.add(t);
	}
	
	public static void clearTankList() {
		tankList.clear();
	}
	
	
	
	//Getters and setters
	
	public Mesh getMesh() {
		return mesh;
	}

	
	public float getXpos() {
		return position.x;
	}

	public float getYpos() {
		return position.y;
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
