package frontend.interfaces;

import java.io.Serializable;

import frontend.classes.view.Field;
import helper.Position;

public interface Tool extends Serializable{
	
	/**
	 * Handles mouse down events in the drawing view.
	 * 
	 * @param p Position of the mouse
	 */
	public void mouseDown(Position p);


	/**
	 * Handles mouse-drag events in the view.
	 * 
	 * @param p Position of the mouse
	 */
	public void mouseDrag(Position p);

	/**
	 * Handles mouse up in the view.
	 * 
	 * @param p Position of the mouse
	 */
	public void mouseUp(Position p);
	
	/**
	 * 
	 * @param p
	 */
	public void mouseOver(Position p);

	public void setField(Field f);

}
