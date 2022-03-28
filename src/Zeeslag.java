import java.awt.*;

public class Zeeslag {
    public static void main(String[] args) {
        Frame frame = new Frame();
        GameScreen myDrawing = new GameScreen();
        MouseListeners mouseListeners = new MouseListeners(myDrawing);

        frame.addMouseMotionListener(mouseListeners.getMotionListener());
        frame.addMouseListener(mouseListeners.getActionListener());
        frame.addMouseWheelListener(mouseListeners.getWheelListener());

        myDrawing.startPlacement();

        frame.add(myDrawing);

        int frameWidth = 1500;

        int frameHeight = 750;

        frame.setSize(frameWidth, frameHeight);

        frame.setVisible(true);
    }
}
