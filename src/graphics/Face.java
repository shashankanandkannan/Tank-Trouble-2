package graphics;
/*
 * Author: Shashank Anand
 * Teacher: Mr. Radulovic
 * Date: 17 June 2019
 * This class is used to make Face objects, which are stored inside model objects. Each face has an array of indices which point to vertices and normals in the lists of vertices and normals that each Model object contains.
 */

public class Face {

	private int[] vertexIndices;
	private int[] normalIndices;
	
	public Face (int[] vertexParameter, int[] normalParameter) {
		vertexIndices = vertexParameter;
		normalIndices = normalParameter;
	}

	public int[] getVertexIndices() {
		return vertexIndices;
	}
	
	public int[] getNormalIndices() {
		return normalIndices;
	}
	
}
