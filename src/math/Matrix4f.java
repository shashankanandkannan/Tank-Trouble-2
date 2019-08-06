package math;

import javax.vecmath.Vector3f;

public class Matrix4f {
	public static final byte SIZE = 4;
	private float[] elements = new float[16];
	
	//Loads the identity matrix:
	/* 1000
	 * 0100
	 * 0010
	 * 0001
	 */
	public static Matrix4f identity() {
		Matrix4f result = new Matrix4f();
		
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				if (i == j) 
					result.set(i, j, 1);
				else
					result.set(i, j, 0);
			}
		}
		
		return result;
	}
	
	//Creates and returns a translation matrix
	public static Matrix4f translate(Vector3f translate) {
		Matrix4f result = Matrix4f.identity();
		
		result.set(3, 0, translate.getX());
		result.set(3, 1, translate.getY());
		result.set(3, 2, translate.getZ());
		
		return result;
	}
	
	//Creates and returns a rotation matrix
	public static Matrix4f rotate(float angle, Vector3f axis) {
		Matrix4f result = Matrix4f.identity();
		
		float cos = (float) Math.cos(Math.toRadians(angle));
		float sin = (float) Math.sin(Math.toRadians(angle));
		float C = 1 - cos;
		
		result.set(0, 0, cos + axis.getX()*axis.getX()*C);
		result.set(0, 1, axis.getX()*axis.getY() * C - axis.getZ() * sin);
		result.set(0, 2, axis.getX()*axis.getZ() * C + axis.getY() * sin);
		result.set(1, 0, axis.getX()*axis.getY() * C + axis.getZ() * sin);
		result.set(1, 1, cos + axis.getY()*axis.getY()*C);
		result.set(1, 2, axis.getY()*axis.getZ() * C - axis.getX() * sin);
		result.set(2, 0, axis.getX()*axis.getZ() * C - axis.getY() * sin);
		result.set(2, 1, axis.getY()*axis.getZ() * C + axis.getX() * sin);
		result.set(2, 2, cos + axis.getZ()*axis.getZ()*C);
		
		return result;
	}

	//Creates and returns a scaling matrix
	public static Matrix4f scale(Vector3f scalar) {
		Matrix4f result = Matrix4f.identity();
		
		result.set(0, 0, scalar.getX());
		result.set(1, 1, scalar.getY());
		result.set(2, 2, scalar.getZ());
		
		return result;
	}
	
	public static Matrix4f transform(Vector3f position, Vector3f rotation, Vector3f scale) {
		Matrix4f result = Matrix4f.identity();
		
		Matrix4f translationMatrix = Matrix4f.translate(position);
		Matrix4f rotXMatrix = Matrix4f.rotate(rotation.getX(), new Vector3f(1, 0, 0));
		Matrix4f rotYMatrix = Matrix4f.rotate(rotation.getY(), new Vector3f(0, 1, 0));
		Matrix4f rotZMatrix = Matrix4f.rotate(rotation.getZ(), new Vector3f(0, 0, 1));
		Matrix4f scaleMatrix = Matrix4f.scale(scale);
		
		Matrix4f rotationMatrix = rotXMatrix.multiply(rotYMatrix.multiply(rotZMatrix));
		
		result = translationMatrix.multiply(rotationMatrix.multiply(scaleMatrix));
		
		return result;
	}
	
	//Creates and returns a projection matrix
	public static Matrix4f projection(float fov, float aspect, float near, float far) {
		Matrix4f result = Matrix4f.identity();
		
		float tanFOV = (float) Math.tan(Math.toRadians(fov / 2));
		float range = far - near;
		
		result.set(0, 0, 1.0f / (aspect * tanFOV));
		result.set(1, 1, 1.0f / tanFOV);
		result.set(2, 2, -((far + near) / range));
		result.set(2, 3, -1.0f);
		result.set(3, 2, -((2 * far * near) / range));
		result.set(3, 3, 0.0f);
		
		return result;
	}
	
	//Creates and returns a view matrix
	public static Matrix4f view(Vector3f position, Vector3f rotation) {
		Matrix4f result = identity();
		
		Vector3f negative = new Vector3f(-position.getX(), -position.getY(), -position.getZ());
		Matrix4f translationMatrix = Matrix4f.translate(negative);
		Matrix4f rotXMatrix = Matrix4f.rotate(rotation.getX(), new Vector3f(1, 0, 0));
		Matrix4f rotYMatrix = Matrix4f.rotate(rotation.getY(), new Vector3f(0, 1, 0));
		Matrix4f rotZMatrix = Matrix4f.rotate(rotation.getZ(), new Vector3f(0, 0, 1));
		
		Matrix4f rotationMatrix = rotZMatrix.multiply(rotYMatrix.multiply(rotXMatrix));
		
		result = translationMatrix.multiply(rotationMatrix);
		
		return result;
	}
	
	//Performs matrix multiplication between this matrix and another
	public Matrix4f multiply(Matrix4f other) {
		Matrix4f result = Matrix4f.identity();
		
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				result.set(i, j, get(i, 0)*other.get(0, j) +
								 get(i, 1)*other.get(1, j) +
								 get(i, 2)*other.get(2, j) +
								 get(i, 3)*other.get(3, j));
			}
		}
		
		return result;
	}
	
	public static Matrix4f multiply(Matrix4f m1, Matrix4f m2) {
		Matrix4f result = Matrix4f.identity();
		
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				result.set(i, j, m1.get(i, 0)*m2.get(0, j) +
								 m1.get(i, 1)*m2.get(1, j) +
								 m1.get(i, 2)*m2.get(2, j) +
								 m1.get(i, 3)*m2.get(3, j));
			}
		}
		
		return result;
	}
	
	public float get(int x, int y) {
		return elements[y*SIZE + x];
	}
	
	public void set(float[] elements) {
		this.elements = elements;
	}
	
	public void set(int x, int y, float value) {
		elements[y*SIZE + x] = value;
	}
	
	public float[] getAll() {
		return elements;
	}
	
	public String toString() {
		String matrixString = "";
		
		for(int y = 0; y < SIZE; y++) {
			for(int x = 0; x < SIZE; x++) {
				matrixString += Float.toString(get(x, y));
			}
			matrixString += "\n";
		}
		
		return matrixString;
	}
}
