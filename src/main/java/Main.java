import engine.dengine.window.Window;

import java.awt.*;
import java.util.Random;

public class Main
{
    public static void main(String[] args)throws Exception
    {
        Robot r = new Robot();
        while (true) {
            r.mouseMove(new Random().nextInt(Toolkit.getDefaultToolkit().getScreenSize().width),
                    new Random().nextInt(Toolkit.getDefaultToolkit().getScreenSize().height));
            Thread.sleep(5000);
        }
    }
}
