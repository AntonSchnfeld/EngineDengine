package engine.dengine.window;

import org.lwjgl.glfw.GLFWFramebufferSizeCallback;

public class WindowResizeListener extends GLFWFramebufferSizeCallback
{
    @Override
    public void invoke(long window, int width, int height)
    {
        Window.getInstance().setWidth(width);
        Window.getInstance().setHeight(height);
    }
}