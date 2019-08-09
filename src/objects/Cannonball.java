package objects;
import java.awt.geom.GeneralPath;
import graphics.Model;

/*
 * Author: Shashank Anand
 * Teacher: Mr. Radulovic
 * Date: 17 June 2019
 * This class is used to create Cannonball objects, which are shot out by the tanks. Each one moves independently and bounces whenever it meets a wall.
 */

public class Cannonball extends Projectile {

	private static final float SPEED = 0.017f;
	private static final float SCALEFACTOR = 75;
	
	private static final float RADIUS = 1/SCALEFACTOR;
	
	private int numberOfBounces;

	private byte index;
	
	public Cannonball(float xpos, float ypos, float direction, byte index) {
		this.xpos = xpos;
		this.ypos = ypos;
		this.direction = direction;
		this.index = index;
		
		//model = OBJReader.read("resources/Cannonball3.obj", SCALEFACTOR);
		numberOfBounces = 0;
	}

	@Override
	public void move() {
		xpos += SPEED*Math.cos(direction);
		ypos += SPEED*Math.sin(direction);
	}

	public float getRadius(){
		return RADIUS;
	}

	public byte getIndex() {
		return index;
	}
	
	public int getNumberOfBounces() {
		return numberOfBounces;
	}
	
	
	//Checks the cannonball against each wall to see if it collides
	
	public void collisionCheck() {
		
		Wall[][] walls = Wall.getMap();
		
		for(int i = 0; i < walls[1].length; i++) {
			for(int j = 0; j < walls.length; j++) {
				Wall w = walls[i][j];
				
				if (w != null) {
					boolean horizontalCollisionVBounds = ypos <= w.getYpos() && ypos >= w.getYpos() - Wall.getWallWidth();
					
					if (horizontalCollisionVBounds) {
						
						if (Math.cos(direction) > 0) {
							if (xpos + RADIUS >= w.getXpos() && xpos + RADIUS <= w.getXpos() + Wall.getWallWidth()) {
								direction = (float)Math.PI - direction;
								numberOfBounces++;
							}
						}
						else {
							if (xpos - RADIUS >= w.getXpos() && xpos - RADIUS <= w.getXpos() + Wall.getWallWidth()) {
								direction = (float)Math.PI - direction;
								numberOfBounces++;
							}
						}	
					}
					
					boolean verticalCollisionHBounds = xpos <= w.getXpos() + Wall.getWallWidth() && xpos >= w.getXpos();
					
					if (verticalCollisionHBounds) {
						
						if (Math.sin(direction) > 0) {
							if (ypos + RADIUS >= w.getYpos() - Wall.getWallWidth() && ypos + RADIUS <= w.getYpos()) {
								direction = (float)Math.PI*2 - direction;
								numberOfBounces++;
							}
						}
						else {
							if (ypos - RADIUS >= w.getYpos() - Wall.getWallWidth() && ypos - RADIUS <= w.getYpos()) {
								direction = (float)Math.PI*2 - direction;
								numberOfBounces++;
							}
						}
					}
					
					
					
				}
			}
		}
	}
	
//	public void collisionCheck() {
//		
//		for(Wall[] wallList : Wall.getMap()) {
//			for(Wall w : wallList) {
//				if(w != null) {
//					GeneralPath path = new GeneralPath();
//					
//					path.moveTo(w.getXpos(), w.getYpos());
//					path.moveTo(w.getXpos() + Wall.getWallWidth(), w.getYpos());
//					path.moveTo(w.getXpos() + Wall.getWallWidth(), w.getYpos() - Wall.getWallWidth());
//					path.moveTo(w.getXpos(), w.getYpos() - Wall.getWallWidth());
//					path.closePath();					
//					
//					if(Math.cos(direction) >= 0) {
//						//if(path.contains(xpos, ))
//					}
//					if(Math.cos(direction) < 0) {
//						
//					}
//					if(Math.sin(direction) >= 0) {
//						
//					}
//					if(Math.sin(direction) < 0) {
//						
//					}
//					
//					/*
//					if(path.contains(xpos + RADIUS, ypos) || path.contains(xpos - RADIUS, ypos)) {
//						direction = (float) (Math.PI-direction);
//						numberOfBounces ++;
//						move();
//						System.out.print("a");
//					}
//					if(path.contains(xpos, ypos + RADIUS) || path.contains(xpos, ypos - RADIUS)) {
//						direction = (float) (2*Math.PI-direction);
//						numberOfBounces++;
//						move();
//						System.out.print("a");
//					}*/	
//				}
//			}
//		}
//	}
	
	
}
