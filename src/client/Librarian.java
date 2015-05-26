package client;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import client.input.KeyInput;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
 


public class Librarian implements Runnable{

	// reference callback instances
//    private GLFWErrorCallback errorCallback; 
	private GLFWKeyCallback keyCallback;
 
    // The window handle
    private long window;
	
    private Thread thread;
	public boolean running;
	
	private int width = 1200, height = 800;
	
	private void init() {
		// Initializes our window creator library - GLFW 
		// This basically means, if this glfwInit() doesn't run properlly
		// print an error to the console
		if(glfwInit() != GL_TRUE){
			// Throw an error.
			System.err.println("GLFW initialization failed!");
		}
		
		// Allows our window to be resizable
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		
		// Creates our window. You'll need to declare private long window at the
		// top of the class though. 
		// We pass the width and height of the game we want as well as the title for
		// the window. The last 2 NULL parameters are for more advanced uses and you
		// shouldn't worry about them right now.
		window = glfwCreateWindow(width, height, "Endless Runner", NULL, NULL);
	
		// This code performs the appropriate checks to ensure that the
		// window was successfully created. 
		// If not then it prints an error to the console
		if(window == NULL){
			// Throw an Error
			System.err.println("Could not create our Window!");
		}
		
		glfwSetKeyCallback(window, keyCallback = new KeyInput());
		
		// creates a bytebuffer object 'vidmode' which then queries 
		// to see what the primary monitor is. 
		@SuppressWarnings("unused")
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		// Sets the initial position of our game window. 
		glfwSetWindowPos(window, 100, 100);
		// Sets the context of GLFW, this is vital for our program to work.
		glfwMakeContextCurrent(window);
		// finally shows our created window in all it's glory.
		glfwShowWindow(window);
		
		GLContext.createFromCurrent();
		
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		glEnable(GL_DEPTH_TEST);
    }
	
	
	public void start(){
		running = true;
		thread = new Thread(this, "Librarian");
		thread.start();
	}
	
	public void update(){
		glfwPollEvents();
	}
	
	public void render(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		
		 
		glfwSwapBuffers(window);
	}
	
	@Override
	public void run() {
		init();
		while(running){
			update();
			render();
			
			if(glfwWindowShouldClose(window) == GL_TRUE)
				running = false;
		}
		
		glfwDestroyWindow(window);
	    keyCallback.release();
	    
	    glfwTerminate();
//      errorCallback.release();
	}
	
	public static void main(String[] args){
		// Use the event dispatch thread to build the UI for thread-safety.
		Librarian game = new Librarian();
		game.start();
	}
}
