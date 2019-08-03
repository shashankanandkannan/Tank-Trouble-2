import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
 * Author: Shashank Anand
 * Teacher: Mr. Radulovic
 * Date: 17 June 2019
 * This class has the sole purpose of taking in OBJ files and converting them to a Model object, which contains all of the faces and normals specified in that file.
 * Originally, I was planning on making my game look 3d and adding perspective after getting enough done, but I didn't have the time for that. 
 */

public class OBJReader {
	
	//Reads obj files and returns a model object that stores the vertices and faces specified in the obj file.
	public static Model read(String filename, float scaleFactor) {
		FileReader fileReader = null;
		
		Model model = new Model();
		
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
					model.addVertex(x, y, z);
				}		
				else if (line.startsWith("vn ")){
					float x = Float.parseFloat(lineComponents[1])/scaleFactor;
					float y = Float.parseFloat(lineComponents[2])/scaleFactor;
					float z = Float.parseFloat(lineComponents[3])/scaleFactor;
					model.addNormal(x, y, z);
				}
				else if (line.startsWith("f ")) {
					
					int arrayLength = lineComponents.length - 1;
					int[] vertexIndices = new int[arrayLength];
					int[] normalIndices = new int[arrayLength];
					String[] indices = new String[2];
					
					for(int i = 1; i<arrayLength; i++) {
						indices = lineComponents[i].split("//");
						vertexIndices[i] = Integer.parseInt(indices[0]) - 1; //The obj format's indices start at 1
						normalIndices[i] = Integer.parseInt(indices[1]) - 1;
					}
					
					model.addFace(vertexIndices, normalIndices);
					
					
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
		
		return model;
	}

}
