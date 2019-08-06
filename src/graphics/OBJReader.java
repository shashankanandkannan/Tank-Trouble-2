package graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.vecmath.Vector3f;

/*
 * This class has the sole purpose of taking in OBJ files and converting them to a Model object, which contains all of the faces and normals specified in that file.
 * Originally, I was planning on making my game look 3d and adding perspective after getting enough done, but I didn't have the time for that. 
 */

public class OBJReader {
	
	//Reads obj files and returns a mesh object that stores the vertices and faces specified in the obj file.
	public static Mesh read(String filename, float scaleFactor) {
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		
		FileReader fileReader = null;
		
		try {
			fileReader = new FileReader(new File(filename));
		} catch(FileNotFoundException e) {
			System.err.println("Couldn't load file.");
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(fileReader);
		String line;
		
		
		try {
			
			while((line = reader.readLine ())!= null) {
				
				String[] lineComponents = line.split(" ");
				if(line.startsWith("v ")) {
					float x = Float.parseFloat(lineComponents[1])/scaleFactor;
					float y = Float.parseFloat(lineComponents[2])/scaleFactor;
					float z = Float.parseFloat(lineComponents[3])/scaleFactor;
					vertices.add(new Vertex(new Vector3f(x,y,z)));
				}		
				/*else if (line.startsWith("vn ")){
					float x = Float.parseFloat(lineComponents[1])/scaleFactor;
					float y = Float.parseFloat(lineComponents[2])/scaleFactor;
					float z = Float.parseFloat(lineComponents[3])/scaleFactor;
					model.addNormal(x, y, z);
				}*/
				else if (line.startsWith("f ")) {
					
					String[] indexList;
					
					for(int i = 0; i < 3; i++) {
						int index = Integer.parseInt(lineComponents[i].split("//")[0]);
						indices.add(index);
					}
					
				}	
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Vertex[] vertexList = vertices.toArray(new Vertex[vertices.size()]);
		Integer[] indexIntegerList = indices.toArray(new Integer[indices.size()]);
		
		int[] indexList = new int[indices.size()];
		for(int i = 0; i < indexList.length; i++) {
			indexList[i] = indexIntegerList[i].intValue();
		}
		
		
		Mesh mesh = new Mesh(vertexList, indexList);
		
		return mesh;
	}

}
