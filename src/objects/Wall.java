package objects;
/*
 * Author: Shashank Anand
 * Teacher: Mr. Radulovic
 * Date: 17 June 2019
 * This class creates Wall objects. They model walls that tanks cannot pass and cannonballs bounce off of. 
 * This class also contains the wall layout, which I was hoping to change to be either like a maze or with a few blocks in the centre. 
 * However, I did not have time to implement this feature.
 */
public class Wall {
	
	private static Model model;
	private static final float SCALEFACTOR = 10;
	private static final float WALLWIDTH = 0.2f;
	
	private static final int MAPWIDTH = 10;
	
	private static Wall[][] map;
	
	private float xpos;	//xpos and ypos are measured from the centre of the object
	private float ypos; 
	
	public Wall(float x, float y) {
		model = OBJReader.read("resources/Wall.obj", SCALEFACTOR);
		
		xpos = x;
		ypos = y;
	}

	//Generates a 2D array of walls. Originally, I was planning to make this method add random blocks in the centre too, but I didn't have enough time to solve the problem of collision with walls from all directions.
	public static void generateMap() {
		map = new Wall[MAPWIDTH][MAPWIDTH];
		
		for (int i = 0; i<MAPWIDTH; i++) {
			for (int j = 0; j<MAPWIDTH; j++) {
				if (i == 0 || j == 0 || i == MAPWIDTH-1 || j == MAPWIDTH-1)
					map[i][j] = new Wall(0.2f*i - 1,  -0.2f*j + 1 );
			}
		}
	}
	
	public static Model getModel() {
		return model;
	}

	public static float getScalefactor() {
		return SCALEFACTOR;
	}

	public static float getWallWidth() {
		return WALLWIDTH;
	}

	public float getXpos() {
		return xpos;
	}

	public float getYpos() {
		return ypos;
	}
	
	public static Wall[][] getMap() {
		return map;
	}
	
}
