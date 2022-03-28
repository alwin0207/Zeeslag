import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class MouseListeners {
    // Fields:
    public GameScreen gameScreenReference;
    public boolean throttled = false;
    public Timer throttleTimer = new Timer();

    //Fields-Mouselisteners:
    MouseMotionListener motionListener = new MouseMotionListener() {
        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if(!throttled) {
                gameScreenReference.setMouseX(e.getX());
                gameScreenReference.setMouseY(e.getY());
                gameScreenReference.updateMouseCoordinates();
                gameScreenReference.hoverUpdate();
                throttled = true;
            }
        }
    };

    MouseWheelListener wheelListener = new MouseWheelListener() {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (!throttled){
                gameScreenReference.toggleHorizontal();
                throttled = true;
            }
        }
    };

    MouseListener actionListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            gameScreenReference.action(e.getX(), e.getY());
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    };

    // Constructor:
    public MouseListeners(GameScreen gameScreen){
        this.gameScreenReference = gameScreen;

        this.throttleTimer.schedule(new TimerTask() {
            @Override
            public void run() {
               resetThrottled();
            }
        }, 0, 10);
    }

    // Methods:
    public void resetThrottled(){
        this.throttled = false;
    }

    // Getters:

    public MouseListener getActionListener() {
        return actionListener;
    }

    public MouseMotionListener getMotionListener() {
        return motionListener;
    }

    public MouseWheelListener getWheelListener() {
        return wheelListener;
    }
}
