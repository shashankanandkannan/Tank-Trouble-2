package graphics;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3f;

/*
 * Author: Shashank Anand
 * Teacher: Mr. Radulovic
 * Date: 17 June 2019
 * This class is used to create Model objects, which contain a list of Face objects, vertices and normals. The vertices and normals are all represented by vectors. The Model objects that this class makes are later used to display models on screen.
 */

public class Model {
	private List<Vector3f> vertices = new ArrayList<Vector3f>();
	private List<Vector3f> normals = new ArrayList<Vector3f>();
	private List<Face> faces = new ArrayList<Face>();
	
	public void addVertex(float x, float y, float z) {
		vertices.add(new Vector3f(x, y, z));
	}
	
	public void addNormal(float x, float y, float z) {
		normals.add(new Vector3f(x, y, z));
	}
	
	public void addFace(int[] vertex, int[] normal) {
		faces.add(new Face(vertex, normal));
	}

	public List<Face> getFaces() {
		return faces;
	}

	public List<Vector3f> getVertices() {
		return vertices;
	}

	public Vector3f getNormal(int x) {
		return normals.get(x);
	}
	
	public Vector3f getVertex(int x) {
		return vertices.get(x);
	}
	
	public Vector3f getVertexByIndex(Face face, int index) {
		return getVertex(face.getVertexIndices()[index]);
	}

	
}
