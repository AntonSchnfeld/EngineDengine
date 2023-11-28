package engine.dengine.window;

import engine.dengine.assets.AssetManager;
import engine.dengine.assets.Shader;
import engine.dengine.exceptions.ShaderAttachmentException;
import engine.dengine.exceptions.ShaderCompileException;
import engine.dengine.exceptions.ShaderLinkingException;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33C.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * @author Anton Schoenfeld
 * @version 1.0
 * @since 1.0
 * <br>
 * <h2>{@link Window}</h2>
 * <br>
 * The {@link Window} class serves as an abstraction of a <b>GLFW window</b>. It is a <b>singleton</b>, because
 * <b>EngineDengine</b> only supports a single window. To obtain the {@link Window} instance, use
 * {@link Window#getInstance()}.
 */
public class Window
{
    private static class SingletonHolder
    {
        private static final Window theWindow = new Window();
    }

    /**
     * Used to get the {@link Window} instance.
     * @return the instance
     */
    public static Window getInstance()
    {
        return SingletonHolder.theWindow;
    }

    // The window handle
    private long window;
    private int width, height;
    private String title;

    private Window ()
    {
        title = "EngineDengine application";

        // Set up an error callback which will print errors to System.err
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        init();
    }

    private void init ()
    {
        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Set up a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, KeyListener.getInstance());

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );

            width = pWidth.get();
            height = pHeight.get();
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }
    private void loop ()
    {
        System.out.println("Hello EngineDengine 1.0!");
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window))
        {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }

        System.out.println("\rGoodbye EngineDengine 1.0!");
    }

    /**
     * Returns the window width.
     * @return the window width
     */
    public int getWidth ()
    {
        return width;
    }

    /**
     * Returns the window height.
     * @return the window height
     */
    public int getHeight ()
    {
        return height;
    }

    /**
     * Returns the window title.
     * @return the window title
     */
    public String getTitle ()
    {
        return title;
    }

    /**
     * Refreshes and sets the title of the window.
     * @param title the new window title
     */
    public void setTitle (String title)
    {
        glfwSetWindowTitle(window, title);
        this.title = title;
    }

    /**
     * Refreshes and sets the width of the window.
     * @param width the new window width
     */
    public void setWidth (int width)
    {
        glfwSetWindowSize(window, width, height);
        this.width = width;
    }

    /**
     * Refreshes and sets the width of the window.
     * @param height the new window width
     */
    public void setHeight (int height)
    {
        glfwSetWindowSize(window, width, height);
        this.height = height;
    }

    /**
     * Refreshes and toggles the visibility of the window.
     * @param visible the new window visibility
     */
    public void setVisible (boolean visible)
    {
        glfwSetWindowAttrib(window, GLFW_VISIBLE, visible ? GLFW_TRUE : GLFW_FALSE);
    }

    /**
     * Refreshes and sets the window position.
     * @param x the new window x coordinate
     * @param y the new window y coordinate
     */
    public void setWindowPosition (int x, int y)
    {
        glfwSetWindowPos(window, x, y);
    }

    /**
     * Refreshes and toggles the decoration of the window.
     * @param decorated the new window decoration
     */
    public void setWindowDecorated (boolean decorated)
    {
        glfwSetWindowAttrib(window, GLFW_DECORATED, decorated ? GLFW_TRUE : GLFW_FALSE);
    }

    /**
     *
     * @param icon will be used for {@link GLFWImage.Buffer}
     */
    public void setWindowIcon (ByteBuffer icon)
    {
        glfwSetWindowIcon(window, new GLFWImage.Buffer(icon));
    }

    /**
     * <b>Runs</b> this {@link Window} instance. This method starts the internal <b>rendering loop</b> of the window
     * and disposes ({@link Window#dispose()}) of it when that loop is finished. Note that calls to this window have
     * to be made in setters or getters from the <b>rendering loop</b> or from another {@link Thread}.
     */
    public void run ()
    {
        loop();
        dispose();
    }

    /**
     * Disposes of this {@link Window} instance. The instances methods and any <b>OpenGL rendering</b> will not
     * work when called after calling this method.
     */
    public void dispose ()
    {
        // Free the memory
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and the free the error callback
        glfwTerminate();
        var c = glfwSetErrorCallback(null);
        if (c != null) c.free();
    }
}
