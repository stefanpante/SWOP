package gui.message;

import gui.GUIElement;
import gui.Label;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

/**
 * A message which is displayed on the screen.
 */
public class Message extends GUIElement {

    /**
     * The label of the message
     */
    protected Label label;

    /**
     * The text which is displayed inside the message.
     */
    private String text;

    /**
     * Constructs a new message.
     *
     * @param width    The width of the message.
     * @param height   The height of the message.
     * @param position The position of the message
     * @param message  The String which is displayed
     * @param gui
     */
    public Message(float width, float height, PVector position, String message, PApplet gui) {
        super(width,height, position, gui);
        this.label = new Label(width, 25, position, "Message", gui);
        setText(message);
    }

    /**
     * Draws the message ( only when isVisible() == true )
     */
    @Override
    public void draw() {
        if(isVisible()){
            
            gui.fill(color);
            gui.stroke(0, 50);
            gui.rect(position.x, position.y, width, height);
            label.draw();
            gui.fill(0,96);
    		gui.textAlign(PConstants.CENTER, PConstants.CENTER);
    		gui.text(text, position.x, position.y - 3, width, height);
        }

    }

    /**
     * Returns the label of the message
     */
    public Label getLabel() {
        return this.label;
    }

    /***
     * Returns the text of the message
     */
    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;

    }



}
