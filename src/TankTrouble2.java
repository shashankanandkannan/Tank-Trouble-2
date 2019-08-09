import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;
//import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import graphics.*;
import objects.*;


public class TankTrouble2 implements Runnable{
	
	private final double FRAMESPERSECOND = 60;
	
	Window window;
	
	private String gameState = "game";
	
	public Renderer renderer;
	public Shader shader;
	
	public Tank tank1;
	
	public Camera camera = new Camera(new Vector3f(0,0,2), new Vector3f(0,0,0));
	public GameObject object = new GameObject(new Vector3f(0.5f,0,-2), new Vector3f(0,0,0), new Vector3f(1,1,1), 
			
			new Mesh(new Vertex[] {
			//Temporary mesh - cube
			//TODO: remove and replace with actual model
			//Back face
			new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(1.0f, 0.0f)),
			
			//Front face
			new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),
			
			//Right face
			new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),
			
			//Left face
			new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),
			
			//Top face
			new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),
			
			//Bottom face
			new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),
	}, new int[] {
			//Back face
			0, 1, 3,	
			3, 1, 2,	
			
			//Front face
			4, 5, 7,
			7, 5, 6,
			
			//Right face
			8, 9, 11,
			11, 9, 10,
			
			//Left face
			12, 13, 15,
			15, 13, 14,
			
			//Top face
			16, 17, 19,
			19, 17, 18,
			
			//Bottom face
			20, 21, 23,
			23, 21, 22
	}, new Material("square.png"))
	);
	
	public void run() {
		
		window = new Window(760, 760);
				
		shader = new Shader("C:/Work/TankTrouble2/resources/shaders/mainVertex.glsl", "C:/Work/TankTrouble2/resources/shaders/mainFragment.glsl");
		shader.create();
		
		renderer = new Renderer(window, shader);
		
		loop();
		close();
	}

	private void loop() {
				
		double prevTime = System.nanoTime();
		double loopStartTime;
		double elapsed;
		double steps = 0;
		double secsPerFrame = 1d/FRAMESPERSECOND;
		
		tank1  = new Tank();
		
		/*
		Wall.generateMap();
		
		Tank.clearTankList();
		Tank tank1 = new Tank();
		Tank tank2 = new Tank();	
		*/
		
		/*Mesh testMesh =  new Mesh(
				new Vertex[] {
						new Vertex(new Vector3f(-0.5f, 0.5f, 0.0f), new Vector3f(1.0f, 1.0f, 0.0f), new Vector2f(0, 0)),
						new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f), new Vector3f(0.0f, 1.0f, 0.0f), new Vector2f(0, 1)),
						new Vertex(new Vector3f(0.5f, -0.5f, 0.0f), new Vector3f(1.0f, 0.0f, 0.0f), new Vector2f(1, 1)),
						new Vertex(new Vector3f(0.5f, 0.5f, 0.0f), new Vector3f(0.0f, 0.0f, 1.0f), new Vector2f(1, 0)),
				},
				new int[] {
						0, 1, 2,
						0, 3, 2
				},
				
				//new Material("/TankTrouble2/bin/TitleImage4.jpg")
				new Material("guy3.png")
		);*/
		
		
		//Mesh tankMesh = OBJReader.read("TriTank.obj", 1);
		
		
		while ( !window.windowShouldClose() ) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			
			//object.update();
			gameUpdate();
			GL11.glClearColor(1.0f, 0f, 0f, 1.0f);
			render();
			
			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}
	}
	
	private double getTime() {
		return System.nanoTime()/(Math.pow(10, 9));
	}
	
	private void sync(double loopStartTime) {
		float loopSlot = 1f / 50;
		double endTime = loopStartTime + loopSlot;
		while(System.nanoTime() < endTime) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException ie) {}
		}
	}
	
	private void gameUpdate() {
		
		glfwPollEvents();
		
		Tank tank1 = Tank.getTankList().get(0);
		//Tank tank2 = Tank.getTankList().get(1);
		
		//Tank 1's movement controls
		if (glfwGetKey(window.getHandle(), GLFW_KEY_UP) == 1) {
			tank1.moveForward();
		}
		if (glfwGetKey(window.getHandle(), GLFW_KEY_DOWN) == 1) {
			tank1.moveBackward();
		}
		if (glfwGetKey(window.getHandle(), GLFW_KEY_RIGHT) == 1) {
			tank1.turnRight();
		}
		if (glfwGetKey(window.getHandle(), GLFW_KEY_LEFT) == 1) {
			tank1.turnLeft();
		}
		
		//Tank 2's movement controls
//		if (glfwGetKey(window.getHandle(), GLFW_KEY_E) == 1) {
//			tank2.moveForward();
//		}
//		if (glfwGetKey(window.getHandle(), GLFW_KEY_D) == 1) {
//			tank2.moveBackward();
//		}
//		if (glfwGetKey(window.getHandle(), GLFW_KEY_F) == 1) {
//			tank2.turnRight();
//		}
//		if (glfwGetKey(window.getHandle(), GLFW_KEY_S) == 1) {
//			tank2.turnLeft();
//		}
//		
//		glfwSetKeyCallback(window.getHandle(), (window, key, scancode, action, mods) -> {
//			if ( key == GLFW_KEY_M && action == GLFW_PRESS)
//				tank1.shoot();
//			if (key == GLFW_KEY_Q && action == GLFW_PRESS) {
//				tank2.shoot();
//			}
//			
//		});

	}
	
	private void render() {
		renderer.renderMesh(tank1, camera);
		renderer.renderMesh(object, camera);
		window.swapColourBuffers();
	}
	
	private void  menuUpdate() {
		
		
	}
	
	private void getInput() {
		
		
	}
	
	//Destroys objects, frees callbacks, and wraps up the game window upon closure
	private void close() {
		window.freeCallbacks();
		window.destroy();
		//testMesh.destroy();
		shader.destroy();
		
		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	public static void main(String[] args) {
		new TankTrouble2().run();
	}

}