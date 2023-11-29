package engine.dengine.window;

import org.lwjgl.glfw.GLFWFramebufferSizeCallback;

/**
 * @author Anton Schoenfeld
 * @version 1.0
 * @since 1.0
 * <br>
 * <h2>{@link WindowResizeListener}</h2>
 * The {@link WindowResizeListener} is used to handle <b>GLFW window resize events</b>.
 */
public class WindowResizeListener extends GLFWFramebufferSizeCallback
{
    private static class SingletonHolder
    {
        private static final WindowResizeListener theResizeListener = new WindowResizeListener();
    }

    /**
     * Returns the {@link WindowResizeListener} instance.
     * @return the {@link WindowResizeListener} instance
     */
    protected static WindowResizeListener getInstance()
    {
        return SingletonHolder.theResizeListener;
    }

    private WindowResizeListener () {}

    @Override
    public void invoke(long window, int width, int height)
    {
        Window.getInstance().width = width;
        Window.getInstance().height = height;
    }
}