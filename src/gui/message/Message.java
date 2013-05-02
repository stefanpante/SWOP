package gui.message;

import gui.GUIElement;
import gui.Label;
import processing.core.PApplet;
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
        super(height, width, position, gui);
        Label label = new Label(width, 20, position, "Message", gui);
        setText(message);
    }

    /**
     * Draws the message ( only when isVisible() == true )
     */
    @Override
    public void draw() {
        if(isVisible()){
            label.draw();
        }

    }

    /**
     * Returns the label of the message
     * @return
     */
    public Label getLabel() {
        return this.label;
    }

    /***
     * Returns the text of the message
     * @return
     */
    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;

    }



}
