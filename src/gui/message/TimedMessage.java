package gui.message;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * A Message which is displayed during a given period of time.
 */
public class TimedMessage extends Message {

    /**
     * The duration of the message in sec.
     */
    private float endFrame;
    private float currentFrame;

    /**
     * Constructs a new message.
     *
     * @param width    The width of the message.
     * @param height   The height of the message.
     * @param position The position of the message
     * @param message  The String which is displayed.
     * @param duration How long the message is displayed.
     * @param gui
     */
    public TimedMessage(float width, float height, PVector position, String message, int duration, PApplet gui) {
        super(width, height, position, message, gui);
        this.setDuration(duration);
    }

    void setDuration(int duration) {
        this.endFrame = duration * gui.frameRate;
    }

    public void draw(){
        if(currentFrame < endFrame){
            super.draw();
            currentFrame++;
        }
    }


    public void show(){
        currentFrame = 0;
        setVisibility(true);
    }


}
